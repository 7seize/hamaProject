function onlyNum(obj){
   if(isNaN(obj.value)){
      //받아온 컨트롤의 값이 숫자가 아니면, ""String화한다.
      obj.value = "";
   }
}