let isPassOk  = false;
let isEmailOk = false;
let isHpOk = false;

const rePass  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{5,16}$/;
const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const reHp = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

window.onload = function (){

    // 비밀번호 유효성 검사
    const inputPass = document.getElementsByName('pass1')[0];
    const inputPass2 = document.getElementsByName('pass2')[0];
    const spanMsgPass = document.getElementsByClassName('msgPass1')[0];

    inputPass.onkeyup = function () {
        const pass1 = inputPass.value;

        if (!pass1.match(rePass)) {
            spanMsgPass.innerText = '비밀번호 형식이 맞지 않습니다.';
            spanMsgPass.style.color = 'red';
            isPassOk = false;
        } else {
            spanMsgPass.innerText = '';
            isPassOk = true;
        }
    };

    // 비밀번호 확인란 유효성 검사
    inputPass2.onkeyup = function () {
        const pass1 = inputPass.value;
        const pass2 = inputPass2.value;
        const spanMsgPass2 = document.querySelector('.msgPass2');

        if (pass1 !== pass2) {
            spanMsgPass2.innerText = '비밀번호가 일치하지 않습니다.';
            spanMsgPass2.style.color = 'red';
            isPassOk = false;
        } else {
            spanMsgPass2.innerText = '비밀번호가 일치합니다.';
            spanMsgPass2.style.color = 'green';
            isPassOk = true;
        }
    };


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

        fetch(`/lotteon/member/checkEmail/${inputEmail.value}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if (!data.result) {
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
                document.getElementById('zip').value = data.zonecode;
                document.getElementById("addr1").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("addr2").focus();
            }
        }).open();
    }

    findZip.onclick = function () {
        postcode();
    }

    // 새창으로 열기
    function open1(){
        window.open(`/lotteon/mypage/formMyinfoPassChange`, "_blank");
    }

}

