package vo;

// 한 명의 회원 정보를 저장할 클래스
public class MemberInfo {
	private String mi_id, mi_pw, mi_name, mi_birth, mi_phone ,mi_memo;
	private String mi_email, mi_lastlogin, mi_joindate, mi_status;
	private int mi_point;

	public String getMi_id() {
		return mi_id;
	}
	public void setMi_id(String mi_id) {
		this.mi_id = mi_id;
	}
	public String getMi_pw() {
		return mi_pw;
	}
	public void setMi_pw(String mi_pw) {
		this.mi_pw = mi_pw;
	}
	public String getMi_name() {
		return mi_name;
	}
	public void setMi_name(String mi_name) {
		this.mi_name = mi_name;
	}

	public String getMi_birth() {
		return mi_birth;
	}
	public void setMi_birth(String mi_birth) {
		this.mi_birth = mi_birth;
	}
	public String getMi_phone() {
		return mi_phone;
	}
	public void setMi_phone(String mi_phone) {
		this.mi_phone = mi_phone;
	}
	public String getMi_email() {
		return mi_email;
	}
	public void setMi_email(String mi_email) {
		this.mi_email = mi_email;
	}
	public String getMi_lastlogin() {
		return mi_lastlogin;
	}
	public void setMi_lastlogin(String mi_lastlogin) {
		this.mi_lastlogin = mi_lastlogin;
	}
	public String getMi_joindate() {
		return mi_joindate;
	}
	public void setMi_joindate(String mi_joindate) {
		this.mi_joindate = mi_joindate;
	}
	public String getMi_status() {
		return mi_status;
	}
	public void setMi_status(String mi_status) {
		this.mi_status = mi_status;
	}
	public int getMi_point() {
		return mi_point;
	}
	public void setMi_point(int mi_point) {
		this.mi_point = mi_point;
	}
	public String getMi_memo() {
		return mi_memo;
	}
	public void setMi_memo(String mi_memo) {
		this.mi_memo = mi_memo;
	}
	
}
