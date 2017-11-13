package com.businessmatrix;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.IntObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;


/**
 * 获取理财基金的到期日
 * 
 * @author Alias
 *
 */  
	@SuppressWarnings("serial")
	@Description( 
	name = "Get_Lc_Enddate",
	value = "_FUNC_(FUNDCODE string,int String) *—  Return the Financial maturity date"
			)
public class Get_Lc_Enddate extends  GenericUDF {
	   private  Get_Lc_Enddate_Utils utils;
	   StringObjectInspector param1; //基金代码
	   IntObjectInspector param2; //注册日期
		//PrimitiveObjectInspector [] argumentOIs;
		
	@Override
	public Object evaluate(DeferredObject[] arguments) throws HiveException {
    String p_fundcode = param1.getPrimitiveJavaObject(arguments[0].get());
	int p_regdate = (Integer) param2.getPrimitiveJavaObject(arguments[1].get());
	if(utils==null){
		utils = new Get_Lc_Enddate_Utils();
	}	
	@SuppressWarnings("static-access")
		String result = utils.getEnddate(p_fundcode,p_regdate);
		return result;
	}

	@Override
	public String getDisplayString(String[] arguments) {
		assert(arguments.length == 2);
		return "Get_Lc_Enddate("+arguments[0]+arguments[1]+")";
	}

	@Override
	public ObjectInspector initialize(ObjectInspector[] arg0)
			throws UDFArgumentException {
		//检测输入参数类型是否正确
		ObjectInspector aInspector = arg0[0];
		ObjectInspector bInspector = arg0[1];
		if(!(aInspector instanceof StringObjectInspector)|| !(bInspector instanceof IntObjectInspector)){
			throw new UDFArgumentException();
		}
		//存储在全局变量的ObjectInspectors元素的输入
		param1 = (StringObjectInspector) aInspector;
		param2 = (IntObjectInspector) bInspector;
		
		//设置返回值
		return PrimitiveObjectInspectorFactory.javaStringObjectInspector;
	}

}
