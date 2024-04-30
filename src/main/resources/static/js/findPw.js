window.onload = function () {

    let isEmailOk = false;

    const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

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
    const btnAreaNext = document.getElementById('btnAreaNext');

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

    btnAreaNext.onclick = async function (e){
        e.preventDefault();

        if(!isEmailOk){
            alertModal('이메일이 유효하지 않습니다.');
            return false;
        }

        // 폼 전송
        document.formFindPw.submit();

    }
}