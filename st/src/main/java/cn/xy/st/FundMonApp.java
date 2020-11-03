package cn.xy.st;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.xy.st.service.FundMonService;

public class FundMonApp {
    public static void main( String[] args ) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContent.xml");
		
		FundMonService fs = (FundMonService)context.getBean("fundMonService");
		fs.run();

    }
    
}
