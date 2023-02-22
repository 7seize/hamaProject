


function checkMem() {
	if(isReceive.checked){
		receiveName.value = orName;
		receivePhone.value = orPhone;
		receiveZip.value = orAddrNum;
		receiveAdd1.value = orAddr1;
		receiveAdd2.value = orAddr2;
	}
}

document.getElementById('receiveDate').value= new Date().toISOString().slice(0, 10);