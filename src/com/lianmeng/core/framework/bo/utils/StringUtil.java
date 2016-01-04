package com.lianmeng.core.framework.bo.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lianmeng.core.framework.exceptions.AppException;

public abstract class StringUtil
{
  
  public static String toGBK(String val)
    throws UnsupportedEncodingException
  {
    return new String(val.getBytes("GBK"));
  }

  public static String toGB2312_ENCODING(String val)
    throws UnsupportedEncodingException
  {
    return new String(val.getBytes(), "GB2312");
  }

  public static String toGB2312(String val)
    throws UnsupportedEncodingException
  {
    return new String(val.getBytes("GB2312"));
  }

  public static String getISO_8859_1(String val)
    throws UnsupportedEncodingException
  {
    return new String(val.getBytes("ISO-8859-1"));
  }

  public static String getGB2312(String val)
    throws UnsupportedEncodingException
  {
    return new String(val.getBytes("GB2312"));
  }

  public static String toISO_8859_1(String val)
    throws UnsupportedEncodingException
  {
    return new String(val.getBytes(), "ISO-8859-1");
  }

  public static String toUTF_8(String val)
    throws UnsupportedEncodingException
  {
    return new String(val.getBytes("UTF-8"));
  }

  public static String encode(String val, String encoding)
    throws UnsupportedEncodingException
  {
    if (val == null) {
      return null;
    }
    return new String(val.getBytes(encoding));
  }

  public static String GBKToISO_8859_1(String val)
    throws UnsupportedEncodingException
  {
    return encode(val, "GBK", "ISO-8859-1");
  }

  public static String GB2312ToISO_8859_1(String val)
    throws UnsupportedEncodingException
  {
    return encode(val, "GB2312", "ISO-8859-1");
  }

  public static String ISO_8859_1ToGB2312(String val)
    throws UnsupportedEncodingException
  {
    return encode(val, "ISO-8859-1", "GB2312");
  }

  public static String ISO_8859_1ToGBK(String val)
    throws UnsupportedEncodingException
  {
    return encode(val, "ISO-8859-1", "GBK");
  }

  public static String getGBK(String val)
  {
    try
    {
      return encode(val, "GBK");
    } catch (UnsupportedEncodingException ex) {
    }
    return "";
  }

  public static String encode(String val, String formEncoding, String toEncoding)
    throws UnsupportedEncodingException
  {
    if (val == null) {
      return null;
    }
    return new String(val.getBytes(formEncoding), toEncoding);
  }

  public static String getStandardStr(String columnName)
  {
    columnName = columnName.toLowerCase();
    StringBuffer sb = new StringBuffer();
    StringTokenizer token = new StringTokenizer(columnName, "_");
    int itoken = token.countTokens();
    for (int i = 0; i < itoken; ++i) {
      if (i == 0) {
        sb.append(token.nextToken());
      }
      else {
        String temp = token.nextToken();
        sb.append(temp.substring(0, 1).toUpperCase());
        sb.append(temp.substring(1));
      }
    }
    return sb.toString();
  }

  public static boolean isEmpty(String val)
  {
    return ((val == null) || (val.length() == 0));
  }

  public static boolean isNotEmpty(String val)
  {
    return ((val != null) && (val.length() != 0));
  }

  public static String toString(Object val)
  {
    if (val == null) {
      return "";
    }
    if (val.getClass().isArray()) {
      return arrayToString((Object[])(Object[])val);
    }

    if (val instanceof List) {
      return listToString((List)val, null);
    }
    return val.toString();
  }

  public static String toString(Object[] objs)
  {
    if ((objs != null) && (objs.length > 0)) {
      StringBuffer buff = new StringBuffer();
      for (int i = 0; i < objs.length; ++i) {
        buff.append("\nItem[").append(i).append("]\n").append(objs[i]);
      }
      return buff.toString();
    }

    return "";
  }

  public static String toString(int val)
  {
    return Integer.toString(val);
  }

  public static String toString(float val)
  {
    return Float.toString(val);
  }

  public static String toString(double val)
  {
    return Double.toString(val);
  }

  public static String toString(long val)
  {
    return Long.toString(val);
  }

  public static String toString(short val)
  {
    return Short.toString(val);
  }

