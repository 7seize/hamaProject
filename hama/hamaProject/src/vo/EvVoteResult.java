package vo;

//투표결과 정보저장용 클래스
public class EvVoteResult {
	private int evr_idx, ev_idx,evo_idx;
	private String mi_id, evr_want;
	
	
	public int getEvr_idx() {
		return evr_idx;
	}
	public void setEvr_idx(int evr_idx) {
		this.evr_idx = evr_idx;
	}
	public int getEv_idx() {
		return ev_idx;
	}
	public void setEv_idx(int ev_idx) {
		this.ev_idx = ev_idx;
	}
	public int getEvo_idx() {
		return evo_idx;
	}
	public void setEvo_idx(int evo_idx) {
		this.evo_idx = evo_idx;
	}
	public String getMi_id() {
		return mi_id;
	}
	public void setMi_id(String mi_id) {
		this.mi_id = mi_id;
	}
	public String getEvr_want() {
		return evr_want;
	}
	public void setEvr_want(String evr_want) {
		this.evr_want = evr_want;
	}
	
	
}
