-- drop SCHEMA `testmall` ;
CREATE SCHEMA `testmall` ;
use `testmall` ;

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
	mi_lastlogin datetime, 					-- 최종로그인일자
	mi_joindate datetime default now(), 	-- 가입일
	mi_status char(1) default 'a'			-- 상태
);

-- 회원 주소록 테이블
create table t_member_addr (
	ma_idx int auto_increment primary key, 	-- 일련번호
	mi_id varchar(20), 						-- 회원 ID
	ma_name varchar(20) not null, 			-- 주소이름
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
	ms_status char(1) not null, 			-- 상태 변경값
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
	pi_desc varchar(50) not null, 			-- 설명 이미지
	pi_read int default 0, 					-- 조회수
	pi_score float default 0, 				-- 평균평점
	pi_review int default 0, 				-- 리뷰개수
	pi_sale int default 0, 					-- 판매량
	pi_isview char(1) default 'n', 			-- 게시여부
	pi_date datetime default now(), 		-- 등록일
	pi_last datetime default now(),			-- 최종 수정일
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
	pmc_isbuy	char(1)	default 't',			-- 구매여부

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
create table t_bbs_cus_tor (
	bct_idx		int auto_increment primary key, -- 토너먼트 인덱스
	pmc_idx		int,							-- 커스텀 인덱스
	mi_id		varchar(20),	 				-- 회원 ID
	bct_date	datetime default now(),			-- 등록일
	bct_vote	int	default 0,					-- 득표수
	bct_isview	char(1)	default 'y',			-- 게시여부
	bct_img1	varchar(50) default	'',			-- 커스텀 이미지1
	bct_title	varchar(50)	not null,			-- 토너먼트 제목
	bct_content	varchar(200) not null,	 		-- 토너먼트 내용

	constraint fk_bbs_cus_tor_pmc_idx foreign key (pmc_idx) 
			references t_product_ma_custom(pmc_idx),
	constraint fk_bbs_cus_tor_mi_id foreign key (mi_id) 
			references t_member_info(mi_id)
);

--  커스텀 토너먼트 투표
create table t_bbs_cus_tor_poll (
	bctp_idx	int auto_increment primary key, 		-- 커스텀 투표 인덱스
	bct_idx		int,									-- 토너먼트 인덱스
	pmc_idx		int,									-- 커스텀 인덱스
	mi_id		varchar(20), 							-- 회원 ID
	bctp_date	datetime default now(),					-- 투표일

	constraint fk_bbs_cus_tor_poll_bct_idx foreign key (bct_idx) 
			references t_bbs_cus_tor(bct_idx),
	constraint fk_bbs_cus_tor_poll_pmc_idx foreign key (pmc_idx) 
			references t_product_ma_custom(pmc_idx),
	constraint fk_bbs_cus_tor_poll_mi_id foreign key (mi_id) 
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
create table t_bbs_vote (
	bv_idx int primary key, 			-- 일련번호
	bv_start datetime, 					-- 투표 시작일
	bv_end datetime, 					-- 투표 종료일
	bv_question varchar(200) not null, 	-- 투표 내용
	bv_status char(1) default 'a', 		-- 투표 상태
	bv_date datetime default now(), 	-- 등록일
	ai_idx int, 						-- 등록관리자
    bv_title varchar(100) not null, 	-- 투표제목
    constraint fk_bbs_vote_idx foreign key (ai_idx) 
		references t_admin_info(ai_idx)
);
-- 투표 보기
create table t_bbs_vote_option (
	bvo_idx int auto_increment primary key, 	-- 일련번호
    bv_idx int,									-- 투표 일련번호
    bvo_seq int default 0,  					-- 보기 번호 및 순서
	bvo_cnt int default 0,  					-- 선택 횟수
	bvo_img varchar(50) not null,  				-- 투표 이미지

    constraint fk_bbs_vote_option_bv_idx foreign key (bv_idx) 
		references t_bbs_vote(bv_idx)
);

-- 투표 결과
create table t_bbs_vote_result (
	bvr_idx	 	int auto_increment unique, 	-- 일련번호
    bv_idx		int,						-- 투표 일련번호
	bvo_idx 	int, 						-- 보기 일련번호
    mi_id 		varchar(20), 				-- 회원 ID
    bvr_want	varchar(20)	default "",		-- 회원희망사항
    
    constraint primary key (mi_id, bv_idx, bvo_idx), 
    constraint fk_bbs_vote_result_mi_id foreign key (mi_id) 
		references t_member_info(mi_id),
    constraint fk_bbs_vote_result_bv_idx foreign key (bv_idx) 
		references t_bbs_vote(bv_idx), 
    constraint fk_bbs_vote_result_bvo_idx foreign key (bvo_idx) 
		references t_bbs_vote_option(bvo_idx)
);