  public static String toString(boolean val)
  {
    return Boolean.toString(val);
  }

  public static String toCommaString(Long[] longArray)
  {
    if ((longArray == null) || (longArray.length <= 0)) {
      return "";
    }
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < longArray.length; ++i) {
      buff.append(longArray[i]);
      if (i != longArray.length - 1) {
        buff.append(",");
      }
    }
    return buff.toString();
  }

  public static String listToString(List list, String itemName)
  {
    if (!(ValidateUtil.validateNotEmpty(list))) {
      return "";
    }

    int size = list.size();
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < size; ++i) {
      buff.append(list.get(i).toString()).append("\n");
    }
    return buff.toString();
  }

  public static String arrayToString(Object[] objs)
  {
    if (!(ValidateUtil.validateNotEmpty(objs))) {
      return "";
    }

    int size = objs.length;
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < size; ++i) {
      buff.append(objs[i].toString()).append("\n");
    }
    return buff.toString();
  }

  public static String getTxtFileMessage(String errMsg)
  {
      
      return errMsg;
  }

  public static String toDBCodeMessage(String str)
  {

        return str;
  }

  public static String[] delRepeatData(ArrayList<String> al)
  {
    Set set = new HashSet(al);
    Object[] objs = set.toArray(new String[0]);
    return ((String[])(String[])objs);
  }

  public static String delRepeatData(String str)
  {
    ArrayList al = new ArrayList();
    StringBuilder dataBuf = new StringBuilder();
    if ((str != null) && (str.length() >= 1)) {
      StringTokenizer st = new StringTokenizer(str, ",");
      while (st.hasMoreTokens()) {
        al.add(st.nextToken());
      }
      String[] singleDateArr = delRepeatData(al);
      if (singleDateArr != null) {
        for (int i = 0; i < singleDateArr.length; ++i) {
          dataBuf.append(singleDateArr[i]);
          if (i != singleDateArr.length - 1) {
            dataBuf.append(",");
          }
        }
      }
    }

    return dataBuf.toString();
  }

  public static String[] delRepeatData(String[] strArr)
  {
    ArrayList al = new ArrayList();

    if (strArr != null) {
      for (int i = 0; i < strArr.length; ++i) {
        al.add(strArr[i]);
      }
    }

    String[] singleDateArr = delRepeatData(al);

    return singleDateArr;
  }

  public static Integer[] delRepeatData(Integer[] intArr)
  {
    ArrayList al = new ArrayList();

    if (intArr != null) {
      for (int i = 0; i < intArr.length; ++i) {
        al.add(intArr[i]);
      }
    }
    Set set = new HashSet(al);
    Object[] objs = set.toArray(new Integer[0]);
    return ((Integer[])(Integer[])objs);
  }

  public static Long[] delRepeatData(Long[] intArr)
  {
    ArrayList al = new ArrayList();

    if (intArr != null) {
      for (int i = 0; i < intArr.length; ++i) {
        al.add(intArr[i]);
      }
    }
    Set set = new HashSet(al);
    return ((Long[])set.toArray(new Long[0]));
  }

  public static String stringFormat(String pattern, Object[] arguments)
  {
    return MessageFormat.format(pattern, arguments);
  }

  public static String stringFormat(String pattern)
  {
    return pattern;
  }

  public static String stringFormat(String pattern, Object arg1) {
    Object[] argList = { arg1 };

    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2) {
    Object[] argList = { arg1, arg2 };

    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3) {
    Object[] argList = { arg1, arg2, arg3 };

    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4) {
    Object[] argList = { arg1, arg2, arg3, arg4 };

    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
    Object[] argList = { arg1, arg2, arg3, arg4, arg5 };

    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6)
  {
    Object[] argList = { arg1, arg2, arg3, arg4, arg5, arg6 };

    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7)
  {
    Object[] argList = { arg1, arg2, arg3, arg4, arg5, arg6, arg7 };

    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8)
  {
    Object[] argList = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8 };

    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9)
  {
    Object[] argList = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9 };

    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10)
  {
    Object[] argList = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10 };

    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11)
  {
    Object[] argList = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11 };

    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12)
  {
    Object[] argList = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12 };

    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13)
  {
    Object[] argList = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13 };

    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14)
  {
    Object[] argList = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14 };

    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14, Object arg15)
  {
    Object[] argList = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15 };

    return MessageFormat.format(pattern, argList);
  }

  public static String getStringDivideByCommaFromList(ArrayList array)
  {
    if ((array == null) || (array.size() == 0)) {
      return "";
    }

    Object[] bundle = (Object[])array.toArray(new Object[array.size()]);
    String ret = null;
    if (bundle[0] != null) {
      ret = bundle[0].toString();
    }

    for (int i = 1; i < bundle.length; ++i) {
      ret = new StringBuilder().append(ret).append(",").append(bundle[i]).toString();
    }
    return ret;
  }

  public static String padLeft(String s, int totalWidth, char paddingChar)
  {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < totalWidth - s.length(); ++i) {
      sb.append(paddingChar);
    }
    sb.append(s);
    return sb.toString();
  }

  public static String padRight(String s, int totalWidth, char paddingChar)
  {
    StringBuffer sb = new StringBuffer();
    sb.append(s);
    for (int i = 0; i < totalWidth - s.length(); ++i) {
      sb.append(paddingChar);
    }
    return sb.toString();
  }

  public static String getNameValueStringByArray(String[] names, String[] values)
    throws AppException
  {
    AssertUtil.areEqual(new Integer(names.length), new Integer(values.length), "The names array length is not euqal values array.");

    StringBuffer outStr = new StringBuffer();
    for (int i = 0; i < names.length; ++i) {
      outStr.append(names[i]);
      outStr.append("=\"");
      outStr.append(values[i]);
      outStr.append("\"");

      if (i != names.length - 1) {
        outStr.append("|");
      }
    }
    return outStr.toString();
  }

  public static boolean isNumeric(String str) {
    if (str == null) {
      return false;
    }
    for (int i = 0; i < str.length(); ++i) {
      if (!(Character.isDigit(str.charAt(i)))) {
        return false;
      }
    }
    return true;
  }

  public static String trim(String str)
  {
    return ((str == null) ? str : str.trim());
  }

  public static Map<String, String> splitPara(String paraSrc, String sepa)
  {
    if ((paraSrc == null) || (paraSrc.trim().length() == 0)) {
      return null;
    }

    LinkedHashMap paraMap = new LinkedHashMap();
    if ((sepa == null) || ("".equals(sepa)))
    {
      sepa = ",";
    }

    String[] paras = paraSrc.split(sepa);
    String[] tmpResult = null;
    int i = 0; for (int j = 0; i < paras.length; ++i) {
      tmpResult = paras[i].split("=");

      if (tmpResult.length >= 2) {
        paraMap.put(tmpResult[0].trim(), tmpResult[1]);
      }
      else if (tmpResult.length == 1) {
        if (paras[i].indexOf("=") >= 0) {
          paraMap.put(tmpResult[0].trim(), "");
        }
        else {
          paraMap.put(new StringBuilder().append("TEXT.").append(j).toString(), paras[i]);
          ++j;
        }
      }
    }

    return paraMap;
  }

  public static String toDBCS(String str)
  {
    if (str == null) {
      return "";
    }

    StringBuffer sb = new StringBuffer();
    int i = 0; for (int n = str.length(); i < n; ++i) {
      int c = str.charAt(i);
      if ((c >= 97) && (c <= 122)) {
        c = c + 65345 - 97;
      }
      else if ((c >= 65) && (c <= 90)) {
        c = c + 65313 - 65;
      }
      else if ((c >= 48) && (c <= 57)) {
        c = c + 65296 - 48;
      }

      sb.append((char)c);
    }

    return sb.toString();
  }

  public static String toSBCS(String str)
  {
    if (str == null) {
      return "";
    }

    StringBuffer sb = new StringBuffer();
    int i = 0; for (int n = str.length(); i < n; ++i) {
      int c = str.charAt(i);
      if ((c >= 65313) && (c <= 65338)) {
        c = c + 65 - 65313;
      }
      else if ((c >= 65345) && (c <= 65370)) {
        c = c + 97 - 65345;
      }
      else if ((c >= 65296) && (c <= 65305)) {
        c = c + 48 - 65296;
      }
      else if (c == 8221)
      {
        c = 34;
      }
      else if (c == 8220) {
        c = 34;
      }
      else if (c == 65308)
      {
        c = 60;
      }
      else if (c == 65310) {
        c = 62;
      }
      else if (c == 8217)
      {
        c = 39;
      }
      else if (c == 8216) {
        c = 39;
      }
      else if (c == 65292) {
        c = 44;
      }
      else if (c == 65307) {
        c = 59;
      }
      else if (c == 12290) {
        c = 46;
      }
      else if (c == 65286) {
        c = 38;
      }

      sb.append((char)c);
    }

    return sb.toString();
  }

  public static String[] findAll(String src, String pattern)
  {
    if (src == null) {
      return new String[0];
    }
    if (pattern == null) {
      return new String[0];
    }

    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(src);
    Collection l = new ArrayList();
    while (m.find()) {
      l.add(m.group());
    }

    return ((String[])(String[])l.toArray(new String[0]));
  }

  public static String replaceAll(String src, String pattern, String to)
  {
    if (src == null) {
      return null;
    }
    if (pattern == null) {
      return src;
    }

    StringBuffer sb = new StringBuffer();
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(src);

    int i = 1;
    while (m.find())
    {
      m.appendReplacement(sb, to);
      ++i;
    }
    m.appendTail(sb);

    return sb.toString();
  }

  public static final String getQryCondtion(String[] conditions, boolean isString)
  {
    if ((conditions == null) || (conditions.length <= 0)) {
      return null;
    }
    StringBuffer cond = new StringBuffer("(");
    for (int i = 0; i < conditions.length; ++i) {
      if (isString) {
        cond.append("'").append(conditions[i]).append("'");
      }
      else {
        cond.append(conditions[i]);
      }
      cond.append(",");
    }
    cond.replace(cond.length() - 1, cond.length(), ")");

    return cond.toString();
  }

  public static String loadFromCollection(Collection c)
  {
    StringBuffer sb = new StringBuffer();
    Iterator it = c.iterator();
    while (it.hasNext()) {
      sb.append(it.next().toString());
    }
    return sb.toString();
  }

  public static String loadFromFile(String filename)
    throws FileNotFoundException, IOException
  {
    StringBuffer sb = new StringBuffer();
    FileInputStream in = new FileInputStream(filename);
    byte[] buf = new byte[1024];
    try
    {
      while (true) {
        int n = in.read(buf);
        sb.append(new String(buf, 0, n));
        if (n < 1024)
          break;
      }
    }
    catch (IOException ex)
    {
    }
    finally
    {
      in.close();
    }

    return sb.toString();
  }

  public static String loadFromFile(String filename, String encoding)
    throws FileNotFoundException, IOException
  {
    StringBuffer sb = new StringBuffer();
    BufferedReader b = new BufferedReader(new InputStreamReader(new FileInputStream(filename), encoding));
    try
    {
      while (true) {
        String s = b.readLine();
        if (s == null) break;
        sb.append(new StringBuilder().append(s).append("\r\n").toString());
      }

    }
    catch (IOException ex)
    {
    }
    finally
    {
      b.close();
    }

    return sb.toString();
  }

  public static String loadFromUrl(String url)
    throws MalformedURLException, IOException
  {
    StringBuffer sb = new StringBuffer();
    URL u = new URL(url);
    InputStream in = u.openStream();
    byte[] buf = new byte[1024];
    try
    {
      while (true) {
        int n = in.read(buf);
        sb.append(new String(buf, 0, n));
        if (n < 1024) {
          break;
        }
      }
    }
    catch (IOException ex)
    {
      throw ex;
    }
    finally {
      in.close();
    }

    return sb.toString();
  }

  public static String replace(String source, String oldString, String newString)
  {
    StringBuffer output = new StringBuffer();
    int lengthOfSource = source.length();
    int lengthOfOld = oldString.length();
    int pos;
    int posStart=0;
    for ( posStart = 0; (pos = source.indexOf(oldString, posStart)) >= 0; posStart = pos + lengthOfOld) {
      output.append(source.substring(posStart, pos));
      output.append(newString);
    }

    if (posStart < lengthOfSource) {
      output.append(source.substring(posStart));
    }
    return output.toString();
  }

  public static String secondSplit(String name, String split)
  {
    if ((name == null) || ("".equals(name))) {
      return "";
    }
    if ((split == null) || ("".equals(split))) {
      split = ".";
    }

    int index = name.indexOf(split);
    if (index >= 0) {
      return name.substring(index + split.length());
    }

    return name;
  }

  public static String pathname(String name, String split)
  {
    if ((name == null) || ("".equals(name))) {
      return "";
    }
    if ((split == null) || ("".equals(split))) {
      split = ".";
    }

    int index = name.lastIndexOf(split);
    if (index >= 0) {
      return name.substring(0, index);
    }

    return name;
  }

  public static String basename(String name, String split)
  {
    if ((name == null) || (name.equals(""))) {
      return "";
    }
    if ((split == null) || (split.equals(""))) {
      split = ".";
    }

    int index = name.lastIndexOf(split);
    if (index >= 0) {
      return name.substring(index + split.length());
    }

    return name;
  }

  

  public static String firstCharToUpperCase(String src)
  {
    if (src == null) {
      return null;
    }

    if (src.length() > 0) {
      src = new StringBuilder().append(src.substring(0, 1).toUpperCase()).append(src.substring(1)).toString();
    }

    return src;
  }

  public static String throwableToString(Throwable t)
  {
    StringBuffer sb = new StringBuffer();
    sb.append(t.getClass().getName());
    if (t.getMessage() != null) {
      ByteArrayOutputStream bo = new ByteArrayOutputStream();
      t.printStackTrace(new PrintStream(bo));
      sb.append(new StringBuilder().append(":\r\n\t").append(bo.toString()).toString());
    }
    return sb.toString();
  }

  public static String long2StringForLen(long num, String str)
  {
    int len = String.valueOf(num).length();
    if (len > str.length()) {
      return String.valueOf(num).substring(len - str.length(), len);
    }

    return new StringBuilder().append(str.substring(0, str.length() - len)).append(String.valueOf(num)).toString();
  }

  public static String objToString(Object obj)
  {
    return ((obj == null) ? "" : obj.toString());
  }

  public static final String[] split(String line, String separator)
  {
    LinkedList list = new LinkedList();
    if (line != null) {
      int start = 0;
      int end = 0;
      int separatorLen = separator.length();
      while ((end = line.indexOf(separator, start)) >= 0) {
        list.add(line.substring(start, end));
        start = end + separatorLen;
      }
      if (start < line.length()) {
        list.add(line.substring(start, line.length()));
      }
    }
    return ((String[])(String[])list.toArray(new String[list.size()]));
  }

  public static String splitString(String str, int len, String elide)
  {
    if (str == null) {
      return "";
    }
    byte[] strByte = str.getBytes();
    int strLen = strByte.length;
    int elideLen = (elide.trim().length() == 0) ? 0 : elide.getBytes().length;
    if ((len >= strLen) || (len < 1)) {
      return str;
    }
    if (len - elideLen > 0) {
      len -= elideLen;
    }
    int count = 0;
    for (int i = 0; i < len; ++i) {
      int value = strByte[i];
      if (value < 0) {
        ++count;
      }
    }
    if (count % 2 != 0) {
      len = (len == 1) ? len + 1 : len - 1;
    }

    return new StringBuilder().append(new String(strByte, 0, len)).append(elide.trim()).toString();
  }

  public static String getStringDivideByCommaFromList(Object[] inputList, boolean isNeedAddSingleQuote)
  {
    if ((inputList == null) || (inputList.length == 0)) {
      return "";
    }
    String ret = null;
    if (inputList[0] != null) {
      if (isNeedAddSingleQuote) {
        ret = new StringBuilder().append("'").append(inputList[0].toString()).append("'").toString();
      }
      else {
        ret = inputList[0].toString();
      }
    }

    for (int i = 1; i < inputList.length; ++i)
    {
      if (isNeedAddSingleQuote) {
        ret = new StringBuilder().append(ret).append(",'").append(inputList[i].toString()).append("'").toString();
      }
      else {
        ret = new StringBuilder().append(ret).append(",").append(inputList[i].toString()).toString();
      }
    }
    return ret;
  }
}