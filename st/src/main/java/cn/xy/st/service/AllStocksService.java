package cn.xy.st.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xy.st.util.ConstsUtil;

/**
 * 	得到所有的股票名称和编号
 * @author xuyao
 *	all stock list service
 */
@Service
public class AllStocksService {
	
	@Autowired
	HttpService httpService;
	
	String url = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?cb=&type=CT&token=4f1862fc3b5e77c150a2b985b12db0fd"
			+"&sty=FCOIATC&js=(x)&cmd=C._A&st=(Code)&sr=1&p=1&ps=5000&_="+ new Date().getTime();
	
	Map<String, Double> map = new HashMap<String, Double>();;
	
	/**
	 * save all stocks's code and name
	 */
	public void run(){
		
		String result = httpService.get(url, "utf-8");
		
		String[] starr = result.split("\",\"");

		String content = "";
		for(String s : starr){
			String[] sto = s.split(",");
			content = content + sto[1] +","+sto[2] + "\n";
		}
		
		content = content.substring(0, content.lastIndexOf('\n'));
		
		try {
			File f = new File(ConstsUtil.getValue("stockpath"));
			if(!f.exists())
				f.createNewFile();
			FileUtils.writeStringToFile(f, content, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * stock price for caculation
	 */
	public Map<String, Double> getStockPrice(){
		String result = httpService.get(url, "utf-8");
		
		String[] starr = result.split("\",\"");

		for(String s : starr){
			String[] sto = s.split(",");
			map.put(sto[1], Double.parseDouble(sto[12]));
		}
		
		return map;
	}
	
}
