-- drop SCHEMA `hama` ;
CREATE SCHEMA `hama` ;
use `hama` ;

create table t_admin_info (
	ai_idx int auto_increment unique, 	-- 일련번호
	ai_id varchar(20) primary key, 		-- 아이디
	ai_pw varchar(20) not null, 		-- 비밀번호
	ai_name varchar(20) not null, 		-- 이름
	ai_use char(1) default 'y', 		-- 사용여부
	ai_date datetime default now()		-- 등록일
);

-- 회원정보 테이블
create table t_member_info (
	mi_id varchar(20) primary key, 			-- 회원 ID
	mi_pw varchar(20) not null, 			-- 비밀번호
	mi_name varchar(20) not null, 			-- 이름
    mi_memo varchar(200) default '',		-- 회원코멘트
	mi_birth char(10) not null, 			-- 생일
	mi_phone varchar(13) not null unique, 	-- 휴대폰
	mi_email varchar(50) not null unique, 	-- 이메일
	mi_point int default 0, 				-- 보유포인트
	mi_joindate datetime default now(), 	-- 가입일
	mi_status char(1) default 'a'			-- 상태
);

-- 회원 주소록 테이블
create table t_member_addr (
	ma_idx int auto_increment primary key, 	-- 일련번호
	mi_id varchar(20), 						-- 회원 ID
    ma_rname varchar(20) not null, 			-- 수취인 이름
	ma_phone varchar(13), 					-- 휴대폰
	ma_zip char(5) not null, 				-- 우편번호
	ma_addr1 varchar(50) not null, 			-- 주소1
	ma_addr2 varchar(50) not null, 			-- 주소2
	ma_basic char(1) default 'y', 			-- 기본주소여부
	ma_date datetime default now(), 		-- 등록일
    
    constraint fk_member_addr_mi_id foreign key (mi_id) 
		references t_member_info(mi_id)
);
-- 회원포인트 내역
create table t_member_point (
	mp_idx int auto_increment primary key, 	-- 일련번호
	mi_id varchar(20), 						-- 회원 ID
	mp_su char(1) default 's', 				-- 사용/적립
	mp_point int default 0, 				-- 포인트
	mp_desc varchar(20) not null, 			-- 사용/적립 내용
	mp_detail varchar(20) default '', 		-- 내역상세
	mp_date datetime default now(), 		-- 사용/적립일
    mp_admin int default 0, 				-- 관리자 번호
    
    constraint fk_member_point_mi_id foreign key (mi_id) 
		references t_member_info(mi_id)
);

-- 회원 상태 변경 정보
create table t_member_status (
	ms_idx int auto_increment primary key, 	-- 일련번호
	mi_id varchar(20), 						-- 회원 ID
	ms_status char(1) default 'a', 			-- 상태 변경값
	ms_self int default 0, 					-- 본인여부
	ms_date datetime default now(), 		-- 일시
    constraint fk_member_status_mi_id foreign key (mi_id) 
		references t_member_info(mi_id)
);

-- 상품 분류
create table t_product_ctgr (
	pc_id char(2) primary key, 	-- 대분류 코드
	pc_name varchar(20) not null 	-- 대분류 이름
);
select * from t_product_info;
-- 상품 정보
create table t_product_info (
	pi_id char(7) primary key, 				-- 상품 ID
	pc_id char(2), 							-- 분류 코드
	pi_name varchar(50) not null, 			-- 상품명
	pi_price int default 0, 				-- 가격
	pi_cost int default 0, 					-- 원가
	pi_dc int default 0, 					-- 할인율
	pi_img1 varchar(50) not null, 			-- 상품이미지1
	pi_img2 varchar(50) default '', 		-- 상품이미지2
	pi_img3 varchar(50) default '', 		-- 상품이미지3
	pi_desc varchar(200) default '', 		-- 설명
	pi_read int default 0, 					-- 조회수
	pi_score float default 0, 				-- 평균평점
	pi_review int default 0, 				-- 리뷰개수
	pi_sale int default 0, 					-- 판매량
	pi_isview char(1) default 'n', 			-- 게시여부
	pi_date datetime default now(), 		-- 등록일
	pi_limit char(1) not null,				-- 유통기한
    pi_alg varchar(200) default '',			-- 상품알러지
    pi_kcal int default 0,					-- 칼로리
    pi_trash int default 0,					-- 폐기량
    pi_stock int default 0,					-- 재고량
	pi_production datetime default now(), 	-- 생산일
    
    constraint fk_product_info_pc_id foreign key (pc_id) 
		references t_product_ctgr(pc_id)
);

-- 상품 재고 내역
create table t_product_out (
	po_idx	int auto_increment primary key,		-- 일련번호
	pi_id	char(7),	 						-- 상품ID
	po_kind	char(1)	default 'i',	 			-- 입고/폐기여부
	po_inout int	default 0,					-- 입고/폐기량
	po_date	datetime default now(),				-- 입고/폐기일

	constraint fk_product_info_pi_id foreign key (pi_id) 
			references t_product_info(pi_id)
);

