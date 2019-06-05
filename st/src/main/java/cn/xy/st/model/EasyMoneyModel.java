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
import cn.xy.st.util.UUIDUtil;
import cn.xy.st.vo.DayKline;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * east money 东方财富模型
 * @author xuyao
 *	all stock list service
 */

@Service
public class EasyMoneyModel {

	@Autowired
	HttpService httpService;
	
	String dk_year = ConstsUtil.getValue("dk_year");
	
	//获得金融界日线信息
	/*http://pdfm.eastmoney.com/EM_UBG_PDTI_Fast/api/js?token=4f1862fc3b5e77c150a2b985b12db0fd&rtntype=6&id=0000602&type=k&authorityType=fa&cb=jsonp1559728734182
	 * 
	 * "2019-06-05,4.76,4.72,4.81,4.71,192967,91827489,2.12%"
	 *   日期             ,昨收       ,收盘       ,开盘       ,最高       ,最低       ,成交量      ,成交额
	 * 
	 * 
	 * */
	
	/**
	 * @param code 股票代码
	 * @param marketType 1-上证 ， 2-深证
	 * @return
	 */
	public List<DayKline> getDayKline(String code , String marketType){
		List<DayKline> list = new ArrayList<DayKline>();
		Map<String,Double> factor_map = new HashMap<String, Double>();
		Date d = new Date();
		String url = "http://pdfm.eastmoney.com/EM_UBG_PDTI_Fast/api/js?"
				+ "token="+UUIDUtil.genUUID()+"&rtntype=6&id=" + code + marketType
				+"&type=k&authorityType=fa&cb=jsonp" + d.getTime();
		
		
		String json = httpService.get(url);
		json = json.substring(json.indexOf("=")+1);
		JSONObject jo = JSONObject.parseObject(json);
		
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
				dk.setC(NumberUtil.doubleMul(stka.getDoubleValue(2), factor));//收盘
				list.add(dk);
			}
		}
		return list;
	}
}
