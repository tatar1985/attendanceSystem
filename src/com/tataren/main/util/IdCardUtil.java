package com.tataren.main.util;

public class IdCardUtil {
	  public String IdCard15to18(String idCard){
	        idCard = idCard.trim();
	        StringBuffer idCard18 = new StringBuffer(idCard);
	        //加权因子
	        //int[] weight = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
	        //校验码值
	        char[] checkBit = {'1','0','X','9','8','7','6','5','4','3','2'};
	        int sum = 0;
	        //15位的身份证
	        if(idCard != null && idCard.length()==15){
	            idCard18.insert(6, "19");
	            for(int index=0;index<idCard18.length();index++){
	                char c = idCard18.charAt(index);
	                int ai = Integer.parseInt(new Character(c).toString());
	                //logger.debug(new Integer(ai));
	                //sum = sum+ai*weight[index];
	                //加权因子的算法
	                int Wi = ((int)Math.pow(2, idCard18.length()-index))%11;
	                sum = sum+ai*Wi;
	            }
	            int indexOfCheckBit = sum%11; //取模
	            idCard18.append(checkBit[indexOfCheckBit]);
	        }
	        //logger.debug(idCard18);
	        return idCard18.toString();
	    }
	  
	  public String IdCard18to15(String idCard){
	        idCard = idCard.trim();
	        StringBuffer idCard15 = new StringBuffer(idCard);
	        if(idCard!=null && idCard.length()==18){
	            idCard15.delete(17, 18);
	            idCard15.delete(6, 8);
	        }
	        return idCard15.toString();
	        
	    }
}