-- 마카롱 커스텀
create table t_product_ma_custom (
	pmc_idx	int auto_increment primary key,		-- 커스텀 인덱스
	mi_id	varchar(20),						-- 회원 ID
	pmc_name	varchar(20)	not null, 	 		-- 마카롱이름
	pmc_sugar	int	 default 50,				-- 당도
	pmc_vg		char(1) default	'n',			-- 비건
	pmc_pl		char(1)	default 'b',			-- 필링량
	pi_id		char(7),	 					-- 상품ID
	pmc_img		varchar(50) default	'',	 		-- 레터링이미지
	pmc_tp1		varchar(5)	default '',	 		-- 토핑1
	pmc_tp2		varchar(5)	default '',			-- 토핑2
	pmc_date	datetime default now(), 		-- 등록일
	pmc_price	int	default 5000,				-- 가격
	pmc_isview	char(1)	default 'y',			-- 게시여부
	pmc_isbuy	char(1)	default 'n',			-- 구매여부

	constraint fk_product_ma_custom_mi_id foreign key (mi_id) 
			references t_member_info(mi_id),
	constraint fk_product_ma_custom_pi_id foreign key (pi_id) 
			references t_product_info(pi_id)
);

-- 마카롱 토핑
create table t_product_ma_topping (
	pmt_idx	int auto_increment unique,		-- 토핑 인덱스	
	pmt_id	char(5)	primary key,		 	-- 토핑 ID
	pmt_name	varchar(10)	 not null,		-- 토핑 이름
	pmt_date	datetime default now(),	 	-- 토핑 등록 일자
	pmt_isview	char(1)	default 'y'			-- 게시여부
);

-- 커스텀 토너먼트
create table t_ev_cus_tor (
	ect_idx		int auto_increment primary key, -- 토너먼트 인덱스
	pmc_idx		int,							-- 커스텀 인덱스
	mi_id		varchar(20),	 				-- 회원 ID
	ect_date	datetime default now(),			-- 등록일
	ect_vote	int	default 0,					-- 득표수
	ect_isview	char(1)	default 'y',			-- 게시여부
	ect_img1	varchar(50) default	'',			-- 커스텀 이미지1
	ect_title	varchar(50)	not null,			-- 토너먼트 제목
	ect_content	varchar(200) not null,	 		-- 토너먼트 내용

	constraint fk_ev_cus_tor_pmc_idx foreign key (pmc_idx) 
			references t_product_ma_custom(pmc_idx),
	constraint fk_ev_cus_tor_mi_id foreign key (mi_id) 
			references t_member_info(mi_id)
);

--  커스텀 토너먼트 투표
create table t_ev_cus_tor_poll (
	ectp_idx	int auto_increment primary key, 		-- 커스텀 투표 인덱스
	ect_idx		int,									-- 토너먼트 인덱스
	pmc_idx		int,									-- 커스텀 인덱스
	mi_id		varchar(20), 							-- 회원 ID
	ectp_date	datetime default now(),					-- 투표일

	constraint fk_ev_cus_tor_poll_ect_idx foreign key (ect_idx) 
			references t_ev_cus_tor(ect_idx),
	constraint fk_ev_cus_tor_poll_pmc_idx foreign key (pmc_idx) 
			references t_product_ma_custom(pmc_idx),
	constraint fk_ev_cus_tor_poll_mi_id foreign key (mi_id) 
			references t_member_info(mi_id)
);

 -- 장바구니
create table t_order_cart (
	oc_idx int auto_increment primary key, 	-- 일련번호
	mi_id varchar(20), 						-- 회원 ID
	pi_id char(7), 							-- 상품 ID
	po_idx int, 							-- 상품 재고 ID
	oc_cnt int default 0, 					-- 개수
	oc_date datetime default now(), 		-- 등록일
    pmc_idx int default 0, 					-- 커스텀 인덱스
    
    constraint fk_order_cart_mi_id foreign key (mi_id) 
		references t_member_info(mi_id), 
    constraint fk_order_cart_pi_id foreign key (pi_id) 
		references t_product_info(pi_id), 
    constraint fk_order_cart_po_idx foreign key (po_idx) 
		references t_product_out(po_idx),
	constraint fk_order_cart_pmc_idx foreign key (pmc_idx) 
		references t_product_ma_custom(pmc_idx)
);

