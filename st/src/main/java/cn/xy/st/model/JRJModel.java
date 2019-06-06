package cn.xy.st.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xy.st.service.HttpService;
import cn.xy.st.util.ConstsUtil;
import cn.xy.st.util.NumberUtil;
import cn.xy.st.vo.DayKline;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 	jrj 金融界模型
 * @author xuyao
 *	all stock list service
 */

@Service
public class JRJModel {

	@Autowired
	HttpService httpService;
	
	String dk_year = ConstsUtil.getValue("dk_year");
	
	//获得金融界日线信息
	/*
	 * [20070305,46.41,44.38,45.98,46.17,43.53,419196,1.880502808E9,false]
	 *   日期             ,昨收       ,收盘       ,开盘       ,最高       ,最低       ,成交量      ,成交额
	 * 
	 * 
	 * */
	
	/**
	 * @param code 股票代码
	 * @param isDivid 是否分红
	 * @return
	 */
	public List<DayKline> getDayKline(String code, boolean isDivid){
		List<DayKline> list = new ArrayList<DayKline>();
		Map<String,Double> factor_map = new HashMap<String, Double>();
		Date d = new Date();
		String url = "http://flashdata2.jrj.com.cn/history/js/share/" + code 
				+ "/other/dayk_ex.js?random=" + d.getTime();
		String json = httpService.get(url);
		json = json.substring(json.indexOf("=")+1);
		JSONObject jo = JSONObject.parseObject(json);
		
		if(isDivid){
			JSONArray factorArr = jo.getJSONArray("factor");
			Iterator itFactor = factorArr.iterator();
			while(itFactor.hasNext()){
				JSONArray ja = (JSONArray)itFactor.next();
//				ja.getDouble(1);//比例
//				ja.getDouble(2);//折算后价格
				if(dk_year.compareTo(ja.getString(0)) <= 0){
					factor_map.put(ja.getString(0), ja.getDouble(1));
				}
			}
		}

		JSONArray ja = jo.getJSONArray("hqs");
		Iterator it = ja.iterator();
		//按照前复权计算
		double factor = 1;
		
		while(it.hasNext()){
			DayKline dk = new DayKline();
			JSONArray stka = (JSONArray)it.next();
			if(dk_year.compareTo(stka.getString(0)) <= 0){
				dk.setCode(code);
				dk.setDate(stka.getString(0));//日期
				dk.setLc(stka.getDoubleValue(1));//昨收
				dk.setO(stka.getDoubleValue(3));//开盘
				dk.setH(stka.getDoubleValue(4));
				dk.setL(stka.getDoubleValue(5));
				dk.setV(stka.getIntValue(6));
				if(isDivid && factor_map.get(dk.getDate()) != null)
					factor =  factor_map.get(dk.getDate()) * factor;
				dk.setC(NumberUtil.doubleMul(stka.getDoubleValue(2), factor));//收盘
				list.add(dk);
//				System.out.println(stka.getString(0)+ " " + dk.getC() + " " + dk.getLc());
			}
		}
		
		return list;
	}
}
