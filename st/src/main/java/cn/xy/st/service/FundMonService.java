package cn.xy.st.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import cn.xy.st.util.ConstsUtil;
import cn.xy.st.util.DateUtil;
import cn.xy.st.util.HttpUtil;
import cn.xy.st.util.MailSenderUtil;
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
	String filePath = ConstsUtil.getValue("filepath");
	  
	public void run(){
	    System.out.println("开始运行！");
	    String[] bondfundsList = this.bondfunds.split(",");

	    StringBuilder sb = new StringBuilder("运行结果如下：").append("\n");
	    for (String bondfund : bondfundsList) {
	      Integer times = Integer.valueOf(bondfunds(bondfund));
	      String pzs = panzhong(bondfund);
	      String[] ar = pzs.split(",");
	      if ((!ar[1].startsWith("-")) || 
	        (times.intValue() < 2)) continue;
	      sb.append(ar[0] + "连续跌" + times + "天，盘中估计为" + ar[1] + "， 建议加仓！").append("\n");
	    }

	    try
	    {
	      FileUtils.writeStringToFile(new File(this.filePath), sb.toString(), "gbk");
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    
	    new MailSenderUtil().sendMail(DateUtil.formatNYR(new Date())+"今日基金盘面",
	    		sb.toString());
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
