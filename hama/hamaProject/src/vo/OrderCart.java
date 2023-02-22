package vo;

public class OrderCart {
	private int oc_idx, oc_cnt;
	private String mi_id, oc_date, pi_id;
	private int pi_price, pi_dc;
	private String pi_name, pi_img1;
	private String oc_pmc_idx, oc_box;
	
	public int getOc_idx() {
		return oc_idx;
	}
	public void setOc_idx(int oc_idx) {
		this.oc_idx = oc_idx;
	}
	public int getOc_cnt() {
		return oc_cnt;
	}
	public void setOc_cnt(int oc_cnt) {
		this.oc_cnt = oc_cnt;
	}
	public String getMi_id() {
		return mi_id;
	}
	public void setMi_id(String mi_id) {
		this.mi_id = mi_id;
	}
	public String getOc_date() {
		return oc_date;
	}
	public void setOc_date(String oc_date) {
		this.oc_date = oc_date;
	}
	public String getPi_id() {
		return pi_id;
	}
	public void setPi_id(String pi_id) {
		this.pi_id = pi_id;
	}
	public int getPi_price() {
		return pi_price;
	}
	public void setPi_price(int pi_price) {
		this.pi_price = pi_price;
	}
	public int getPi_dc() {
		return pi_dc;
	}
	public void setPi_dc(int pi_dc) {
		this.pi_dc = pi_dc;
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
	public String getOc_pmc_idx() {
		return oc_pmc_idx;
	}
	public void setOc_pmc_idx(String oc_pmc_idx) {
		this.oc_pmc_idx = oc_pmc_idx;
	}
	public String getOc_box() {
		return oc_box;
	}
	public void setOc_box(String oc_box) {
		this.oc_box = oc_box;
	}
	
}
