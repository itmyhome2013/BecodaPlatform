package com.farm.core.util;

public class GenerateRandom {
	/** 
     * 随机生成 num位数字字符数组 
     *  
     * @param num 
     * @return 
     */  
    public static String generateRandomArray(int num) {  
        String chars = "0123456789";  
        char[] rands = new char[num];  
        for (int i = 0; i < num; i++) {  
            int rand = (int) (Math.random() * 10);  
            rands[i] = chars.charAt(rand);  
        }  
        return String.valueOf(rands);  
    }  
}
