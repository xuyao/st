package cn.xy.st.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import cn.xy.st.util.ConstsUtil;
import cn.xy.st.util.HttpUtil;
import cn.xy.st.util.NumberUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author xuyao
 *	fund monitor 
 */
@Service
public class FundMonService {
	
	HttpUtil http = new HttpUtil();
	String bondfunds = ConstsUtil.getValue("bondfunds");
	
	public void run(){
		System.out.println("开始运行！");
		String[] bondfundsList = bondfunds.split(",");
		
		for(String bondfund : bondfundsList){
			Integer times = bondfunds(bondfund);
			String pzs = panzhong(bondfund);
			String[] ar = pzs.split(",");
			if(ar[1].startsWith("-")){
				if(times>=2){
					System.out.println(ar[0] + "连续跌"+times+"天，且今天盘中估计为"+ar[1]+"， 建议加仓！");
				}
			}
		}
	}
	
	
	public int bondfunds(String bondfund){
		int times = 0;//连跌次数
//		String url = "http://fundf10.eastmoney.com/jjjz_"+bondfund+".html";
		String url = "http://api.fund.eastmoney.com/f10/lsjz?callback=&fundCode="+bondfund+
				"&pageIndex=1&pageSize=20&startDate=&endDate=&_="+NumberUtil.genRd();
		String result = "";
		try {
			result = http.sendGetFundMon(url, bondfund, "api.fund.eastmoney.com", 
					"http://fundf10.eastmoney.com/jjjz_"+bondfund+".html",new ArrayList<NameValuePair>());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
//		System.out.println(result);
		JSONObject jsono = JSONObject.parseObject(result).getJSONObject("Data");
		JSONArray jsona = jsono.getJSONArray("LSJZList");
		Iterator it = jsona.iterator();
		
		while(it.hasNext()){
			JSONObject jo = (JSONObject)it.next();
			if(jo.getString("JZZZL").startsWith("-")){//如果跌，就计数
				times++;
			}else{//如果不跌，不需加仓，直接退出
				break;
			}
			if(times>=2){
//				System.out.println(bondfund + "连续跌"+times+"天，且今天盘中估计为负值， 建议加仓！");
				break;
			}
		}
		
		try {
			Thread.sleep(NumberUtil.genRd()*10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return times;
	}
	
	
	public String panzhong(String bondfund){//拿到盘中净值
		String url = "http://fundf10.eastmoney.com/jjjz_"+bondfund+".html";
		String result = "";
		try {
			result = http.sendGetFundMon(url, bondfund, "fundf10.eastmoney.com",
					"http://fund.eastmoney.com/"+bondfund+".html",new ArrayList<NameValuePair>());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
//		System.out.println(result);
		Document doc = Jsoup.parse(result);
		Element fundGszf = doc.getElementById("fund_gszf");
		Elements h4s = doc.getElementsByTag("h4");
		Element h4 = h4s.get(0);
//		System.out.println(h4.text()+" "+fundGszf.text());
		return h4.text()+","+fundGszf.text();
	}
	
	
}
