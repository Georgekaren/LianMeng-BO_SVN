package com.lianmeng.core.framework.bo.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.lianmeng.core.framework.bo.server.DynamicDict;
import com.lianmeng.core.framework.exceptions.AppException;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class JSONUtil
{
  static final char fieldTypeString = 83;
  static final char fieldTypeLong = 76;
  static final char fieldTypeDouble = 70;
  static final char fieldTypeDate = 68;
  static final char fieldTypeList = 65;
  static final char fieldTypeBO = 66;
  static final String serviceNameField = "TFM_SERVICE_NAME";
  static final String errorCodeField = "TFM_ERROR_CODE";
  static final String errorMsgField = "TFM_ERROR_MSG";
  private static final Logger logger = Logger.getLogger(JSONObject.class);

  private static JSONObject BO2JSON(DynamicDict bo) {
    JSONObject jo = new JSONObject();
    if (bo != null) {
      HashMap valueMap = bo.valueMap;
      Iterator iter = valueMap.keySet().iterator();

      while (iter.hasNext()) {
        String key = (String)iter.next();
        Object value = valueMap.get(key);
        fillJSONObject(jo, key, value);
      }
      if (!(StringUtil.isEmpty(bo.getServiceName()))) {
        jo.put("TFM_SERVICE_NAME", bo.getServiceName());
      }
    }
    return jo;
  }

  public static String BO2JSONString(DynamicDict bo) {
    return BO2JSON(bo).toString();
  }

  public static DynamicDict JSON2BO(String json) throws AppException {
    JSONObject jo = JSONObject.fromObject(json);
    return JSON2BO(jo);
  }

  private static DynamicDict JSON2BO(JSONObject jo)
    throws AppException
  {
    DynamicDict bo = new DynamicDict();
    Iterator keys = jo.keys();
    while (keys.hasNext()) {
      String key = (String)keys.next();
      if (key.equals("TFM_SERVICE_NAME")) {
        bo.setServiceName(jo.getString(key));
      }

      char firstLetter = key.charAt(0);
      String fieldName = key.substring(1);
      if (firstLetter == 'S') {
        bo.set(fieldName, jo.getString(key));
      }
      else if (firstLetter == 'L') {
        bo.set(fieldName, jo.getLong(key));
      }
      else if (firstLetter == 'F') {
        bo.set(fieldName, Double.valueOf(jo.getDouble(key)));
      }
      else if (firstLetter == 'D') {
        Date d = new Date(jo.getLong(key));
        bo.set(fieldName, d);
      }
      else if (firstLetter == 'B') {
        JSONObject subJO = jo.getJSONObject(key);
        DynamicDict subBO = JSON2BO(subJO);
        bo.set(fieldName, subBO);
      }
      else if (firstLetter == 'A') {
        JSONArray ja = jo.getJSONArray(key);
        ArrayList subList = JSONArray2ArrayList(ja);
        bo.set(fieldName, subList);
      }
    }
    return bo;
  }

  private static ArrayList JSONArray2ArrayList(JSONArray ja)
    throws AppException
  {
    ArrayList al = new ArrayList();
    for (int i = 0; i < ja.size(); ++i) {
      Object obj = ja.get(i);

      if (obj instanceof String) {
        String value = (String)obj;
        char firstLetter = value.charAt(0);
        String actualValue = value.substring(1);
        if (firstLetter == 'S') {
          al.add(actualValue);
        }
        else if (firstLetter == 'L') {
          al.add(Long.valueOf(Long.parseLong(actualValue, 10)));
        }
        else if (firstLetter == 'F') {
          al.add(Double.valueOf(Double.parseDouble(actualValue)));
        }
        else if (firstLetter == 'D') {
          al.add(new Date(Long.parseLong(actualValue, 10)));
        }
      }
      else if (obj instanceof JSONArray) {
        ArrayList subArray = JSONArray2ArrayList((JSONArray)obj);
        al.add(subArray);
      }
      else if (obj instanceof JSONObject) {
        DynamicDict subBO = JSON2BO((JSONObject)obj);
        al.add(subBO);
      }
    }
    return al;
  }

  private static void fillJSONObject(JSONObject jo, String key, Object value)
    throws JSONException
  {
    logger.debug( new Object[] { key, value.getClass().getName() });
    if ((value instanceof String) || (value instanceof Boolean)) {
      jo.put('S' + key, value.toString());
    }
    else if (value instanceof Long) {
      jo.put('L' + key, (Long)value);
    }
    else if (value instanceof Double) {
      jo.put(Character.valueOf('F'), (Double)value);
    }
    else if (value instanceof Date) {
      jo.put('D' + key, Long.valueOf(((Date)value).getTime()));
    }
    else if (value instanceof List) {
      JSONArray ja = new JSONArray();
      List l = (List)value;
      for (int i = 0; i < l.size(); ++i) {
        fillJSONArray(ja, l.get(i));
      }
      if (ja.size() > 0) {
        jo.put('A' + key, ja);
      }
    }
    else if (value instanceof DynamicDict) {
      jo.put('B' + key, BO2JSON((DynamicDict)value));
    }
  }

  private static void fillJSONArray(JSONArray ja, Object obj) throws JSONException
  {
    logger.debug(new Object[] { obj.getClass().getName() });
    if (obj instanceof String) {
      ja.add('S' + ((String)obj));
    }
    else if ((obj instanceof Long) || (obj instanceof Integer) || (obj instanceof Short)) {
      ja.add('L' + obj.toString());
    }
    else if ((obj instanceof Float) || (obj instanceof Double)) {
      ja.add('F' + obj.toString());
    }
    else if (obj instanceof Date) {
      ja.add('D' + String.valueOf(((Date)obj).getTime()));
    }
    else if (obj instanceof List) {
      List l = (List)obj;
      JSONArray ja2 = new JSONArray();
      for (int i = 0; i < l.size(); ++i) {
        fillJSONArray(ja2, l.get(i));
      }
      if (ja2.size() > 0) {
        ja.add(ja2);
      }
    }
    else if (obj instanceof DynamicDict) {
      ja.add(BO2JSON((DynamicDict)obj));
    }
  }

  public static String AppException2JSON(AppException e) {
    JSONObject jo = new JSONObject();
    jo.put("TFM_ERROR_CODE", e.getCode());
    jo.put("TFM_ERROR_MSG", e.getMessage());
    return jo.toString();
  }
}