-- 주문 정보 
create table t_order_info (
	oi_id char(12) primary key, 		-- 주문번호
	mi_id varchar(20), 					-- 회원 ID
	oi_name varchar(20) not null, 		-- 수취인명
	oi_phone varchar(13) not null, 		-- 배송지 전화번호
	oi_zip char(5) not null, 			-- 배송지 우편번호
	oi_addr1 varchar(50) not null, 		-- 배송지 주소1
	oi_addr2 varchar(50) not null, 		-- 배송지 주소2
	oi_memo varchar(50), 				-- 요청사항
	oi_payment char(1) default 'a', 	-- 결제수단
	oi_pay int default 0, 				-- 결제액
	oi_upoint int default 0, 			-- 사용 포인트
	oi_spoint int default 0, 			-- 적립 포인트
	oi_invoice varchar(50), 			-- 송장번호
	oi_status char(1) default 'a', 		-- 주문상태
	oi_date datetime default now(), 	-- 주문일
    oi_redate datetime default now(), 	-- 배송희망일
    
    constraint fk_order_info_mi_id foreign key (mi_id) 
		references t_member_info(mi_id)
);

-- 주문 정보 프로시저 // 보류 
/*
drop procedure if exists sp_order;
delimiter $$ 
create procedure sp_order(
kind char(1), oi_id	char(12), mi_id	varchar(20), oi_name varchar(20), oi_phone	varchar(13), oi_zip	char(5), oi_addr1 varchar(50), oi_addr2	varchar(50),
oi_memo	varchar(50), oi_payment	char(1), oi_pay	int, oi_upoint	int, oi_spoint int, oi_invoice	varchar(50), oi_status char(1), oi_date	datetime, oi_redate	datetime
	
)
begin 
		insert into t_order_info( mi_id, oi_name, oi_phone, oi_zip, oi_addr1, oi_addr2, oi_memo, oi_payment, oi_pay, oi_upoint, oi_spoint, oi_invoice, 
        oi_status,  oi_redate)
        values(miid, oiname, oiphone, oizip, oiaddr1, oiaddr2, oimemo, oipayment, oipay, oiupoint, oispoint, oiinvoice, oistatus,  oiredate );
end $$
delimiter ;

													-- 주문 정보 예시--	
-- =================================================================================================================================

call sp_order('1001', 'mc101','mc','딸기',4000,'mc101.png','mc101_v.png','','신선한 제철 딸기로 만든 딸기 마카롱','y','a','아몬드, 달걀, 우유, 딸기',113);
-- call sp_order('1001', 'mc101','mc','딸기',4000,'mc101.png','mc101_v.png','','신선한 제철 딸기로 만든 딸기 마카롱','y','a','아몬드, 달걀, 우유, 딸기',113);
-- call sp_order('주문정보', 'mc101','mc','딸기',4000,'mc101.png','mc101_v.png','','신선한 제철 딸기로 만든 딸기 마카롱','y','a','아몬드, 달걀, 우유, 딸기',113);


-- =================================================================================================================================

-- 주문정보

call sp_order( '1001'	);
call sp_order(			);
call sp_order(												);

*/
-- ===============================

-- 주문 상세 정보 
create table t_order_detail (
	od_idx int auto_increment primary key, 	-- 일련번호
	oi_id char(12), 						-- 주문번호
	pi_id char(7), 							-- 상품 ID
	po_idx int, 							-- 재고 ID
	od_cnt int default 0, 					-- 개수
	od_price int default 0, 				-- 단가
	od_name varchar(50) not null, 			-- 상품명
	od_img varchar(50) not null, 			-- 상품이미지
	pmc_idx int, 							-- 커스텀 인덱스
    
    constraint fk_order_detail_oi_id foreign key (oi_id) 
		references t_order_info(oi_id), 
    constraint fk_order_detail_pi_id foreign key (pi_id) 
		references t_product_info(pi_id), 
    constraint fk_order_detail_po_idx foreign key (po_idx) 
		references t_product_out(po_idx),
	constraint fk_order_detail_pmc_idx foreign key (pmc_idx) 
		references t_product_ma_custom(pmc_idx)
);

-- 상품 리뷰
create table t_product_review (
	pr_idx		int auto_increment unique,	-- 글번호
	mi_id		varchar(20),				-- 회원ID
	oi_id		char(12),					-- 주문번호
	pi_id		char(7),					-- 상품ID
	pr_name		varchar(100) not null,		-- 상품명 & 옵션명
	pr_title	varchar(100) not null,		-- 제목
	pr_content	text not null,				-- 내용
	pr_img1		varchar(50)	default '',		-- 이미지1
	pr_img2		varchar(50)	default '',		-- 이미지2
	pr_score	int	default 5,				-- 평점
	pr_ip		varchar(15)	not null,		-- IP주소
	pr_date		datetime default now(),		-- 작성일
	pr_isdel	char(1)	default 'n',		-- 삭제여부
	
    constraint pk_product_review primary key (mi_id, oi_id, pi_id), 
    constraint fk_product_review_mi_id foreign key (mi_id) 
		references t_member_info(mi_id), 
    constraint fk_product_review_oi_id foreign key (oi_id) 
		references t_order_info(oi_id), 
    constraint fk_product_review_pi_id foreign key (pi_id) 
		references t_product_info(pi_id)
);
	-- 환불정보
