function chkAll(all){
	let chk = document.frmCart.chk;
	for(let i=0; i<chk.length; i++){
		chk[i].checked = all.checked;
	}
}

function getSelectedValues(){

		let chk = document.frmCart.chk; //chk 컨트롤 배열에서 선택된 체크박스의 값들을
		let chkArr = [];
		for(let i=0; i<chk.length; i++){ //히든객체로 하나 넣어놨기에 1부터 돌림 히든돌리지않게
			if (chk[i].checked) chkArr.add(i);
		}
			return chkArr.join();
	}

function cartDel(ocidx){
    //장바구니 내 특정 상품을 삭제하는 함수
    if(confirm("정말 삭제하시겠습니까?")){
        $.ajax({
            type : "POST",
            url : "/hamaProject/cart_proc_del",
            data : {"ocidx" : ocidx},
            success : function(chkRs){
                if(chkRs==0){
                    alert("상품 삭제에 실패했습니다. \n 다시 시도하세요");
                }
                location.reload();
            },
            error : function(request, status, error ) {   // 오류가 발생했을 때 호출된다. 
            	console.log("오류");
            }
        }); 
    }
}


function cartUp(ocidx, cnt){
	//장바구니 특정 상품의 옵션 및 수량을 변경하는 함수

	$.ajax({
		type : "POST",
		url : "/hamaProject/cart_proc_up",
		data : {"ocidx" : ocidx, "cnt" : cnt},
		success : function(chkRs){
			if(chkRs==0){
				alert("상품 변경에 실패했습니다. \n 다시 시도하세요");
				return;
			}
			location.reload(); //새로고침 
		}
	}); 
}

