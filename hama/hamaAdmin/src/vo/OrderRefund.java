package vo;

public class OrderRefund {
	private String oi_id , mi_id, or_reas, or_status;
	private int or_pay;
	public String getOi_id() {
		return oi_id;
	}
	public void setOi_id(String oi_id) {
		this.oi_id = oi_id;
	}
	public String getMi_id() {
		return mi_id;
	}
	public void setMi_id(String mi_id) {
		this.mi_id = mi_id;
	}
	public String getOr_reas() {
		return or_reas;
	}
	public void setOr_reas(String or_reas) {
		this.or_reas = or_reas;
	}
	public String getOr_status() {
		return or_status;
	}
	public void setOr_status(String or_status) {
		this.or_status = or_status;
	}
	public int getOr_pay() {
		return or_pay;
	}
	public void setOr_pay(int or_pay) {
		this.or_pay = or_pay;
	}
	
}
