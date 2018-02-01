package com.coolweather.app.model;

public class Province {
	private int id;
	private String provinceName;
	private String provincePyName;
	private String provinceCode;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getProvincePyName() {
		return provincePyName;
	}
	public void setProvincePyName(String provincePyName) {
		this.provincePyName = provincePyName;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
}
