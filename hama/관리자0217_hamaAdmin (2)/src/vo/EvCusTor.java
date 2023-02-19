package vo;

import java.util.ArrayList;
//ect_ectm_idx : 월 토너먼트 인덱스 , ect_ectm_isview : 토너먼트 가능여부
public class EvCusTor {
	private int ect_idx, pmc_idx, ect_vote, ect_ectm_idx;
	private String mi_id, ect_date, ect_isview, ect_img1, ect_title, ect_content, ect_ectm_isview;
	private  ArrayList<ProductCustom> customList;
	
	public int getEct_idx() {
		return ect_idx;
	}
	public void setEct_idx(int ect_idx) {
		this.ect_idx = ect_idx;
	}
	public int getPmc_idx() {
		return pmc_idx;
	}
	public void setPmc_idx(int pmc_idx) {
		this.pmc_idx = pmc_idx;
	}
	public int getEct_vote() {
		return ect_vote;
	}
	public void setEct_vote(int ect_vote) {
		this.ect_vote = ect_vote;
	}
	public String getMi_id() {
		return mi_id;
	}
	public void setMi_id(String mi_id) {
		this.mi_id = mi_id;
	}
	public String getEct_date() {
		return ect_date;
	}
	public void setEct_date(String ect_date) {
		this.ect_date = ect_date;
	}
	public String getEct_isview() {
		return ect_isview;
	}
	public void setEct_isview(String ect_isview) {
		this.ect_isview = ect_isview;
	}
	public String getEct_img1() {
		return ect_img1;
	}
	public void setEct_img1(String ect_img1) {
		this.ect_img1 = ect_img1;
	}
	public String getEct_title() {
		return ect_title;
	}
	public void setEct_title(String ect_title) {
		this.ect_title = ect_title;
	}
	public String getEct_content() {
		return ect_content;
	}
	public void setEct_content(String ect_content) {
		this.ect_content = ect_content;
	}
	public ArrayList<ProductCustom> getCustomList() {
		return customList;
	}
	public void setCustomList(ArrayList<ProductCustom> customList) {
		this.customList = customList;
	}
	public int getEct_ectm_idx() {
		return ect_ectm_idx;
	}
	public void setEct_ectm_idx(int ect_ectm_idx) {
		this.ect_ectm_idx = ect_ectm_idx;
	}
	public String getEct_ectm_isview() {
		return ect_ectm_isview;
	}
	public void setEct_ectm_isview(String ect_ectm_isview) {
		this.ect_ectm_isview = ect_ectm_isview;
	}

	
	
}
