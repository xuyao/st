package cn.xy.st.util;

import java.util.Random;

public class NumberUtil {

	public static int genRd(){
		return (int)(Math.random() * 5);
	}
    
    public static String getRandomNum(Integer length) {
        String str = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
           str += String.valueOf(random.nextInt(10));
        }
        return str;
    }
    
    public static void main(String[] args){
        System.out.println(getRandomNum(17));
    }
}
