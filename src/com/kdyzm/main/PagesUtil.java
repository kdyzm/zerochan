package com.kdyzm.main;

import java.util.HashSet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PagesUtil {
	private static HashSet<String>set=new HashSet<String>();
	private static long flagCount=0;
	/**
	 * 获取所有的图片超链接
	 * @param pages
	 */
	public static HashSet<String> getAllImagesPage(int pages) {
		if(pages>100)
		{
			//先得到前100页的数据
			getHandrundPages();
			//后面的特殊处理，递推运算
			getImagesByNext();
		}
		else
		{
			getNomal(pages);
		}
		return set;
	}
	//通过next获取下一页的值
	private static void getImagesByNext() {
		long index=100;
		String startUrl="http://www.zerochan.net/Kuchiki+Rukia?p=100";
		Document document=Utils.getDocument(startUrl);
		if(document==null)
			return;
		int pageCount=Utils.getPages(Utils.getDocument("http://www.zerochan.net/Kuchiki+Rukia?p=1"));
		while(true)
		{
			if(pageCount==index)
				break;
			Element pages=document.getElementsByClass("pagination").first();
			Elements as=pages.getElementsByTag("a");
			Element nextUrl=as.get(as.size()-1);
			document=Utils.getDocument("http://www.zerochan.net/Kuchiki+Rukia"+nextUrl.attr("href"));
			if(document==null)
				continue;
			index++;
			System.out.println("正在解析第 "+index+" 页！");
			Element thumbs2=document.getElementById("thumbs2");
			Elements lis=thumbs2.getElementsByTag("li");
			for(Element li:lis)
			{
				System.out.print("正在解析 "+(++flagCount)+" 条数据！解析结果为：");
				Element a=li.getElementsByTag("a").first();
				String href=a.attr("href");
				System.out.println(href);
				if(!set.contains(href))
				{
					set.add(href);
				}
				else
				{
					System.out.println(href+" 重复！");
					continue;
				}
			}
		}
	}
	//如果总量没有超过100页，则使用该方法获取所有链接
	private static void getNomal(int pages) {
		for(int i=1;i<=pages;i++)
		{
			System.out.println("正在解析第 "+i+" 页！");
			String url="http://www.zerochan.net/Kuchiki+Rukia?p="+i;
			Document document=Utils.getDocument(url);
			if(document==null)
				continue;
			Element thumbs2=document.getElementById("thumbs2");
			Elements lis=thumbs2.getElementsByTag("li");
			for(Element li:lis)
			{
				System.out.print("正在解析 "+(++flagCount)+" 条数据！解析结果为：");
				Element a=li.getElementsByTag("a").first();
				String href=a.attr("href");
				System.out.println(href);
				if(!set.contains(href))
				{
					set.add(href);
				}
				else
				{
					System.out.println(href+" 重复！");
					continue;
				}
			}
		}
	}
	//如果总量超过了100页，先得到前100页的url
	public static void getHandrundPages()
	{
		for(int i=1;i<=100;i++)
		{
			System.out.println("正在解析第 "+i+" 页！");
			String url="http://www.zerochan.net/Kuchiki+Rukia?p="+i;
			Document document=Utils.getDocument(url);
			if(document==null)
				continue;
			Element thumbs2=document.getElementById("thumbs2");
			Elements lis=thumbs2.getElementsByTag("li");
			for(Element li:lis)
			{
				System.out.print("正在解析 "+(++flagCount)+" 条数据！解析结果为：");
				Element a=li.getElementsByTag("a").first();
				String href=a.attr("href");
				System.out.println(href);
				if(!set.contains(href))
				{
					set.add(href);
				}
				else
				{
					System.out.println(href+" 重复！");
					continue;
				}
			}
		}
	}
}
