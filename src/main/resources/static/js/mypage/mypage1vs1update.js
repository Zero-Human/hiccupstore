'use strict';

function prevpage(){
    location.href = '/mypage/mypage1vs1'
}

function updatepage(){

//    let csrfHeader = $('meta[name=_csrf_header]').attr('content');
//    let csrfToken = $('meta[name=_csrf]').attr('content');
//
//      var httpRequest = new XMLHttpRequest();
//        httpRequest.onreadystatechange = function() {
//            if(httpRequest.readyState == XMLHttpRequest.DONE && httpRequest.status == 200) {
//                document.getElementById("text").innerHTML = httpRequest.responseText;
//            }
//        };
//    httpRequest.open("POST", "URL", true);
//    httpRequest.setRequestHeader(csrfHeader,csrfToken);
//    httpRequest.setRequestHeader("x-Requested-With","XMLHttpRequests");
//    httpRequest.send();
    document.information.submit();

}

function setThumbnail(event) {

document.querySelector("div#image_container").innerHTML = '';

    for (var image of event.target.files) {
          var reader = new FileReader();
          reader.onload = function(event) {
            var img = document.createElement("img");
            img.setAttribute("src", event.target.result);
            document.querySelector("div#image_container").appendChild(img);
          };
          console.log(image);
          reader.readAsDataURL(image);
        }
}