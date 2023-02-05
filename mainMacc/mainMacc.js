//let elem = document.getElementById('addCart');
//imgEl.src = "./vimg/mc101_v.png";

let prolist = document.querySelectorAll('.pro_slider>div');
let selectMacc = document.querySelectorAll('.select_macc');
let maccNum = 10; //마카롱 박스 개수
let arr = new Array(maccNum).fill(0);

//마카롱 클릭시 해당 name값을 arr에 넣음 
for(let i = 0 ; i < prolist.length; i++){
    prolist[i].addEventListener("click",function(){
        if(arr.length < maccNum){
            arr[i] = (prolist[i].getAttribute('name'));
        }
        for(let j = 0; j < maccNum; j++){
            selectMacc[j].src="./vimg/"+arr[j]+"_v.png"
        }
        console.log(arr)
    })
}


for(let i = 0; i < maccNum; i++){
    selectMacc[i].addEventListener("click",function(){
        if(arr[i] !=undefined){
            arr[i]='mc101';
            selectMacc[i].src="./vimg/"+arr[i]+"_v.png";
        }
    })
}
