package com.businessmatrix;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.IntObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;

public class UDFTEST  extends  GenericUDF{

	
	StringObjectInspector param1;
	StringObjectInspector param2;
	@Override
	public Object evaluate(DeferredObject[] arg0) throws HiveException {
		//获取输入参数
		String a = param1.getPrimitiveJavaObject(arg0[0].get());
		String b = param2.getPrimitiveJavaObject(arg0[1].get());
		//返回输出结果
		 
		return a+b;
	}

	@Override
	public String getDisplayString(String[] arg0) {
		assert(arg0.length>0);
		return "输两个参数相加";
	}

	@Override
	public ObjectInspector initialize(ObjectInspector[] arg0)
			throws UDFArgumentException {
		//检测输入参数类型是否正确
		ObjectInspector aInspector = arg0[0];
		ObjectInspector bInspector = arg0[1];
		if(!(aInspector instanceof StringObjectInspector)|| !(bInspector instanceof StringObjectInspector)){
			throw new UDFArgumentException();
		}
		//存储在全局变量的ObjectInspectors元素的输入
		param1 = (StringObjectInspector) aInspector;
		param2 = (StringObjectInspector) bInspector;
		
		//设置返回值
		return PrimitiveObjectInspectorFactory.javaStringObjectInspector;
	}

}
