function chkAll(all){
	let chk = document.frmCart.chk;
	for(let i=1; i < chk.length; i++){
		chk[i].checked = all.checked;
	}
}
function unchkAll(unChk) {
	if(document.getElementById('all').checked )
		document.frmCart.all.checked = unChk.checked;
}

function getSelectedValues(){

		let chk = document.frmCart.chk; //chk 컨트롤 배열에서 선택된 체크박스의 값들을
		let chkArr = '';
		console.log(chk.length);
		
		for(let i = 1 ; i < chk.length; i ++){
			if(chk[i].checked){
				chkArr += (chk[i].value);
				chkArr += (',');
			}
			
		}
		chkArr = chkArr.slice(0, chkArr.length - 1);
		console.log(chkArr);
		return chkArr;
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

function chkBuy(){
	//사용자가 선택한 상품(들)을 구매히는 함수
	let ocidx = getSelectedValues();
	document.frmCart.chk[0].setAttribute('value',ocidx)
	if(ocidx == "") alert("구매할 상품을 선택하세요.");
	else			document.frmCart.submit();
	//
}
function allBuy(){
	let ocidx = getSelectedValues();
	document.frmCart.chk[0].setAttribute('value',ocidx)
	document.frmCart.submit();
}