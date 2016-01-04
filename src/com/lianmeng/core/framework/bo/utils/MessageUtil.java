package com.lianmeng.core.framework.bo.utils;

import com.lianmeng.core.framework.bo.server.DynamicDict;
import com.lianmeng.core.framework.exceptions.AppException;


public class MessageUtil
{
  public static String build(LoggingParameter lp, String msg)
  {
    return msg;
  }

  public static String build(LoggingParameter lp, String msg, Throwable t) {
    return msg;
  }

  public static String build(LoggingParameter lp, DynamicDict bo)
  {
    String serviceName;
    String xml;
    if (bo == null) {
      serviceName = null;
      xml = null;
    }
    else {
      serviceName = bo.getServiceName();
      try {
        xml = bo.asXML("UTF-8");
      }
      catch (AppException e) {
        xml = "error while converting xml : " + e.toString();
      }
    }

    return xml;
  }


}