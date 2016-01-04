package com.lianmeng.core.framework.bo.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpUtils
{
  public static String httpGet(String urlStr)
    throws IOException
  {
    return httpGet(urlStr, null);
  }

  public static String httpGet(String urlStr, Map requestProp)
    throws IOException
  {
    URL url = new URL(urlStr);
    HttpURLConnection con = (HttpURLConnection)url.openConnection();
    con.setRequestMethod("GET");
    Map.Entry prop;
    Iterator iter;
    if (requestProp != null) {
      prop = null;
      for (iter = requestProp.entrySet().iterator(); iter.hasNext(); ) {
        prop = (Map.Entry)iter.next();
        con.setRequestProperty((String)prop.getKey(), (String)prop.getValue());
      }

    }

    con.getResponseCode();
    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    StringBuffer strBuf = new StringBuffer();
    String tmp = in.readLine();
    while (tmp != null) {
      strBuf.append(tmp + "\r\n");
      tmp = in.readLine();
    }
    in.close();

    return strBuf.toString();
  }

  public static String httpPost(String urlStr, String postRequest)
    throws IOException
  {
    return httpPost(urlStr, postRequest, null);
  }

  public static String httpPost(String urlStr, String postRequest, Map requestProp)
    throws IOException
  {
    URL url = new URL(urlStr);
    HttpURLConnection con = (HttpURLConnection)url.openConnection();
    con.setRequestMethod("POST");
    con.setUseCaches(false);
    con.setDoOutput(true);
    con.setDoInput(true);

    Map.Entry prop = null;
    Iterator iter;
    if (requestProp != null) {
      for (iter = requestProp.entrySet().iterator(); iter.hasNext(); ) {
        prop = (Map.Entry)iter.next();
        con.setRequestProperty((String)prop.getKey(), (String)prop.getValue());
      }

    }

    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
    out.write(postRequest);
    out.flush();
    out.close();

    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    StringBuffer strBuf = new StringBuffer();
    String tmp = in.readLine();
    while (tmp != null) {
      strBuf.append(tmp + "\r\n");
      tmp = in.readLine();
    }
    in.close();

    return strBuf.toString();
  }
}