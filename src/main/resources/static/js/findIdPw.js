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