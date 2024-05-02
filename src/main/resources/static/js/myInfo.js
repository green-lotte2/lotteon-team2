window.onload = function (){
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    // 우편번호 주소검색
    // 다음 우편번호 API 스크립트 상단 추가, postcode 함수 util.js 파일 추가
    const inputZip = document.getElementById('findZip');
    inputZip.onclick = function (){
        alert('click!!!');
        postcode();
    }
}