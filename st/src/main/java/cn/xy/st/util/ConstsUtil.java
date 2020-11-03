package cn.xy.st.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConstsUtil {
	
	public static final Properties prop = new Properties();
	public static String cookie = "FundTradeLoginUser=QXBDWnNW2ehJDscDoPJAJWltBo8gqCvxEZZklsQ8ja4v1K6xK0TFBpQrtOBQp1p0p8Ku01d9; FundTradeLoginCard=0; "
			+ "FundTradeLoginTab=0; st_pvi=18619731129501; st_sp=2020-07-07%2012%3A01%3A48; st_inirUrl=http%3A%2F%2Ffundf10.eastmoney.com%2Fjjfl_519672.html";
	
	public static String cookie2 = "em_hq_fls=js; intellpositionL=1328.8px; em-quote-version=topspeed; ct=aSoj3aXnHzcCSfm56kimEA3Zvx5zPX9T24AgFxrF0mkIhov8gVKO"
			+ "Ei3M1l0gp7IZ_HXzMzOaH6FaWa2i3uQQwnwe-7b1r8O_u3DEBHRgIvpcNdHdK8vZqm2zDpDqOhKHAwQuWwx8hjB7WnJoQ_eq-KrTUx48TsY4PgeO5IJ1crk; ut=FobyicMgeV4XKdbUhrvv"
			+ "Y7UN6GWXQwzALgLvbiM_I1f_GbKHmX__Z_FXX1yMvreooYdFsT5B8JP_oHzUvF0Hf0oOAE5QlYStDKHF2UHiP2XISbd_hJd1xFkPnwVArkD9kIY03VwHGlyA_FTDFnogdgfrZTXOB8N4If3fc"
			+ "5Qp-cMdDNm5zn-LdEFjUvJbt6viqyE_mg8CgEq7_YBn4B2RrZkGdi1Kes6--I41fJlFdHfPprUs61Wc-oVf6ptPeH6kYL84hLL1aupXEQ2pPfqR4IK_WJnhjWc2; pi=6564245859148062%"
			+ "3bw6564245859148062%3bbug%e8%b6%85%e4%ba%ba%3brnSf1l5db06vMwt1uUxe5odETHMW16otzYuU6D2mEuhQ2hmSpGMCkGVnxfyByur90QIE1r%2bMpjDqayHp6j5LyYD1HPTscZn3N"
			+ "YHp5dgVsaFkbt7Lm39HKNbbWeaCnVn9Jp30RYDNkksayGrNNt4meuKjl3yC6sHcm33BEPpFFmpqEXQKoJxe3u8i5gFVCAPObEMmKoeu%3blX6DKNPys5RxVDaKR55EflNvEAoLOMX4rPPx9f"
			+ "SWOGuJ2b3MfL6Fe1ZVGvoFQAlnwpgmeZvJI9ZxjxmktGSdm2FLYRnZTMvDAi0reuc1LDC8SY0CcBcHT1LiXPpNsTqQcY4X8Y41CflzDVg%2b0XszAi777%2b56cg%3d%3d; uidal=6564245"
			+ "859148062bug%e8%b6%85%e4%ba%ba; sid=119513719; vtpst=|; fund_trade_trackid=VDpl3ERZ6wZnB2a+upvuz5bVVtDBs5WG9uTYi6hQSRQ90zS6QrL2Zdi0w4PvUKngz3y38Nk"
			+ "xztcc1i/bV4VXfw==; AUTH_FUND.EASTMONEY.COM_GSJZ=AUTH*TTJJ*TOKEN; emshistory=%5B%22%E8%85%BE%E8%AE%AF%E8%82%A1%E4%BB%BD%22%2C%22%E8%85%BE%E8%AE%AF%"
			+ "22%2C%22%E4%BF%9D%E9%BE%84%E5%AE%9D%22%2C%22%E7%AB%8B%E8%AE%AF%E7%B2%BE%E5%AF%86%22%2C%22QFII%E6%9C%80%E6%96%B0%E6%8C%81%E4%BB%93%E6%9B%9D%E5%85%89"
			+ "%22%2C%22%E9%99%8D%E5%87%86%22%2C%22%E5%80%BA%E5%88%B8%E5%9F%BA%E9%87%91%22%2C%22%E5%80%BA%E5%88%B8%E5%9F%BA%E9%87%91%E5%92%8C%E8%82%A1%E7%A5%A8%E7"
			+ "%9A%84%E5%85%B3%E7%B3%BB%22%2C%22%E5%80%BA%E5%88%B8%E6%9C%9F%E8%B4%A7%22%2C%22%E5%80%BA%E5%88%B8%22%2C%22%E6%85%A7%E8%B5%A2%22%2C%22%E7%BE%8E%E7%9A"
			+ "%84%E7%A9%BA%E8%B0%83%22%5D; qgqp_b_id=ead5d0b2983c9d61817ba52f56aa14af; HAList=a-sz-002007-%u534E%u5170%u751F%u7269%2Ca-sz-000423-%u4E1C%u963F%u96"
			+ "3F%u80F6%2Ca-sz-300122-%u667A%u98DE%u751F%u7269%2Ca-sh-600887-%u4F0A%u5229%u80A1%u4EFD%2Ca-sz-000860-%u987A%u946B%u519C%u4E1A%2Ca-sz-002352-%u987A"
			+ "%u4E30%u63A7%u80A1%2Ca-sz-300782-%u5353%u80DC%u5FAE%2Ca-sh-600529-%u5C71%u4E1C%u836F%u73BB%2Ca-sh-600809-%u5C71%u897F%u6C7E%u9152%2Cd-hk-00700%2"
			+ "Ca-sh-600519-%u8D35%u5DDE%u8305%u53F0%2Ca-sh-603181-%u7687%u9A6C%u79D1%u6280; st_si=04083596102583; st_asi=delete; cowCookie=true; "
			+ "intellpositionT=1955px; EMFUND0=10-30%2014%3A50%3A54@%23%24%u9E4F%u534E%u4E30%u7984%u503A%u5238@%23%24003547; EMFUND1=11-02%2014%3A22%3A11@%23%24%"
			+ "u9E4F%u534E%u53CC%u503A%u4FDD%u5229%u503A%u5238@%23%24000338; EMFUND2=11-02%2014%3A22%3A21@%23%24%u8BFA%u5B89%u53CC%u5229%u503A%u5238%u53D1%u8D77@%"
			+ "23%24320021; EMFUND3=11-02%2013%3A24%3A07@%23%24%u56FD%u6CF0%u6D88%u8D39%u4F18%u9009%u80A1%u7968@%23%24005970; EMFUND4=11-02%2014%3A22%3A51@%23%24"
			+ "%u5927%u6469%u53CC%u5229%u589E%u5F3A%u503A%u5238A@%23%24000024; EMFUND5=11-02%2014%3A22%3A52@%23%24%u5927%u6469%u5F3A%u6536%u76CA%u503A%u5238@%23"
			+ "%24233005; EMFUND6=11-02%2014%3A46%3A26@%23%24%u6613%u65B9%u8FBE%u88D5%u4E30%u56DE%u62A5%u503A%u5238@%23%24000171; EMFUND7=11-03%2009%3A19%3A13@%"
			+ "23%24%u4E2D%u6D77%u533B%u7597%u4FDD%u5065%u4E3B%u9898%u80A1%u7968@%23%24399011; EMFUND8=11-03%2013%3A40%3A07@%23%24%u6613%u65B9%u8FBE%u65B0%u6536%u"
			+ "76CA%u6DF7%u5408A@%23%24001216; EMFUND9=11-03 13:43:09@#$%u5929%u5F18%u533B%u7597%u5065%u5EB7%u6DF7%u5408A@%23%24001558; ASP.NET_SessionId=2anef5k4"
			+ "dl2emcotibtkdsgx; st_pvi=30612436128217; st_sp=2020-06-11%2014%3A16%3A55; st_inirUrl=http%3A%2F%2Fguba.eastmoney.com%2F; st_sn=58; st_psi=202011031"
			+ "34401340-0-8880100240";
	
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
