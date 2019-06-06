package cn.xy.st.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author xuyao
 *	http service tools
 */
@Service
public class HttpService extends LogService{
	
	//默认utf-8
	public String get(String urlAll) {
		return get(urlAll, "utf-8");
	}
	
	public String get(String urlAll, String charsetName) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		String userAgent = "User-Agent:Mozilla/5.0(Macintosh;IntelMacOSX10_7_0)AppleWebKit/535.11(KHTML,likeGecko)Chrome/17.0.963.56Safari/535.11";// 模拟浏览器
		try {
			URL url = new URL(urlAll);
			HttpURLConnection connection = null;
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(30000);
			connection.setConnectTimeout(30000);
			connection.setRequestProperty("User-agent", userAgent);
			connection.connect();
			InputStream is = null;
			try{
				is = connection.getInputStream();
			}catch(Exception e){
				e.printStackTrace();
			}
			if(is==null)
				return null;
			reader = new BufferedReader(new InputStreamReader(is, charsetName));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	final WebClient webClient = new WebClient(BrowserVersion.CHROME);
	public String htmlunit(String urlAll) {
        HtmlPage page = null;
        try {
            page = webClient.getPage(urlAll);//尝试加载上面图片例子给出的网页
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            webClient.close();
        }
	   return  page.asText();
	}
	
}
