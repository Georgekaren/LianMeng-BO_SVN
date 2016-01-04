package com.lianmeng.core.framework.bo.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

public class ObjectUtil
{
  private static final Logger logger = Logger.getLogger(ObjectUtil.class);
  private Object obj;
  private Class cls;
  private static ClassLoader defaultClassLoader;

  public ObjectUtil(Object obj)
  {
    this.obj = obj;
    this.cls = obj.getClass();
  }

  public Object getObject() {
    return this.obj;
  }

  public Field getField(String fieldName)
  {
    Field[] fieldArr = this.cls.getDeclaredFields();
    Field fld = null;
    for (int i = 0; i < fieldArr.length; ++i)
    {
      if (fieldArr[i].getName().equals(fieldName)) {
        if (!(fieldArr[i].isAccessible()))
        {
          fieldArr[i].setAccessible(true);
        }

        fld = fieldArr[i];
      }

    }

    return fld;
  }

  public Method getMethod(String methodName, Class[] paramCls)
    throws SecurityException, NoSuchMethodException
  {
    Method mtd = null;
    mtd = this.cls.getMethod(methodName, paramCls);

    return mtd;
  }

  public Object callMethod(String methodName, Class[] paramClsArr, Object[] paramVal)
    throws NoSuchMethodException, SecurityException, InvocationTargetException, IllegalArgumentException, IllegalAccessException
  {
    Object object = null;
    Method mtd = getMethod(methodName, paramClsArr);
    if (ValidateUtil.validateNotNull(mtd)) {
      object = mtd.invoke(this.obj, paramVal);
    }

    return object;
  }

  public static Class classForName(String className)
    throws ClassNotFoundException
  {
    Class clazz = null;
    try {
      clazz = getClassLoader().loadClass(className);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    if (clazz == null) {
      clazz = Class.forName(className);
    }
    return clazz;
  }

  public static ClassLoader getClassLoader() {
    if (defaultClassLoader != null) {
      return defaultClassLoader;
    }

    return Thread.currentThread().getContextClassLoader();
  }

  public static Object newInstance(String className)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    return classForName(className).newInstance();
  }

  public static Object toArray(Object vec, Class cls)
  {
    if (!(ValidateUtil.validateNotNull(vec))) {
      return vec;
    }

    if (!(vec.getClass().isArray())) {
      return vec;
    }

    int length = Array.getLength(vec);

    Object[] newvec = (Object[])(Object[])Array.newInstance(cls, length);

    for (int i = 0; i < length; ++i) {
      newvec[i] = Array.get(vec, i);
    }
    return newvec;
  }

  public static ClassLoader getDefaultClassLoader()
  {
    return defaultClassLoader;
  }

  public static void setDefaultClassLoader(ClassLoader defaultClassLoader)
  {
    defaultClassLoader = defaultClassLoader;
  }

  public static int compare(Comparable a, Comparable b)
  {
    if (a == b) {
      return 0;
    }
    if (a == null) {
      return -1;
    }
    if (b == null) {
      return 1;
    }
    return a.compareTo(b);
  }

  public static boolean equals(Object a, Object b)
  {
    if (a == b) {
      return true;
    }
    if ((a == null) || (b == null)) {
      return false;
    }
    if (a.getClass().isArray()) {
      if (b.getClass().isArray()) {
        Object[] ar = (Object[])(Object[])a;
        Object[] br = (Object[])(Object[])b;
        if (ar.length == br.length) {
          for (int i = 0; i < ar.length; ++i) {
            if (!(equals(ar[i], br[i]))) {
              return false;
            }
          }
          return true;
        }
        return false;
      }
      return false;
    }
    return a.equals(b);
  }
}