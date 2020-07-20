package cn.xy.st.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.springframework.stereotype.Service;

import cn.xy.st.util.ConstsUtil;
import cn.xy.st.util.DateUtil;
import cn.xy.st.util.HttpUtil;
import cn.xy.st.util.NumberUtil;
import cn.xy.st.util.StrUtils;

/**
 * @author xuyao
 *	share fund
 */
@Service
public class FundService {
	
	HttpUtil http = new HttpUtil();
	String ft = ConstsUtil.getValue("ft");
	String[] args={"zzf","1yzf","3yzf","6yzf","1nzf","2nzf","3nzf","jnzf","lszf"};
	
	String[][] year={{"2019-01-01","2020-01-01"},{"2018-01-01","2019-01-01"},{"2017-01-01","2018-01-01"},{"2016-01-01","2017-01-01"}};
//	String[][] year={{"2019-01-01","2020-01-01"},{"2018-01-01","2019-01-01"},{"2017-01-01","2018-01-01"},{"2016-01-01","2017-01-01"},{"2015-01-01","2016-01-01"}
//	,{"2014-01-01","2015-01-01"},{"2013-01-01","2014-01-01"}};
	
	public void run(){
		List<String> list1 = guding();
		List<String> list2 = year();
		list1.retainAll(list2);
		
		System.out.println("==================分析基金如下");
		for(String s : list1){
			System.out.println(s);
		}
	}
	
	
	public List<String> guding(){
		List<String> list = new ArrayList<String>();
		String date = DateUtil.format(new Date(), DateUtil.STR_DATE_PATTERN);
		for(String arg : args){
			List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
			String url = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft="+ft+"&rs=&gs=0&sc="+arg+"&st=desc&sd="+date+
					"&ed="+date+"&qdii=&tabSubtype=,,,,,&pi=1&pn=2000&dx=1&v=0."+NumberUtil.getRandomNum(17);
			try {
				ArrayList<String> listfund = new ArrayList<String>();
				String result = http.sendGet(url, nameValuePairList);
				result = StrUtils.subString(result, "[\"", "\"]");
				String[] line = result.split("\",\"");
				for(String s : line ){
					String[] c = s.split(",");
					listfund.add(c[0]);
				}
				
				if(list.size() == 0)
					list = listfund;
				else
					list.retainAll(listfund);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(NumberUtil.genRd());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	public List<String> year(){
		List<String> list = new ArrayList<String>();
		for(String[] ys : year){
			List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
			String url = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft="+ft+"&rs=&gs=0&sc=qjzf&st=desc&sd="+ys[0]+
					"&ed="+ys[1]+"&qdii=&tabSubtype=,,,,,&pi=1&pn=2000&dx=1&v=0."+NumberUtil.getRandomNum(17);
			try {
				ArrayList<String> listfund = new ArrayList<String>();
				String result = http.sendGet(url, nameValuePairList);
				result = StrUtils.subString(result, "[\"", "\"]");
				String[] line = result.split("\",\"");
				for(String s : line ){
					String[] c = s.split(",");
					listfund.add(c[0]);
				}
				
				if(list.size() == 0)
					list = listfund;
				else
					list.retainAll(listfund);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(NumberUtil.genRd());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

}
