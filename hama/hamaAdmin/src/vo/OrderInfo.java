package vo;
import java.util.*;

public class OrderInfo {
	private String oi_id, mi_id, oi_name, oi_phone, oi_zip;
	private String oi_addr1, oi_addr2, oi_memo, oi_payment;
	private String oi_invoice, oi_status, oi_date, oi_redate;
	private String oi_sender,oi_sephone;
	private int oi_pay, oi_upoint , oi_spoint;
	private ArrayList<OrderDetail> detailList;
	
	
	public String getOi_sender() {
		return oi_sender;
	}
	public void setOi_sender(String oi_sender) {
		this.oi_sender = oi_sender;
	}
	public String getOi_sephone() {
		return oi_sephone;
	}
	public void setOi_sephone(String oi_sephone) {
		this.oi_sephone = oi_sephone;
	}
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
	public String getOi_name() {
		return oi_name;
	}
	public void setOi_name(String oi_name) {
		this.oi_name = oi_name;
	}
	public String getOi_phone() {
		return oi_phone;
	}
	public void setOi_phone(String oi_phone) {
		this.oi_phone = oi_phone;
	}
	public String getOi_zip() {
		return oi_zip;
	}
	public void setOi_zip(String oi_zip) {
		this.oi_zip = oi_zip;
	}
	public String getOi_addr1() {
		return oi_addr1;
	}
	public void setOi_addr1(String oi_addr1) {
		this.oi_addr1 = oi_addr1;
	}
	public String getOi_addr2() {
		return oi_addr2;
	}
	public void setOi_addr2(String oi_addr2) {
		this.oi_addr2 = oi_addr2;
	}
	public String getOi_memo() {
		return oi_memo;
	}
	public void setOi_memo(String oi_memo) {
		this.oi_memo = oi_memo;
	}
	public String getOi_payment() {
		return oi_payment;
	}
	public void setOi_payment(String oi_payment) {
		this.oi_payment = oi_payment;
	}
	public String getOi_invoice() {
		return oi_invoice;
	}
	public void setOi_invoice(String oi_invoice) {
		this.oi_invoice = oi_invoice;
	}
	public String getOi_status() {
		return oi_status;
	}
	public void setOi_status(String oi_status) {
		this.oi_status = oi_status;
	}
	public String getOi_date() {
		return oi_date;
	}
	public void setOi_date(String oi_date) {
		this.oi_date = oi_date;
	}
	public String getOi_redate() {
		return oi_redate;
	}
	public void setOi_redate(String oi_redate) {
		this.oi_redate = oi_redate;
	}
	public int getOi_pay() {
		return oi_pay;
	}
	public void setOi_pay(int oi_pay) {
		this.oi_pay = oi_pay;
	}
	public int getOi_upoint() {
		return oi_upoint;
	}
	public void setOi_upoint(int oi_upoint) {
		this.oi_upoint = oi_upoint;
	}
	public int getOi_spoint() {
		return oi_spoint;
	}
	public void setOi_spoint(int oi_spoint) {
		this.oi_spoint = oi_spoint;
	}
	public ArrayList<OrderDetail> getDetailList() {
		return detailList;
	}
	public void setDetailList(ArrayList<OrderDetail> detailList) {
		this.detailList = detailList;
	}
	
	
}
