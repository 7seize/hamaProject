const slide = document.querySelector(".slide");
let slideWidth = 290;

// 버튼 엘리먼트 선택하기
const prevBtn = document.querySelector(".slide_prev_button");
const nextBtn = document.querySelector(".slide_next_button");

// 슬라이드 전체를 선택해 값을 변경해주기 위해 슬라이드 전체 선택하기
const slideItems = document.querySelectorAll(".slide_item");
// 현재 슬라이드 위치가 슬라이드 개수를 넘기지 않게 하기 위한 변수
const maxSlide = slideItems.length;

// 버튼 클릭할 때 마다 현재 슬라이드가 어디인지 알려주기 위한 변수
let currSlide = 1;

// 페이지네이션 생성
const pagination = document.querySelector(".slide_pagination");
for (let i = 0; i < maxSlide; i++) {
    if (i === 0) pagination.innerHTML += `<li class="active">•</li>`;
    else pagination.innerHTML += `<li>•</li>`;
}

const paginationItems = document.querySelectorAll(".slide_pagination > li");

function nextMove() {
    currSlide++;
    // 마지막 슬라이드 이상으로 넘어가지 않게 하기 위해서
    if (currSlide <= maxSlide) {
        // 슬라이드를 이동시키기 위한 offset 계산
        const offset = slideWidth * (currSlide - 1);
        // 각 슬라이드 아이템의 left에 offset 적용
        slideItems.forEach((i) => {
        i.setAttribute("style", `left: ${-offset}px`);
        });
        // 슬라이드 이동 시 현재 활성화된 pagination 변경
        paginationItems.forEach((i) => i.classList.remove("active"));
        paginationItems[currSlide - 1].classList.add("active");
    } else {
        currSlide--;
    }
}
function prevMove() {
    currSlide--;
    // 1번째 슬라이드 이하로 넘어가지 않게 하기 위해서
    if (currSlide > 0) {
        // 슬라이드를 이동시키기 위한 offset 계산
        const offset = slideWidth * (currSlide - 1);
        // 각 슬라이드 아이템의 left에 offset 적용
        slideItems.forEach((i) => {
        i.setAttribute("style", `left: ${-offset}px`);
        });
        // 슬라이드 이동 시 현재 활성화된 pagination 변경
        paginationItems.forEach((i) => i.classList.remove("active"));
        paginationItems[currSlide - 1].classList.add("active");
    } else {
        currSlide++;
    }
}

// 버튼 엘리먼트에 클릭 이벤트 추가하기
nextBtn.addEventListener("click", () => {
  // 이후 버튼 누를 경우 현재 슬라이드를 변경
    nextMove();
});
// 버튼 엘리먼트에 클릭 이벤트 추가하기
prevBtn.addEventListener("click", () => {
  // 이전 버튼 누를 경우 현재 슬라이드를 변경
    prevMove();
});
// 각 페이지네이션 클릭 시 해당 슬라이드로 이동하기
for (let i = 0; i < maxSlide; i++) {
  // 각 페이지네이션마다 클릭 이벤트 추가하기
    paginationItems[i].addEventListener("click", () => {
    // 클릭한 페이지네이션에 따라 현재 슬라이드 변경해주기(currSlide는 시작 위치가 1이기 때문에 + 1)
    currSlide = i + 1;
    // 슬라이드를 이동시키기 위한 offset 계산
    const offset = slideWidth * (currSlide - 1);
    // 각 슬라이드 아이템의 left에 offset 적용
    slideItems.forEach((i) => {
        i.setAttribute("style", `left: ${-offset}px`);
    });
    // 슬라이드 이동 시 현재 활성화된 pagination 변경
    paginationItems.forEach((i) => i.classList.remove("active"));
    paginationItems[currSlide - 1].classList.add("active");
    });
}
// 드래그(스와이프) 이벤트를 위한 변수 초기화
let startPoint = 0;
let endPoint = 0;

// PC 클릭 이벤트 (드래그)
window.addEventListener("mousedown", (e) => {
  startPoint = e.pageX; // 마우스 드래그 시작 위치 저장
});

window.addEventListener("mouseup", (e) => {
    endPoint = e.pageX; // 마우스 드래그 끝 위치 저장
    if (startPoint < endPoint) {
        // 마우스가 오른쪽으로 드래그 된 경우
        prevMove();
    } else if (startPoint > endPoint) {
        // 마우스가 왼쪽으로 드래그 된 경우
        nextMove();
    }
});

// 받아야하는 값 = 박스 크기, 마카롱 종류, 커스텀마카롱 종류
//./vimg/0_v.png"는 빈칸, 투명그림으로 대체
let maccImg = document.querySelectorAll('.macc-img');
let selectMacc = document.querySelectorAll('.select_macc');
let maccNum = 10; //마카롱 박스 개수 #####################이전 페이지에서 받아올 값임############################
let arr = new Array(maccNum).fill(0);   //마카롱 박스에 들어가는 마카롱을 저장하는 배열,arr[?]가 0이면 빈칸 
let custombox = new Array(maccNum).fill(0); //커스텀마카롱을 저장하는 배열
//마카롱 클릭시 해당 마카롱을 박스에 담음
for(let i = 0 ; i < maccImg.length; i++){
    
    maccImg[i].addEventListener("click",function(){

        for(let j = 0; j < maccNum; j++){
            if(arr[j]==0) { //마카롱 name을 arr배열에 넣음
                arr[j]=maccImg[i].getAttribute('name');
                if(arr[j].includes('mc100')){  //만약 선택한것이 커스텀 마카롱이면
                    custombox[j] = arr[j].split('-')[1];
                    arr[j] = arr[j].split('-')[0];
                }
                break;
            }
        }
        for(let j = 0; j < maccNum; j++){   //선택한 마카롱들을 박스에 담음
            selectMacc[j].src="./mc_v/"+arr[j]+"_v.png"
        }
    })
}
//마카롱 90도 회전된 그림 클릭시 상품제거
for(let i = 0; i < maccNum; i++){
    selectMacc[i].addEventListener("click",function(){
        if(arr[i] !=0){
            arr[i]=0;
            custombox[i]=0;
            for(let j = 0; j < maccNum; j++){//선택한 마카롱들을 박스에서 뺌
                selectMacc[j].src="./mc_v/"+arr[j]+"_v.png"
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
        console.log(arr);

//이런형태로 이동
/*  ajax 
    $.ajax({
		type : "POST", 
		url : "여기부분에 들어갈 주소", 
		data : {"arr" : arr.join(), "custombox" : custombox.sort((a,b)=>b-a).join()}, 
		success : function(chkRs) {
			if (chkRs == 0) {
				alert("오류 발생");
			}
			여기부분에 이동할 페이지
		}
	});
*/
        console.log('보내질 형태' );
        console.log('마카롱 박스에 담긴 마카롱 : ' + arr.join());
        console.log('마카롱 박스에 담긴 커스텀마카롱 인덱스 : ' + custombox.sort((a,b)=>b-a).join());
    }
})