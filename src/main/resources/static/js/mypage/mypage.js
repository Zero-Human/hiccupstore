function purchaseconfirm(){

    let confirmed = confirm('구매확정하시면 되돌릴수없습니다.');

            if(confirmed){
                    let orderid = $('.purchaseconfirm').attr('data_orderid');
                    let val = $('.purchaseconfirm').attr('data_value');

                    if( val != '배송완료'){
                        alert('구매확정은 배송완료단계에서만 할수있습니다.');
                        return;
                    }
                    let data = JSON.stringify({orderid: orderid});
                    console.log(orderid);

                    $.ajax({
                   	url : "/mypage/purchaseconfirm",
                   	type : "post",
                   	data : data,
                   	contentType: "application/json",
                   	success : function(result){
                        alert("구매확정 되었습니다.");
                   	},
                   	error : function(){
                   		alert("잘못된 요청입니다. 다시 시도해주세요.");
                   	    }
                    })
            }

}