create table t_order_refund (
	or_idx		int auto_increment primary key,		-- 환불번호
	oi_id		char(12),							-- 주문번호
	mi_id		varchar(20),						-- 회원 ID
	or_reas		char(1)	 default 'e',				-- 환불사유
	or_pay		int	 default 0,						-- 환불액
	or_status	char(1)	default 'a',				-- 환불상태

    constraint fk_order_refund_mi_id foreign key (mi_id) 
		references t_member_info(mi_id), 
    constraint fk_order_refund_oi_id foreign key (oi_id) 
		references t_order_info(oi_id)
);

-- 1:1 문의
create table t_bbs_qna (
	bq_idx int primary key, 			-- 글번호
	mi_id varchar(20), 					-- 회원 ID
	bq_ctgr char(1) default 'a', 		-- 질문 분류
	bq_title varchar(100) not null, 	-- 질문 제목
	bq_content text not null, 			-- 질문 내용
	bq_img1 varchar(50), 				-- 이미지1
	bq_img2 varchar(50), 				-- 이미지2
	bq_qdate datetime default now(), 	-- 질문 일자
	bq_isanswer char(1) default 'n', 	-- 답변 여부
	bq_ai_idx int default 0, 			-- 답변 관리자
	bq_answer text, 					-- 답변 내용
	bq_adate datetime, 					-- 답변 일자
    constraint fk_bbs_qna_mi_id foreign key (mi_id) 
		references t_member_info(mi_id)
);

 -- 공지
create table t_bbs_notice (
	bn_idx int primary key, 			-- 글번호
	bn_ctgr varchar(10) not null, 		-- 분류
	ai_idx int, 						-- 작성자
	bn_title varchar(100) not null, 	-- 제목
	bn_content text not null, 			-- 내용
	bn_read int default 0, 				-- 조회수
	bn_isview char(1) default 'n', 		-- 게시여부
	bn_date datetime default now(), 	-- 작성일
    bn_img	varchar(100) default '',	-- 이벤트이미지
	bn_sdate	datetime ,				-- 이벤트시작일
	bn_edate	datetime ,				-- 이벤트종료일
	bn_iscal	char(1) default 'n',	-- 캘린더게시여부
	bn_color	char(7)	default '',		-- 캘린더색상

    constraint fk_bbs_notice_ai_idx foreign key (ai_idx) 
		references t_admin_info(ai_idx)
);

-- faq 게시판
create table t_bbs_faq (
	bq_idx		int auto_increment primary key,		-- 글번호
	bq_title	varchar(100) not null, 				-- 제목
	bq_content	text not null, 						-- 내용
	bq_isview	char(1)	default 'n'					-- 게시여부
);

-- 투표조사
create table t_ev_vote (
	ev_idx int primary key, 			-- 일련번호
	ev_start datetime, 					-- 투표 시작일
	ev_end datetime, 					-- 투표 종료일
	ev_question varchar(200) not null, 	-- 투표 내용
	ev_status char(1) default 'a', 		-- 투표 상태
	ev_date datetime default now(), 	-- 등록일
	ai_idx int, 						-- 등록관리자
    ev_title varchar(100) not null, 	-- 투표제목
    
    constraint fk_ev_vote_ai_idx foreign key (ai_idx) 
		references t_admin_info(ai_idx)
);
-- 투표 보기
create table t_ev_vote_option (
	evo_idx int auto_increment primary key, 	-- 일련번호
    ev_idx int,									-- 투표 일련번호
    ev_choice varchar(20) not null,				-- 투표 보기 내용
    evo_seq int default 0,  					-- 보기 번호 및 순서
	evo_cnt int default 0,  					-- 선택 횟수
	evo_img varchar(50) not null,  				-- 투표 이미지

    constraint fk_ev_vote_option_ev_idx foreign key (ev_idx) 
		references t_ev_vote(ev_idx)
);

-- 투표 결과
create table t_ev_vote_result (
	evr_idx	 	int auto_increment unique, 	-- 일련번호
    ev_idx		int,						-- 투표 일련번호
	evo_idx 	int, 						-- 보기 일련번호
    mi_id 		varchar(20), 				-- 회원 ID
    evr_want	varchar(20)	default "",		-- 회원희망사항
    
    constraint primary key (mi_id, ev_idx, evo_idx), 
    constraint fk_ev_vote_result_mi_id foreign key (mi_id) 
		references t_member_info(mi_id),
    constraint fk_ev_vote_result_ev_idx foreign key (ev_idx) 
		references t_ev_vote(ev_idx), 
    constraint fk_ev_vote_result_evo_idx foreign key (evo_idx) 
		references t_ev_vote_option(evo_idx)
);

-- select * from t_admin_info;
insert into t_admin_info (ai_id, ai_pw, ai_name) value ('admin1', '1111', '관리자');


-- 회원 가입 프로시저

