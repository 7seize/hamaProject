package vo;

public class ProductReview {
	// 하나의 상품 리뷰를 저장할 클래스
	private int pr_idx, pr_score ;
	private String mi_id, oi_id, pi_id, pr_name, pr_title,
	pr_content, pr_img1, pr_img2, pr_ip, pr_date, pr_isdel;
	
	public int getPr_idx() {
		return pr_idx;
	}
	public void setPr_idx(int pr_idx) {
		this.pr_idx = pr_idx;
	}
	public int getPr_score() {
		return pr_score;
	}
	public void setPr_score(int pr_score) {
		this.pr_score = pr_score;
	}
	public String getMi_id() {
		return mi_id;
	}
	public void setMi_id(String mi_id) {
		this.mi_id = mi_id;
	}
	public String getOi_id() {
		return oi_id;
	}
	public void setOi_id(String oi_id) {
		this.oi_id = oi_id;
	}
	public String getPi_id() {
		return pi_id;
	}
	public void setPi_id(String pi_id) {
		this.pi_id = pi_id;
	}
	public String getPr_name() {
		return pr_name;
	}
	public void setPr_name(String pr_name) {
		this.pr_name = pr_name;
	}
	public String getPr_title() {
		return pr_title;
	}
	public void setPr_title(String pr_title) {
		this.pr_title = pr_title;
	}
	public String getPr_content() {
		return pr_content;
	}
	public void setPr_content(String pr_content) {
		this.pr_content = pr_content;
	}
	public String getPr_img1() {
		return pr_img1;
	}
	public void setPr_img1(String pr_img1) {
		this.pr_img1 = pr_img1;
	}
	public String getPr_img2() {
		return pr_img2;
	}
	public void setPr_img2(String pr_img2) {
		this.pr_img2 = pr_img2;
	}
	public String getPr_ip() {
		return pr_ip;
	}
	public void setPr_ip(String pr_ip) {
		this.pr_ip = pr_ip;
	}
	public String getPr_date() {
		return pr_date;
	}
	public void setPr_date(String pr_date) {
		this.pr_date = pr_date;
	}
	public String getPr_isdel() {
		return pr_isdel;
	}
	public void setPr_isdel(String pr_isdel) {
		this.pr_isdel = pr_isdel;
	}
	
	
}
