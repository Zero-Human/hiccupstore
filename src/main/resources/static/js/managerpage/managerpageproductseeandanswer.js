'use strict';



function prevpage(){
    location.href = '/managerpage/managerpageproduct'
}

function nextpage(){

oEditors.getById["editorTxt"].exec("UPDATE_CONTENTS_FIELD", []);
var value = document.getElementById("editorTxt").value;

$("textarea[name='boardcontent']").val(value);
console.log(value);
document.information.submit();

}