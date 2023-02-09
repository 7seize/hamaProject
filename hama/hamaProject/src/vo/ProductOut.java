package vo;

public class ProductOut {
	// 상품 재고 내역을 저장할 클래스
	private int po_idx, po_inout;
	private String pi_id, po_kind, po_date;
	public int getPo_idx() {
		return po_idx;
	}
	public void setPo_idx(int po_idx) {
		this.po_idx = po_idx;
	}
	public int getPo_inout() {
		return po_inout;
	}
	public void setPo_inout(int po_inout) {
		this.po_inout = po_inout;
	}
	public String getPi_id() {
		return pi_id;
	}
	public void setPi_id(String pi_id) {
		this.pi_id = pi_id;
	}
	public String getPo_kind() {
		return po_kind;
	}
	public void setPo_kind(String po_kind) {
		this.po_kind = po_kind;
	}
	public String getPo_date() {
		return po_date;
	}
	public void setPo_date(String po_date) {
		this.po_date = po_date;
	}
	
	
}