drop procedure if exists sp_member;
delimiter $$ 
create procedure sp_member(miid varchar(20), mipw varchar(20), miname varchar(20), mibirth char(10),
 miphone varchar(13), miemail varchar(50) , mazip char(5), maaddr1 varchar(50), maaddr2 varchar(50))
begin 

	insert into t_member_info(mi_id, mi_pw, mi_name, mi_birth, mi_phone, mi_email, mi_point) 
	values(miid, mipw, miname , mibirth, miphone , miemail , 1000); 
   
	insert into t_member_addr(mi_id, ma_rname, ma_phone, ma_zip, ma_addr1, ma_addr2) 
	values(miid, miname, miphone , mazip , maaddr1, maaddr2); 
    
	insert into t_member_point(mi_id, mp_su,mp_point,mp_desc) 
	values(miid, 's',1000,'가입 축하금');
    
    insert into t_member_status(mi_id) 
	values(miid);
       
end $$
delimiter ;

													-- 회원 가입시 입력 사항 예시--	
-- =================================================================================================================================

call sp_member('atest', '1111', '테스터', '2000-02-02','010-4444-5555', 'dou@test.com' , '33333', '경기도 파주시','555-22');
-- call sp_member('아이디', '비밀번호', '이름', '생일', '핸드폰', '이메일' , '우편번호', '주소1','주소2');

-- =================================================================================================================================

select * from t_member_info;
select * from t_member_addr;
select * from t_member_point;


-- 분류 입력 mc:마카롱 ck:쿠키 jm:잼 te:차 bx:세트 cx:커스텀박스
insert into t_product_ctgr(pc_id , pc_name) values('mc' , '마카롱');
insert into t_product_ctgr(pc_id , pc_name) values('ck', '쿠키');
insert into t_product_ctgr(pc_id , pc_name) values('jm', '잼');
insert into t_product_ctgr(pc_id , pc_name) values('te', '차');
insert into t_product_ctgr(pc_id , pc_name) values('bx', '세트');
insert into t_product_ctgr(pc_id , pc_name) values('cb', '커스텀박스');

-- select * from t_product_ctgr;



-- 커스텀 마카롱 상품정보
insert into t_product_info( pi_id, pc_id, pi_name, pi_price, pi_cost, pi_img1, pi_img2, pi_isview, pi_limit)
values('mc100', 'mc','커스텀마카롱', 6000, 6000, 'mc100.png', 'mc100_v.png', 'y', 'a');


-- 상품 등록 프로시저
-- 커스텀 마카롱은 이곳에서 프로시저 처리 x
drop procedure if exists sp_product;
delimiter $$ 
create procedure sp_product(
	piid char(5), pcid char(2), piname varchar(20), picost int, piimg1 varchar(50), piimg2 varchar(50),piimg3 varchar(50),pidesc varchar(200),piisview char(1),pilimit char(1),
    pialg varchar(200), pikcal int
)
begin 
		insert into t_product_info( pi_id, pc_id, pi_name, pi_price, pi_cost, pi_img1, pi_img2, pi_img3, pi_isview, pi_limit, pi_desc, pi_alg, pi_kcal)
        values(piid, pcid, piname, picost, picost, piimg1, piimg2, piimg3, piisview, pilimit, pidesc, pialg, pikcal );
end $$
delimiter ;

													-- 상품 입력 예시--	
-- =================================================================================================================================

call sp_product('mc101','mc','딸기',4000,'mc101.png','mc101_v.png','','신선한 제철 딸기로 만든 딸기 마카롱','y','a','아몬드, 달걀, 우유, 딸기',113);
-- call sp_product('제품코드','분류','이름',가격,'이미지1','이미지2','이미지3','설명','개시여부','유통기한','알러지',칼로리);

-- =================================================================================================================================

-- 마카롱

