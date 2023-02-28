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
	pmc_price	int	default 6000,				-- 가격
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
    oc_pmc_idx varchar(50) default '', 		-- 커스텀 인덱스
    oc_box char(50) default '', 			-- 박스에 담길 제품
    
    constraint fk_order_cart_mi_id foreign key (mi_id) 
		references t_member_info(mi_id), 
    constraint fk_order_cart_pi_id foreign key (pi_id) 
		references t_product_info(pi_id), 
    constraint fk_order_cart_po_idx foreign key (po_idx) 
		references t_product_out(po_idx)
);
-- constraint fk_order_cart_pmc_idx foreign key (pmc_idx) 
	-- references t_product_ma_custom(pmc_idx)
    
-- 주문 정보 
create table t_order_info (
	oi_id char(12) primary key, 		-- 주문번호
	mi_id varchar(20), 					-- 회원 ID
	oi_name varchar(20) not null, 		-- 수취인명
    oi_sender varchar(20) not null, 	-- 보내는이
    oi_sephone varchar(13) not null, 	-- 보내는이 연락처
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
	pmc_idx varchar(50) default '', 		-- 커스텀 인덱스
    od_box varchar(50) default '', 			-- 커스텀 인덱스
    
    constraint fk_order_detail_oi_id foreign key (oi_id) 
		references t_order_info(oi_id), 
    constraint fk_order_detail_pi_id foreign key (pi_id) 
		references t_product_info(pi_id), 
    constraint fk_order_detail_po_idx foreign key (po_idx) 
		references t_product_out(po_idx)
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
	declare rnd_date date;
    set rnd_date = curdate() - interval floor(rand() * 730) day;
    
	insert into t_member_info(mi_id, mi_pw, mi_name, mi_birth, mi_phone, mi_email, mi_point, mi_joindate) 
	values(miid, mipw, miname , mibirth, miphone , miemail , 1000, rnd_date); 
   
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
call sp_member('btest', '1234', '비테스터', '1999-01-09', '010-8888-9999', 'blue@test.com' , '22222', '경기도 성남시','222-66');
call sp_member('ctest', '1234', '시테스터', '1989-05-08', '010-1111-8888', 'ciwey@test.com' , '11111', '경기도 하남시','444-22');
call sp_member('dtest', '1234', '디테스터', '1993-11-05', '010-2222-5555', 'day@test.com' , '44444', '서울시 강남구','888-25');

-- =================================================================================================================================

call sp_member('tiro1', '1234', '강쇼', '1964-11-09', '010-1824-9439', 'bsdj3@test.com' , '22222', '경기도 성남시','222-66');
call sp_member('taehee',	'thth1234',	'김태희',	'1991-09-30',	'010-2492-6811',	'taehee@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('ysko0202',	'pcQYe ',	'고윤서',	'2004-02-04',	'010-1109-2750',	'ysko0202@naver.com','22222', '경기도 성남시','222-66');
call sp_member('skycho0426',	'KZgCD9qmx',	'조천태',	'1983-04-26',	'010-3720-2998',	'skycho0426@naver.com','22222', '경기도 성남시','222-66');
call sp_member('0me00321',	'euRuaxEi',	'공나영',	'2006-03-21',	'010-9631-8007',	'0me00321@naver.com','22222', '경기도 성남시','222-66');
call sp_member('hyo0703',	'SeYVFcXtBd',	'강효란 ',	'1996-07-03',	'010-1655-1267',	'hyo0703@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('singsang96',	'8aLbxPU8',	'송재경',	'1987-05-17',	'010-9608-8864',	'singsang96@naver.com','22222', '경기도 성남시','222-66');
call sp_member('ss501',	'Irszy',	'김현중',	'1993-02-29',	'010-2677-3810',	'ss501@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('park0jun',	'17MoR',	'박준영',	'1996-05-03',	'010-4791-3968',	'park0jun@naver.com','22222', '경기도 성남시','222-66');
call sp_member('hyehuman',	'Yu6hE',	'김혜인',	'1999-05-24',	'010-9944-4700',	'hyehuman@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('alskod',	'dadaLu13',	'한이유',	'1989-05-01',	'010-5623-4895',	'lu13@naver.com','22222', '경기도 성남시','222-66');
call sp_member('gi768',	'gjUsn768',	'이유한',	'1990-04-11',	'010-4453-7958',	'gi768@gmail.net','22222', '경기도 성남시','222-66');
call sp_member('6JeLY9 ',	' dfNm55Z',	'문숙사 ',	'1980-06-06',	'010-3538-1898',	'6JeLY9@nate.com','22222', '경기도 성남시','222-66');
call sp_member('GOg7FG',	'rndRMD1O2 ',	'차규화 ',	'1981-03-30',	'010-7093-6441 ',	'GOg7FG@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('NTkvuUTFD',	'Cf108Zgzni ',	'현성라 ',	'1988-05-30',	'010-1205-7098',	'NTkvuUTFD@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('Ix7Nkgo',	'X7u0Otfkj',	'권휘인',	'1985-06-11',	'010-1867-4302 ',	'Ix7Nkgo@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('7uVYmi3w',	'RMurKydhWVG ',	'설율린',	'1999-05-14',	'010-9785-2921',	'7uVYmi3w@naver.com','22222', '경기도 성남시','222-66');
call sp_member('yyuu67',	'Yy6y7uuke',	'배연유 ',	'1997-06-07',	'010-6205-2275',	'yyuu67@nate.com','22222', '경기도 성남시','222-66');
call sp_member('nkuhmip ',	' P6R4nlnRgq5 ',	'진공아 ',	'1999-06-07',	'010-1268-8428',	'nkuhmip @naver.com','22222', '경기도 성남시','222-66');
call sp_member('reopft',	'6Vh4sC',	'김승근',	'1999-03-26',	'010-9115-8746',	'w3izsHbz@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('u75hv8',	'aSIcfA',	'양종우',	'1996-02-09',	'010-2459-6995',	'GEGuPrj@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('ksc2iuw2',	'HfH0eT',	'황윤지',	'1990-04-26',	'010-8742-7487',	'sUoHU3O@yahoo.com','22222', '경기도 성남시','222-66');
call sp_member('qqvl6pb',	'zhr5qrzL',	'임우철',	'1995-12-10',	'010-4198-1025',	'Rg1Qwxd@daum.net','22222', '경기도 성남시','222-66');
call sp_member('yca8s82',	'eV4CIqex',	'하태준',	'2008-01-12',	'010-2640-8530',	'BJMx@daum.net','22222', '경기도 성남시','222-66');
call sp_member('pactrw',	'gEFZvNOs',	'백남기',	'1993-02-14',	'010-1709-9167',	'h67dM6X@daum.net','22222', '경기도 성남시','222-66');
call sp_member('jz9ds1',	'3xE4OI8s',	'제갈선혜',	'1998-03-07',	'010-1872-2277',	'WO7kk@naver.com','22222', '경기도 성남시','222-66');
call sp_member('se420os',	'4pt1ORku',	'오미나',	'2002-07-02',	'010-4930-3699',	'nikwqTS@naver.com','22222', '경기도 성남시','222-66');
call sp_member('aogap6x',	'fKUd915o',	'송진우',	'1994-03-19',	'010-3166-4872',	'd1dyr2bhU@naver.com','22222', '경기도 성남시','222-66');
call sp_member('ibrslag',	'ql33QOxa',	'안진경',	'1993-05-15',	'010-3381-9843',	'zESnruY@naver.com','22222', '경기도 성남시','222-66');					
call sp_member('bvcmem2',	'clHzOM',	'권진욱',	'2008-03-25',	'010-1186-6174',	'C3OEV9kuV@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('z4yul7v',	'uG9ihvQY',	'손다빈',	'1972-08-14',	'010-2825-1487',	'4YwzrmCh@daum.net','22222', '경기도 성남시','222-66');
call sp_member('vdv0t7dq7',	'DvauFebr',	'유연주',	'1970-02-11',	'010-8815-9226',	'vOyw0Gr6@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('tfguny',	'Ujj0aku7',	'복민석',	'1972-11-26',	'010-6539-7986',	'nNpBU8ap@hotmail.com','22222', '경기도 성남시','222-66');
call sp_member('clu5xb',	'BAP7gZjm',	'황보민경',	'1970-06-14',	'010-8515-4947',	'27Jty2@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('edsaoztx',	'KbQ6fkvO',	'안지호',	'1975-11-23',	'010-5486-6996',	'cDwuD7@nate.com','22222', '경기도 성남시','222-66');
call sp_member('xpnb6f88e ',	'LKDIpKNL',	'장형원',	'1968-12-25',	'010-3738-2319',	'yW6Ht@naver.com','22222', '경기도 성남시','222-66');
call sp_member('yo0qkeo4',	'Cq1VV1vz',	'서지은',	'1953-12-24',	'010-3664-8646',	'tYHBR5d@nate.com','22222', '경기도 성남시','222-66');
call sp_member('zc6oms',	'5bMJH6oV',	'권혜주',	'1974-04-17',	'010-1832-3636',	'5PZsOU@yahoo.com ','22222', '경기도 성남시','222-66');
call sp_member('m5v4xo',	'I3fWzevQ',	'정유선',	'1972-05-30',	'010-7165-3682',	'57Tsd@hotmail.com','22222', '경기도 성남시','222-66');
call sp_member('qawsef11',	'test1234',	'김민지',	'1994-02-02',	'010-2345-9393',	'qawsef11@naver.com','22222', '경기도 성남시','222-66');
call sp_member('god0023',	'qodkso1',	'전혜빈',	'1990-01-30',	'010-3949-2322',	'god0023@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('ehakdcla20',	'22930aw',	'이지혜',	'1993-09-09',	'010-9900-2340',	'ehakdcla20@daum.net','22222', '경기도 성남시','222-66');
call sp_member('vv9vv90',	'apfhddlek',	'조인수',	'1984-03-22',	'010-4502-2433',	'qkqhdlfrk@naver.com','22222', '경기도 성남시','222-66');
call sp_member('ehalthff03',	'ehfkqkdtm',	'김보령',	'1991-05-21',	'010-7890-1909',	'dkgbdlrjf@daum.net','22222', '경기도 성남시','222-66');
call sp_member('dhsmfeh50',	'sodlfeh',	'김민정',	'1989-03-25',	'010-4580-9329',	'tlfgdjgksms@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('ektlakssks07',	'skfdlek',	'이현지',	'1986-01-23',	'010-1190-0091',	'wlrmaekdwkd@naver.com','22222', '경기도 성남시','222-66');
call sp_member('wldhelchlrh',	'tthshdud',	'차지현',	'1979-12-13',	'010-8192-1092',	'djswprkwlsk@daum.net','22222', '경기도 성남시','222-66');
call sp_member('sigud1202',	'sksmsahffi',	'조시형',	'1999-08-01',	'010-9960-4623',	'sjehsork@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('qjfmfakssk',	'godqhrgkfrk',	'양창수',	'1983-02-11',	'010-7463-2882',	'dlwwlakfdkdy@gmail.com','22222', '경기도 성남시','222-66');					
call sp_member('yun4812',	'wlrmadlsl48',	'윤상훈',	'1993-06-13',	'010-7612-7991',	'yun4812@naver.com','22222', '경기도 성남시','222-66');
call sp_member('leemo1250',	'qkfhrk1212',	'이정문',	'1977-12-20',	'010-5603-8919',	'leemo1250@naver.com','22222', '경기도 성남시','222-66');
call sp_member('zeezee133',	'dndbwhdk%',	'정대성',	'1999-03-25',	'010-8852-6436',	'zeezee133@daum.net','22222', '경기도 성남시','222-66');
call sp_member('anhee0505',	'gidtn9595',	'안희철',	'1998-05-05',	'010-9003-4120',	'anhee0505@naver.com','22222', '경기도 성남시','222-66');
call sp_member('lecul04',	'rkarbf37@',	'임은철',	'2000-05-22',	'010-7431-8435',	'lecul04@daum.net','22222', '경기도 성남시','222-66');
call sp_member('karina2034',	'dbwlals123',	'유지민',	'2004-03-21',	'010-3859-2039',	'karina2034@naver.com','22222', '경기도 성남시','222-66');
call sp_member('giselle',	'higiselle15',	'김애리',	'2000-10-30',	'010-5922-2928',	'higiselle@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('whfhdqkr',	'qkrwhfhd',	'조지은',	'1990-12-20',	'010-9775-5142',	'whfhdqkr@daum.net','22222', '경기도 성남시','222-66');
call sp_member('chronge3',	'chchfhd0909',	'박초롱',	'1996-09-09',	'010-7522-1844',	'chronge2@naver.com','22222', '경기도 성남시','222-66');
call sp_member('josy0622',	'znlszk123',	'조서연',	'1999-06-22',	'010-8501-9241',	'josy0622@naver.com','22222', '경기도 성남시','222-66');
call sp_member('katie018',	'twelve012',	'이은정',	'2001-03-02',	'010-5786-0231',	'katie018@naver.com','22222', '경기도 성남시','222-66');
call sp_member('sage956',	'intro1102',	'하연주',	'2003-07-19',	'010-3321-9977',	'sage956@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('popo99',	'pwoe09',	'박민혜',	'2002-08-30',	'010-9981-1198',	'popo99@daum.net','22222', '경기도 성남시','222-66');
call sp_member('ydudu23',	'dduudu65',	'이연주',	'1996-01-10',	'010-4487-3354',	'ydudu23@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('eunjikim',	'kimeun09',	'배은지',	'1995-04-22',	'010-1780-8586',	'eunji@naver.com','22222', '경기도 성남시','222-66');
call sp_member('kiki20',	'kkwjdo',	'최슬기',	'1989-05-12',	'010-7594-7750',	'kiki20@naver.com','22222', '경기도 성남시','222-66');
call sp_member('hwangmin',	'mina86',	'황민아',	'1998-06-11',	'010-6523-5498',	'mimina@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('cholyoung',	'young90',	'최아영',	'1990-09-08',	'010-2592-4607',	'cholyoung@naver.com','22222', '경기도 성남시','222-66');
call sp_member('junghu33',	'johntlfg',	'황유정',	'2001-11-30',	'010-7299-9270',	'junghu33@daum.net','22222', '경기도 성남시','222-66');
call sp_member('yataptkfwl',	'rkaaldhr',	'장은진',	'2002-07-28',	'010-3349-3154',	'yataptkfwl@daum.net','22222', '경기도 성남시','222-66');
call sp_member('zimiri',	'ahffk',	'지미리',	'2003-06-01',	'010-6006-3225',	'zimiri@naver.com','22222', '경기도 성남시','222-66');
call sp_member('sjusung65',	'wndgkrtod',	'시주성',	'2002-03-18',	'010-1979-8968',	'sijusung65@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('stonevud',	'tjsqkdgksp',	'석평선',	'1994-01-26',	'010-0126-9449',	'stonevud@naver.com','22222', '경기도 성남시','222-66');
call sp_member('yyeebin',	'ehakdcu',	'최예빈',	'1997-09-15',	'010-5190-7935',	'yyeebin@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('kimyoung2',	'youngrkfl',	'김의용',	'1990-05-08',	'010-7760-0613',	'kimyoung2@daum.net','22222', '경기도 성남시','222-66');
call sp_member('younggari',	'qkqhtorrl',	'이용희',	'1986-02-24',	'010-4758-6961',	'younggari@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('munsang',	'ansghktkdvnarnjs',	'문상철',	'1998-09-16',	'010-3744-0295',	'munsang@daum.net','22222', '경기도 성남시','222-66');
call sp_member('wpqngkdlfn1',	'dkqjwldmlgla',	'김의한',	'1993-07-10',	'010-0170-0356',	'wpqngkdl1@daum.net','22222', '경기도 성남시','222-66');
call sp_member('rowlgnswmf',	'sjantlfgek',	'박지훈',	'1999-05-26',	'010-4494-9948',	'rowlgnswmf@naver.com','22222', '경기도 성남시','222-66');
call sp_member('dbsalsgud',	'qnffbsskadla',	'이윤민',	'2001-11-14',	'010-8870-6457',	'dbsalsgud@naver.com','22222', '경기도 성남시','222-66');
call sp_member('rudgns30',	'dhsusehddks',	'이경훈',	'1985-07-11',	'010-3999-5532',	'rudgns30@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('chungk86',	'wldrmfqpf',	'김정권',	'1986-09-08',	'010-6465-3510',	'chungk86@naver.com','22222', '경기도 성남시','222-66');
call sp_member('dbatkdqkqh',	'ajdcnddl',	'유상민',	'1991-03-02',	'010-2592-8216',	'dbatkdqkqh@daum.net','22222', '경기도 성남시','222-66');
call sp_member('ohyoung',	'dhwlddjtorl',	'오영준',	'2003-11-18',	'010-9975-7574',	'ohyoung@naver.com','22222', '경기도 성남시','222-66');
call sp_member('wjspwlsdla13',	'socldrn',	'김혜진',	'2006-10-08',	'010-4823-3168',	'wjspwlsdla13@duam.net','22222', '경기도 성남시','222-66');
call sp_member('tbslwh90',	'rhakdnjdyd',	'정수인',	'2002-12-01',	'010-7007-8007',	'tbslwh90@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('rudgusgyd',	'skehsjeh',	'이현경',	'1997-09-26',	'010-2529-0050',	'rudgusgyd@naver.com','22222', '경기도 성남시','222-66');
call sp_member('fbsmscja',	'rmfldnstkfka',	'류민정',	'1998-01-13',	'010-6098-0989',	'fbsmscja@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('rbfdlwleodi',	'eoeksgo',	'김규리',	'1999-12-14',	'010-7830-2025',	'rbfdlwleodi@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('skaclsrn',	'akdgofk',	'남민상',	'1996-01-06',	'010-3525-0913',	'skaclsrn@naver.com','22222', '경기도 성남시','222-66');
call sp_member('young1123',	'1bc4efgh',	'하석영',	'1996-04-23',	'010-0343-4061',	'young1123@naver.com','22222', '경기도 성남시','222-66');
call sp_member('jpchae123',	'ab2def13',	'최재필',	'2001-03-21',	'010-3453-0014',	'jpchae123@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('dongdong9',	'dxwfq35f',	'류동윤',	'1993-05-13',	'010-1736-7567',	'dongdong9@daum.net','22222', '경기도 성남시','222-66');
call sp_member('suckwater',	'h69d446o',	'한석수',	'1982-12-02',	'010-4366-0603',	'suckwater@naver.com','22222', '경기도 성남시','222-66');
call sp_member('whiteiron',	'5w9609na',	'신백철',	'2005-10-12',	'010-0736-4103',	'whiteiron@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('tabletirger',	'cyt0uma9',	'전상호',	'1993-09-16',	'010-2323-3457',	'tabletirger@naver.com','22222', '경기도 성남시','222-66');
call sp_member('sitdown1',	'tji1do8l',	'안지유',	'2006-07-28',	'010-2066-6153',	'sitdown1@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('2orsweet',	'aeo7m972',	'이나정',	'1996-07-27',	'010-6671-3006',	'2orsweet@naver.com','22222', '경기도 성남시','222-66');
call sp_member('easyyyy',	'5pxla8qg',	'이지아',	'2002-10-25',	'010-3221-5400',	'easyyyy@naver.com','22222', '경기도 성남시','222-66');
call sp_member('doraemi7',	'higidkh7',	'강도희',	'1999-01-10',	'010-7043-3238',	'doraemi7@naver.com','22222', '경기도 성남시','222-66');
call sp_member('dmswnd',	'pqh28g',	'이은주',	'1994-09-06',	'010-1930-7296',	'eunju@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('hwnjny',	'qw73bmn',	'황진영',	'1995-05-28',	'010-2781-5139',	'jinyoung@naver.com','22222', '경기도 성남시','222-66');
call sp_member('parhyejon',	'kw9udj7',	'박혜정',	'1999-04-26',	'010-3264-8921',	'hyejung@daum.net','22222', '경기도 성남시','222-66');
call sp_member('wlsgur99',	'jgd0dkv',	'백진혁',	'2001-03-09',	'010-2341-8504',	'jinhyeok@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('jhoohae',	'76wdfusc',	'하재후',	'1992-09-16',	'010-9852-4832',	'jaehoo@daum.net','22222', '경기도 성남시','222-66');
call sp_member('chnayon',	'hdnx57we',	'최나영',	'1995-11-09',	'010-5832-2384',	'nayoung@naver.com','22222', '경기도 성남시','222-66');
call sp_member('kwowooj',	'kdf2ju8s',	'권재우',	'1989-12-06',	'010-4352-1654',	'jaewoo@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('sonhwnn',	'dksh9eh2',	'손백현',	'2002-04-12',	'010-8957-0821',	'bakhyeon@naver.com','22222', '경기도 성남시','222-66');
call sp_member('mgyng21',	'knmcwrt82',	'문가영',	'1991-11-21',	'010-7742-9140',	'gayoung@daum.net','22222', '경기도 성남시','222-66');					
call sp_member('tw5ppp1p7w',	'lgilpp9luq',	'이태영',	'2005-05-13',	'010-8271-3343',	'tw5ppp1p7w@naver.com','22222', '경기도 성남시','222-66');
call sp_member('bq0trg2ree',	'ew5grm9mva',	'류수진',	'2015-05-29',	'010-4210-8702',	'bq0trg2ree@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('1bq5mu7u7y',	'r1j7v616t4',	'홍문용',	'1982-07-16',	'010-8339-6630',	'1bq5mu7u7y@naver.com','22222', '경기도 성남시','222-66');
call sp_member('whfp17q7em',	'cawmm3ji51',	'문준웅',	'2009-07-08',	'010-2182-9531',	'whfp17q7em@daum.net','22222', '경기도 성남시','222-66');
call sp_member('oddfv9zruu',	'xk71x4s1nn',	'배현재',	'2014-02-16',	'010-8375-7768',	'oddfv9zruu@naver.com','22222', '경기도 성남시','222-66');
call sp_member('kaa47kis6l',	'a1l58sbwf4',	'전우희',	'2000-04-24',	'010-6902-3298',	'kaa47kis6l@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('ikt72hc25v',	'o4i9m6lebo',	'배선옥',	'1997-10-21',	'010-5792-3805',	'ikt72hc25v@daum.net','22222', '경기도 성남시','222-66');
call sp_member('cdinf66o9z',	'lbs35snzjz',	'정인옥',	'2003-04-24',	'010-1885-0730',	'cdinf66o9z@daum.net','22222', '경기도 성남시','222-66');
call sp_member('vbojfw7re0',	'qb5xobpmz3',	'손재하',	'2011-06-10',	'010-7993-2738',	'vbojfw7re0@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('ukj5rbfdc3',	'zerydfq51q',	'하범준',	'1983-08-13',	'010-1708-1049',	'ukj5rbfdc3@naver.com','22222', '경기도 성남시','222-66');				
call sp_member('eeeffff11',	'aa123123',	'박수진',	'1997-03-20',	'010-7751-1212',	'parksu@naver.com','22222', '경기도 성남시','222-66');
call sp_member('ggdsdsw2',	'tt121212',	'김민규',	'1988-10-07',	'010-5658-8878',	'ming11@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('bulback7',	'bb124578',	'이승우',	'1998-01-01',	'010-4454-8289',	'bulback@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('coooool22',	'vv235689',	'홍혜림',	'1995-06-10',	'010-2241-5635',	'coolcoll@naver.com','22222', '경기도 성남시','222-66');
call sp_member('stone33',	'ff125478',	'김짱돌',	'1992-05-30',	'010-3636-8947',	'kingstone@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('blueblue',	'bb754236',	'김푸른',	'1994-08-20',	'010-1538-4862',	'bluekim@naver.com','22222', '경기도 성남시','222-66');
call sp_member('dragonsub',	'dd258525',	'김용섭',	'1996-01-01',	'010-5624-7844',	'dragon11@naver.com','22222', '경기도 성남시','222-66');
call sp_member('seogancook',	'ss854262',	'서가연',	'2001-02-04',	'010-5635-9974',	'seocook@naver.com','22222', '경기도 성남시','222-66');
call sp_member('gukbab12',	'gg778542',	'진도준',	'1980-02-03',	'010-5523-4321',	'jindo123@naver.com','22222', '경기도 성남시','222-66');
call sp_member('tasergun',	'ss456545',	'정상수',	'1985-07-16',	'010-2543-6934',	'lisangsu@gmail.com','22222', '경기도 성남시','222-66');					
call sp_member('j1nu6734',	'rlawlsn8475',	'김진우',	'1995-08-02',	'010-4178-9534',	'j1nu6734@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('2chamcham2',	'ciaciaqn58',	'김점례',	'1994-05-20',	'010-8523-4715',	'chamcham2@naver.com','22222', '경기도 성남시','222-66');
call sp_member('daina78',	'skskdid235',	'김은혜',	'1992-01-11',	'010-2396-4860',	'dainakim@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('rngudwnswkd',	'vnfls8472',	'구형준',	'1995-10-05',	'010-3586-2389',	'purin54@naver.com','22222', '경기도 성남시','222-66');
call sp_member('gongpa28',	'rhdvkflvk547',	'권순혁',	'1996-07-18',	'010-2476-9584',	'august28qsj@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('gyeolhee233',	'alsrufnld129',	'민결희',	'1995-08-03',	'010-8543-5843',	'gyeolhee233@naver,com','22222', '경기도 성남시','222-66');
call sp_member('pjs9073',	'Whsemr57439',	'박준석',	'1995-09-01',	'010-3576-4759',	'pjs9073@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('gravityp14',	'rmfoqlxl4857',	'박중력',	'1996-11-14',	'010-5764-3759',	'gravity1114@naver.com','22222', '경기도 성남시','222-66');
call sp_member('95pingman',	'vldaos51843',	'박찬우',	'1995-01-20',	'010-9578-4773',	'ak47zld12@naver.com','22222', '경기도 성남시','222-66');
call sp_member('s1032204',	'QlqnQlqn5583',	'유수정',	'1995-02-22',	'010-7685-1357',	's1032204@naver.com','22222', '경기도 성남시','222-66');
call sp_member('cherrypach',	'Rhvlsldia375',	'김보미',	'1992-05-09',	'010-3481-3476',	'cherrypach41@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('kimddolbok',	'EhfEhfaos57',	'김똘복',	'1994-08-08',	'010-2357-2466',	'kimddolbok94@naver.com','22222', '경기도 성남시','222-66');
call sp_member('rkdwl12',	'wjdrkawk354',	'정도현',	'1994-11-15',	'010-6473-3757',	'rkdwl12@gmail.com','22222', '경기도 성남시','222-66');
call sp_member('sahyang99',	'dmsowhdk7684',	'유은애',	'1999-12-15',	'010-4467-4786',	'sahyang99@naver.com','22222', '경기도 성남시','222-66');
call sp_member('liok0485',	'RbdRbdTlq572',	'전수진',	'2001-07-30',	'010-5478-3749',	'liok0485@naver.com','22222', '경기도 성남시','222-66');


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
call sp_product('cb101','cb','마카롱선물상자5구',20000,		'cb101.png',	'',	'',	'5입 상자에 마카롱 담기'	,'y','a','',0);
call sp_product('cb102',	'cb',	'마카롱선물상자10구',	40000	,	'cb102.png',	'',	'',	'10입 상자에 마카롱 담기',	'y',	'a','',0);

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



-- 주문 등록 프로시저

drop procedure if exists sp_order;
delimiter $$ 
create procedure sp_order(
	miid varchar(20)
)
begin 
	declare p_name varchar(20);
    declare p_phone varchar(13);
    declare p_zip char(5);
    declare p_add1 varchar(50);
    declare p_add2 varchar(50);
    declare p_piid char(7);
    declare p_piprice char(7);
    declare p_piimg varchar(50);
    declare p_piname varchar(20);
    declare p_oiid char(12);
    
    declare rad_char char(2);
    declare rad_int int;
    declare rad_num int;	-- 랜덤 물품 번호
    declare rad_cnt int;
    declare rad_day int;
    
    
    SET rad_char = CONCAT(CHAR(65 + FLOOR(RAND() * 26)), CHAR(65 + FLOOR(RAND() * 26)));
    SET rad_int = FLOOR(RAND() * 1000) + 1;
    SET rad_num = FLOOR(RAND() * 51);
    SET rad_cnt = FLOOR(RAND() * 20) + 1;
    SET rad_day = FLOOR(RAND() * 700) + 1;
    SET @rndday = DATE_SUB(now(), INTERVAL rad_day DAY);
    SET @strday = DATE_FORMAT(@rndday, '%y%m%d');
    SET p_oiid = CONCAT(@strday, rad_char,LPAD( rad_int, 4, '0'));
    
	select mi_name, mi_phone into p_name, p_phone from t_member_info where mi_id = miid;
    select ma_zip, ma_addr1, ma_addr2 into p_zip, p_add1, p_add2 from t_member_addr where mi_id = miid limit 1;
    select pi_id, pi_price, pi_img1, pi_name into p_piid, p_piprice, p_piimg, p_piname from t_product_info limit rad_num, 1;
    
    insert into t_order_info(oi_id,mi_id,oi_name,oi_phone,oi_zip,oi_addr1,oi_addr2,oi_memo,oi_pay,oi_spoint,oi_date,oi_redate,oi_sender,oi_sephone)
        values(p_oiid, miid, p_name, p_phone, p_zip, p_add1, p_add2, '조심히와주세요', p_piprice * rad_cnt, p_piprice /100,@rndday ,DATE_ADD(@rndday, INTERVAL 5 DAY),p_name, p_phone );
    insert into t_member_point (mi_id, mp_point,mp_desc,mp_detail, mp_date)
        values(miid,p_piprice /100,'상품구매', p_oiid, @rndday );
	insert into t_order_detail(oi_id, pi_id, od_cnt, od_price, od_name, od_img)
        values(p_oiid, p_piid, rad_cnt, p_piprice, p_piname, p_piimg);
	
    update t_product_info set pi_sale = pi_sale + rad_cnt where pi_id = p_piid;
    update t_member_info set mi_point = mi_point + p_piprice/100 where mi_id = miid;
    
end $$
delimiter ;

													-- 주문 예시 --	
-- =================================================================================================================================

call sp_order('atest');
-- 회원이름 입력시 해당회원이 랜덤 주문을 함
-- =================================================================================================================================

-- 주문 등록 프로시저 여러개 등록시 ##주의 100명이상 회원일때만 작동

drop procedure if exists sp_order_num;
delimiter $$ 
create procedure sp_order_num(num int)
begin 
    declare p_miid varchar(20);
    declare rad_num int;
    declare count_num int;
    SET count_num = 0;
    
    WHILE count_num < num DO
		SET rad_num = FLOOR(RAND() * 100) + 1;
		select mi_id into p_miid from t_member_info limit rad_num, 1;
		call sp_order(p_miid);
		set count_num = count_num + 1;
    END WHILE;
end $$
delimiter ;

													-- 주문 예시  --	
-- =================================================================================================================================

call sp_order_num(500);
-- 숫자 입력한만큼 주문이 들어감
-- =================================================================================================================================


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

insert into t_product_ma_custom(mi_id,   pmc_name,   pmc_sugar,   pmc_vg,   pmc_pl,   pi_id,   pmc_img,   pmc_tp1,   pmc_tp2,   
pmc_price,   pmc_isview,   pmc_isbuy)
values('btest',   '비마카롱1',   50, 'y', 'c', 'mc103' , '', 'tp104' , '',   6000,    'y',   'n'   );
insert into t_product_ma_custom(mi_id,   pmc_name,   pmc_sugar,   pmc_vg,   pmc_pl,   pi_id,   pmc_img,   pmc_tp1,   pmc_tp2,   
pmc_price,   pmc_isview,   pmc_isbuy)
values('btest',   '비마카롱2',   100, 'n', 'a', 'mc104' , '', 'tp105' , 'tp106',   6000,    'y',   'n'   );
insert into t_product_ma_custom(mi_id,   pmc_name,   pmc_sugar,   pmc_vg,   pmc_pl,   pi_id,   pmc_img,   pmc_tp1,   pmc_tp2,   
pmc_price,   pmc_isview,   pmc_isbuy)
values('ctest',   '시마카롱1',   50, 'y', 'a', 'mc105' , '', 'tp106' , '',   6000,    'y',   'n'   );
insert into t_product_ma_custom(mi_id,   pmc_name,   pmc_sugar,   pmc_vg,   pmc_pl,   pi_id,   pmc_img,   pmc_tp1,   pmc_tp2,   
pmc_price,   pmc_isview,   pmc_isbuy)
values('ctest',   '시마카롱1',   0, 'n', 'a', 'mc106' , '', 'tp108' , '',   6000,    'y',   'n'   );
insert into t_product_ma_custom(mi_id,   pmc_name,   pmc_sugar,   pmc_vg,   pmc_pl,   pi_id,   pmc_img,   pmc_tp1,   pmc_tp2,   
pmc_price,   pmc_isview,   pmc_isbuy)
values('dtest',   '디마카롱1',   100, 'n', 'c', 'mc107' , '', 'tp104' , 'tp102',   6000,    'y',   'n'   );
insert into t_product_ma_custom(mi_id,   pmc_name,   pmc_sugar,   pmc_vg,   pmc_pl,   pi_id,   pmc_img,   pmc_tp1,   pmc_tp2,   
pmc_price,   pmc_isview,   pmc_isbuy)
values('dtest',   '디마카롱2',   0, 'n', 'c', 'mc108' , '', 'tp109' , 'tp101',   6000,    'y',   'n'   );
insert into t_product_ma_custom(mi_id,   pmc_name,   pmc_sugar,   pmc_vg,   pmc_pl,   pi_id,   pmc_img,   pmc_tp1,   pmc_tp2,   
pmc_price,   pmc_isview,   pmc_isbuy)
values('ctest',   '내비건마카랑',   100, 'y', 'c', 'mc109' , '', 'tp112' , 'tp110',   6000,    'y',   'n'   );
insert into t_product_ma_custom(mi_id,   pmc_name,   pmc_sugar,   pmc_vg,   pmc_pl,   pi_id,   pmc_img,   pmc_tp1,   pmc_tp2,   
pmc_price,   pmc_isview,   pmc_isbuy)
values('ctest', '마싯는카롱', 50, 'n', 'a', 'mc110' , '', 'tp111', 'tp19', 6000, 'y', 'y');
insert into t_product_ma_custom(mi_id,   pmc_name,   pmc_sugar,   pmc_vg,   pmc_pl,   pi_id,   pmc_img,   pmc_tp1,   pmc_tp2,   
pmc_price,   pmc_isview,   pmc_isbuy)
values('btest', '카롱아일등하자', 100, 'n', 'c', 'mc111' , '', 'tp105', '', 6000, 'y', 'y');


-- update t_product_ma_custom set pmc_tp2 = 'tp110' where pmc_idx = '10';


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

update t_ev_cus_tor
set ect_date ='2023-01-10'
where mi_id ='atest' and pmc_idx ='2' ;


 insert into t_ev_cus_tor( pmc_idx, mi_id, ect_date, ect_vote, ect_isview, ect_img1, ect_title, ect_content)
values(  '1' ,'atest', '2022-12-01', 0, 'y', '',  '12월의 명예 마카롱이 될거야', '제 마카롱 진짜 .........'  );

 insert into t_ev_cus_tor( pmc_idx, mi_id, ect_date, ect_vote, ect_isview, ect_img1, ect_title, ect_content)
values(  '2' ,'atest', '2023-01-10', 0, 'y', '',  '내가 당연히 1등', '제 마카롱 진짜 맛있어요 비건이라 건강합니다.'  );

 insert into t_ev_cus_tor( pmc_idx, mi_id, ect_date, ect_vote, ect_isview, ect_img1, ect_title, ect_content)
values(  '3' ,'atest', '2023-02-05', 0, 'y', '',  '아니 내 레시피가 1등임', '제 마카롱 진짜 맛있어요22222'  );

insert into t_ev_cus_tor( pmc_idx, mi_id, ect_date, ect_vote, ect_isview, ect_img1, ect_title, ect_content)
values(  '1' ,'btest', '2023-01-05', 0, 'y', '',  '주인공은 나야 나', '내 마카롱을 보아라'  );

insert into t_ev_cus_tor( pmc_idx, mi_id, ect_date, ect_vote, ect_isview, ect_img1, ect_title, ect_content)
values(  '2' ,'btest', '2023-03-01', 0, 'y', '',  '뽐내봅니다', '제 레시피는 어쩌고 저쩌고'  );


-- atest
 insert into t_ev_cus_tor_poll( ect_idx, pmc_idx, mi_id, ectp_date)
values( '1',   '1', 'atest', '2023-02-28' );
 insert into t_ev_cus_tor_poll( ect_idx, pmc_idx, mi_id, ectp_date)
values( '2',   '2', 'atest', '2023-02-28' );
-- btest
insert into t_ev_cus_tor_poll( ect_idx, pmc_idx, mi_id, ectp_date)
values( '1',   '1', 'atest', '2023-02-28' );
 insert into t_ev_cus_tor_poll( ect_idx, pmc_idx, mi_id, ectp_date)
values( '2',   '2', 'atest', '2023-02-28' );


-- EvTorProcDao의  getCustomList() 쿼리
select a.pmc_idx, a.pmc_img , ect_title
from t_product_ma_custom a,  t_ev_cus_tor b
where a.mi_id = b.mi_id and a.pmc_idx = b.pmc_idx and a.pmc_isview='y' and a.pmc_isbuy='y';


-- 레시피 게시글 쿼리 
select a.pmc_idx, a.pmc_sugar, a.pmc_vg, a.pmc_pl, a.pi_id, a.pmc_tp1, a.pmc_tp2, a.pmc_img, b.ect_idx, b.ect_vote, b.ect_title, ect_content
from t_product_ma_custom a, t_ev_cus_tor b
where a.pmc_idx = b.pmc_idx and b.ect_isview = 'y' and a.pmc_isview='y' and a.pmc_isbuy='y' order by b.ect_vote desc;

-- 인기순 동작하는지 확인용
update t_product_info set pi_sale = 16 where pi_id = 'ck101';

select * from t_order_cart;
-- insert into t_order_cart (mi_id, pi_id, oc_cnt)   values ('atest', 'mc101', '1') ;


--  kind=a 일때 이전 달꺼 보여주기3###########################
select  a.*, b.* 
from t_product_ma_custom a,  t_ev_cus_tor b  
where a.mi_id = b.mi_id and a.pmc_idx = b.pmc_idx and a.pmc_isview='y' and a.pmc_isbuy='y' and  left(b.ect_date, 7) < date(left(now() ,7)) ;


-- select period_diff(replace(left(now(), 7), '-', ''), replace(left(ect_date, 7), '-', '') from t_ev_cus_tor where ect_idx = 1;
select a.*, b.*  from t_product_ma_custom a,  t_ev_cus_tor b  where a.mi_id = b.mi_id and a.pmc_idx = b.pmc_idx and a.pmc_isview='y'  and a.pmc_isbuy='y' order by ect_vote desc;
select a.*, b.*  from t_product_ma_custom a,  t_ev_cus_tor b  where a.mi_id = b.mi_id and a.pmc_idx = b.pmc_idx and a.pmc_isview='y'  and a.pmc_isbuy='y' and period_diff(replace(left(now(), 7), '-', ''), replace(left(ect_date, 7), '-', '')) > 0  order by ect_vote desc;
select left(date(now()), 7);
select * from t_product_ma_custom;
select * from t_ev_cus_tor;
update t_ev_cus_tor set pmc_idx = 11, mi_id = 'btest' where ect_idx = 6;
update t_ev_cus_tor set pmc_idx = 10, mi_id = 'ctest' where ect_idx = 6;
select * from t_ev_cus_tor where left(ect_date, 7) <> left(now(), 7);
 -- from t_ev_cus_tor where ect_idx = 1;
select period_diff('202302', '202301');
-- left(b.ect_date, 7)!=  2023-01   != year-0month






