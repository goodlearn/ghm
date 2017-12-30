package com.thinkgem.jeesite.common.utils;

import java.beans.IntrospectionException;  
import java.beans.Introspector;  
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;  

public class JsonUtil {
	
    private static ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 动态生成对象，对象数据源来自于json
     * @param json
     * @param cls
     * @return
     * @throws Exception
     */
    public static <T> T generatorObject(String json,Class<T> cls) throws Exception{
        T object = objectMapper.readValue(json, cls);
        return object;
    }
    
    /**
     * 动态生成对象，对象数据源来自于json
     * @param json
     * @param cls
     * @return
     * @throws Exception
     */
    public static <T> List<T> generatorObjects(String json,Class<T> cls) throws Exception{
        List<LinkedHashMap<String, Object>> list = objectMapper.readValue(json, List.class);
        if(list.size()<=0) return null;
        List<T> ret = new ArrayList<T>();
        for (int i = 0; i < list.size(); i++) {
            T instance = cls.newInstance();
            Map<String, Object> map = list.get(i);
            Set<String> set = map.keySet();
            for (Iterator<String> it = set.iterator();it.hasNext();) {
                String key = it.next();
                setProperty(instance,key , map.get(key));
            }
            ret.add(instance);
        }
        return ret;
    }
    
    /**
     * 对某一个对象的某一个属性赋值
     * @param bean 对象
     * @param propertyName 属性
     * @param value 赋值
     * @return
     */
    public static Object setProperty(Object bean, String propertyName,
                                     Object value) throws Exception{
        Class clazz = bean.getClass();
        Method method = getClassMethod(clazz,getSetterName(propertyName));
        return ((null==method)?null:method.invoke(bean, new Object[] { value }));
    }
    
    /**
     * 获取类的方法实例
     *
     * @param clazz
     * @param methodName
     * @return
     */
    private static Method getClassMethod(Class clazz, String methodName) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            // getDeclaredMethods()
            //返回 Method 对象的一个数组，这些对象反映此 Class 对象表示的类或接口声明的所有方法，包括公共、保护、默认（包）访问和私有方法，但不包括继承的方法。
            //getMethods()
            //返回一个包含某些 Method 对象的数组，这些对象反映此 Class 对象所表示的类或接口（包括那些由该类或接口声明的以及从超类和超接口继承的那些的类或接口）的公共 member 方法。
            if (method.getName().equals(methodName)) {
                return method;// define in this class
            }
        }
        Class superclass = clazz.getSuperclass();
        if (superclass != null) {// 简单的递归一下
            return getClassMethod(superclass, methodName);
        }
        return null;
    }
    
    /**
     * set方法名
     * @param propertyName
     * @return
     */
    public static String getSetterName(String propertyName) {
        String method = "set" + propertyName.substring(0, 1).toUpperCase()
                + propertyName.substring(1);
        return method;
    }
    
    /**
     * 得到ID的集合
     * 用途：批量操作的时候，操作对象的ID是以json传递到后台，需要将所有的id取出，转换成集合方便操作
     * @param ids
     * @return
     * @throws Exception
     */
    public static List<String> getIds(String ids) throws Exception{
        List<LinkedHashMap<String, Object>> list = objectMapper.readValue(ids, List.class);
        if(list.size()<=0) return null;
        List<String> ret = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++){
            Map<String, Object> map = list.get(i);
            Set<String> keys = map.keySet();
            for (Iterator<String> it = keys.iterator();it.hasNext();) {
                String key = it.next();
                ret.add(map.get(key).toString());
            }
        }
        return ret;
    }
	 /** 
     * @param object 
     *            任意对象 
     * @return java.lang.String 
     */  
    public static String objectToJson(Object object) {  
        StringBuilder json = new StringBuilder();  
        if (object == null) {  
            json.append("\"\"");  
        } else if (object instanceof String) {  
            json.append("\"").append((String)object).append("\"");  
        }else if(object instanceof Integer){
            json.append("\"").append(object).append("\"");  
        } else if (object instanceof Boolean) { 
        	
        }else {  
            json.append(beanToJson(object));  
        }  
        return json.toString();  
    }  
    
    public static String objectToPropJson(Object object) {  
        StringBuilder json = new StringBuilder();  
        json.append((String)object); 
        return json.toString();  
    }  
  
    /** 
     * 功能描述:传入任意一个 javabean 对象生成一个指定规格的字符串 
     *  
     * @param bean 
     *            bean对象 
     * @return String 
     */  
    public static String beanToJson(Object bean) {  
        StringBuilder json = new StringBuilder();  
        json.append("{");  
        PropertyDescriptor[] props = null;  
        try {  
            props = Introspector.getBeanInfo(bean.getClass(), Object.class)  
                    .getPropertyDescriptors();  
        } catch (IntrospectionException e) {
        	e.printStackTrace();
        }  
        if (props != null) {  
            for (int i = 0; i < props.length; i++) {  
                try {  
                    String name = objectToPropJson(props[i].getName());  
                    String value = objectToJson(props[i].getReadMethod().invoke(bean));  
                    json.append(name);  
                    json.append(":");
                    json.append(value);  
                    json.append(",");  
                } catch (Exception e) {
                	e.printStackTrace();
                }  
            }  
            json.setCharAt(json.length() - 1, '}');  
        } else {  
            json.append("}");  
        }  
        return json.toString();  
    }  
  
    /** 
     * 功能描述:通过传入一个列表对象,调用指定方法将列表中的数据生成一个JSON规格指定字符串 
     *  
     * @param list 
     *            列表对象 
     * @return java.lang.String 
     */  
    public static String listToJson(List<?> list) {  
        StringBuilder json = new StringBuilder();  
        json.append("[");  
        if (list != null && list.size() > 0) {  
            for (Object obj : list) {  
                json.append(objectToJson(obj));  
                json.append(",");  
            }  
            json.setCharAt(json.length() - 1, ']'); 
        }else {  
            json.append("]");  
        }  
        return json.toString();  
    }  
}
