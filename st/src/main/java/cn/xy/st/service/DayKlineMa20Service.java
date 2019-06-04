package cn.xy.st.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xy.st.model.JRJModel;
import cn.xy.st.util.ConstsUtil;
import cn.xy.st.util.NumberUtil;
import cn.xy.st.vo.Account;
import cn.xy.st.vo.DayKline;

/**
 * 	20日均线法
 *  @author xuyao
 *	ma20 service
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
	
	int amount = 0;//stock amount
	
	Queue<Double> queue = new LinkedList<Double>();
	
	int flag = 0;//0-hold, 1-buy, 2-sell
	
	Account account = new Account();
	
	public void run(){
		//init account
		account.setBalance(total);
		
		
		//获取日线
		List<DayKline> list = jRJModel.getDayKline("601318", false);
		double ma20 = -1;
		
		double lastdkline = 0;
		for(DayKline dk : list){//日线回放
			
			queue.offer(dk.getC());//放入收盘价
			if(queue.size()==ma){
				double ma20Total = 0;
				for(Double close : queue){
					ma20Total = ma20Total+close;
				}

				//1、计算20日均线
				ma20 = NumberUtil.doubleDiv(ma20Total, ma, 3);
				
				queue.poll();//干掉第一个元素
			}
			
			//2、设置买卖位
			if(ma20 > 0) {//如果均线有效
				if(dk.getLc() < ma20 && dk.getC() > ma20) {//收盘价格大于20日均线
//					System.out.println(dk.getDate() + " "+ma20 + " " + dk.getC());
					flag = 1;//buy
				}else if(dk.getLc() > ma20 && dk.getC() > ma20) {
					flag = 0;
				}else if(dk.getLc() < ma20 && dk.getC() < ma20) {
					flag = 0;
				}else if(dk.getLc() > ma20 && dk.getC() < ma20) {
					flag = 2;
				}
			}
			
			//3、计算资金
			if(flag == 1) {//买入的时候
				int amount = NumberUtil.amount(total, dk.getC());//caculate amount
				double totalprice = amount*100*dk.getC();
				account.setAmount(amount);
				account.setBalance(0);
				account.setLeft(account.getBalance() - totalprice);
			}
			if(flag == 2) {//卖出的时候
				int amount = account.getAmount();
				double totalprice = amount*100*dk.getC()*(1-tax);
				account.setAmount(0);
				account.setBalance(account.getLeft() + totalprice);
				account.setLeft(0);
			}
			
			lastdkline = dk.getC();
			
		}
		
		System.out.println(account.getBalance() + account.getAmount()*lastdkline + account.getLeft());
	}

}
