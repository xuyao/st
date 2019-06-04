package cn.xy.st;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.xy.st.service.AllStocksService;
import cn.xy.st.service.DayKlineMa20Service;
import cn.xy.st.service.DayKlinepricePropotionService;
import cn.xy.st.service.DividendService;

public class App {
    public static void main( String[] args ) {
		System.out.println("#########start!!!");
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContent.xml");
		Scanner scanner = new Scanner(System.in);
		String in = scanner.nextLine();

		switch(in){
	    case "1" ://all stock list --1
			AllStocksService allStocksService = (AllStocksService)context.getBean("allStocksService");
			allStocksService.run();
	       break;
	    case "2" ://caculate stock bonus --2
			DividendService dividendService = (DividendService)context.getBean("dividendService");
			dividendService.run();
			dividendService.caculatePriceDiv();
	       break;
	    case "3" ://day kline price statistic --3
	    	DayKlinepricePropotionService dayKlinepricePropotionService = (DayKlinepricePropotionService)context.getBean("dayKlinepricePropotionService");
	    	dayKlinepricePropotionService.run();
	       break;
	    case "4" ://day kline ma 20 --4
	    	DayKlineMa20Service dayKlineMa20Service = (DayKlineMa20Service)context.getBean("dayKlineMa20Service");
	    	dayKlineMa20Service.run();
	       break;
	    default : //可选
	    	System.out.println("Noting!!!");
		}
		
		scanner.close();
		System.out.println("#########end!!!");
    }
    
}
