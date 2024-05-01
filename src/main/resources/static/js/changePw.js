let isPassOk  = false;
const rePass  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{5,16}$/;

window.onload = function (){
    const inputPasses = document.getElementsByClassName('inputPass');
    const resultPass = document.getElementById('resultPass');
    inputPasses[1].addEventListener('focusout', ()=>{

        if(inputPasses[0].value == inputPasses[1].value){

            if(!inputPasses[0].value.match(rePass)){
                showInputInvalid(inputPasses);
                showResultInvalid(resultPass, '비밀번호 형식에 맞지 않습니다.');
                isPassOk = false;
            }else{
                showInputValid(inputPasses);
                showResultValid(resultPass, '사용 가능한 비밀번호 입니다.');
                isPassOk = true;
            }

        }else{
            showInputInvalid(inputPasses);
            showResultInvalid(resultPass, '비밀번호가 일치하지 않습니다.');
            isPassOk = false;
        }
    });

    document.formFindPassChange.onsubmit = async function (e){
        e.preventDefault();
        const uidValue = document.querySelector('#inputUid').value;
        const passValue = document.querySelector('#inputPass1').value;
        console.log(uidValue);
        console.log(passValue);

        if (!isPassOk){
            alert('비밀번호가 유효하지 않습니다.');
        }else {
            const jsonData = {
                "uid": uidValue,
                "pass": passValue
            };
            console.log(jsonData);

            try {
                const data = await fetchPut(`/lotteon/member/updatePass`, jsonData);

                console.log(data);
                alert("비밀번호 변경이 완료되었습니다.");

                window.location.href = `/lotteon/member/login`;

            }catch (error){
                console.error('비밀번호 변경 요청 실패', error);
            }

        }

    }

}