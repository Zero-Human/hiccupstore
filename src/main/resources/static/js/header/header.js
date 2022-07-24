'use strict';


// function searchalcohol(event){
//     if (e.event.keyCode == 13) {
//         alert('안녕');
//     	movesearchform();
//     }
// }


document.getElementById("btn1").addEventListener("click", movesearchform);
//document.getElementById("btn1").addEventListener("keyup", (e) => searchalcohol(e));

function movesearchform(){
    document.searhname.submit();
}

function characterCheck(obj){
    let regExp = /[ \{\}\[\]\/?.,;:|\)*~`!^\-_+┼<>@\#$%&\'\"\\\(\=]/; 
    if( regExp.test(obj.value) ){
        alert("특수문자는 입력하실수 없습니다.");
        obj.value = obj.value.substring( 0 , obj.value.length - 1 ); // 입력한 특수문자 한자리 지움
        }
}