call sp_product('mc102',	'mc',	'초코',	4000,			'mc102.png',	'mc102_v.png',	'',	'벨기에 공수 초콜릿으로 생산한 초코 마카롱',	'y',	'a',	'아몬드, 달걀, 우유, 카카오, 카페인',	110	);
call sp_product('mc103',	'mc',	'유자',	4000,			'mc103.png',	'mc103_v.png',	'',	'고흥에서 재배한 유자로 만든 유자 마카롱',	'y',	'a',	'아몬드, 달걀, 우유, 유자',	100					);
call sp_product('mc104',	'mc',	'피스타치오',	4000,			'mc104.png',	'mc104_v.png',	'',	'천연 색소로 만든 피스타치오 마카롱',	'y',	'a',	'아몬드, 달걀, 우유, 피스타치오, 견과류',	150												);
call sp_product('mc105',	'mc',	'민트',	4000,			'mc105.png',	'mc105_v.png',	'',	'치약맛이 아닌 민트맛! 민트맛 마카롱',	'y',	'a',	'아몬드, 달걀, 우유, 민트',	120			);
call sp_product('mc106',	'mc',	'치즈',	4000,			'mc106.png',	'mc106_v.png',	'',	'꾸덕한 치즈로 만든 치즈맛 마카롱',	'y',	'a',	'아몬드, 달걀, 우유, 치즈',	155						);
call sp_product('mc107',	'mc',	'바닐라',	4000,			'mc107.png',	'mc107_v.png',	'',	'바닐라빈이 콕콕 박혀있는 바닐라 마카롱',	'y',	'a',	'아몬드, 달걀, 우유, 바닐라빈',	140			);
call sp_product('mc108',	'mc',	'우유',	4000,			'mc108.png',	'mc108_v.png',	'',	'마카롱의 기본! 목장산 우유로 만든우유맛 마카롱',	'y',	'a',	'아몬드, 달걀, 우유',	153					);
call sp_product('mc109',	'mc',	'캬라멜',	4000,			'mc109.png',	'mc109_v.png',	'',	'특별한 국내제작 카라멜로 만든 카라멜 마카롱',	'y',	'a',	'아몬드, 달걀, 우유, 카라멜',	165				);
call sp_product('mc110',	'mc',	'레드벨벳',	4000,			'mc110.png',	'mc110_v.png',	'',	'풍부한 크림치즈가 들어간 레드벨벳 마카롱',	'y',	'a',	'아몬드, 달걀, 우유, 크림치즈',	164				);
call sp_product('mc111',	'mc',	'얼그레이',	4000,			'mc111.png',	'mc111_v.png',	'',	'TWG 얼그레이차로 만든 얼그레이 마카롱',	'y',	'a',	'아몬드, 달걀, 우유, 얼그레이',	120			);
call sp_product('mc112',	'mc',	'오레오',	4000,			'mc112.png',	'mc112_v.png',	'',	'미국산 오레오로 만든 오레오 마카롱',	'y',	'a',	'아몬드, 달걀, 우유, 오레오',	135						);
call sp_product('mc113',	'mc',	'블루베리',	4000,			'mc113.png',	'mc113_v.png',	'',	'고성 블루베리로 만든 블루베리 마카롱',	'y',	'a',	'아몬드, 달걀, 우유, 블루베리',	125		);


-- 마카롱 세트
call sp_product('bx101',	'bx',	'베스트 5구',	20000,			'bx101.png',	'',	'',	'하마롱의 베스트 상품만을 모은 5구 상자',	'y',	'a',''	,600	);
call sp_product('bx102',	'bx',	'베스트 10구',	40000,			'bx102.png',	'',	'',	'하마롱의 베스트 상품만을 모은 10구 상자',	'y',	'a',	'',	1200	);
call sp_product('bx103',	'bx',	'전체 15구',	60000,			'bx103.png',	'',	'',	'하마롱의 모든 상품을 맛보고 싶다면!',	'y',	'a',	'',	1800	);

-- 커스텀 박스
call sp_product('cb101','cb','마카롱선물상자5구',0,		'cb101.png',	'',	'',	'5입 상자에 마카롱 담기'	,'y','a','',0);
call sp_product('cb102',	'cb',	'마카롱선물상자10구',	0	,	'cb102.png',	'',	'',	'10입 상자에 마카롱 담기',	'y',	'a','',0);

