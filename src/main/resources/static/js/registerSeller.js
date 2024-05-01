//유효성 검사에 사용할 상태변수
let isUidOk   = false;
let isPassOk  = false;
let isNameOk  = false;
let isEmailOk = false;
let isHpOk    = false;
let isRepresentOk    = false;
let isCompanyOk   = false;
let isBizRegNumOk = false;
let isReportNumOk = false;
let isTelOk   = false;
let isFaxOk   = false;

// 유효성 검사에 사용할 정규표현식
const reUid   = /^[a-z]+[a-z0-9]{4,19}$/g;
const rePass  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{5,16}$/;
const reName  = /^[가-힣]{2,10}$/
const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const reHp    = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;
const reRepresent  = /^[가-힣]{2,10}$/
const reCompany = /^[a-zA-Z가-힣0-9]{1,12}$/;                      // 회사이름
const reBizRegNum = /^[0-9]{3}-[0-9]{2}-[0-9]{5}$/;               // 사업자등록번호
const reReportNum = /^[0-9]{4}-[가-힣]{2,6}-[0-9]{4,5}$/;         // 통신판매업신고 번호
const reTel   = /^(0[2-8][0-5]?)-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})$/;
const reFax   = /^\d{2,3}-\d{3,4}-\d{4}$/;

