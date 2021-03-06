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
 * 基于基金收益的统计
 * @author xuyao
 *	share fund
 */
@Service
public class FundService {
	
	HttpUtil http = new HttpUtil();
	String ft = ConstsUtil.getValue("ft");
	String num = ConstsUtil.getValue("num");
//	String[] args={"zzf","1yzf","3yzf","6yzf","1nzf","2nzf","3nzf","jnzf","lszf"};
	String[] args={"1yzf","3yzf","6yzf","1nzf","2nzf","3nzf","jnzf"};
	
	String[][] year={{"2021-01-01","2022-01-01"},{"2020-01-01","2021-01-01"},{"2019-01-01","2020-01-01"},{"2018-01-01","2019-01-01"},{"2017-01-01","2018-01-01"}};
//	String[][] year={{"2019-01-01","2020-01-01"},{"2018-01-01","2019-01-01"},{"2017-01-01","2018-01-01"},{"2016-01-01","2017-01-01"},{"2015-01-01","2016-01-01"}
//	,{"2014-01-01","2015-01-01"},{"2013-01-01","2014-01-01"}};
	
	List<String> fundlist = null;
	
	public void run(){
//		List<String> list1 = guding();
//		List<String> list2 = year();
		
		String start = "";
		String end = DateUtil.formatNYR(new Date());
		
		//5 year 1/5
		start = DateUtil.formatNYR(new Date(), -5);
		List<String> list5 = oneYear(start, end, 858);
		//3 year 1/4
		start = DateUtil.formatNYR(new Date(), -3);
		List<String> list3 = oneYear(start, end, 1073);
		//2 year 1/3
		start = DateUtil.formatNYR(new Date(), -2);
		List<String> list2 = oneYear(start, end, 1430);
		//1 year 1/2
		start = DateUtil.formatNYR(new Date(), -1);
		List<String> list1 = oneYear(start, end, 2145);
		
		
		list5.retainAll(list3);
		list5.retainAll(list2);
		list5.retainAll(list1);
		
		System.out.println("==================分析基金如下");
		fundlist = list5;
		for(String s : fundlist){
			System.out.println(s);
		}
	}
	
	public List<String> oneYear(String begin, String end, int pn){
		List<String> list = new ArrayList<String>();
		List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
		String url = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft="+ft+"&rs=&gs=0&sc=qjzf&st=desc&sd="+begin+
				"&ed="+end+"&qdii=&tabSubtype=,,,,,&pi=1&pn="+pn+"&dx=1&v=0."+NumberUtil.getRandomNum(17);
		System.out.println(url);
		try {
			String result = http.sendGet(url, nameValuePairList);
			result = StrUtils.subString(result, "[\"", "\"]");
			String[] line = result.split("\",\"");
			for(String s : line){
				String[] c = s.split(",");
				list.add(c[0]);
				System.out.println(c[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(NumberUtil.genRd());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.println("===========================================");
		return list;
	}

	
	public List<String> guding(){
		List<String> list = new ArrayList<String>();
		boolean isfirst = true;
		String date = DateUtil.format(new Date(), DateUtil.STR_DATE_PATTERN);
		for(String arg : args){
			List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
			String url = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft="+ft+"&rs=&gs=0&sc="+arg+"&st=desc&sd="+date+
					"&ed="+date+"&qdii=&tabSubtype=,,,,,&pi=1&pn="+num+"&dx=1&v=0."+NumberUtil.getRandomNum(17);
			System.out.println(url);
			try {
				ArrayList<String> listfund = new ArrayList<String>();
				String result = http.sendGet(url, nameValuePairList);
				result = StrUtils.subString(result, "[\"", "\"]");
				String[] line = result.split("\",\"");
				for(String s : line ){
					String[] c = s.split(",");
					listfund.add(c[0]);
				}
				
				if(isfirst){
					list = listfund;
					isfirst = false;
				}else{
					list.retainAll(listfund);
				}
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
		boolean isfirst = true;
		for(String[] ys : year){
			List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
			String url = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft="+ft+"&rs=&gs=0&sc=qjzf&st=desc&sd="+ys[0]+
					"&ed="+ys[1]+"&qdii=&tabSubtype=,,,,,&pi=1&pn="+num+"&dx=1&v=0."+NumberUtil.getRandomNum(17);
			System.out.println(url);
			try {
				ArrayList<String> listfund = new ArrayList<String>();
				String result = http.sendGet(url, nameValuePairList);
				result = StrUtils.subString(result, "[\"", "\"]");
				String[] line = result.split("\",\"");
				for(String s : line){
					String[] c = s.split(",");
					listfund.add(c[0]);
				}
				if(isfirst){
					list = listfund;
					isfirst = false;
				}else{
					list.retainAll(listfund);
				}
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

	public List<String> getFundlist() {
		return fundlist;
	}

}
