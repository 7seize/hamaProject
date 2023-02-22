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
	//二쇰Ц 愿��젴�맂 荑쇰━�옉�뾽(�뤌, �벑濡�, 蹂�寃�)�뱾�쓣 紐⑤몢 泥섎━�븯�뒗 �겢�옒�뒪 
		private static OrderProcDao orderProcDao;
		private Connection conn;
		private OrderProcDao() {}
		
		public static OrderProcDao  getInstance() {
			if (orderProcDao == null) orderProcDao = new OrderProcDao(); 
			//�씠誘� �깮�꽦�맂 orderProcDao �겢�옒�뒪�쓽 �씤�뒪�꽩�뒪媛� �뾾�쑝硫� �깉濡�寃�  �씤�뒪�꽩�뒪瑜� �깮�꽦�븯�씪 
			return orderProcDao;
		}
		public void setConnection(Connection conn) {
		//�쁽 Dao �겢�옒�뒪�뿉�꽌 �궗�슜�븷 而ㅻ꽖�뀡 媛앹껜瑜� 諛쏆븘���꽌 �깮�꽦�빐二쇰뒗 硫붿냼�뱶 
			this.conn = conn;
		}
		public ArrayList<OrderCart> getBuyList(String kind, String sql) {
			//二쇰Ц �뤌�뿉�꽌 蹂댁뿬以� 援щℓ�븷 �긽�뭹 紐⑸줉�쓣 ArrayList濡� 由ы꽩�븯�뒗 硫붿냼�뱶
			Statement stmt = null;
			ResultSet rs = null;
			ArrayList<OrderCart> pdtList = new ArrayList<OrderCart>();
			OrderCart oc = null;
			
			System.out.println("장바구니 sql:" + sql);
			
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					oc = new OrderCart();
					if(kind.equals("c"))
						oc.setOc_idx(rs.getInt("oc_idx"));
						oc.setPi_id(rs.getString("pi_id"));
						oc.setPi_img1(rs.getString("pi_img1"));
						oc.setPi_name(rs.getString("pi_name"));
						oc.setPi_price(rs.getInt("pi_price"));
						//oc.setOc_box(rs.getString("oc_box"));
						//oc.setOc_pmc_idx(rs.getString("oc_pmc_idx"));
						oc.setOc_cnt(rs.getInt("cnt")); // cnt alias濡� �꽕�젙�빐�꽌 cnt�엫
						pdtList.add(oc);
				}
				
			}catch(Exception e){
				System.out.println("OrderProcDao �겢�옒�뒪�쓽 getBuyList硫붿냼�뱶�뿉�꽌�삤瑜섎컻�깮");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt);
			}
			return pdtList;

		}
		public ArrayList<MemberAddr> getAddrList(String miid)  {
			//二쇰Ц �뤌�뿉�꽌 蹂댁뿬以� 濡쒓렇�씤�븳 �쑀���쓽 諛곗넚�젙蹂대��  ArrayList濡� 由ы꽩�븯�뒗 硫붿냼�뱶
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
				System.out.println("OrderProcDao �겢�옒�뒪�쓽 getAddrList硫붿냼�뱶�뿉�꽌�삤瑜섎컻�깮");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt);
			}
			return addrList;
		}
		
		
		public String getOrderId() {
			//�깉濡쒖슫 二쇰Ц踰덊샇瑜� �깮�꽦�븯�뿬 由ы꽩�븯�뒗 硫붿냼�뱶
			//二쇰Ц踰덊샇 (yymmdd + �옖�뜡�쁺臾�2�옄由� + �씪�젴踰덊샇 4�옄由�(1001)瑜� �깮�꽦�븯�뿬 由ы꽩
			//�씪�젴踰덊샇瑜� �벐�젮硫� �삤�뒛 媛��옣 理쒓렐 二쇰Ц�븳 �씪�젴踰덊샇�쓽 + 1 �빐�빞�븯�땲源� select �빐�빞�븿..
			Statement stmt = null;
			ResultSet rs = null;
			String oi_id = null;
			try {			
				stmt = conn.createStatement();
				LocalDate today = LocalDate.now(); //�삤�뒛�궇吏� yyyy-mm-dd 
				String td = (today+"").substring(2).replace("-", "");
				//yyyy-mm-dd 媛� yymmdd媛� �맖. 
				
				//�옖�뜡�쁺臾몃몮�옄由�
				String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
				Random rnd = new Random();
				String rn = alpha.charAt(rnd.nextInt(26)) + ""; 
				//0~25源뚯��쓽 �닔媛� �옖�뜡�쑝濡� �굹�샂 
				//以묐났寃��궗�븯�젮硫� set�씠�슜�븯�뒗�뜲 洹몃깷洹몃윭吏�留먭퀬 以묐났�뿀�슜�븯湲� 
				rn += alpha.charAt(rnd.nextInt(26)) + ""; 
				
				//�씪�젴踰덊샇4�옄由� 留ㅼ씪 �븯猷⑥쓽 泥レ＜臾� 1001 
				String sql = " select right(oi_id, 4) seq from t_order_info "
				+ " where left(oi_id,6) = '" + td +"' order by oi_date "
				+ " desc limit 0, 1;";
				
				//二쇰Ц踰덊샇(oi_id)�뿉�꽌 �삤瑜몄そ�뿉�꽌 4媛�-�씪�젴踰덊샇瑜� 媛��졇�삩�떎 seq濡� alias
				//(�씪�젴踰덊샇�뒗 �뀈�뀈�썡�썡�씪�씪AS(�븣�뙆踰노몮)�씪�젴踰덊샇(1001)遺��꽣�떆�옉瑜� 媛��졇�삩�떎  
				//�삤�뜑�씤�룷�뀒�씠釉붿뿉�꽌
				//洹쇰뜲 �궇吏쒓�(�씪�젴踰덊샇�븵�뿉�꽌 6媛쒕�遺�)�씠 td�� �씪移섑빐�빞�븿
				//date�궇吏쒖닚�쑝濡� �뿭�닚->理쒓렐嫄곕�� 媛��졇�삤怨�
				//�븯�굹留� 媛��졇�삤硫� �릺�땲源� 0踰덉㎏遺��꽣 �븯�굹 媛��졇�삤湲�
				
				rs=stmt.executeQuery(sql);
				if(rs.next()) { //�삤�뒛 援щℓ�븳 二쇰Ц踰덊샇媛� �엳�쑝硫�
					int num = Integer.parseInt(rs.getString("seq")) + 1;
					oi_id = td+rn+num;
				}else { //�삤�뒛 泥� 援щℓ�씪 寃쎌슦 (�삤�뒛移섎줈 寃��깋�룎�졇�뒗�뜲 �뾾�떎=)
					oi_id = td+rn+"1001";
				}
				
				//�뾾�쑝硫� 洹몃궇 泥レ＜臾몄씠湲곗뿉 �씪�젴踰덊샇 1001�쓣 �꽔怨� �븘�땲硫� 諛쏆븘�삩 �씪�젴踰덊샇�뿉 +1�븳寃�
				//�깉濡쒖슫 二쇰Ц�쓽 �씪�젴踰덊샇 
				
				
			}catch(Exception e){
				System.out.println("OrderProcDao �겢�옒�뒪�쓽 getOrderId硫붿냼�뱶�뿉�꽌�삤瑜섎컻�깮");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt);
			}
			return oi_id;
		}
		
		
		public String orderInsert(String kind, OrderInfo oi, String temp){
			Statement stmt = null;
			ResultSet rs = null; 
			String oi_id = getOrderId();
			
			String result = oi_id + ","; 
			int rcount=0, target=0;

			
			try {			
				stmt = conn.createStatement();
				
				String sql = "insert into t_order_info (" + 
						"oi_id, mi_id, oi_name, oi_phone, oi_zip, oi_addr1, " + 
						"oi_addr2, oi_payment, oi_pay, oi_memo, oi_sender, oi_sephone) values ('" + 
						oi_id			+ "', '" + oi.getMi_id()	+ "', '" + 
						oi.getOi_name()	+ "', '" + oi.getOi_phone() + "', '" + 
						oi.getOi_zip()	+ "', '" + oi.getOi_addr1()	+ "', '" + 
						oi.getOi_addr2()+ "', '" + oi.getOi_payment()+"', '" + 
						oi.getOi_pay()	+ "', '" + oi.getOi_memo()	+ "', '" + 
						oi.getOi_sender()	+ "', '"+ 
						oi.getOi_sephone() +"') ";	
				target++; 
				
				System.out.println("test1 :" + sql);
				
				rcount = stmt.executeUpdate(sql); 

				
				System.out.println("kind :" + kind);
				
				if(kind.equals("c")) { 
					sql = "select a.pi_id, a.oc_cnt, " + 
							" b.pi_name, b.pi_img1, " + 
							" b.pi_price " + 
							" from t_order_cart a, t_product_info b " + 
							"  where a.pi_id = b.pi_id " + 
							" and a.mi_id = '" + 
							oi.getMi_id()+ "' and (";

					String delWhere = " where mi_id = '" 
					+ oi.getMi_id()+ "' and ("; 
					
					System.out.println("temp : " + temp);			
					String[] arr = temp.split(",");
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
					
					System.out.println("test2 :" + sql);
					
					rs = stmt.executeQuery(sql);
					
					if(rs.next()) { //�옣諛붽뎄�땲�뿉 援щℓ�븷 �긽�뭹 �젙蹂닿� �엳�쑝硫�
						//猷⑦봽�룎硫댁꽌 insert�빐�빞�븿
						do {
							Statement stmt2 = conn.createStatement();
							//t_order_detail �뀒�씠釉붿뿉�꽌 �궗�슜�븷 insert臾� 
							sql = "insert into t_order_detail ("
							+ "oi_id, pi_id, od_cnt, od_price, "
							+ "od_name, od_img) values ('" +
							oi_id			 + 	"', '"	+ 
							rs.getString("pi_id")	 + 	"', '"	+ 
							rs.getInt("oc_cnt")		 + 	"', '"	+
							rs.getInt("pi_price")	 + 	"', '"	+
							rs.getString("pi_name")  + 	"', '"	+ 
							rs.getString("pi_img1")  +  "' ) ";		
							
							
							System.out.println("t_order_detail �뀒�씠釉붿뿉�꽌 �궗�슜�븷 insert臾� :");
							System.out.println(sql);
							
							target++; 
							rcount += stmt2.executeUpdate(sql);
							
							
						}while(rs.next());		

						sql = "delete from t_order_cart " + delWhere ; 
						System.out.println(sql + ": t_order_cart�뿉�꽌 援щℓ�븳 寃껊뱾 �궘�젣");
						stmt.executeUpdate(sql); //荑쇰━�떎�뻾
						//�떎�뻾�떆 臾몄젣媛� 諛쒖깮�빐�룄 援щℓ���뒗 �긽愿� �뾾�쑝誘�濡� rcount�뿉 �늻�쟻�븯吏� �븡�쓬.
						
		
						
					}else { // �옣諛붽뎄�땲�뿉 援щℓ�븷 �긽�뭹 �젙蹂닿� �뾾�쑝硫� 
						//�옣諛붽뎄�땲瑜� �넻�빐 �뱾�뼱�삩嫄대뜲 �옣諛붽뎄�땲�뿉�꽌 �긽�뭹�젙蹂닿� �뾾�떎?
						//�옒紐삳맂嫄곗엫.. 
						return result + "1, 4"; // result 1�� �떎�젣�떎�뻾�맂嫄�
						//4�뒗 ��源�. 洹쇰뜲 result�옉 ��源껉컪�씠�옉 �떎瑜대땲源� 濡ㅻ갚�맆寃껋엫(�꽌鍮꾩뒪�뿉�꽌)
						//4�뒗 �븘臾댁닽�옄�굹 �꽔��嫄�
						
					}
				}else { //바로구매 주문시

					sql = "select a.pi_id, a.pi_name, a.pi_img1, " + 
							" a.pi_price " + 
							" from  t_product_info a " + 
							 temp;
					
					System.out.println("test2 :" + sql);
					
					rs = stmt.executeQuery(sql);
					
					if(rs.next()) {
							Statement stmt2 = conn.createStatement();
							//t_order_detail �뀒�씠釉붿뿉�꽌 �궗�슜�븷 insert臾� 
							sql = "insert into t_order_detail ("
							+ "oi_id, pi_id, od_cnt, od_price, "
							+ "od_name, od_img) values ('" +
							oi_id			 + 	"', '"	+ 
							rs.getString("pi_id")	 + 	"', '"	+ 
							1		 + 	"', '"	+
							rs.getInt("pi_price")	 + 	"', '"	+
							rs.getString("pi_name")  + 	"', '"	+ 
							rs.getString("pi_img1")  +  "' ) ";		
							
							
							System.out.println("t_order_detail �뀒�씠釉붿뿉�꽌 �궗�슜�븷 insert臾� :");
							System.out.println(sql);
							
							target++; 
							rcount += stmt2.executeUpdate(sql);
							
							
						};

				}
				
				
				// #####################################�룷�씤�듃 �옉�뾽 �굹以묒뿉##############################################
				
				//�룷�씤�듃 �궗�슜 諛� �쟻由� 愿��젴 �옉�뾽
				if(oi.getOi_upoint()>0) { 
					//�궗�슜�븳 �룷�씤�듃媛� 0蹂대떎 �겕硫�->援ъ엯�떆 �룷�씤�듃瑜� �궗�슜�뻽�쑝硫�
					
				}else { 
					//援щℓ�떆 �룷�씤�듃瑜� �궗�슜�븯吏� �븡�븯�쑝硫�
					int pnt = oi.getOi_pay() * 2/100; //�쟻由쏀븷 �룷�씤�듃
					//t_member_info �뀒�씠釉붿쓽 蹂댁쑀 �룷�씤�듃 蹂�寃� 荑쇰━
					sql = "update t_member_info set mi_point = mi_point + " + 
					pnt + " where mi_id = '" + oi.getMi_id() + "' ";
					System.out.println(sql + ": �룷�씤�듃 �궗�슜 諛� �쟻由� 愿��젴 �옉�뾽 update t_member_info");
					target++;
					rcount += stmt.executeUpdate(sql); 
					
					//t_member_point �뀒�씠釉붿쓽 �룷�씤�듃 �궗�슜 �궡�뿭異붽� 荑쇰━ 
					sql = "insert into t_member_point "
							+ " (mi_id, mp_su, mp_point, mp_desc, mp_detail) "
							+ " values ('"+oi.getMi_id() +"',  's', '"+ pnt
							+"', " + "'�긽�뭹援щℓ', '"+ oi_id +"')";
					System.out.println(sql + ":t_member_point �뀒�씠釉붿쓽 �룷�씤�듃 �궗�슜 �궡�뿭異붽� 荑쇰━ ");
					target++;
					rcount += stmt.executeUpdate(sql); 
					
					// ###################################################################################
				}

			}catch(Exception e){
				System.out.println("OrderProcDao �겢�옒�뒪�쓽 orderInsert硫붿냼�뱶�뿉�꽌�삤瑜섎컻�깮");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt);
			}
			return result + rcount + "," + target;
			//二쇰Ц踰덊샇, �떎�젣 �쟻�슜�맂 �젅肄붾뱶 �닔, �쟻�슜�릺�뼱�빞 �븷 �젅肄붾뱶 �닔瑜� 由ы꽩  
		}
		
		
		public OrderInfo getOrderInfo(String miid, String oiid) {
			//諛쏆븘�삩 �쉶�썝 �븘�씠�뵒�� 二쇰Ц 踰덊샇�뿉 �빐�떦�븯�뒗 �젙蹂대뱾�쓣 OrderInfo�삎 �씤�뒪�꽩�뒪�뿉 
			//�떞�븘 由ы꽩�븯�뒗 硫붿냼�뱶 
			
			Statement stmt = null;
			ResultSet rs = null;
			OrderInfo oi = null;
			ArrayList<OrderDetail> detailList = new ArrayList<OrderDetail>();

			try {
				String sql = "select a.oi_id, a.mi_id, a.oi_sender, oi_sephone, a.oi_name, " + 
						"a.oi_phone, a.oi_zip, a.oi_addr1,a.oi_addr2, " + 
						"a.oi_memo, a.oi_payment , a.oi_pay, a.oi_date, a.oi_redate, "+
						"b.pi_id, b.od_cnt, b.od_price, b.od_img, b.od_name " + 
						"from t_order_info a , t_order_detail b " + 
						"where a.oi_id = b.oi_id and a.mi_id = '"+miid+"' " +
						"and a.oi_id = '"+oiid+"' order by b.pi_id ";
				
				
				System.out.println("OrderProcDao:getOrderInfo :");
				System.out.println(sql);
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				
				
				if(rs.next()) { //�븳踰덈쭔 �꽔�뼱�룄 �릺�뒗 寃�
					oi = new OrderInfo();
					oi.setOi_id(oiid);
					oi.setMi_id(rs.getString("mi_id"));
					oi.setOi_name(rs.getString("oi_name"));
					oi.setOi_phone(rs.getString("oi_phone"));
					oi.setOi_zip(rs.getString("oi_zip"));
					oi.setOi_addr1(rs.getString("oi_addr1"));
					oi.setOi_addr2(rs.getString("oi_addr2"));
					oi.setOi_memo(rs.getString("oi_memo"));
					oi.setOi_payment(rs.getString("oi_payment"));
					oi.setOi_pay(rs.getInt("oi_pay"));
					oi.setOi_date(rs.getString("oi_date"));
					oi.setOi_redate(rs.getString("oi_redate"));
					oi.setOi_sender(rs.getString("oi_sender"));
					oi.setOi_sephone(rs.getString("oi_sephone"));
					
					do { //猷⑦봽�룎硫� �뿬�윭踰덈떞�븘�빞�븿. 紐⑸줉�긽�뭹留덈떎 �뵲濡쒖씤 寃�
						OrderDetail od = new OrderDetail();
						od.setOd_img(rs.getString("od_img"));
						od.setOd_name(rs.getString("od_name"));
						od.setOd_cnt(rs.getInt("od_cnt"));
						od.setOd_price(rs.getInt("od_price"));
						od.setPi_id(rs.getString("pi_id"));
						detailList.add(od);
					}while(rs.next());
					
					oi.setDetailList(detailList); 
					//猷⑦봽 �걹�굹硫� �븞�뿉�꽌 �떞湲�
					//if�븞�뿉�꽌 �떞�뒗 �씠�쑀 �뜲�씠�꽣媛� �뾾�쑝硫� null濡� 媛��빞�븯湲� �븣臾� 
					//if諛뽰뿉�꽌 �꽑�뼵�븯硫� detailList媛� null�씠 �븘�땶 寃� �맖 
				}
				
				
				
			}catch(Exception e){
				System.out.println("OrderProcDao �겢�옒�뒪�쓽 getOrderInfo硫붿냼�뱶�뿉�꽌�삤瑜섎컻�깮");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt);
			}	
			
			return oi;
		}
	}
