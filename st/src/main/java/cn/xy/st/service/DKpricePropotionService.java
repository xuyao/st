package cn.xy.st.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xy.st.model.JRJModel;
import cn.xy.st.util.NumberUtil;
import cn.xy.st.vo.DayKline;

/**
 * 	股票日线统计，复权后，股价小于当前收盘价的历史占比
 * @author xuyao
 *	day k line service
 */
@Service
public class DKpricePropotionService {
	
	@Autowired
	JRJModel jRJModel;
	
	/**
	 * 
	 */
	public void run(){
		
		List<DayKline> list = jRJModel.getDayKline("000060", true);
		int count = list.size();
		DayKline last = list.get(count-1);
		int less = 0;//记录收盘价少于以往价格的数量
		for(DayKline dk : list){
			System.out.println(last.getC() + " " + dk.getC());
			if(last.getC() < dk.getC()) {
				//如果最后一个收盘价，小于之前的收盘，计数
				less++;
			}
		}
		System.out.println("股票日线收盘数量："+count);
		System.out.println("股票收盘价格小于以往日线收盘数量："+less);
		System.out.println("股票价格，比历史日线低价格占比："+NumberUtil.doubleDiv(less, count, 3));
	}
	
}
