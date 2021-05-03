package com.tataren.main.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class WeChatValue {
	private static String accessToken = "1";
	private static String jsTicket="2";

	public static String getAccessToken() {
		return accessToken;
	}

	public static void setAccessToken(String accessToken) {
		WeChatValue.accessToken = accessToken;
	}

	public static String getJsTicket() {
		return jsTicket;
	}

	public static void setJsTicket(String jsTicket) {
		WeChatValue.jsTicket = jsTicket;
	}
	
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{  
		  
	    Map<String, String> map = new HashMap<String, String>();  
	  
	    //从dom4j的jar包中，拿到SAXReader对象。  
	  
	    SAXReader reader = new SAXReader();  
	  
	       
	  
	    InputStream is = request.getInputStream();//从request中，获取输入流  
	  
	    Document doc =  reader.read(is);//从reader对象中,读取输入流  
	  
	    Element root = doc.getRootElement();//获取XML文档的根元素  
	  
	    List<Element> list = root.elements();//获得根元素下的所有子节点  
	  
	    for (Element e : list) {  
	  
	    map.put(e.getName(), e.getText());//遍历list对象，并将结果保存到集合中  
	  
	    }  
	       
	    is.close();  
	  
	    return map;  
	  
	    }  
	
	
}
