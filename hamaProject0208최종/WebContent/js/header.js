function onlyNum(obj){
   if(isNaN(obj.value)){
      //받아온 컨트롤의 값이 숫자가 아니면, ""String화한다.
      obj.value = "";
   }
}
let menubig = document.querySelectorAll('.menu-big');
let menusmall = document.querySelectorAll('.menu-small'); 
let menubigAtive = document.querySelectorAll('.unactive');
let menu = document.querySelector('.menu'); 

for(let i = 0 ; i < menubig.length; i++){
    menubig[i].addEventListener('mouseover',()=>{
        for(let j = 0 ; j < menubig.length; j++){
            if(menubigAtive[j].classList[1] =='active'){
                menubigAtive[j].classList.replace('active', 'unactive');
                
            }
        }

        if(menubigAtive[i].classList[1] =='unactive'){
            menubigAtive[i].classList.replace('unactive', 'active');
        }
    })
    menusmall[i].addEventListener('mouseout',()=>{
        menubigAtive[i].classList.replace('active', 'unactive');
    })
}
