package cn.xy.st.vo;

/**
 * 	抓取分红时候用的vo
 * */
public class Fhvo implements Comparable<Fhvo>{

	String code;
	String name;
	Integer count;
	Double totaldiv;/* total dividend */
	
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getTotaldiv() {
		return totaldiv;
	}
	public void setTotaldiv(Double totaldiv) {
		this.totaldiv = totaldiv;
	}
	
	@Override
    public int compareTo(Fhvo f) {
//		Double dthat = NumberUtil.doubleDiv(f.getTotaldiv(), f.getCount(), 4);
//		Double dthis = NumberUtil.doubleDiv(this.getTotaldiv(), this.getCount(), 4);
//        return dthat.compareTo(dthis);
		return f.getCount().compareTo(this.count);
    }
	
}
