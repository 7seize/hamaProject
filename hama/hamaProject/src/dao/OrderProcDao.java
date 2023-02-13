package dao;

import static db.JdbcUtil.*;  
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.*;
import vo.*;
import svc.*;
import java.time.*;



public class OrderProcDao {
	//주문 관련된 쿼리작업(폼, 등록, 변경)들을 모두 처리하는 클래스 
		private static OrderProcDao orderProcDao;
		private Connection conn;
		private OrderProcDao() {}
		
		public static OrderProcDao  getInstance() {
			if (orderProcDao == null) orderProcDao = new OrderProcDao(); 
			//이미 생성된 orderProcDao 클래스의 인스턴스가 없으면 새롭게  인스턴스를 생성하라 
			return orderProcDao;
		}
		public void setConnection(Connection conn) {
		//현 Dao 클래스에서 사용할 커넥션 객체를 받아와서 생성해주는 메소드 
			this.conn = conn;
		}
		public ArrayList<OrderCart> getBuyList(String kind, String sql) {
			//주문 폼에서 보여줄 구매할 상품 목록을 ArrayList로 리턴하는 메소드
			Statement stmt = null;
			ResultSet rs = null;
			ArrayList<OrderCart> pdtList = new ArrayList<OrderCart>();
			OrderCart oc = null;
			
			System.out.println(sql);
			
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					oc = new OrderCart();
					if(kind.equals("c"))
						oc.setOc_idx(rs.getInt("oc_idx"));
					//장바구니를 통한 구매일 경우에만 장바구니 인덱스를 추가함 
					oc.setPi_id(rs.getString("pi_id"));
					oc.setPi_img1(rs.getString("pi_img1"));
					oc.setPi_name(rs.getString("pi_name"));
					oc.setPi_price(rs.getInt("pi_price"));
					oc.setOc_cnt(rs.getInt("cnt")); // cnt alias로 설정해서 cnt임
					pdtList.add(oc);
				}
				
			}catch(Exception e){
				System.out.println("OrderProcDao 클래스의 getBuyList메소드에서오류발생");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt);
			}
			return pdtList;

		}
		public ArrayList<MemberAddr> getAddrList(String miid)  {
			//주문 폼에서 보여줄 로그인한 유저의 배송정보를  ArrayList로 리턴하는 메소드
			Statement stmt = null;
			ResultSet rs = null;
			ArrayList<MemberAddr> addrList = new ArrayList<MemberAddr>();
			MemberAddr ma = null;

			try {
				String sql = " select * from t_member_addr where mi_id = '"
						+ miid +"' ";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					ma = new MemberAddr();
					
					ma.setMa_idx(rs.getInt("ma_idx"));
					ma.setMi_id(rs.getString("mi_id"));
					ma.setMa_rname(rs.getString("ma_rname"));
					ma.setMa_phone(rs.getString("ma_phone"));
					ma.setMa_zip(rs.getString("ma_zip"));
					ma.setMa_addr1(rs.getString("ma_addr1"));
					ma.setMa_addr2(rs.getString("ma_addr2"));
					ma.setMa_basic(rs.getString("ma_basic"));
					addrList.add(ma);
				}
				
			}catch(Exception e){
				System.out.println("OrderProcDao 클래스의 getAddrList메소드에서오류발생");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt);
			}
			return addrList;
		}
		
		// ################################################################################
		public String getOrderId() {
			//새로운 주문번호를 생성하여 리턴하는 메소드
			//주문번호 (yymmdd + 랜덤영문2자리 + 일련번호 4자리(1001)를 생성하여 리턴
			//일련번호를 쓰려면 오늘 가장 최근 주문한 일련번호의 + 1 해야하니까 select 해야함..
			Statement stmt = null;
			ResultSet rs = null;
			String oi_id = null;
			try {			
				stmt = conn.createStatement();
				LocalDate today = LocalDate.now(); //오늘날짜 yyyy-mm-dd 
				String td = (today+"").substring(2).replace("-", "");
				//yyyy-mm-dd 가 yymmdd가 됨. 
				
				//랜덤영문둘자리
				String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
				Random rnd = new Random();
				String rn = alpha.charAt(rnd.nextInt(26)) + ""; 
				//0~25까지의 수가 랜덤으로 나옴 
				//중복검사하려면 set이용하는데 그냥그러지말고 중복허용하기 
				rn += alpha.charAt(rnd.nextInt(26)) + ""; 
				
				//일련번호4자리 매일 하루의 첫주문 1001 
				String sql = " select right(oi_id, 4) seq from t_order_info "
				+ " where left(oi_id,6) = '" + td +"' order by oi_date "
				+ " desc limit 0, 1;";
				
				//주문번호(oi_id)에서 오른쪽에서 4개-일련번호를 가져온다 seq로 alias
				//(일련번호는 년년월월일일AS(알파벳둘)일련번호(1001)부터시작를 가져온다  
				//오더인포테이블에서
				//근데 날짜가(일련번호앞에서 6개부분)이 td와 일치해야함
				//date날짜순으로 역순->최근거를 가져오고
				//하나만 가져오면 되니까 0번째부터 하나 가져오기
				
				rs=stmt.executeQuery(sql);
				if(rs.next()) { //오늘 구매한 주문번호가 있으면
					int num = Integer.parseInt(rs.getString("seq")) + 1;
					oi_id = td+rn+num;
				}else { //오늘 첫 구매일 경우 (오늘치로 검색돌렸는데 없다=)
					oi_id = td+rn+"1001";
				}
				
				//없으면 그날 첫주문이기에 일련번호 1001을 넣고 아니면 받아온 일련번호에 +1한게
				//새로운 주문의 일련번호 
				
				
			}catch(Exception e){
				System.out.println("OrderProcDao 클래스의 getOrderId메소드에서오류발생");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt);
			}
			return oi_id;
		}
		
		
		public String orderInsert(String kind, OrderInfo oi, String temp){
			Statement stmt = null;
			ResultSet rs = null; //장바구니에서 뽑아와야하니가 필요함 
			String oi_id = getOrderId(); //주문번호 만드는거 메소드로 작업
			
			String result = oi_id + ","; //리턴할 놈 
			int rcount=0, target=0;
			//rcount는 실제쿼리실행결과로 적용되는 레코드 개수를 누적저장할 변수
			//target 적용되어야 할 레코드 개수 : insert update delete 쿼리 실행 횟수로
			//적용되어야 할 레코드의 총 개수를 저장할 변수 
			
			try {			
				stmt = conn.createStatement();
				//t_order_info 테이블에 사용할 insert문 
				String sql = "insert into t_order_info (" + 
						"oi_id, mi_id, oi_name, oi_phone, oi_zip, oi_addr1, " + 
						"oi_addr2, oi_payment, oi_pay, oi_status) values ('" + 
						oi_id			+ "', '" + oi.getMi_id()	+ "', '" + 
						oi.getOi_name()	+ "', '" + oi.getOi_phone() + "', '" + 
						oi.getOi_zip()	+ "', '" + oi.getOi_addr1()	+ "', '" + 
						oi.getOi_addr2()+ "', '" + oi.getOi_payment()+"', '" + 
						oi.getOi_pay()	+ "', '" + oi.getOi_status()+ "') ";	
				target++; 
				rcount = stmt.executeUpdate(sql); 
					//둘다 0에서 시작되는거니 둘다 잘 동작하면 1씩 증가
					//rcount는 누적시킬거고 target(적용되어야되는레코드개수)은 ++로 증가할 것임
				
				if(kind.equals("c")) { //장바구니를 통한 구매일 경우
					//장바구니에서 t_order_detail 테이블에 insert할 상품 정보를 추출
					sql = "select a.pi_id, a.ps_idx, a.oc_cnt, " + 
							" b.pi_name, b.pi_img1, c.ps_size, " + 
							" if(b.pi_dc > 0, (100 - b.pi_dc) / 100 * " + 
							" b.pi_price, b.pi_price) price " + 
							" from t_order_cart a, t_product_info b, " + 
							" t_product_stock c where a.pi_id = b.pi_id " + 
							" and a.ps_idx = c.ps_idx and a.mi_id = '" + 
							oi.getMi_id()+ "' and (";
					
					String delWhere = " where mi_id = '" 
					+ oi.getMi_id()+ "' and ("; 
					//delete용 쿼리 앞부분 미리 생성해둠 
					
					String[] arr = temp.split(",");
					//장바구니 테이블의 인덱스 번호들로 배열 생성 
					for(int i=0; i<arr.length; i++) {
						if(i==0) { 
							sql += "a.oc_idx = " + arr[i];
							delWhere += "oc_idx = " + arr[i];
						}else { 	
							sql += " or a.oc_idx = " + arr[i];
							delWhere += " or oc_idx = " + arr[i];
						}
					}
					sql+= ")";
					delWhere += ")";
					rs = stmt.executeQuery(sql);
					if(rs.next()) { //장바구니에 구매할 상품 정보가 있으면
						//루프돌면서 insert해야함
						do {
							Statement stmt2 = conn.createStatement();
							//t_order_detail 테이블에서 사용할 insert문 
							sql = "insert into t_order_detail ("
							+ "oi_id, pi_id, ps_idx, od_cnt, od_price, "
							+ "od_name, od_img, od_size) values ('" +
							oi_id			 + 	"', '"	+ 
							rs.getString("pi_id")	 + 	"', '"	+ 
							rs.getInt("ps_idx") 	 + 	"', '"	+
							rs.getInt("oc_cnt")		 + 	"', '"	+
							rs.getInt("price")		 + 	"', '"	+
							rs.getString("pi_name")  + 	"', '"	+ 
							rs.getString("pi_img1")   + 	"', '"	+ 
							rs.getInt("ps_size")	 +  "' ) ";					
							System.out.println(sql + ": t_order_detail 테이블에서 사용할 insert문 ");
							target++; 
							rcount += stmt2.executeUpdate(sql);
							
							
							//t_product_info 테이블의 판매수 증가 update문
							sql = "update t_product_info set "
									+ "pi_sale = pi_sale + " + rs.getInt("oc_cnt")
									+ " where pi_id= '"+rs.getString("pi_id")+"' ";
							System.out.println(sql + ": t_product_info 테이블의 판매수 증가 update문");
							target++; 
							rcount += stmt2.executeUpdate(sql); 
							
							
							
							//t_product_stock 테이블의 판매 및 재고 변경 증가 update문
							sql = "update t_product_stock set "
									+ " ps_stock = ps_stock - " + rs.getInt("oc_cnt")
									+ ", ps_sale = ps_sale + " + rs.getInt("oc_cnt") +
									" where ps_idx = " + rs.getInt("ps_idx");
							System.out.println(sql + ": t_product_stock 테이블의 판매 및 재고 변경 증가 update문");
							target++; 
							rcount += stmt2.executeUpdate(sql);
							
						}while(rs.next());		
						
						//t_order_cart에서 구매한 것들 삭제 delete 문 
						sql = "delete from t_order_cart " + delWhere ; 
						System.out.println(sql + ": t_order_cart에서 구매한 것들 삭제");
						stmt.executeUpdate(sql); //쿼리실행
						//실행시 문제가 발생해도 구매와는 상관 없으므로 rcount에 누적하지 않음.
						
						
						
					}else { // 장바구니에 구매할 상품 정보가 없으면 
						//장바구니를 통해 들어온건데 장바구니에서 상품정보가 없다?
						//잘못된거임.. 
						return result + "1, 4"; // result 1은 실제실행된거
						//4는 타깃. 근데 result랑 타깃값이랑 다르니까 롤백될것임(서비스에서)
						//4는 아무숫자나 넣은거
						
					}
				}else { //바로 구매일 경우
					
				}
				
				//포인트 사용 및 적립 관련 작업
				if(oi.getOi_upoint()>0) { 
					//사용한 포인트가 0보다 크면->구입시 포인트를 사용했으면
					
				}else { 
					//구매시 포인트를 사용하지 않았으면
					int pnt = oi.getOi_pay() * 2/100; //적립할 포인트
					//t_member_info 테이블의 보유 포인트 변경 쿼리
					sql = "update t_member_info set mi_point = mi_point + " + 
					pnt + " where mi_id = '" + oi.getMi_id() + "' ";
					System.out.println(sql + ": 포인트 사용 및 적립 관련 작업 update t_member_info");
					target++;
					rcount += stmt.executeUpdate(sql); 
					
					//t_member_point 테이블의 포인트 사용 내역추가 쿼리 
					sql = "insert into t_member_point "
							+ " (mi_id, mp_su, mp_point, mp_desc, mp_detail) "
							+ " values ('"+oi.getMi_id() +"',  's', '"+ pnt
							+"', " + "'상품구매', '"+ oi_id +"')";
					System.out.println(sql + ":t_member_point 테이블의 포인트 사용 내역추가 쿼리 ");
					target++;
					rcount += stmt.executeUpdate(sql); 
				}

			}catch(Exception e){
				System.out.println("OrderProcDao 클래스의 orderInsert메소드에서오류발생");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt);
			}
			return result + rcount + "," + target;
			//주문번호, 실제 적용된 레코드 수, 적용되어야 할 레코드 수를 리턴  
		}
		public OrderInfo getOrderInfo(String miid, String oiid) {
			//받아온 회원 아이디와 주문 번호에 해당하는 정보들을 OrderInfo형 인스턴스에 
			//담아 리턴하는 메소드 
			
			Statement stmt = null;
			ResultSet rs = null;
			OrderInfo oi = null;
			ArrayList<OrderDetail> detailList = new ArrayList<OrderDetail>();

			try {
				
				String sql = "select a.oi_name, a.oi_phone, a.oi_zip, a.oi_addr1, "
				+ " a.oi_addr2, a.oi_payment, a.oi_pay,"
				+ " b.od_img, b.od_name, b.od_size, b.od_cnt, b.od_price, "
				+ " b.pi_id, c.pi_isview from t_order_info a, t_order_detail b, "
				+ " t_product_info c where a.oi_id = b.oi_id and "
				+ " b.pi_id = c.pi_id and a.mi_id = '"+ miid
				+"' and a.oi_id = '"+ oiid +"'  order by c.pi_id, b.od_size";
				//제품순, 사이즈순 정리 
				System.out.println(sql + ":OrderProcDao:getOrderInfo");
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				
				
				if(rs.next()) { //한번만 넣어도 되는 것
					oi = new OrderInfo();
					oi.setOi_id(oiid);
					oi.setOi_name(rs.getString("oi_name"));
					oi.setOi_phone(rs.getString("oi_phone"));
					oi.setOi_zip(rs.getString("oi_zip"));
					oi.setOi_addr1(rs.getString("oi_addr1"));
					oi.setOi_addr2(rs.getString("oi_addr2"));
					oi.setOi_payment(rs.getString("oi_payment"));
					oi.setOi_pay(rs.getInt("oi_pay"));
					
					do { //루프돌며 여러번담아야함. 목록상품마다 따로인 것
						OrderDetail od = new OrderDetail();
						od.setOd_img(rs.getString("od_img"));
						od.setOd_name(rs.getString("od_name"));
						od.setOd_size(rs.getInt("od_size"));
						od.setOd_cnt(rs.getInt("od_cnt"));
						od.setOd_price(rs.getInt("od_price"));
						od.setPi_id(rs.getString("pi_id"));
						od.setPi_isview(rs.getString("pi_isview"));
						detailList.add(od);
					}while(rs.next());
					
					oi.setDetailList(detailList); 
					//루프 끝나면 안에서 담기
					//if안에서 담는 이유 데이터가 없으면 null로 가야하기 때문 
					//if밖에서 선언하면 detailList가 null이 아닌 게 됨 
				}
				
				
				
			}catch(Exception e){
				System.out.println("OrderProcDao 클래스의 getOrderInfo메소드에서오류발생");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt);
			}	
			
			return oi;
		}
	}
