package com.kdyzm.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.kdyzm.error.Error;

public class Main {
	public static void main(String[] args) {
		String url="http://www.zerochan.net/Kuchiki+Rukia";
		Document document=Utils.getDocument(url);
		int pages=Utils.getPages(document);
		System.out.println("一共有 "+pages+" 页！");
		HashSet<String>set=PagesUtil.getAllImagesPage(pages);
		HashSet<String>urls=getAllImagesUrl(set);
		writeToFile("result.txt",urls);
	}
	//将结果保存到文件中
	private static void writeToFile(String string, HashSet<String> urls) {
		try {
			PrintWriter pw=new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(string))),true);
			for(String url:urls)
			{
				pw.println(url);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取每一张图片的源图片信息并保存到HashSet中
	 * @param set
	 * @return
	 */
	private static HashSet<String> getAllImagesUrl(HashSet<String> set) {
		HashSet<String>result=new HashSet<String>();
		int sum=result.size();
		int index=0;
		for(String temp:set)
		{
			index++;
			System.out.print("正在解析第 "+index+"\t 条：");
			String url="http://www.zerochan.net"+temp;
			Document urlDocument =Utils.getDocument(url);
			if(urlDocument==null)
			{
				continue;
			}
			Elements allElements=urlDocument.getElementById("large").children();
			Element first=allElements.get(0);
			if(first.nodeName().equals("ul"))
			{
				/*System.out.print("可能出错的ul:");*/
				first=allElements.get(1);
//				System.out.print(first.nodeName());
			}
			if(first.nodeName().equals("a"))
			{
				System.out.println(first.attr("href"));
				result.add(first.attr("href"));
			}
			else if(first.nodeName().equals("img"))
			{
				System.out.println(first.attr("src"));
				result.add(first.attr("src"));
			}
			else
			{
				System.out.println(temp+":没有匹配项目！");
				System.out.println(url);
				/*//测试输出
				for(Element e:allElements)
				{
					System.out.println(e.nodeName());
				}
				System.exit(0);*/
				Error.writeErrorLog(url);
			}
		}
		return result;
	}
}
