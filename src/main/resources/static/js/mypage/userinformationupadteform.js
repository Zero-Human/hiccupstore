'use strict';

const error = document.querySelectorAll('.error_next_box');

let t = new Date();
console.log(t.toISOString().substring(0,10));



    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample6_extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample6_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample6_postcode').value = data.zonecode;
                document.getElementById("sample6_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample6_detailAddress").focus();
            }
        }).open();
    }


/* email callback 함수 */

const form = document.querySelector("#email");
form.addEventListener("focusout", isEmailCorrect);


function isEmailCorrect() {

    var emailPattern = /^[a-z0-9A-Z]{2,}@[a-z0-9A-Z]{2,}\.[a-zA-Z0-9]{2,3}$/i;

    if(email.value === ""){ 
        error[2].innerHTML = "필수 정보입니다.";
        error[2].style.color = "red";
        error[2].style.fontSize = "12px";
        error[2].style.fontFamily = "Noto Sans KR,sans-serif";
        error[2].style.display = "block";
        error[2].style.marginTop = "15px";
        return false;
    } else if(!emailPattern.test(email.value)) {
        error[2].innerHTML = "사용 불가능한 이메일형식입니다.";
        error[2].style.color = "red";
        error[2].style.fontSize = "12px";
        error[2].style.fontFamily = "Noto Sans KR,sans-serif";
        error[2].style.display = "block";
        error[2].style.marginTop = "15px";
        return false;
    } else {
        /* 중복 이메일 검사 ajax도 가능하다.*/
        error[2].style.display = "none";
        return true;
    }

}

function changevalue(value){
    
    let storage = form.value;

    let emailcontentindex = storage.indexOf('@');

    if(emailcontentindex != -1){
        storage = storage.substring(0,emailcontentindex);
    }

    storage += value;

    form.value=storage;

    isEmailCorrect();
}

/* 전화번호 callback 함수 */

let mobile = document.querySelector('.input_num');

mobile.addEventListener("focusout", checkPhoneNum);

function checkPhoneNum() {
    var isPhoneNum = /([01]{2})([01679]{1})([0-9]{3,4})([0-9]{4})/;

    if(mobile.value === "") {
        error[3].innerHTML = "필수 정보입니다.";
        error[3].style.color = "red";
        error[3].style.fontSize = "12px";
        error[3].style.fontFamily = "Noto Sans KR,sans-serif";
        error[3].style.display = "block";
        error[3].style.marginTop = "15px";
        return false;
    } else if(!isPhoneNum.test(mobile.value)) {
        error[3].innerHTML = "전화번호 형식이 맞지 않습니다.";
        error[3].style.color = "red";
        error[3].style.fontSize = "12px";
        error[3].style.fontFamily = "Noto Sans KR,sans-serif";
        error[3].style.display = "block";
        error[3].style.marginTop = "15px";
        return false;
    } else {
        error[3].style.display = "none";
        return true;
    }

    
}


/* 주소 callback 함수 */


let postcode = document.querySelector('#sample6_postcode');
let address = document.querySelector('#sample6_address');
let detailaddress = document.querySelector('#sample6_detailAddress');

detailaddress.addEventListener("focusout", checkaddress);

function checkaddress() {

    var addressPattern = /[\{\}\[\]\/?.,;:|\)*~`!^\+┼<>@\#$%&\'\"\\\(\=]/;

    if(postcode.value === "" || address.value === "" || detailaddress.value === "") {
        error[4].innerHTML = "필수 정보입니다.";
        error[4].style.color = "red";
        error[4].style.fontSize = "12px";
        error[4].style.fontFamily = "Noto Sans KR,sans-serif";
        error[4].style.display = "block";
        error[4].style.marginTop = "10px";
        return false;

    } else if(addressPattern.test(detailaddress.value)) {

        error[4].innerHTML = "특수문자는 입력하실수 없습니다.";
        error[4].style.color = "red";
        error[4].style.fontSize = "12px";
        error[4].style.fontFamily = "Noto Sans KR,sans-serif";
        error[4].style.display = "block";
        error[4].style.marginTop = "10px";
        return false;

    }else {
        error[4].style.display = "none";
        return true;
    }


}


/* 전송 검증하기 */

let bar = document.querySelector("#header");

function updateinformation(){

    if(!isEmailCorrect()){
        form.focus();
        return false;
    } else if(!checkPhoneNum()){
        mobile.focus();
        return false;
    } else if(!checkaddress()){
        detailaddress.focus();
        return false;
    }
    document.information.submit();
}
