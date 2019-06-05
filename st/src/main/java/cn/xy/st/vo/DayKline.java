package cn.xy.st.vo;

public class DayKline {

	private String code;//股票编码
	private String name;//股票名称
	private String date;//日期
	private double lc;//昨收
	private double c;//收盘价
	private double o;//开盘
	private double h;//最高
	private double l;//最低
	private int v;//成交量
	private int q;//成交额
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getLc() {
		return lc;
	}
	public void setLc(double lc) {
		this.lc = lc;
	}
	public double getC() {
		return c;
	}
	public void setC(double c) {
		this.c = c;
	}
	public double getO() {
		return o;
	}
	public void setO(double o) {
		this.o = o;
	}
	public double getH() {
		return h;
	}
	public void setH(double h) {
		this.h = h;
	}
	public double getL() {
		return l;
	}
	public void setL(double l) {
		this.l = l;
	}
	public int getV() {
		return v;
	}
	public void setV(int v) {
		this.v = v;
	}
	public int getQ() {
		return q;
	}
	public void setQ(int q) {
		this.q = q;
	}

}
