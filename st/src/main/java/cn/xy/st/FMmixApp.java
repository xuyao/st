package cn.xy.st;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.xy.st.service.FundManagerService;
import cn.xy.st.service.FundService;

public class FMmixApp {
    public static void main( String[] args ) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContent.xml");
		
		FundService fs = (FundService)context.getBean("fundService");
		fs.run();
		List<String> list = fs.getFundlist();
		
		FundManagerService fms = (FundManagerService)context.getBean("fundManagerService");
		fms.run();
		Map<String,String> map = fms.getManagerFundMap();
		
		System.out.println("===============================================================");
		for(String fund : list){
			if(map.get(fund)!=null)
				System.err.println(fund);
		}

    }
    
}
