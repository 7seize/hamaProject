package vo;

import java.util.ArrayList;

public class ProductInfo {
	// 하나의 상품 정보를 저장할 클래스
	private String pi_id, pc_id, pi_name;  
	private int pi_price, pi_cost, pi_dc, pi_read ;
	private int pi_review, pi_sale,pi_kcal ,pi_trash, pi_stock;
	private String pi_img1, pi_img2, pi_img3, pi_desc, 
	pi_limit, pi_isview, pi_date, pi_alg, pi_production;
	private float pi_score;
	
	private ArrayList<ProductOut> stockList;

	public String getPi_id() {
		return pi_id;
	}

	public void setPi_id(String pi_id) {
		this.pi_id = pi_id;
	}

	public String getPc_id() {
		return pc_id;
	}

	public void setPc_id(String pc_id) {
		this.pc_id = pc_id;
	}

	public String getPi_name() {
		return pi_name;
	}

	public void setPi_name(String pi_name) {
		this.pi_name = pi_name;
	}

	public int getPi_price() {
		return pi_price;
	}

	public void setPi_price(int pi_price) {
		this.pi_price = pi_price;
	}

	public int getPi_cost() {
		return pi_cost;
	}

	public void setPi_cost(int pi_cost) {
		this.pi_cost = pi_cost;
	}

	public int getPi_dc() {
		return pi_dc;
	}

	public void setPi_dc(int pi_dc) {
		this.pi_dc = pi_dc;
	}

	public int getPi_read() {
		return pi_read;
	}

	public void setPi_read(int pi_read) {
		this.pi_read = pi_read;
	}

	public int getPi_review() {
		return pi_review;
	}

	public void setPi_review(int pi_review) {
		this.pi_review = pi_review;
	}

	public int getPi_sale() {
		return pi_sale;
	}

	public void setPi_sale(int pi_sale) {
		this.pi_sale = pi_sale;
	}

	public int getPi_kcal() {
		return pi_kcal;
	}

	public void setPi_kcal(int pi_kcal) {
		this.pi_kcal = pi_kcal;
	}

	public int getPi_trash() {
		return pi_trash;
	}

	public void setPi_trash(int pi_trash) {
		this.pi_trash = pi_trash;
	}

	public int getPi_stock() {
		return pi_stock;
	}

	public void setPi_stock(int pi_stock) {
		this.pi_stock = pi_stock;
	}

	public String getPi_img1() {
		return pi_img1;
	}

	public void setPi_img1(String pi_img1) {
		this.pi_img1 = pi_img1;
	}

	public String getPi_img2() {
		return pi_img2;
	}

	public void setPi_img2(String pi_img2) {
		this.pi_img2 = pi_img2;
	}

	public String getPi_img3() {
		return pi_img3;
	}

	public void setPi_img3(String pi_img3) {
		this.pi_img3 = pi_img3;
	}

	public String getPi_desc() {
		return pi_desc;
	}

	public void setPi_desc(String pi_desc) {
		this.pi_desc = pi_desc;
	}

	public String getPi_limit() {
		return pi_limit;
	}

	public void setPi_limit(String pi_limit) {
		this.pi_limit = pi_limit;
	}

	public String getPi_isview() {
		return pi_isview;
	}

	public void setPi_isview(String pi_isview) {
		this.pi_isview = pi_isview;
	}

	public String getPi_date() {
		return pi_date;
	}

	public void setPi_date(String pi_date) {
		this.pi_date = pi_date;
	}

	public String getPi_alg() {
		return pi_alg;
	}

	public void setPi_alg(String pi_alg) {
		this.pi_alg = pi_alg;
	}

	public String getPi_production() {
		return pi_production;
	}

	public void setPi_production(String pi_production) {
		this.pi_production = pi_production;
	}

	public float getPi_score() {
		return pi_score;
	}

	public void setPi_score(float pi_score) {
		this.pi_score = pi_score;
	}

	public ArrayList<ProductOut> getStockList() {
		return stockList;
	}

	public void setStockList(ArrayList<ProductOut> stockList) {
		this.stockList = stockList;
	}
	
	
	
	
	
	
	
	
}
