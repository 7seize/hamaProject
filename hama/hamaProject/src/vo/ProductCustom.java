package vo;

public class ProductCustom {
	//고객이 만든 커스텀마카롱(레시피라봐도무방)을 저장하는 클래스 
	private int pmc_idx, pmc_sugar, pmc_price, ect_idx, ect_vote;
	private String mi_id, pmc_name,	pmc_vg, pmc_pl, ect_title;
	private String pi_id, pmc_img, pmc_tp1, pmc_tp2, pmc_isview, pmc_isbuy, pmc_date;
	 //ect_idx, ect_vote,ect_title (다희:임의)
	
	private String pi_name , pi_img1, pi_isview, pmt_name, tpname1, tpname2;
	//디테일리스트 만들기 싫어서 추가함(커스텀 마카롱의 맛에 따른(ㅇㅇ맛, ㅇㅇ이미지,게시여부)(한유진:지우지말것)
	
	
	public int getPmc_idx() {
		return pmc_idx;
	}
	public void setPmc_idx(int pmc_idx) {
		this.pmc_idx = pmc_idx;
	}
	public int getPmc_sugar() {
		return pmc_sugar;
	}
	public void setPmc_sugar(int pmc_sugar) {
		this.pmc_sugar = pmc_sugar;
	}
	public int getPmc_price() {
		return pmc_price;
	}
	public void setPmc_price(int pmc_price) {
		this.pmc_price = pmc_price;
	}
	public String getMi_id() {
		return mi_id;
	}
	public void setMi_id(String mi_id) {
		this.mi_id = mi_id;
	}
	public String getPmc_name() {
		return pmc_name;
	}
	public void setPmc_name(String pmc_name) {
		this.pmc_name = pmc_name;
	}
	public String getPmc_vg() {
		return pmc_vg;
	}
	public void setPmc_vg(String pmc_vg) {
		this.pmc_vg = pmc_vg;
	}
	public String getPmc_pl() {
		return pmc_pl;
	}
	public void setPmc_pl(String pmc_pl) {
		this.pmc_pl = pmc_pl;
	}
	public String getPi_id() {
		return pi_id;
	}
	public void setPi_id(String pi_id) {
		this.pi_id = pi_id;
	}
	public String getPmc_img() {
		return pmc_img;
	}
	public void setPmc_img(String pmc_img) {
		this.pmc_img = pmc_img;
	}
	public String getPmc_tp1() {
		return pmc_tp1;
	}
	public void setPmc_tp1(String pmc_tp1) {
		this.pmc_tp1 = pmc_tp1;
	}
	public String getPmc_tp2() {
		return pmc_tp2;
	}
	public void setPmc_tp2(String pmc_tp2) {
		this.pmc_tp2 = pmc_tp2;
	}
	public String getPmc_isview() {
		return pmc_isview;
	}
	public void setPmc_isview(String pmc_isview) {
		this.pmc_isview = pmc_isview;
	}
	public String getPmc_isbuy() {
		return pmc_isbuy;
	}
	public void setPmc_isbuy(String pmc_isbuy) {
		this.pmc_isbuy = pmc_isbuy;
	}
	public int getEct_idx() {
		return ect_idx;
	}
	public void setEct_idx(int ect_idx) {
		this.ect_idx = ect_idx;
	}
	public String getEct_title() {
		return ect_title;
	}
	public void setEct_title(String ect_title) {
		this.ect_title = ect_title;
	}
	public int getEct_vote() {
		return ect_vote;
	}
	public void setEct_vote(int ect_vote) {
		this.ect_vote = ect_vote;
	}
	public String getPmc_date() {
		return pmc_date;
	}
	public void setPmc_date(String pmc_date) {
		this.pmc_date = pmc_date;
	}
	public String getPi_name() {
		return pi_name;
	}
	public void setPi_name(String pi_name) {
		this.pi_name = pi_name;
	}
	public String getPi_img1() {
		return pi_img1;
	}
	public void setPi_img1(String pi_img1) {
		this.pi_img1 = pi_img1;
	}
	public String getPi_isview() {
		return pi_isview;
	}
	public void setPi_isview(String pi_isview) {
		this.pi_isview = pi_isview;
	}
	public String getPmt_name() {
		return pmt_name;
	}
	public void setPmt_name(String pmt_name) {
		this.pmt_name = pmt_name;
	}
	public String getTpname1() {
		return tpname1;
	}
	public void setTpname1(String tpname1) {
		this.tpname1 = tpname1;
	}
	public String getTpname2() {
		return tpname2;
	}
	public void setTpname2(String tpname2) {
		this.tpname2 = tpname2;
	}
	

	
	
}
