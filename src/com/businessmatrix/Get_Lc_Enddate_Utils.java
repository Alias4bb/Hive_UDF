package com.businessmatrix;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;



public class Get_Lc_Enddate_Utils {
	
	private static List<ExchangeDate> list=new ArrayList<ExchangeDate>();
	private static List<CrmDuedate> list1=new ArrayList<CrmDuedate>();
	
	 public Get_Lc_Enddate_Utils(){
		 selectFundcode();
		 selectAllDate();
	 }
	
	@SuppressWarnings("rawtypes")
	public  static List selectFundcode(){
		Connection conn = null;
        Statement sm = null;
        ResultSet rs = null;
		String sql = "select fundcode,crm_duedate from dc_ods.v_dc_crm_duedate where crm_subtype='100000006'";
		try {
		conn = JdbcDao.getConnection();
		 sm = conn.createStatement();
		 rs = sm.executeQuery(sql);
		while(rs.next()) {
		CrmDuedate A = new CrmDuedate();
		A.setFundcode(rs.getString(1));
		A.setCrm_duedate(rs.getInt(2));
		list1.add(A); }
		} catch (Exception e) {
		e.printStackTrace();
		} finally {
			try {
				JdbcDao.close(conn, sm, rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list1;
		}
	@SuppressWarnings("rawtypes")
	public  static List selectAllDate(){
		Connection conn = null;
        Statement sm = null;
        ResultSet rs = null;
		String sql = "select * from dc_ods.v_dc_crm_exchange_date ";
		try {
		conn = JdbcDao.getConnection();
		 sm = conn.createStatement();
		 rs = sm.executeQuery(sql);
		while(rs.next()) {
		ExchangeDate A = new ExchangeDate();
		A.setTradedate(rs.getInt(1));
		A.setFlag(rs.getInt(2));
		list.add(A); }
		} catch (Exception e) {
		e.printStackTrace();
		} finally {
			try {
				JdbcDao.close(conn, sm, rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
		}
	
	

	public static int getMinResult(int p_regdate){
		Integer[] result =new Integer[list.size()];
		for(int i=0;i<list.size();i++){
			if(list.get(i).getFlag()==1&&list.get(i).getTradedate()>=p_regdate){
				ExchangeDate date = list.get(i);
				result[i]=new Integer(date.getTradedate());
			}
		}
	//	String sql=" SELECT   MIN(TRADEDATE) FROM dc_ods.v_dc_crm_exchange_date  WHERE  FLAG = 1 and  TRADEDATE >= "+p_regdate ;
	//	int result=Connect_Hive_Db.query(sql);//拿到传入日期最近的工作日
		  List<Integer> tmp = new ArrayList<Integer>(); 
		   for(Integer str:result){ 
		        if(str!=null){ 
		               tmp.add(str); 
		             } 
		           } 
		   result= tmp.toArray(new Integer[0]); 
		int result1 = (int) Collections.min(Arrays.asList(result));
		return result1;
	}

public static int getMaxResult(int p_regdate){
		Integer[] result =new Integer[list.size()];
		for(int i=0;i<list.size();i++){

			if(list.get(i).getFlag()==1&&list.get(i).getTradedate()<=p_regdate){
				ExchangeDate date = list.get(i);
				result[i]=new Integer(date.getTradedate());
			}
		}
		  List<Integer> tmp = new ArrayList<Integer>(); 
		   for(Integer str:result){ 
		        if(str!=null){ 
		               tmp.add(str); 
		             } 
		           } 
		   result= tmp.toArray(new Integer[0]); 
		int result1 = (int) Collections.max(Arrays.asList(result));
		return result1;
	}

public static int getFlag(int p_regdate){
	Integer[] result =new Integer[list.size()];
	for(int i=0;i<list.size();i++){
		if(list.get(i).getTradedate()==p_regdate){
			ExchangeDate date = list.get(i);
			result[i]=new Integer(date.getFlag());
		}
	}
	  List<Integer> tmp = new ArrayList<Integer>(); 
	   for(Integer str:result){ 
	        if(str!=null){ 
	               tmp.add(str); 
	             } 
	           } 
	   result= tmp.toArray(new Integer[0]); 
	
	int result1 = (int) Collections.max(Arrays.asList(result));
	return result1;
}

public static int getDuedate(String p_fundcode){
	int result1=0;
	Integer[] result =new Integer[list1.size()];
	for(int i=0;i<list1.size();i++){
		if(list1.get(i).getFundcode().equals(p_fundcode)){
			CrmDuedate date = list1.get(i);
			result[i]=new Integer(date.getCrm_duedate());
		}
	}
	  List<Integer> tmp = new ArrayList<Integer>(); 
	   for(Integer str:result){ 
	        if(str!=null){ 
	               tmp.add(str); 
	             } 
	           } 
	   result= tmp.toArray(new Integer[0]); 
 if(result.length!=0){
		   result1=(int)Collections.max(Arrays.asList(result));
 }
	return result1;
}
	
	public static String getEnddate(String p_fundcode,int p_regdate){
	
		int V_REGDATE;  
		int V_ENDDATE = 0  ;
        int V_ENDDATE_1  ;  
        String V_RETURNDATE  ;  
        int V_DUEDAY  ;  
		if  (Get_Lc_Enddate_Utils.getDuedate(p_fundcode)==0) { 
			return null;}
		else 
		{
	       V_DUEDAY=Get_Lc_Enddate_Utils.getDuedate(p_fundcode);//获取理财到期日
		 if(Get_Lc_Enddate_Utils.getFlag(p_regdate)==0){
			//拿到传入日期最近的工作日
			V_REGDATE=Get_Lc_Enddate_Utils.getMinResult(p_regdate);
		 } 
		 else {
			 V_REGDATE=p_regdate;//否则赋予传入日期
		 }
		 if( ( p_fundcode.equals("202303") && V_REGDATE ==20120814  ) 
			|| ( p_fundcode.equals("202304")  && V_REGDATE ==20120814  )  
            ||( p_fundcode.equals("202305")  && V_REGDATE == 20121019  )  
            || ( p_fundcode.equals("202306") && V_REGDATE == 20121019 )  
            || ( p_fundcode.equals("202307")  && V_REGDATE == 20130122)  
            || ( p_fundcode.equals("202308") && V_REGDATE ==20130122 ) ) {
			 V_ENDDATE_1= V_REGDATE ;
		   } 
		 else{
			 V_ENDDATE_1=Calendar_Utils.getDaysAdd(p_regdate, -1);
			 V_ENDDATE_1=Get_Lc_Enddate_Utils.getMaxResult(V_ENDDATE_1);
		 }
		 
		if(Calendar_Utils.getDaysAdd(V_ENDDATE_1, V_DUEDAY)<Calendar_Utils.getCurrentDay()){
			 V_ENDDATE=V_ENDDATE_1 ;
			 while  (V_ENDDATE<Calendar_Utils.getCurrentDay()){
				 if (V_DUEDAY == 14 ){
					 V_ENDDATE=Calendar_Utils.getDaysAdd(V_ENDDATE, V_DUEDAY);
					
					 if(String.valueOf(V_ENDDATE).substring(0, 6).equals(String.valueOf(Calendar_Utils.getCurrentDay()).substring(0, 6))
			            		&&  V_ENDDATE<Calendar_Utils.getCurrentDay()
			            		&& Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE)>=Calendar_Utils.getCurrentDay()){
						 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE);					 
					 } 
				 }
				 else if (V_DUEDAY == 30){
					 if(Calendar_Utils.getMonthsAdd(V_ENDDATE, 1)<Calendar_Utils.getCurrentDay()){
						 V_ENDDATE=Calendar_Utils.getMonthsAdd(V_ENDDATE, 1); 
					 }
					 else{
						 V_ENDDATE=Integer.parseInt(String.valueOf(V_ENDDATE).substring(0, 6)+String.valueOf(V_ENDDATE_1).substring(6, 8));
			        	 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE); 
						 if(V_ENDDATE>=Calendar_Utils.getCurrentDay()){
							 V_ENDDATE=Integer.parseInt(String.valueOf(V_ENDDATE).substring(0, 6)+String.valueOf(V_ENDDATE_1).substring(6, 8));
				        	 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE);
							 
						 }
						 else{
							 V_ENDDATE=Calendar_Utils.getMonthsAdd(V_ENDDATE, 1);
				        	 V_ENDDATE=Integer.parseInt(String.valueOf(V_ENDDATE).substring(0, 6)+String.valueOf(V_ENDDATE_1).substring(6, 8));
				        	 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE);
						 }
						 
					 }
				 }
				 else if(V_DUEDAY == 60){

					 if(Calendar_Utils.getMonthsAdd(V_ENDDATE, 2)<Calendar_Utils.getCurrentDay()){
						 V_ENDDATE=Calendar_Utils.getMonthsAdd(V_ENDDATE, 2);
						 if(p_fundcode.equals("001041")){
				        	 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE); 
						 }
					 }
					 else{
						 if(p_fundcode.equals("001041")){
				        	 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE); 
						 }
						 else if(Get_Lc_Enddate_Utils.getMinResult(Integer.parseInt(String.valueOf(V_ENDDATE).substring(0, 6)+String.valueOf(V_ENDDATE_1).substring(6, 8)))>=Calendar_Utils.getCurrentDay())
						 {
							 V_ENDDATE=Integer.parseInt(String.valueOf(V_ENDDATE).substring(0, 6)+String.valueOf(V_ENDDATE_1).substring(6, 8));
				        	 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE);
							 
						 }
						 else {
							 V_ENDDATE=Calendar_Utils.getMonthsAdd(V_ENDDATE, 2);
							 V_ENDDATE=Integer.parseInt(String.valueOf(V_ENDDATE).substring(0, 6)+String.valueOf(V_ENDDATE_1).substring(6, 8));
				        	 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE);
						 }
					 }
					 
				 }
			 }
		 }
		 else{
			 V_ENDDATE =V_ENDDATE_1 ;
         if (V_DUEDAY ==14 ){
        	 V_ENDDATE=Calendar_Utils.getDaysAdd(V_ENDDATE_1, V_DUEDAY);                                         
             if(String.valueOf(V_ENDDATE).substring(0, 6).equals(String.valueOf(Calendar_Utils.getCurrentDay()).substring(0, 6))
            		&&  V_ENDDATE<Calendar_Utils.getCurrentDay()
            		&& Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE)>=Calendar_Utils.getCurrentDay()
            		 ){
            	 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE);
             }
         }
         else if (V_DUEDAY ==30){
        	 V_ENDDATE=Calendar_Utils.getMonthsAdd(V_ENDDATE, 1);
        	 V_ENDDATE=Integer.parseInt(String.valueOf(V_ENDDATE).substring(0, 6)+String.valueOf(V_ENDDATE_1).substring(6, 8));
   
        	 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE);
         }
         else if (V_DUEDAY ==60){
        	if (!p_fundcode.equals("001041") ){
        		 V_ENDDATE=Calendar_Utils.getMonthsAdd(V_ENDDATE, 2);
        		 V_ENDDATE=Integer.parseInt(String.valueOf(V_ENDDATE).substring(0, 6)+String.valueOf(V_ENDDATE_1).substring(6,8));
        	}
        	else {
        		 V_ENDDATE=Calendar_Utils.getMonthsAdd(V_ENDDATE, 2);
        	}
       	   V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE);     	
         }   
	 
		 }
    	   V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE); 
		}
		V_RETURNDATE=String.valueOf(V_ENDDATE);
		return V_RETURNDATE;
	}

	public static String getEnddateReqdate(String p_fundcode,int p_regdate){
		
		int V_REGDATE;  
		int V_ENDDATE = 0  ;
        int V_ENDDATE_1  ;  
        String V_RETURNDATE  ;  
        int V_DUEDAY  ;  
		if  (Get_Lc_Enddate_Utils.getDuedate(p_fundcode)==0) { 
			return null;}
		else 
		{
	       V_DUEDAY=Get_Lc_Enddate_Utils.getDuedate(p_fundcode);//获取理财到期日
		 if(Get_Lc_Enddate_Utils.getFlag(p_regdate)==0){
			//拿到传入日期最近的工作日
			V_REGDATE=Get_Lc_Enddate_Utils.getMinResult(p_regdate);
		 } 
		 else {
			 V_REGDATE=p_regdate;//否则赋予传入日期
		 }
		 if( ( p_fundcode.equals("202303") && V_REGDATE ==20120814  ) 
			|| ( p_fundcode.equals("202304")  && V_REGDATE ==20120814  )  
            ||( p_fundcode.equals("202305")  && V_REGDATE == 20121019  )  
            || ( p_fundcode.equals("202306") && V_REGDATE == 20121019 )  
            || ( p_fundcode.equals("202307")  && V_REGDATE == 20130122)  
            || ( p_fundcode.equals("202308") && V_REGDATE ==20130122 ) ) {
			 V_ENDDATE_1= V_REGDATE ;
		   } 
		 else{
			 V_ENDDATE_1= V_REGDATE ;
			 //V_ENDDATE_1=Calendar_Utils.getDaysAdd(p_regdate, -1);
			 //V_ENDDATE_1=Get_Lc_Enddate_Utils.getMaxResult(V_ENDDATE_1);
		 }
		 
		if(Calendar_Utils.getDaysAdd(V_ENDDATE_1, V_DUEDAY)<Calendar_Utils.getCurrentDay()){
			 V_ENDDATE=V_ENDDATE_1 ;
			 while  (V_ENDDATE<Calendar_Utils.getCurrentDay()){
				 if (V_DUEDAY == 14 ){
					 V_ENDDATE=Calendar_Utils.getDaysAdd(V_ENDDATE, V_DUEDAY);
					
					 if(String.valueOf(V_ENDDATE).substring(0, 6).equals(String.valueOf(Calendar_Utils.getCurrentDay()).substring(0, 6))
			            		&&  V_ENDDATE<Calendar_Utils.getCurrentDay()
			            		&& Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE)>=Calendar_Utils.getCurrentDay()){
						 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE);					 
					 } 
				 }
				 else if (V_DUEDAY == 30){
					 if(Calendar_Utils.getMonthsAdd(V_ENDDATE, 1)<Calendar_Utils.getCurrentDay()){
						 V_ENDDATE=Calendar_Utils.getMonthsAdd(V_ENDDATE, 1); 
					 }
					 else{
						 V_ENDDATE=Integer.parseInt(String.valueOf(V_ENDDATE).substring(0, 6)+String.valueOf(V_ENDDATE_1).substring(6, 8));
			        	 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE); 
						 if(V_ENDDATE>=Calendar_Utils.getCurrentDay()){
							 V_ENDDATE=Integer.parseInt(String.valueOf(V_ENDDATE).substring(0, 6)+String.valueOf(V_ENDDATE_1).substring(6, 8));
				        	 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE);
							 
						 }
						 else{
							 V_ENDDATE=Calendar_Utils.getMonthsAdd(V_ENDDATE, 1);
				        	 V_ENDDATE=Integer.parseInt(String.valueOf(V_ENDDATE).substring(0, 6)+String.valueOf(V_ENDDATE_1).substring(6, 8));
				        	 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE);
						 }
						 
					 }
				 }
				 else if(V_DUEDAY == 60){

					 if(Calendar_Utils.getMonthsAdd(V_ENDDATE, 2)<Calendar_Utils.getCurrentDay()){
						 V_ENDDATE=Calendar_Utils.getMonthsAdd(V_ENDDATE, 2);
						 if(p_fundcode.equals("001041")){
				        	 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE); 
						 }
					 }
					 else{
						 if(p_fundcode.equals("001041")){
				        	 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE); 
						 }
						 else if(Get_Lc_Enddate_Utils.getMinResult(Integer.parseInt(String.valueOf(V_ENDDATE).substring(0, 6)+String.valueOf(V_ENDDATE_1).substring(6, 8)))>=Calendar_Utils.getCurrentDay())
						 {
							 V_ENDDATE=Integer.parseInt(String.valueOf(V_ENDDATE).substring(0, 6)+String.valueOf(V_ENDDATE_1).substring(6, 8));
				        	 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE);
							 
						 }
						 else {
							 V_ENDDATE=Calendar_Utils.getMonthsAdd(V_ENDDATE, 2);
							 V_ENDDATE=Integer.parseInt(String.valueOf(V_ENDDATE).substring(0, 6)+String.valueOf(V_ENDDATE_1).substring(6, 8));
				        	 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE);
						 }
					 }
					 
				 }
			 }
		 }
		 else{
			 V_ENDDATE =V_ENDDATE_1 ;
         if (V_DUEDAY ==14 ){
        	 V_ENDDATE=Calendar_Utils.getDaysAdd(V_ENDDATE_1, V_DUEDAY);                                         
             if(String.valueOf(V_ENDDATE).substring(0, 6).equals(String.valueOf(Calendar_Utils.getCurrentDay()).substring(0, 6))
            		&&  V_ENDDATE<Calendar_Utils.getCurrentDay()
            		&& Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE)>=Calendar_Utils.getCurrentDay()
            		 ){
            	 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE);
             }
         }
         else if (V_DUEDAY ==30){
        	 V_ENDDATE=Calendar_Utils.getMonthsAdd(V_ENDDATE, 1);
        	 V_ENDDATE=Integer.parseInt(String.valueOf(V_ENDDATE).substring(0, 6)+String.valueOf(V_ENDDATE_1).substring(6, 8));
   
        	 V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE);
         }
         else if (V_DUEDAY ==60){
        	if (!p_fundcode.equals("001041") ){
        		 V_ENDDATE=Calendar_Utils.getMonthsAdd(V_ENDDATE, 2);
        		 V_ENDDATE=Integer.parseInt(String.valueOf(V_ENDDATE).substring(0, 6)+String.valueOf(V_ENDDATE_1).substring(6,8));
        	}
        	else {
        		 V_ENDDATE=Calendar_Utils.getMonthsAdd(V_ENDDATE, 2);
        	}
       	   V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE);     	
         }   
	 
		 }
    	   V_ENDDATE=Get_Lc_Enddate_Utils.getMinResult(V_ENDDATE); 
		}
		V_RETURNDATE=String.valueOf(V_ENDDATE);
		return V_RETURNDATE;
	}
	public static void main(String[] args) {
		//Get_Lc_Enddate_Utils test;	
			//test = new Get_Lc_Enddate_Utils(p_fundcode, p_fundcode);
		Get_Lc_Enddate_Utils utils = new Get_Lc_Enddate_Utils();
		String result = utils.getEnddate("202305",20161101);
		System.out.println(result);	 
			///int a=Get_Lc_Enddate_Utils.getDuedate("202305");
			///System.out.println(a);	
	
	}
	
}
