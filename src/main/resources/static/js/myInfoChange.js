$(function() {
    // infoAccessCheck 비밀번호 확인
    $('.btnPassCheck').click(function(e){
        e.preventDefault();
        const inputPass = $('input[name=pass1]').val();
        console.log(inputPass);

        // 컨트롤러에서 비밀번호 일치여부를 판단해서
        // true를 반환하면 페이지 이동, false를 반환하면 alert
        $.ajax({
            url: contextPath + '/mypage/infoAccessCheck',
            type: 'POST',
            data: {
                uid: uid,
                inputPass: inputPass
            },
            success: function(data) {
                if (data === "true") {
                    window.location.href=contextPath + "/mypage/info";
                } else {
                    alert("비밀번호가 일치하지 않습니다.");
                }
            }
        });
    });

    // 비밀번호 변경 팝업창
    $('#btnPassChange').click(function(e) {
        e.preventDefault();

        // 새 창 열기
        let passChangeWindow = window.open("", "_blank", "width=800,height=400");


        // 새 창에 HTML 삽입
        passChangeWindow.document.write(`
        <html xmlns:th="http://www.thymeleaf.org">
        <head>

        <style>
        .popup > div {
            width: auto;
            height: auto;
            margin: 0 auto;
            background: #fff;
        }
        .popup > div > nav {
            width: 100%;
            height: auto;
            padding: 10px;
            box-sizing: border-box;
            background: #ef3f43;
            overflow: hidden;
        }
        .popup > div > nav > h1 {
            float: left;
            font-size: 18px;
            color: white;
        }
        .popup > div > nav > button {
            float: right;
            width: 30px;
            height: 30px;
            background-image: url('../images/ico_sprites.png');
            background-color: transparent;
            background-position: 227px -25px;
            border: none;
            font-size: 0;
            color: white;
            padding: 2px;
            border: 1px solid #999;
        }
        .popup > div > nav > button:hover {
            background-color: #e9e9e9;
            cursor: pointer;
        }
        .popup > div > section {
            padding: 16px;
            box-sizing: border-box;
        }
        .popup > div > section > table {
            width: 100%;
            border-top: 1px solid #e9e9e9;
            border-bottom: 1px solid #aaa;
            border-collapse: collapse;
            margin-bottom: 10px;
        }
        .popup > div > section > table th {
            text-align: right;
            width: 120px;
            padding-right: 8px;
        }
        .popup > div > section > table tr {
            border-bottom: 1px solid #f1f1f1;
        }
        
        .popup > div > section > table tr > td {
            padding: 10px;
            border-left: 1px solid #f1f1f1;
            box-sizing: border-box;
        }
        
        .popup > div > section > table tr > td:nth-child(2) {
            width: 360px;
        }
        .popup > div > section > div {
            text-align: center;
        }
        .popup > div > section table textarea {
            width: 100%;
            height: 160px;
            padding: 4px;
            box-sizing: border-box;
            border: 1px solid #f1f1f1;
            vertical-align: middle;
            resize: none;
        }
        .popup > div > section > p {
            padding: 26px;
            box-sizing: border-box;
            font-size: 12px;
        }
       
        .popup > div > section > div > #btnComplete {
            padding: 6px 12px;
            background: #ef3f43;
            border: none;
            box-sizing: border-box;
            color: white;
            margin: 10px auto;
            cursor: pointer;
        }
        #popPassChange > div > section > p {
            margin: 10px;
            padding: 0;
            font-size: 11px;
        }
        #popPassChange > div > section > table > tbody > tr:nth-child(1) > td > span {
            margin-top: 2px;
            display: block;
        }
        #popPassChange > div > section > table > tbody > tr:nth-child(2) > td > span {
            margin-top: 2px;
            display: block;
            width : 350px;
        }
        </style>
        <title>비밀번호 변경</title>
        
        </head>
        <body>
            <div id="popPassChange" class="popup">
                <div>
                    <nav>
                        <h1>비밀번호 변경</h1>
                        <button class="btnClose">X</button>
                    </nav>
                    <section>
                        <table>
                            <input type="hidden" name="uid" value="${$('input[name=uid]').val()}"/>
                            <tr>
                                <th>새 비밀번호</th>
                                <td>
                                    <input type="password" id="newPassword" name="pass1" placeholder="새 비밀번호 입력"/>
                                    <span class="msgPass1"></span>
                                </td>
                            </tr>
                            <tr>
                                <th>새 비밀번호 확인</th>
                                <td>
                                    <input type="password" id="confirmPassword" name="pass2" placeholder="새 비밀번호 입력 확인"/>
                                    <span class="msgPass2"></span>
                                </td>
                            </tr>
                        </table>
                        <p>
                            비밀번호를 변경해 주세요.<br>
                            영문, 숫자, 특수문자를 사용하여 8자 이상 입력해 주세요.
                        </p>
                        <div>
                            <button id="btnComplete">완료</button>
                        </div>
                    </section>
                </div>
            </div>
        </body>
        <script>
            console.log('123');
            let isPassOk  = false;
        
            const rePass  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$\`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{5,16}$/;
        
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
        
        </script>
        </html>
    `);

        // 변경 버튼 클릭 시
        passChangeWindow.document.getElementById("btnComplete").addEventListener("click", function(){
            const uid = passChangeWindow.document.querySelector("input[name=uid]").value;
            const newPassword = passChangeWindow.document.getElementById("newPassword").value;
            const confirmPassword = passChangeWindow.document.getElementById("confirmPassword").value;

            // 비밀번호 일치 여부 확인
            if(newPassword !== confirmPassword) {
                passChangeWindow.alert("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
                return;
            }

            $.ajax({
                url: contextPath + '/mypage/formMyinfoPassChange',
                type: 'POST',
                data: {
                    uid: uid,
                    inputPass: newPassword
                },
                success: function (data) {
                    if (data === "success") {
                        passChangeWindow.alert('비밀번호가 변경되었습니다.');
                        // 새 창 닫기
                        passChangeWindow.close();
                    } else {
                        passChangeWindow.alert("error");
                    }
                }
            });
        });
    });



    // 탈퇴하기
    $('#btnWithdraw').click(function(e){

        let result = confirm("회원 탈퇴를 희망하시는게 맞나요?");

        if (result) {
            $('#popWithdraw').addClass('on'); // 비밀번호 입력 팝업

            $('.btnPopWithdraw').click(function(e){

                const uid = $('input[name=uid]').val();
                const inputPass = $('input[name=passCheck]').val();

                $.ajax({
                    url: contextPath + '/mypage/withdraw',
                    type: 'POST',
                    data: {
                        uid: uid,
                        inputPass: inputPass
                    },
                    success: function(data) {
                        if (data === "success") {
                            alert('탈퇴가 완료되었습니다.');
                            window.location.href=contextPath + "/member/logout";
                        } else {
                            alert("비밀번호가 일치하지 않습니다.");
                        }
                    }
                });
            });
        }
    });

    // 회원정보 수정 완료
    $('#btnWithdrawFinal').click(function(e){

        let result = confirm("회원정보를 수정하시겠습니까?");

        if (result) {
            const email = $('input[name=email]').val();
            const hp = $('input[name=hp]').val();
            const zip = $('#zip').val();
            const addr1 = $('#addr1').val();
            const addr2 = $('#addr2').val();
            console.log("최종버튼 데이터 : "+email+", "+hp+", "+zip+", "+addr1+", "+addr2);

            let data = {
                uid: uid,
                email: email,
                hp: hp,
                zip: zip,
                addr1: addr1,
                addr2: addr2
            };
            let jsonData = JSON.stringify(data);

            $.ajax({
                url: contextPath + '/mypage/withdrawFinal',
                type: 'POST',
                data: jsonData,
                contentType: 'application/json',
                success: function(data) {
                    if (data === "success") {
                        alert('회원정보 수정이 완료되었습니다.');
                        window.location.href=contextPath + "/mypage/";
                    } else {
                        alert("error : 새로고침 후 다시 시도해주세요.");
                    }
                }
            });
        }
    });
});