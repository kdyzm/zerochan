package com.kdyzm.main;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.kdyzm.error.Error;

public class Utils {
	public static Document getDocument(String url)
	{
		Connection conn=Jsoup.connect(url);
		conn.header("Cookie", "z_theme=1; PHPSESSID=n96cd2h9gfua2n8b77c173psh4; __utmt=1; __utma=7894585.1547861522.1428923856.1436830483.1438845384.11; __utmb=7894585.4.10.1438845384; __utmc=7894585; __utmz=7894585.1438845384.11.5.utmcsr=baidu.com|u");
		conn.header("Accept", "text/html, application/xhtml+xml, */*");
		conn.header("Accept-Language", "zh-CN");
		conn.header("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Trident/7.0; rv:11.0) like Gecko");
		conn.header("Host", "www.pixiv.net");
		conn.header("DNT", "1");
		conn.header("Connection", "Keep-Alive");
		conn=conn.timeout(72000);
		try {
			Response response=conn.execute();
			Document document=Jsoup.parse(response.body());
			return document;
		} catch (IOException e) {
			e.printStackTrace();
			//将错误日志信息保存到文件
			Error.writeErrorLog(url);
			return null;
		}
	}
	
	/**
	 * 获取一共有多少个列表页
	 */
	public static int getPages(Document document) {
		Element pages=document.getElementsByClass("pagination").first();
//		System.out.println(pages.text());
		String str=pages.text();
		str=str.replaceAll(" ", "");
//		System.out.println(str);
		String regex="(\\d+)of(\\d+)";
		Pattern p=Pattern.compile(regex);
		Matcher matcher=p.matcher(str);
		if(matcher.find())
		{
			return Integer.parseInt(matcher.group(2));
		}
		else
		{
			return 0;
		}
	}
}
