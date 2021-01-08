package cn.xy.st;

import java.util.TreeMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.xy.st.service.FundManagerService;

public class FundManagerApp {
    public static void main( String[] args ) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContent.xml");
		FundManagerService fs = (FundManagerService)context.getBean("fundManagerService");
		fs.run();
		
		TreeMap<Float,String> tmap = fs.getTreeFundMap();
		for(Float key : tmap.keySet()){
		    System.out.println(tmap.get(key)+ " " + key);
		}
    }
}
