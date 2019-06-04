package cn.xy.st.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xy.st.model.JRJModel;
import cn.xy.st.util.ConstsUtil;
import cn.xy.st.util.NumberUtil;
import cn.xy.st.vo.DayKline;

/**
 * 	20日均线法
 * @author xuyao
 *	day k line service
 */

@Service
public class DayKlineMa20Service {
	
	@Autowired
	JRJModel jRJModel;
	
	//ma20
	Integer ma = 20;
	//total
	Double total = Double.parseDouble(ConstsUtil.getValue("total"));
	//tax
	Double tax = Double.parseDouble(ConstsUtil.getValue("tax"));
	
	Queue<Double> queue = new LinkedList<Double>();
	
	/**
	 * 
	 */
	public void run(){
		List<DayKline> list = jRJModel.getDayKline("000060", false);
		double ma20 = -1;
		
		for(DayKline dk : list){
			queue.offer(dk.getC());//放入收盘价
			if(queue.size()==ma){
				double ma20Total = 0;
				for(Double close : queue){
					ma20Total = ma20Total+close;
				}
				ma20 = NumberUtil.doubleDiv(ma20Total, ma, 3);
//				System.out.println(dk.getDate() + " "+ma20);
				
				if(ma20 > 0 && dk.getC() > ma20) {
					System.out.println(dk.getDate() + " "+ma20 + " " + dk.getC());
				}
				queue.poll();//干掉第一个元素
			}
		}
	}
	
}
