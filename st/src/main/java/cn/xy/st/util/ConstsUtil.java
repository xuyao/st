package cn.xy.st.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConstsUtil {
	
	public static final Properties prop = new Properties();
	public static String cookie = "FundTradeLoginUser=QXBDWnNW2ehJDscDoPJAJWltBo8gqCvxEZZklsQ8ja4v1K6xK0TFBpQrtOBQp1p0p8Ku01d9; FundTradeLoginCard=0; "
			+ "FundTradeLoginTab=0; st_pvi=18619731129501; st_sp=2020-07-07%2012%3A01%3A48; st_inirUrl=http%3A%2F%2Ffundf10.eastmoney.com%2Fjjfl_519672.html";
	
	static{
		InputStream is = ConstsUtil.class.getClassLoader().getResourceAsStream("conf.properties");
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key){
		return prop.getProperty(key);
	}
	
}
