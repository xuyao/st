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
public class SnowBallModel {

	@Autowired
	HttpService httpService;
	
	String dk_year = ConstsUtil.getValue("dk_year");
	
	//获得金融界日线信息
	/* 前复权：https://stock.xueqiu.com/v5/stock/chart/kline.json?symbol=SH600371&begin=1559892210785&period=day&type=before&count=-142&indicator=kline,pe,pb,ps,pcf,market_capital,agt,ggt,balance
	 * 不复权：https://stock.xueqiu.com/v5/stock/chart/kline.json?symbol=SH600371&begin=1559892235863&period=day&type=normal&count=-142&indicator=kline,pe,pb,ps,pcf,market_capital,agt,ggt,balance
	 * "2019-06-05,4.76,4.72,4.81,4.71,192967,91827489,2.12%"
	 *   日期             ,昨收       ,收盘       ,开盘       ,最高       ,最低       ,成交量      ,成交额
	 * 
	 * 
	 * */
	
	/**
	 * @param code 股票代码
	 * @param marketType SH-上证 ， SZ-深证
	 * @return
	 */
	public List<DayKline> getDayKline(String code, String marketType, boolean ifreright){
		List<DayKline> list = new ArrayList<DayKline>();
		Map<String,Double> factor_map = new HashMap<String, Double>();
		Date d = new Date();
		String fa = "";
		if(ifreright)//是否复权计算
			fa = "before";
		else
			fa = "normal";
//		String url = "http://pdfm.eastmoney.com/EM_UBG_PDTI_Fast/api/js?"
//				+ "token="+UUIDUtil.genUUID()+"&rtntype=6&id=" + code + marketType
//				+"&type=k&authorityType=" + fa+ "&cb=jsonp" + d.getTime();
		String url = "https://stock.xueqiu.com/v5/stock/chart/kline.json?symbol=" + marketType + 
				code + "&begin=" + d.getTime() + "&period=day&type=" + 
				fa + "&count=-142&indicator=kline,pe,pb,ps,pcf,market_capital,agt,ggt,balance";
		
		String json = httpService.htmlunit(url);
		System.out.println(json);
		JSONObject jo = JSONObject.parseObject(json);
		JSONArray items = jo.getJSONArray("item");
		Iterator it = items.iterator();
		
		double lc = 0;
		while(it.hasNext()){
			String stka = (String)it.next();
			String[] stkaArr = stka.split(",");//这个没有昨收
			if(dk_year.compareTo(stkaArr[0].replace("-", "")) <= 0){
				DayKline dk = new DayKline();
				dk.setCode(code);
				dk.setO(Double.parseDouble(stkaArr[2]));
				dk.setC(Double.parseDouble(stkaArr[5]));
				dk.setH(Double.parseDouble(stkaArr[3]));
				dk.setL(Double.parseDouble(stkaArr[4]));
				dk.setLc(lc);
				lc = dk.getC();//赋值今天的收盘
				list.add(dk);
//				System.out.println(stkaArr[0].replace("-", "")+ " " + dk.getC() + " " + dk.getLc());
			}
		}
		
		return list;
	}
	
	public static void main(String[] args){
		SnowBallModel sb = new SnowBallModel();
		sb.httpService = new HttpService();
		sb.getDayKline("601318", "SH" ,false);
	}
	
}
