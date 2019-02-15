package cn.xy.st;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.xy.st.service.AllStocksService;
import cn.xy.st.service.DividendService;

public class App {
    public static void main( String[] args ) {
		System.out.println("#########start!!!");
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContent.xml");
		
		//all stock list
		AllStocksService allStocksService = (AllStocksService)context.getBean("allStocksService");
		allStocksService.run();
		
		//caculate stock bonus
//		DividendService dividendService = (DividendService)context.getBean("dividendService");
//		dividendService.run();
//		dividendService.caculatePriceDiv();
		
		System.out.println("#########end!!!");
    }
    
}
