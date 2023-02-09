

function findAdd() {
    new daum.Postcode({
        oncomplete: function(data) {

            let roadAddr = data.roadAddress; 
            let extraRoadAddr = ''; 

            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            if(data.buildingName !== '' && data.apartment === 'Y'){
                extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }

            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            document.getElementById('ma_zip').value = data.zonecode;
            document.getElementById("ma_addr1").value = roadAddr;
            document.getElementById("ma_addr1").value = data.jibunAddress;

        }
    }).open();
}