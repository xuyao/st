package cn.xy.st.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xy.st.util.ConstsUtil;
import cn.xy.st.util.NumberUtil;
import cn.xy.st.vo.Fhvo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 	从东方财富拿到股票分红数据，分红次数和分红额度，进行统计
 * @author xuyao
 *	caculate stock bonus sort and save
 */
@Service
public class DividendService {
	
	@Autowired
	HttpService httpService;
	
	@Autowired
	AllStocksService allStocksService;
	
	String[] years = null;
	
	Map<String,Fhvo> map = new HashMap<String,Fhvo>();
	
	List<Fhvo> list = new ArrayList<Fhvo>();
	
	Map<String,String> stockNameMap = new HashMap<String,String>();
	
	StringBuilder sb = new StringBuilder();
	
	
	/**
	 * save times of stock dividend
	 */
	public void run(){
		//init all stock map for name
		init();
		//loop years and group by count
		for(String year : years){
			String url = "http://data.eastmoney.com/DataCenter_V3/yjfp/getlist.ashx?js=&pagesize=200&page=1&sr=-1&sortType=XJFH"
					+"&mtk=%C8%AB%B2%BF%B9%C9%C6%B1&filter=(ReportingPeriod=^"+year+"^)&rt="+ new Date().getTime();
			String result = httpService.get(url, "gb2312");
			
			JSONObject json = JSONObject.parseObject(result);
			JSONArray array = json.getJSONArray("data");
			Iterator it = array.iterator();
			while(it.hasNext()){
				JSONObject jo = (JSONObject)it.next();
//				String name = jo.getString("Name");
				String code = jo.getString("Code");
				if( jo.getString("XJFH").endsWith("-"))
					continue;
				Double xjfh = jo.getDouble("XJFH");
		 		if(map.get(code) != null){
		 			Fhvo fhvo = map.get(code);
		 			fhvo.setCount(fhvo.getCount()+1);
		 			fhvo.setTotaldiv(fhvo.getTotaldiv()+xjfh);//历次分红相加
		 		}else{
		 			Fhvo fhvo = new Fhvo();
		 			fhvo.setCode(code);
		 			fhvo.setCount(1);
		 			fhvo.setTotaldiv(xjfh);
		 			map.put(code, fhvo);
		 		}
		 		
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//deal dividend times > 3
		map.forEach((key, value) -> {
			if(value.getCount() > 3){
				value.setName(stockNameMap.get(key));//set stock name
				list.add(value);
			}
		});
		Collections.sort(list);
		writeList(list);
	}
	
	
	/****caculate the relationship between price and dividend times*/
	public void caculatePriceDiv(){
		
		List<String> lines = null;
		try {
			lines = FileUtils.readLines(new File(ConstsUtil.getValue("fhsort")), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Map<String, Double> map = allStocksService.getStockPrice();
		for(String line : lines){
			String[] arr = line.split(" ");
			Double price = map.get(arr[0]);
			sb.append(arr[0]).append(" ").append(arr[1]).append(" ").append(arr[2]).append(" ")
			.append(price).append(" ").append(arr[3]).append(" ")
			.append(NumberUtil.doubleDiv(Double.parseDouble(arr[3]), price, 4))
			.append("\n");
		}
		
		//write to file
		try {
			File f = new File(ConstsUtil.getValue("pricedivd"));
			if(!f.exists())
				f.createNewFile();
			FileUtils.writeStringToFile(f, sb.toString().substring(0, sb.lastIndexOf("\n")), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 	init stock name map
	 * */
	void init(){
		//stock amount 
		List<String> lines = null;
		try {
			lines = FileUtils.readLines(new File(ConstsUtil.getValue("stockpath")), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String s : lines){
			String[] arr = s.split(",");
			stockNameMap.put(arr[0], arr[1]);
		}
		//dividend years
		String fhyear = ConstsUtil.getValue("fhyear");
		years = fhyear.split(",");
	}
	
	/**
	 * 	save stock bonus to file
	 * */
	void writeList(List<Fhvo> list){
		for(Fhvo f : list){
			sb.append(f.getCode()).append(" ").append(f.getName()).append(" ").append(f.getCount())
			.append(" ").append(NumberUtil.doubleDiv(f.getTotaldiv(), f.getCount(), 4)).append("\n");
//			System.out.println(f.getCode() + " " + f.getName() +" "  +f.getCount());
		}
		//write to file
		try {
			File f = new File(ConstsUtil.getValue("fhsort"));
			if(!f.exists())
				f.createNewFile();
			FileUtils.writeStringToFile(f, sb.toString().substring(0, sb.lastIndexOf("\n")), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
