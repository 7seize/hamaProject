//let elem = document.getElementById('addCart');
//imgEl.src = "./vimg/mc101_v.png";

let prolist = document.querySelectorAll('.pro_slider>div');
let arr = []

for(let i = 0 ; i < prolist.length; i++){
    prolist[i].addEventListener("click",function(){
        arr.push(prolist[i]);
        console.log(arr);
    })
}
