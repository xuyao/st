package cn.xy.st;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.xy.st.service.FundService;
import cn.xy.st.util.ConstsUtil;

public class App {
    public static void main( String[] args ) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContent.xml");
		
		FundService fs = (FundService)context.getBean("fundService");
		fs.run();

    }
    
}
