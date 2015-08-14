package com.kdyzm.error;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Error {
	private static PrintWriter pw=null;
	static
	{
		try {
			pw=new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File("error.text"))),true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	//记录错误信息到文件
	public static void writeErrorLog(String log)
	{
		pw.println(log);
	};
}
