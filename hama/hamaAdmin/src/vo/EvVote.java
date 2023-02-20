package vo;

// 투표조사 정보저장 클래스 
public class EvVote {
	private int ev_idx,ai_idx ;
	private String ev_start, ev_end, ev_qust,ev_status, ev_date, ev_title;
	public int getEv_idx() {
		return ev_idx;
	}
	public void setEv_idx(int ev_idx) {
		this.ev_idx = ev_idx;
	}
	public int getAi_idx() {
		return ai_idx;
	}
	public void setAi_idx(int ai_idx) {
		this.ai_idx = ai_idx;
	}
	public String getEv_start() {
		return ev_start;
	}
	public void setEv_start(String ev_start) {
		this.ev_start = ev_start;
	}
	public String getEv_end() {
		return ev_end;
	}
	public void setEv_end(String ev_end) {
		this.ev_end = ev_end;
	}
	public String getEv_qust() {
		return ev_qust;
	}
	public void setEv_qust(String ev_qust) {
		this.ev_qust = ev_qust;
	}
	public String getEv_status() {
		return ev_status;
	}
	public void setEv_status(String ev_status) {
		this.ev_status = ev_status;
	}
	public String getEv_date() {
		return ev_date;
	}
	public void setEv_date(String ev_date) {
		this.ev_date = ev_date;
	}
	public String getEv_title() {
		return ev_title;
	}
	public void setEv_title(String ev_title) {
		this.ev_title = ev_title;
	}
	
	
}
