window.onload = function (){
    const setHp = document.getElementById('btnChangeHp');
    const setZip = document.getElementById('editAddrBtn');
    const btnWithdraw = document.getElementById('btnWithdraw');


    setHp.onclick = function (){
        editModal('번호');
        document.getElementById('editInput').value = '';
        hideResultInvalid();

        saveButton.onclick = async function () {
            const value = document.getElementById('editInput').value;
            const type = 'hp';
            console.log(value);
            const reHp    = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

            if(reHp.test(value)) {
                // 이름이 유효한 경우 중복 체크를 실행합니다.

                const jsonData = {
                    "uid" : uid,
                    "value" : value
                };
                console.log("jsonData...! " + JSON.stringify(jsonData));

                // 중복 확인을 위해 서버에 요청을 보냅니다.
                const data = await fetchGet(`/lotteon/user/${type}/${value}`);

                if(data.result > 0) {
                    // 중복이 있는 경우 사용자에게 알립니다.
                    showResultInvalid(resultComment, '이미 사용중인 번호입니다.');
                }else{
                    // 중복이 없는 경우 서버로 데이터를 업데이트하는 요청을 보냅니다.
                    const updateResponse = await fetch(`/lotteon/my/setting/${type}/${value}/${uid}`);
                    const updateData = await updateResponse.json();
                    console.log(updateData);
                    document.getElementById('userHp').innerText = value;

                    closeEditModal();
                }

            } else{
                // 입력값이 유효하지 않은 경우 사용자에게 알립니다.
                showResultInvalid(resultComment, '바른 형식으로 입력해주세요');
                document.getElementById('editInput').addEventListener('click', function(){
                    // 결과 메세지가 표시되어 있다면 숨깁니다.
                    hideResultInvalid();
                });
            }
        }
    }

    setZip.onclick = function (){
        editAddrModal();
    }

    const editAddrBtn = document.getElementById('editAddrBtn');
    editAddrBtn.onclick = function () {
        editAddrModal();

        const inputZip = document.getElementById('inputZip');
        const inputAddr1 = document.getElementById('inputAddr1');
        const inputAddr2 = document.getElementById('inputAddr2');
        const saveAddrBtn = document.getElementById('saveAddrBtn');

        // 저장 버튼 클릭 시
        saveAddrBtn.onclick = async function () {
            const zipValue = inputZip.value;
            const addr1Value = inputAddr1.value;
            const addr2Value = inputAddr2.value;
            const jsonData = {
                "uid"   : uid,
                "zip" : zipValue,
                "addr1" : addr1Value,
                "addr2" : addr2Value
            };

            try {
                const data = await fetchPut(`/farmstory/updateZip`, jsonData);
                document.getElementById('userZip').innerText = zipValue;
                document.getElementById('userAddr1').innerText = addr1Value;
                document.getElementById('userAddr2').innerText = addr2Value;
                closeEditZipModal();
            } catch (error) {
                console.error('주소 변경 요청 실패', error);
            }
        }
    }

    btnWithdraw.onclick = function (){
        confirmModal('회원 탈퇴를 클릭하면 모든 계정 정보가 삭제됩니다.\n정말 탈퇴하시겠습니까?');
        const btnOk = document.getElementById('btnOk');

        btnOk.onclick = async function (){
            try{
                const data = await fetchDelete(`/lotteon/mypage/${uid}`);
                console.log(data);
                closeconfirmModal();
                window.location.href = '/lotteon/user/login?success=400';

            }catch (error){
                console.error('탈퇴실패', error);
            }
        }
    }
}