// 폼 유효성 검사
window.onload = function (){
    const inputUid = document.getElementsByName('uid')[0];
    const spanMsgId = document.getElementsByClassName('msgSId')[0];
    const inputPass = document.getElementsByName('pass')[0];
    const inputPass2 = document.getElementsByName('pass2')[0];
    const spanMsgPass = document.getElementsByClassName('msgPass')[0];
    const inputName = document.getElementsByName('name')[0];
    const spanMsgName = document.getElementsByClassName('msgName')[0];
    const inputRepresent = document.getElementsByName('represent')[0];
    const spanMsgRepresent = document.getElementsByClassName('msgRepresent')[0];
    const inputCompany = document.getElementsByName('company')[0];
    const spanMsgCompany = document.getElementsByClassName('msgCompany')[0];
    const inputRegNum = document.getElementsByName('regnum')[0];
    const spanMsgCorp = document.getElementsByClassName('msgCorp')[0];
    const inputReportNum = document.getElementsByName('reportnum')[0];
    const spanMsgOnline = document.getElementsByClassName('msgOnline')[0];
    const inputCoHp = document.getElementsByName('cohp')[0];
    const spanMsgTel = document.getElementsByClassName('msgTel')[0];
    const inputFax = document.getElementsByName('fax')[0];
    const spanMsgFax = document.getElementsByClassName('msgFax')[0];
    const inputHp = document.getElementsByName('hp')[0];
    const spanMsgHp = document.getElementsByClassName('msgHp')[0];


    // 아이디 유효성 검사
    inputUid.onkeyup = function (){
        const value = inputUid.value;
        const size = value.length;

        if(size >= 4){
            if(!value.match(reUid)){
                spanMsgId.innerText = '아이디 형식이 맞지 않습니다.';
                spanMsgId.style.color = 'red';
                isUidOk = false;
                return;
            }

            // 입력한 아이디 서버 전송
            fetch(`/lotteon/member/checkUid?uid=${value}`)
                .then(response => response.json())
                .then(data => {
                    if(data.result > 0){
                        spanMsgId.innerText = '이미 존재하는 아이디 입니다.';
                        spanMsgId.style.color = 'red';
                        isUidOk = false;
                    } else {
                        spanMsgId.innerText = '사용 가능한 아이디 입니다.';
                        spanMsgId.style.color = 'green';
                        isUidOk = true;
                    }
                });
        }
    };


    // 비밀번호 유효성 검사
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



    // 이름 유효성 검사
    inputName.onkeyup = function () {
        const value = inputName.value;

        if (!value.match(reName)) {
            spanMsgName.innerText = '올바른 이름 형식이 아닙니다.';
            spanMsgName.style.color = 'red';
            isNameOk = false;
        } else {
            spanMsgName.innerText = '';
            isNameOk = true;
        }
    };

    // 이메일 유효성 검사
    const divEmailCode = document.getElementById('divEmailCode');
    const inputEmail = document.getElementById('inputEmail');
    const btn_email = document.getElementById('btn_email');
    const resultEmail = document.getElementById('result_email');

    btn_email.onclick = function () {

        const type = this.dataset.type;

        console.log("type : " + type);

        // 유효성 검사
        if (!inputEmail.value.match(reEmail)) {

            resultEmail.innerText = '이메일 형식이 맞지 않습니다.';
            resultEmail.style.color='red';
            isEmailOk = false;
            return;
        }

        console.log("fetchData1...1");

        fetch(`/lotteon/member/checkEmail/${inputEmail.value}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if(!data.result){
                    resultEmail.innerText='인증번호가 발송 되었습니다.';
                    resultEmail.style.color='green';
                    isEmailOk = true;
                }else{
                    resultEmail.innerText='이미 사용중인 이메일 입니다.';
                    resultEmail.style.color = 'red';
                    isEmailOk = false;
                }
            });
    }

    // 이메일 인증코드 확인
    const btnCheckEmailCode = document.getElementById('btnCheckEmailCode');
    const inputEmailCode = document.getElementById('inputEmailCode');
    const resultEmailCode = document.getElementById('resultEmailCode');

    btnCheckEmailCode.onclick = async function () {

        fetch(`/lotteon/member/checkEmailCode/${inputEmailCode.value}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);

                if (!data.result) {
                    resultEmailCode.innerText = '인증코드가 일치하지 않습니다.';
                    resultEmailCode.style.color='red';
                    isEmailOk = false;
                } else {
                    resultEmailCode.innerText = '이메일이 인증되었습니다.';
                    resultEmailCode.style.color='green';
                    isEmailOk = true;
                }
            });
    }

    // 전화번호 유효성 검사
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

    // 휴대폰 유효성 검사
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

    // 대표이름 유효성 검사
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

    // 회사이름 유효성 검사
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

    // 사업자등록번호 유효성 검사
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

    // 팩스 번호 유효성 검사
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

    // 우편번호 주소검색
    // 다음 우편번호 API 스크립트 상단 추가, postcode 함수 util.js 파일 추가
    const inputZip = document.getElementById('findZip');
    inputZip.onclick = function (){
        //alert('click!!!');
        postcode();
    }


    // 최종 폼 전송
    document.formRegisterSeller.onsubmit = function () {

        if (!isUidOk) {
            alert('아이디를 다시 확인하시기 바랍니다.');
            return false;
        }

        if (!isPassOk) {
            alert('비밀번호를 다시 확인하시기 바랍니다.');
            return false;
        }

        if (!isNameOk) {
            alert('이름을 다시 확인하시기 바랍니다.');
            return false;
        }

        if (!isRepresentOk) {
            alert('대표자 이름을 다시 확인하시기 바랍니다.');
            return false;
        }

        if (!isCompanyOk) {
            alert('회사이름을 다시 확인하시기 바랍니다.');
            return false;
        }

        if (!isBizRegNumOk) {
            alert('사업자등록번호를 다시 확인하시기 바랍니다.');
            return false;
        }

        if (!isReportNumOk) {
            alert('통신판매업신고 번호를 다시 확인하시기 바랍니다.');
            return false;
        }

        if (!isFaxOk) {
            alert('팩스 번호를 다시 확인하시기 바랍니다.');
            return false;
        }

        if (!isEmailOk) {
            alert('이메일을 다시 확인하시기 바랍니다.');
            return false;
        }

        if (!isHpOk) {
            alert('전화번호를 다시 확인하시기 바랍니다.');
            return false;
        }

        return true; // 폼 전송
    }


}

