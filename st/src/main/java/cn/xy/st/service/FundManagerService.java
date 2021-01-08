package cn.xy.st.service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.NameValuePair;
import org.springframework.stereotype.Service;

import cn.xy.st.util.ConstsUtil;
import cn.xy.st.util.HttpUtil;
import cn.xy.st.util.NumberUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 基金经理
 * @author xuyao
 *	fund manager filter 
 */
@Service
public class FundManagerService {
	
	HttpUtil http = new HttpUtil();
	Map<String,String> managerFundMap = new HashMap<String,String>();
	TreeMap<Float,String> treeFundMap = new TreeMap<Float,String>();
	String managerYearStr = ConstsUtil.getValue("managerYear");
	Float managerYear = Float.valueOf(managerYearStr);
	
	public void run(){
	    System.out.println("开始运行！");
	    managerList();
	}
	
	
	public void managerList(){
		NumberFormat numberFormat = NumberFormat.getPercentInstance();
		boolean run = true;
		int i = 1;
		while(run){
//			System.out.println(i);
			String url = "http://fund.eastmoney.com/Data/FundDataPortfolio_Interface.aspx?dt=14&mc=returnjson&ft=all&pn=50&pi="
			+i+"&sc=totaldays&st=desc";
			String result = "";
			try {
				result = http.sendGet(url, new ArrayList<NameValuePair>());
				result = result.substring(result.indexOf("{"));
//				System.out.println(result);
				JSONObject jsonobj = JSONObject.parseObject(result);
				JSONArray ja = jsonobj.getJSONArray("data");
				Iterator it = ja.iterator();
				
				while(it.hasNext()){
					JSONArray js = (JSONArray)it.next();
					String yearstr = js.getString(6);
					Float yearint = Float.parseFloat(yearstr);
					Float year = yearint/365;
					if(year < managerYear){//任职大于4年
						run = false;
						continue;
					}
						String managerNo = js.getString(0);
						String managerName = js.getString(1);
						String fundNos = js.getString(4);
						String fundNames = js.getString(5);
						String funds = js.getString(4);//掌管的基金
						String profitstr = js.getString(7);//总收益
						String bestfundNo = js.getString(8);//代表作基金编号
						String bestfund = js.getString(9);//代表作基金名称
						String bestprofit = js.getString(11);//最佳基金收益
//						profitstr = bestprofit;
						
						if("--".equals(profitstr))
							profitstr = "0.1%";
						Number profitnum = numberFormat.parse(profitstr);
						Float profit = profitnum.floatValue();
						Float div = profit/year;
						if(div > 0.3){
							System.out.println(managerNo+" "+managerName+" "+String.format("%.2f", year)+
									" "+profitstr+" "+String.format("%.2f", div) +" "+fundNos);
							String[] arrfundNos = fundNos.split(",");
							treeFundMap.put(div, managerName);
							for(String fundNo : arrfundNos){
								managerFundMap.put(fundNo, fundNo);
							}
						}
				}
			
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				i++;
				Thread.sleep(NumberUtil.genRd()*100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	public Map<String, String> getManagerFundMap() {
		return managerFundMap;
	}

	public TreeMap<Float, String> getTreeFundMap() {
		return treeFundMap;
	}
	
}
