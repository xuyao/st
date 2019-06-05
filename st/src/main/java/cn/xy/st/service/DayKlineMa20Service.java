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
	
	double totalBanlance;
	
	public void run(){
		
		//获取日线
		List<DayKline> list = jRJModel.getDayKline("601318", false);
		
		for(int i = 5;i<100;i++){
			ma = i;//赋值均线
			Account account = new Account();
			//init account
			account.setBalance(total);
			
			double ma20 = -1;//20日均线值
			
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
						if(account.getBalance() == 0){//没钱没买了
							//nothing to do
						}else{
							flag = 1;//buy
						}
					}else if(dk.getLc() > ma20 && dk.getC() > ma20) {
						flag = 0;
					}else if(dk.getLc() < ma20 && dk.getC() < ma20) {
						flag = 0;
					}else if(dk.getLc() > ma20 && dk.getC() < ma20) {
						if(account.getLeft() == 0){
							//noting to do
						}else{
							flag = 2;
						}
					}
				}
				
				//3、计算资金
				if(flag == 1) {//买入的时候
					int amount = NumberUtil.amount(total, dk.getC());//caculate amount
					double totalprice = amount*100*dk.getC();
					account.setAmount(amount);
					account.setLeft(account.getBalance() - totalprice);
					account.setBalance(0);
//					System.out.println("buy " + dk.getDate() + " " + amount + " " +totalprice + " " +account.getLeft() + " " +dk.getC());
				}
				if(flag == 2) {//卖出的时候
					int amount = account.getAmount();
					if(amount == 0)
						continue;
					double totalprice = amount*100*dk.getC()*(1-tax);
					account.setBalance(account.getLeft() + totalprice);
					account.setAmount(0);
					account.setLeft(0);
//					System.out.println("sell "  + dk.getDate() + " " + amount + " " +totalprice + " " +account.getLeft() + " " + dk.getC());
				}
				
				flag = 0;//hold
				
				lastdkline = dk.getC();
				
				totalBanlance = account.getBalance() + account.getAmount()*100*lastdkline + account.getLeft();
//				System.out.println(account.getBalance() + account.getAmount()*100*lastdkline + account.getLeft());
			}
			
			System.out.println(ma + "日均线： "+totalBanlance);
			
		}
		
		

		
	}

}
