package cn.xy.st;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.xy.st.service.ShareService;
import cn.xy.st.service.ZqService;
import cn.xy.st.util.ConstsUtil;

public class App {
    public static void main( String[] args ) {
		String service = ConstsUtil.getValue("service");
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContent.xml");
		
		if("share".equals(service)){
			ShareService fs = (ShareService)context.getBean("shareService");
			fs.run();
		}

		if("zq".equals(service)){
			ZqService zs = (ZqService)context.getBean("zqService");
			zs.run();
		}
    }
    
}
