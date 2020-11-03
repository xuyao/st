package cn.xy.st.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	private static final int SUCCESS_CODE = 200;
	
    public static String sendGetFundMon(String url, String bondfund, String host, String referer,List<NameValuePair> nameValuePairList) throws Exception{
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            client = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder(url);
            uriBuilder.addParameters(nameValuePairList);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            httpGet.setHeader(new BasicHeader("Accept", "*/*"));
            httpGet.setHeader(new BasicHeader("Accept-Encoding", "gzip, deflate, br"));
            httpGet.setHeader(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7"));
            httpGet.setHeader(new BasicHeader("Cookie", ConstsUtil.cookie2));
            httpGet.setHeader(new BasicHeader("Host", host));
            httpGet.setHeader(new BasicHeader("Connection", "keep-alive"));
            httpGet.setHeader(new BasicHeader("Referer", referer));
            httpGet.setHeader(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36"));
            response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (SUCCESS_CODE == statusCode){
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity,"UTF-8");
                return result;
            }else{
                System.out.println("GET请求失败！"+statusCode);
            }
        }catch (Exception e){
        	e.printStackTrace();
        } finally {
            response.close();
            client.close();
        }
        return null;
    }
    
    
    /**
     * 发送GET请求
     * @param url   请求url
     * @param nameValuePairList    请求参数
     * @throws Exception
     */
    public static String sendGet(String url, List<NameValuePair> nameValuePairList) throws Exception{
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            client = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder(url);
            uriBuilder.addParameters(nameValuePairList);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            httpGet.setHeader(new BasicHeader("Accept", "*/*"));
            httpGet.setHeader(new BasicHeader("Accept-Encoding", "gzip, deflate, br"));
            httpGet.setHeader(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7"));
            httpGet.setHeader(new BasicHeader("Cookie", ConstsUtil.cookie));
            httpGet.setHeader(new BasicHeader("Host", "fund.eastmoney.com"));
            httpGet.setHeader(new BasicHeader("Connection", "keep-alive"));
            httpGet.setHeader(new BasicHeader("Referer", "http://fund.eastmoney.com/data/fundranking.html"));
            httpGet.setHeader(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36"));
            response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (SUCCESS_CODE == statusCode){
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity,"UTF-8");
                return result;
            }else{
                System.out.println("GET请求失败！"+statusCode);
            }
        }catch (Exception e){
        	e.printStackTrace();
        } finally {
            response.close();
            client.close();
        }
        return null;
    	
    }

    /**
     * 发送POST请求
     * @param url
     * @param nameValuePairList
     * @throws Exception
     */
    public static String sendPost(String url, List<NameValuePair> nameValuePairList) throws Exception{
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            client = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            StringEntity entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            post.setEntity(entity);
            post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            post.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
            response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode){
                String result = EntityUtils.toString(response.getEntity(),"UTF-8");
                return result;
            }else{
                System.out.println("POST请求失败！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            response.close();
            client.close();
        }
        return null;
    }

    /**
     * 组织请求参数{参数名和参数值下标保持一致}
     * @param params    参数名数组
     * @param values    参数值数组
     * @return 参数对象
     */
    public static List<NameValuePair> getParams(Object[] params, Object[] values){
        boolean flag = params.length>0 && values.length>0 &&  params.length == values.length;
        if (flag){
            List<NameValuePair> nameValuePairList = new ArrayList<>();
            for(int i =0; i<params.length; i++){
                nameValuePairList.add(new BasicNameValuePair(params[i].toString(),values[i].toString()));
            }
            return nameValuePairList;
        }else{
            System.err.println("请求参数为空且参数长度不一致");
        }
        return null;
    }
}