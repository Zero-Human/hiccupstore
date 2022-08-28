'use strict';

//각 버튼마다 이벤트를 건다.

function nextpage(){

oEditors.getById["editorTxt"].exec("UPDATE_CONTENTS_FIELD", []);
var value = document.getElementById("editorTxt").value;

$("textarea[name='boardcontent']").val(value);
console.log(value);
document.information.submit();

}

function prevpage(){
    location.href='/managerpage/managerpage1vs1';
}