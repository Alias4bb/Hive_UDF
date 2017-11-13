package com.businessmatrix;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Calendar_Utils {
	/**
	 * 处理日期相关的问题
	 * 
	 * @author Alias
	 *
	 */  
	public static int getNextDay(int date) {  //获取日期的前一天
        Calendar calendar = Calendar.getInstance(); 
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date1 = null;
		try {
			date1 = formatter.parse(String.valueOf(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        calendar.setTime(date1);  
        calendar.add(Calendar.DAY_OF_MONTH, -1);  
        date1 = calendar.getTime();
        String date2=formatter.format(date1);
        int date3=Integer.parseInt(date2);
        return date3;  
    }  
	public static int getCurrentDay() {  //获取日期当天
		 Calendar calendar = Calendar.getInstance(); 
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	        Date date1;
	        Date now = new Date(); 
		
	        calendar.setTime(now);  
	        //calendar.add(Calendar.DAY_OF_MONTH, 0);  
	        date1 = calendar.getTime();
	        String date2=formatter.format(date1);
	        int date3=Integer.parseInt(date2);
	        return date3;   
    }  
	public static int getDaysAdd(int date,int day) {  //获取日期加上某一天
		 Calendar calendar = Calendar.getInstance(); 
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	        Date date1 = null;
			try {
				date1 = formatter.parse(String.valueOf(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        calendar.setTime(date1);  
	      calendar.add(Calendar.DAY_OF_MONTH, day);  
	        date1 = calendar.getTime();
	        String date2=formatter.format(date1);
	        int date3=Integer.parseInt(date2);
	        return date3;   
    }  
	public static int getMonthsAdd(int date,int month) {  //获取日期加上几个月
		 Calendar calendar = Calendar.getInstance(); 
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	        Date date1 = null;
			try {
				date1 = formatter.parse(String.valueOf(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        calendar.setTime(date1);  
	        calendar.add(Calendar.MONTH, month);  
	        date1 = calendar.getTime();
	        String date2=formatter.format(date1);
	        int date3=Integer.parseInt(date2);
	        return date3;    
    }  
	public static void main(String[] args) {
		try {
			int date=20171029;
			int day=12;
			int month=1;
			int a=Calendar_Utils.getCurrentDay();
			int a1=Calendar_Utils.getDaysAdd(date, day);
			int a2=Calendar_Utils.getMonthsAdd(date, month);
			int a3=Calendar_Utils.getNextDay(date);
			System.out.println(a);	
			System.out.println(a1);
			System.out.println(a2);
			System.out.println(a3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
