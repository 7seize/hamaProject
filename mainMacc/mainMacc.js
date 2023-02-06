let prolist = document.querySelectorAll('.pro_slider>div');
let selectMacc = document.querySelectorAll('.select_macc');
let maccNum = 10; //마카롱 박스 개수 이전 페이지에서 받아올 값임
let arr = new Array(maccNum).fill(0);   //마카롱 박스에 들어가는 마카롱을 저장하는 배열
                                        //arr[?]가 0이면 빈칸 
let custombox = new Array(maccNum).fill(0); //커스텀마카롱을 저장하는 배열

//마카롱 클릭시 해당 마카롱을 박스에 담음
for(let i = 0 ; i < prolist.length; i++){
    prolist[i].addEventListener("click",function(){
        for(let j = 0; j < maccNum; j++){
            if(arr[j]==0) { //마카롱 name을 arr배열에 넣음
                arr[j]=prolist[i].getAttribute('name');
                if(arr[j].includes('mc100')){  //만약 선택한것이 커스텀 마카롱이면
                    custombox[j] = arr[j].split('-')[1];
                    arr[j] = arr[j].split('-')[0];
                }
                break;
            }
        }
        for(let j = 0; j < maccNum; j++){   //선택한 마카롱들을 박스에 담음
            selectMacc[j].src="./vimg/"+arr[j]+"_v.png"
        }
    })
}

//마카롱 90도 회전된 그림 클릭시 상품제거
//./vimg/0_v.png"는 빈칸 투명그림으로 대체
for(let i = 0; i < maccNum; i++){
    selectMacc[i].addEventListener("click",function(){
        if(arr[i] !=0){
            arr[i]=0;
            custombox[i]=0;
            for(let j = 0; j < maccNum; j++){//선택한 마카롱들을 박스에서 뺌
                selectMacc[j].src="./vimg/"+arr[j]+"_v.png"
            }
        }
    })
}

// 장바구니 버튼을 누르면 장바구니가 채워 졌는지 확인
result.addEventListener("click",function(){
    let isFill = true;
    for(let i =0; i < arr.length; i++){
        if(arr[i] == 0){
            isFill = false;
        }
    }
    if(!isFill){
        alert("박스를 채워주세요");
    }else{
        alert("이곳에서 ajex 실행");

        //이런형태로 이동
        console.log('마카롱 박스에 담긴 마카롱 : ' + arr.join());
        console.log('마카롱 박스에 담긴 커스텀마카롱 인덱스 : ' + custombox.sort((a,b)=>b-a).join());
    }
})
