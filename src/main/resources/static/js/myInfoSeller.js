let isPassOk  = false;
let isEmailOk = false;
let isHpOk = false;
let isRepresentOk    = false;
let isCompanyOk   = false;
let isBizRegNumOk = false;
let isReportNumOk = false;
let isTelOk   = false;
let isFaxOk   = false;

const rePass  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{5,16}$/;
const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const reHp = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;
const reRepresent  = /^[가-힣]{2,10}$/
const reCompany = /^[a-zA-Z가-힣0-9]{1,12}$/;                      // 회사이름
const reBizRegNum = /^[0-9]{3}-[0-9]{2}-[0-9]{5}$/;               // 사업자등록번호
const reReportNum = /^[0-9]{4}-[가-힣]{2,6}-[0-9]{4,5}$/;         // 통신판매업신고 번호
const reTel   = /^(0[2-8][0-5]?)-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})$/;
const reFax   = /^\d{2,3}-\d{3,4}-\d{4}$/;

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

    // 회사이름 유효성 검사
    const inputCompany = document.getElementsByName('company')[0];
    const spanMsgCompany = document.getElementsByClassName('msgCompany')[0];

    inputCompany.onkeyup = function () {
        const value = inputCompany.value;

        if (!value.match(reCompany)) {
            spanMsgCompany.innerText = '올바른 회사이름 형식이 아닙니다.';
            spanMsgCompany.style.color = 'red';
            isCompanyOk = false;
        } else {
            spanMsgCompany.innerText = '';
            isCompanyOk = true;
        }
    };

    // 대표이름 유효성 검사
    const inputRepresent = document.getElementsByName('represent')[0];
    const spanMsgRepresent = document.getElementsByClassName('msgRepresent')[0];

    inputRepresent.onkeyup = function () {
        const value = inputRepresent.value;

        if (!value.match(reRepresent)) {
            spanMsgRepresent.innerText = '올바른 이름 형식이 아닙니다.';
            spanMsgRepresent.style.color = 'red';
            isRepresentOk = false;
        } else {
            spanMsgRepresent.innerText = '';
            isRepresentOk = true;
        }
    };

    // 사업자등록번호 유효성 검사
    const inputRegNum = document.getElementsByName('regnum')[0];
    const spanMsgCorp = document.getElementsByClassName('msgCorp')[0];

    inputRegNum.onkeyup = function () {
        const regnum = inputRegNum.value.trim();

        if (!regnum.match(reBizRegNum)) {
            spanMsgCorp.innerText = '올바른 사업자등록번호 형식이 아닙니다.';
            spanMsgCorp.style.color = 'red';
            isBizRegNumOk = false;
        } else {
            spanMsgCorp.innerText = '';
            isBizRegNumOk = true;

            // 중복 확인
            fetch(`/lotteon/member/checkRegnum?regnum=${regnum}`)
                .then(response => response.json())
                .then(data => {
                    if (data.result > 0) {
                        spanMsgCorp.innerText = '이미 사용 중인 사업자등록번호입니다.';
                        spanMsgCorp.style.color = 'red';
                        isBizRegNumOk = false;
                    } else {
                        spanMsgCorp.innerText = '사용 가능한 사업자등록번호입니다.';
                        spanMsgCorp.style.color = 'green';
                        isBizRegNumOk = true;
                    }
                });
        }
    };

    // 통신판매업신고 번호 유효성 검사
    const inputReportNum = document.getElementsByName('reportnum')[0];
    const spanMsgOnline = document.getElementsByClassName('msgOnline')[0];

    inputReportNum.onkeyup = function () {
        const reportnum = inputReportNum.value.trim();

        if (!reportnum.match(reReportNum)) {
            spanMsgOnline.innerText = '올바른 통신판매업신고 번호 형식이 아닙니다.';
            spanMsgOnline.style.color = 'red';
            isReportNumOk = false;
        } else {
            spanMsgOnline.innerText = '';
            isReportNumOk = true;

            // 중복 확인
            fetch(`/lotteon/member/checkReportnum?reportnum=${reportnum}`)
                .then(response => response.json())
                .then(data => {
                    if (data.result > 0) {
                        spanMsgOnline.innerText = '이미 사용 중인 통신판매업신고 번호입니다.';
                        spanMsgOnline.style.color = 'red';
                        isReportNumOk = false;
                    } else {
                        spanMsgOnline.innerText = '사용 가능한 통신판매업신고 번호입니다.';
                        spanMsgOnline.style.color = 'green';
                        isReportNumOk = true;
                    }
                });
        }
    };

    // 전화번호 유효성 검사
    const inputCoHp = document.getElementsByName('cohp')[0];
    const spanMsgTel = document.getElementsByClassName('msgTel')[0];

    inputCoHp.onkeyup = function () {
        const cohp = inputCoHp.value.trim();

        if (!cohp.match(reTel)) {
            spanMsgTel.innerText = '올바른 전화번호 형식이 아닙니다.';
            spanMsgTel.style.color = 'red';
            isTelOk = false;
        } else {
            spanMsgTel.innerText = '';
            isTelOk = true;

            // 중복 확인
            fetch(`/lotteon/member/checkCohp?cohp=${cohp}`)
                .then(response => response.json())
                .then(data => {
                    if (data.result > 0) {
                        spanMsgTel.innerText = '이미 사용 중인 전화번호입니다.';
                        spanMsgTel.style.color = 'red';
                        isTelOk = false;
                    } else {
                        spanMsgTel.innerText = '사용 가능한 전화번호입니다.';
                        spanMsgTel.style.color = 'green';
                        isTelOk = true;
                    }
                });
        }
    };

    // 팩스 번호 유효성 검사
    const inputFax = document.getElementsByName('fax')[0];
    const spanMsgFax = document.getElementsByClassName('msgFax')[0];

    inputFax.onkeyup = function () {
        const fax = inputFax.value.trim();

        if (!fax.match(reFax)) {
            spanMsgFax.innerText = '올바른 팩스 번호 형식이 아닙니다.';
            spanMsgFax.style.color = 'red';
            isFaxOk = false;
        } else {
            spanMsgFax.innerText = '';
            isFaxOk = true;

            // 중복 확인
            fetch(`/lotteon/member/checkFax?fax=${fax}`)
                .then(response => response.json())
                .then(data => {
                    if (data.result > 0) {
                        spanMsgFax.innerText = '이미 사용 중인 팩스 번호입니다.';
                        spanMsgFax.style.color = 'red';
                        isFaxOk = false;
                    } else {
                        spanMsgFax.innerText = '사용 가능한 팩스 번호입니다.';
                        spanMsgFax.style.color = 'green';
                        isFaxOk = true;
                    }
                });
        }
    };

}

