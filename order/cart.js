function chkAll(all){
	let chk = document.frmCart.chk;
	for(let i=0; i<chk.length; i++){
		chk[i].checked = all.checked;
	}
}

function cartDel(ocidx){
    //장바구니 내 특정 상품을 삭제하는 함수
        if(confirm("정말 삭제하시겠습니까?")){
            $.ajax({
                type : "POST",
                url : "/hamaproject/cart_proc_del",
                data : {"ocidx" : ocidx},
                success : function(chkRs){
                    if(chkRs==0){
                        alert("상품 삭제에 실패했습니다. \n 다시 시도하세요");
                    }
                    location.reload();
                }
            }); 
        }
    }