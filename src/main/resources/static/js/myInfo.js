let isEmailOk = false;
let isHpOk = false;

const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const reHp = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

window.onload = function (){
    // 이메일 유효성 검사
    const auth = document.getElementById('auth');
    const inputEmail = document.getElementById('inputEmail');
    const btnEmailCode = document.getElementById('btnEmailCode');
    const resultEmail = document.getElementById('resultEmail');

    btnEmailCode.onclick = function () {

        const type = this.dataset.type;

        console.log("type : " + type);

        // 유효성 검사
        if (!inputEmail.value.match(reEmail)) {

            resultEmail.innerText = '이메일 형식이 맞지 않습니다.';
            resultEmail.style.color = 'red';
            isEmailOk = false;
            return;
        }

        console.log("fetchData1...1");

        fetch(`/lotteon/member/checkFindEmail/${inputEmail.value}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if (data.result) {
                    resultEmail.innerText = '인증번호가 발송 되었습니다.';
                    resultEmail.style.color = 'green';
                    isEmailOk = true;
                } else {
                    resultEmail.innerText = '이미 사용중인 이메일 입니다.';
                    resultEmail.style.color = 'red';
                    isEmailOk = false;
                }
            });
    }

    // 이메일 인증코드 확인
    const btnEmailAuth = document.getElementById('btnEmailAuth');
    const inputEmailCode = document.getElementById('inputEmailCode');
    const resultEmailCode = document.getElementById('resultEmailCode');

    btnEmailAuth.onclick = async function () {

        fetch(`/lotteon/member/checkEmailCode/${inputEmailCode.value}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);

                if (!data.result) {
                    resultEmailCode.innerText = '인증코드가 일치하지 않습니다.';
                    resultEmailCode.style.color = 'red';
                    isEmailOk = false;
                } else {
                    resultEmailCode.innerText = '이메일이 인증되었습니다.';
                    resultEmailCode.style.color = 'green';
                    isEmailOk = true;
                }
            });
    }

    // 휴대폰 유효성 검사
    const inputHp = document.getElementsByName('hp')[0];
    const spanMsgHp = document.getElementsByClassName('msgHp')[0];

    inputHp.onkeyup = function () {
        const hp = inputHp.value.trim();

        if (!hp.match(reHp)) {
            spanMsgHp.innerText = '올바른 휴대폰 번호 형식이 아닙니다.';
            spanMsgHp.style.color = 'red';
            isHpOk = false;
        } else {
            spanMsgHp.innerText = '';
            isHpOk = true;

            // 중복 확인
            fetch(`/lotteon/member/checkHp?hp=${hp}`)
                .then(response => response.json())
                .then(data => {
                    if (data.result > 0) {
                        spanMsgHp.innerText = '이미 사용 중인 휴대폰 번호입니다.';
                        spanMsgHp.style.color = 'red';
                        isHpOk = false;
                    } else {
                        spanMsgHp.innerText = '사용 가능한 휴대폰 번호입니다.';
                        spanMsgHp.style.color = 'green';
                        isHpOk = true;
                    }
                });
        }
    };

    //우편번호 검색
    function postcode() {
        new daum.Postcode({
            oncomplete: function (data) {
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('inputZip').value = data.zonecode;
                document.getElementById("inputAddr1").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("inputAddr2").focus();
            }
        }).open();
    }

    findZip.onclick = function () {
        postcode();
    }

    // 최종 폼 전송
    document.changeForm.onsubmit = function () {

        if (!isEmailOk) {
            alert('이메일을 다시 확인하시기 바랍니다.');
            return false;
        }

        if (!isHpOk) {
            alert('전화번호를 다시 확인하시기 바랍니다.');
            return false;
        }

        if (document.getElementById('inputZip').value === '') {
            alert('주소를 입력해주세요');
            return false;
        }

        return true; // 폼 전송
    }

    document.getElementById('btnWithdraw').addEventListener('click', function (e) {
        let result = confirm("회원 탈퇴를 희망하시는게 맞나요?");
        if (result) {
            document.getElementById('popWithdraw').classList.add('on'); // 비밀번호 입력 팝업

            document.querySelector('.btnPopWithdraw').addEventListener('click', function (e) {
                const uid = document.querySelector('input[name=uid]').value;
                const inputPass = document.querySelector('input[name=passCheck]').value;

                fetch('/lotteon/mypage/withdraw', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: 'uid=' + encodeURIComponent(uid) + '&inputPass=' + encodeURIComponent(inputPass)
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }
                        return response.text();
                    })
                    .then(data => {
                        if (data === "success") {
                            alert('탈퇴가 완료되었습니다.');
                            window.location.href = "/lotteon/member/logout";
                        } else {
                            alert("비밀번호가 일치하지 않습니다.");
                        }
                    })
                    .catch(error => {
                        console.error('There has been a problem with your fetch operation:', error);
                    });
            });
        }
    });
}