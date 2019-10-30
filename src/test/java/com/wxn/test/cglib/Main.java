package com.wxn.test.cglib;

import com.mysql.jdbc.Driver;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Intercepter;
import com.wxn.test.Test;
import org.apache.tools.ant.taskdefs.Classloader;
import sun.misc.ProxyGenerator;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;

public class Main   extends ClassLoader {

	public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
//		Person p = (Person)new Interceptor().getInstance(new Person().getClass());
//		p.sayHi();
//
//		System.out.println(p.getClass());
//		for(Method m : p.getClass().getDeclaredMethods()) {
//			System.out.println(m.getName());
//		}


//		Obj p = (Obj) new Interceptor().getInstance(new Person());
////		p.sayHi();
////
////
////
////		System.out.println(p.getClass());
//		System.out.println(p.getClass().getInterfaces());
////		System.out.println(new Interceptor().getInstance(new Person()).getClass());
//		byte[] bytes = ProxyGenerator.generateProxyClass("A", new Class[]{Obj.class});
//		FileOutputStream fos = new FileOutputStream(new File("d:/A.class"));
//		fos.write(bytes);
//		fos.close();

//		Class.forName("d:/A").getMethods();


		/*Main loader = new Main();
		Class<?> objClass = loader.loadClass("A", true);
		Object obj = objClass.newInstance();
		System.out.println(objClass.getName());
		System.out.println(objClass.getClassLoader());
		System.out.println(obj.getClass().toString());*/

		String URL="jdbc:mysql://192.168.2.20:3306/bbs?useUnicode=true&amp;characterEncoding=utf-8";
		String USER="hari";
		String PASSWORD="hari";
		//1.加载驱动程序
//		Class.forName("com.mysql.jdbc.Driver");
		//2.获得数据库链接
		Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		//3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
		Statement st=conn.createStatement();
		ResultSet rs=st.executeQuery("select * from apabi_newspaper");
		//4.处理数据库的返回结果(使用ResultSet类)
		while(rs.next()){
			System.out.println(rs.getString("id")+" "
					+rs.getString("product_name"));
		}

		//关闭资源
		rs.close();
		st.close();
		conn.close();


	}


	@Override
	public Class<?> findClass(String name) {
		try {
			FileInputStream fis = new FileInputStream(new File("d:/A.class"));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int ch = 0;
			byte[] data;
			while ((ch = fis.read()) != -1) {
				baos.write(ch);
			}
			data = baos.toByteArray();
			return defineClass(name, data, 0, data.length);// 将一个 byte 数组转换为 Class// 类的实例
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
class Person implements Obj {
	public void sayHi() {
		System.out.println("ho");
	}
}
interface Obj {
	public void sayHi();
}
