package com.example.demo;

public class Pass {

	private int pass_id;
	private String from_id;
	private String to_id;
	private int flag;
	private String limit_price;
	private String card_num;
	
	
	public String getCard_num() {
		return card_num;
	}
	
	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}
	public void setPass_id(int pass_id) {
		this.pass_id = pass_id;
	}
	public void setFrom_id(String from_id) {
		this.from_id = from_id;
	}
	public void setTo_id(String to_id) {
		this.to_id = to_id;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public void setLimit_price(String limit_price) {
		this.limit_price = limit_price;
	}
	
	
	public int getPass_id() {
		return pass_id;
	}
	public String getFrom_id() {
		return from_id;
	}
	public String getTo_id() {
		return to_id;
	}
	public int getFlag() {
		return flag;
	}
	public String getLimit_price() {
		return limit_price;
	} 
}
