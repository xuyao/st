package cn.xy.st.util;

import java.util.UUID;

public class UUIDUtil {

	//return 32 bit uuid
	public static String genUUID(){
	    UUID uuid = UUID.randomUUID();
	    return uuid.toString().replace("-", "");
	}
}
