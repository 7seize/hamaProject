package svc;
import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class CartViewSvc {
	public ArrayList<OrderCart> getCartList(String miid){
		ArrayList<OrderCart> cartList = new ArrayList<OrderCart>();
		Connection conn = getConnection();
		CartProcDao cartProcDao = CartProcDao.getInstance();
		cartProcDao.setConnection(conn);
		
		cartList = cartProcDao.getCartList(miid);

		close(conn);
		return cartList;
		
	}
}