-- 쿠키
call sp_product('ck101',	'ck',	'초코칩',	2000,			'ck101.png',	'',	'',	'쌉싸름한 블랙 초코칩과 달콤한 화이트 초콜릿의 환상 궁합',	'y',	'a',	'달걀, 우유  ',	300			);
call sp_product('ck102',	'ck',	'라즈베리',	2000,			'ck102.png',	'',	'',	'상큼한 라즈베리와 달콤한 초콜릿의 환상 궁합',	'y',	'a',	'달걀, 우유 ',	300												);
call sp_product('ck103',	'ck',	'마카다미아',	2000,			'ck103.png',	'',	'',	'고소함 가득한 마카다미아와 달콤한 화이트 초콜릿의 환상 궁합',	'y',	'a',	'달걀, 우유,마카다미아',	300												);
call sp_product('ck104',	'ck',	'화이트초코',	2000,			'ck104.png',	'',	'',	'녹진한 화이트 초코쿠키',	'y',	'a',	'달걀, 우유 ',	300												);
call sp_product('ck105',	'ck',	'레인보우',	2000,			'ck105.png',	'',	'',	'화려한 레인보우 초코쿠키',	'y',	'a',	'달걀, 우유 ',	300												);
call sp_product('ck106',	'ck',	'아몬드',	2000,			'ck106.png',	'',	'',	'고소함 가득한 아몬드와 달콤한 초콜릿의 환상 궁합',	'y',	'a',	'달걀, 우유,아몬드',	300												);
call sp_product('ck107',	'ck',	'호두',	2000,			'ck107.png',	'',	'',	'고소함 가득한 호두와 달콤한 초콜릿의 환상 궁합',	'y',	'a',	'달걀, 우유,호두',	300												);
call sp_product('ck108',	'ck',	'오트밀',	2000,			'ck108.png',	'',	'',	'고소함 가득한 오트밀과 달콤한 초콜릿의 환상 궁합',	'y',	'a',	'달걀, 우유  ',	300												);
call sp_product('ck109',	'ck',	'다크',	2000,			'ck109.png',	'',	'',	'농후한 다크 초코쿠키',	'y',	'a',	'달걀, 우유 ',	300												);
call sp_product('ck110',	'ck',	'민트',	2000,			'ck110.png',	'',	'',	'민트와 달콤한 초콜릿의 환상 궁합',	'y',	'a',	'달걀, 우유 ',	300												);
call sp_product('ck111',	'ck',	'진저',	2000,			'ck111.png',	'',	'',	'진저와 달콤한 초콜릿의 환상 궁합',	'y',	'a',	'달걀, 우유 ',	300												);
call sp_product('ck112',	'ck',	'카라멜',	2000,			'ck112.png',	'',	'',	'카라멜과 달콤한 초콜릿의 환상 궁합',	'y',	'a',	'달걀, 우유 ',	300												);
call sp_product('ck113',	'ck',	'버터',	2000,			'ck113.png',	'',	'',	'버터와 달콤한 초콜릿의 환상 궁합',	'y',	'a',	'달걀, 우유 ',	300												);
call sp_product('ck114',	'ck',	'시나몬',	2000,			'ck114.png',	'',	'',	'시나몬과 달콤한 초콜릿의 환상 궁합',	'y',	'a',	'달걀, 우유 ',	300												);
call sp_product('ck115',	'ck',	'슈가',	2000,			'ck115.png',	'',	'',	'설탕과 초콜릿의 환상 궁합',	'y',	'a',	'달걀, 우유 ',	300												);


-- 잼
call sp_product('jm101',	'jm',	'블루베리',	7000,			'jm101.png',	'',	'',	'달콤한 블루베리 잼 ',	'y',	'b',	'',	800												);
call sp_product('jm102',	'jm',	'시나몬사과',	7000,			'jm102.png',	'',	'',	'달콤한 시나몬사과 잼 ',	'y',	'b',	'',	800												);
call sp_product('jm103',	'jm',	'무화과',	7000,			'jm103.png',	'',	'',	'달콤한 무화과 잼 ',	'y',	'b',	'',	800												);
call sp_product('jm104',	'jm',	'딸기',	7000,			'jm104.png',	'',	'',	'달콤한딸기 잼 ',	'y',	'b',	'',	800												);
call sp_product('jm105',	'jm',	'복숭아',	7000,			'jm105.png',	'',	'',	'달콤한 복숭아 잼 ',	'y',	'b',	'복숭아',	800												);
call sp_product('jm106',	'jm',	'크랜베리',	7000,			'jm106.png',	'',	'',	'달콤한 크랜베리 잼 ',	'y',	'b',	'',	800												);
call sp_product('jm107',	'jm',	'오렌지마멀레이드',	7000,			'jm107.png',	'',	'',	'달콤한 오렌지마멀레이드 잼',	'y',	'b',	'',	800												);

-- 차
call sp_product('te101',	'te',	'얼그레이',	10000,			'te101.png',	'',	'',	'향긋한 얼그레이 티',	'y',	'b',	'',	50												);
call sp_product('te102',	'te',	'레몬',	10000,			'te102.png',	'',	'',	'향긋한 레몬티',	'y',	'b',	'',	50												);
call sp_product('te103',	'te',	'딸기',	10000,			'te103.png',	'',	'',	'향긋한 딸기차',	'y',	'b',	'',	50												);
call sp_product('te104',	'te',	'복숭아',	10000,			'te104.png',	'',	'',	'향긋한 복숭아',	'y',	'b',	'',	50												);
call sp_product('te105',	'te',	'실론티',	10000,			'te105.png',	'',	'',	'향긋한 실론티',	'y',	'b',	'',	50												);
call sp_product('te106',	'te',	'카모마일',	10000,			'te106.png',	'',	'',	'향긋한 카모마일 티',	'y',	'b',	'',	50												);
call sp_product('te107',	'te',	'페퍼민트',	10000,			'te107.png',	'',	'',	'향긋한 페퍼민트 티',	'y',	'b',	'',	50												);
call sp_product('te108',	'te',	'루이보스',	10000,			'te108.png',	'',	'',	'향긋한 루이보스티',	'y',	'b',	'',	50												);
call sp_product('te109',	'te',	'블랙티',	10000,			'te109.png',	'',	'',	'향긋한 블랙티',	'y',	'b',	'',	50												);
call sp_product('te110',	'te',	'생강',	10000,			'te110.png',	'',	'',	'향긋한 생강차',	'y',	'b',	'',	50												);

select * from t_product_info;


select * from t_order_infom t_product_ma_topping;
-- 커스텀 토핑 정보

insert into t_product_ma_topping(pmt_id,pmt_name)
values('tp101',	'초코');
insert into t_product_ma_topping(pmt_id,pmt_name)
values('tp102',	'딸기'																								);
insert into t_product_ma_topping(pmt_id,pmt_name)
values('tp103',	'프레첼'																								);
insert into t_product_ma_topping(pmt_id,pmt_name)
values('tp104',	'오레오'																								);
insert into t_product_ma_topping(pmt_id,pmt_name)
values('tp105',	'캬라멜'																								);
insert into t_product_ma_topping(pmt_id,pmt_name)
values('tp106',	'청포도'																								);
insert into t_product_ma_topping(pmt_id,pmt_name)
values('tp107',	'뽀또'																								);
insert into t_product_ma_topping(pmt_id,pmt_name)
values('tp108',	'블루베리'																								);
insert into t_product_ma_topping(pmt_id,pmt_name)
values('tp109',	'아몬드'																								);
insert into t_product_ma_topping(pmt_id,pmt_name)
values('tp110',	'민트'																								);
insert into t_product_ma_topping(pmt_id,pmt_name)
values('tp111',	'체리'																								);
insert into t_product_ma_topping(pmt_id,pmt_name)
values('tp112',	'로투스'																								);



-- ============한유진 추가 20230206========================================================
-- 커스텀 마카롱(mc100) 상세 설명 빠져있어서 update 
update t_product_info set pi_desc = '직접 마카롱을 커스텀할 수 있습니다. 비건 마카롱, 저당 마카롱을 즐겨 보세요.' where pi_id = 'mc100';

select * from t_product_ma_custom;
-- 마카롱 커스텀 테이블에 예시 추가 

insert into t_product_ma_custom(mi_id,   pmc_name,   pmc_sugar,   pmc_vg,   pmc_pl,   pi_id,   pmc_img,   pmc_tp1,   pmc_tp2,   
pmc_price,   pmc_isview,   pmc_isbuy)
values('atest',   '커마1',   100, 'y', 'c', 'mc101' , '',   'tp102'   , '',   6000,    'y',   'n'   );
-- 회원아이디, 마카롱리스트저장명, 당도, 비건여부, 필링양(a적게 c많이), 맛상징마카롱아이디, 레터링이미지, 토핑아이디1(빈문자열가능), 토핑아이디2(빈문자열가능), 가격, 게시여부, 판매된여부

insert into t_product_ma_custom(mi_id,   pmc_name,   pmc_sugar,   pmc_vg,   pmc_pl,   pi_id,   pmc_img,   pmc_tp1,   pmc_tp2,   
pmc_price,   pmc_isview,   pmc_isbuy)
values(   'atest', '커마2', 0, 'y', 'a',   'mc102', '',   'tp102', 'tp107', 6000,   'y',   'n'   );

select * from t_member_info;
select * from t_product_ma_custom;
-- 공지/이벤트 예시 추가 (민)
select * from t_bbs_notice;
insert into t_bbs_notice (bn_idx, bn_ctgr, bn_title, bn_content, bn_isview) value ('1','공지', '공지입니다', '시범 공지입니다.', 'y');
insert into t_bbs_notice (bn_idx, bn_ctgr, bn_title, bn_content, bn_isview, bn_sdate, bn_edate, bn_iscal, bn_color)
value ('2','이벤트', '이벤트입니다', '시범 이벤트입니다.', 'y', '2023-02-23', '2023-02-28', 'y', '#FA8072');
insert into t_bbs_notice (bn_idx, bn_ctgr, bn_title, bn_content, bn_isview) value ('3','공지2', '공지2입니다', '시범 공지입니다.', 'y');


-- 커스텀 토너먼트 /커스텀 토너먼트 투표 데이터 입력<이다희>
select * from t_ev_cus_tor;
select * from t_ev_cus_tor_poll;

 insert into t_ev_cus_tor( pmc_idx, mi_id, ect_date, ect_vote, ect_isview, ect_img1, ect_title, ect_content)
values(  '1' ,'atest', '2023-02-05', 0, 'y', '',  '내가 당연히 1등', '제 마카롱 진짜 맛있어요 비건이라 건강합니다.'  );

 insert into t_ev_cus_tor( pmc_idx, mi_id, ect_date, ect_vote, ect_isview, ect_img1, ect_title, ect_content)
values(  '2' ,'atest', '2023-02-05', 0, 'y', '',  '아니 내 레시피가 1등임', '제 마카롱 진짜 맛있어요22222'  );

 insert into t_ev_cus_tor_poll( ect_idx, pmc_idx, mi_id, ectp_date)
values( '1',   '1', 'atest', '2023-02-28' );
 insert into t_ev_cus_tor_poll( ect_idx, pmc_idx, mi_id, ectp_date)
values( '2',   '2', 'atest', '2023-02-28' );