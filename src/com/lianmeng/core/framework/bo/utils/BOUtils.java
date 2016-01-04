package com.lianmeng.core.framework.bo.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMDocument;
import org.apache.axiom.om.OMElement;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.lianmeng.core.framework.bo.server.DynamicDict;
import com.lianmeng.core.framework.bo.server.IAction;
import com.lianmeng.core.framework.exceptions.AppException;
import com.lianmeng.core.framework.exceptions.ExceptionHandler;

public class BOUtils
{
  private static final Logger logger = Logger.getLogger(BOUtils.class);
  private static XmlParser xmlParser;
  private static final String __BO_TO_XML_MODE__ = "__BO_TO_XML_MODE__";

  public static IAction getActionInstance(String actionClassName)
    throws AppException
  {
    IAction action = null;
    try
    {
      action = (IAction)Class.forName(actionClassName.trim()).newInstance();
    }
    catch (ClassNotFoundException e)
    {
      logger.error("class not found className[" + actionClassName + "]", e);
      ExceptionHandler.publish("S-SYS-00017", null, e, actionClassName);
    }
    catch (InstantiationException e) {
      logger.error("class instance error className[" + actionClassName + "]", e);
      ExceptionHandler.publish("S-SYS-00018", null, e, actionClassName);
    }
    catch (IllegalAccessException e) {
      logger.error("class access error className[" + actionClassName + "]", e);
      ExceptionHandler.publish("S-SYS-00019", null, e, actionClassName);
    }
    catch (Throwable t) {
      logger.error("class access error className[" + actionClassName + "]", t);
      ExceptionHandler.publish("S-SYS-00019", null, t, actionClassName);
    }
    return action;
  }

  public static Class getActionClass(String actionClassName) throws AppException {
    Class actionCls = null;
    try
    {
      actionCls = Class.forName(actionClassName.trim());
    }
    catch (ClassNotFoundException e)
    {
      logger.error("class not found className[" + actionClassName + "]", e);
      ExceptionHandler.publish("S-SYS-00017", null, e, actionClassName);
    }
    catch (Throwable t) {
      logger.error("class access error className[" + actionClassName + "]", t);
      ExceptionHandler.publish("S-SYS-00019", null, t, actionClassName);
    }
    return actionCls;
  }

  public static String bo2xml(DynamicDict dict, String encoding)
    throws AppException
  {
    return bo2xml(dict, encoding, Bo2XmlMode.Old);
  }

  public static String bo2xml(DynamicDict dict, String encoding, Bo2XmlMode bo2XmlMode) throws AppException {
    if (bo2XmlMode == null) {
      bo2XmlMode = Bo2XmlMode.Old;
    }
    String xml = null;
    try {
      xml = xmlParser.bo2Xml(dict, encoding, bo2XmlMode);
    }
    catch (Exception ex) {
      logger.error("fail to convert bo to xml,bo_map [ " + dict.asMapString() + " ]", ex);
      ExceptionHandler.publish("UTIL-0001", ex);
    }
    return xml;
  }

  public static DynamicDict xml2bo(XMLBODocument document, Object xmlSource, String encoding)
    throws AppException
  {
    if (xmlSource instanceof String) {
      return xmlParser.xml2Bo(null, (String)xmlSource, encoding);
    }

    return null;
  }

  public static DynamicDict xml2bo(Object xmlSource, String encoding)
    throws AppException
  {
    return xml2bo(null, xmlSource, encoding);
  }

  

  public static void add2Map(Map map, String name, Object value)
  {
    if ((map == null) || (name == null)) {
      return;
    }

    Object obj = map.get(name);
    ArrayList newObj;
    if (obj != null)
    {
      if (obj instanceof ArrayList) {
        ((ArrayList)obj).add(value);
        map.remove(name);
        map.put(name, obj);
      }
      else {
        newObj = new ArrayList();
        newObj.add(obj);

        newObj.add(value);

        map.remove(name);
        map.put(name, newObj);
      }

    }
    else if (value instanceof DynamicDict) {
      newObj = new ArrayList();
      newObj.add(value);
      map.remove(name);
      map.put(name, newObj);
    }
    else {
      map.put(name, value);
    }
  }

  

  public static enum Bo2XmlMode
  {
    Old, ArraySensitive;

    String mode;
    private static final Map<String, Bo2XmlMode> CACHE;

    public String getMode()
    {
      return this.mode;
    }

    public static Bo2XmlMode forName(String name)
    {
      if (CACHE.size() <= 0) {
        synchronized (CACHE) {
          if (CACHE.size() <= 0) {
            Bo2XmlMode[] array = values();
            for (Bo2XmlMode e : array) {
              CACHE.put(e.getMode(), e);
            }
          }
        }
      }
      return ((Bo2XmlMode)CACHE.get(name));
    }

    static
    {
      CACHE = new HashMap();
    }
  }

  private class XmlParserStrategy_Stax_Axiom
    implements BOUtils.XmlParserStrategy
  {
    public String parseBo(DynamicDict dict, String encoding, BOUtils.Bo2XmlMode bo2XmlMode)
      throws AppException
    {
      OMDocument document = XMLStAXUtils.createDocument();

      String xmlVesion = dict.getString("XML_VERSION");
      if (StringUtils.equals("1.1", xmlVesion)) {
        document.setXMLVersion("1.1");
      }

      OMElement zsmartroot = XMLStAXUtils.appendChild(document, "zsmart", "");

      if (document != null)
      {
        XMLStAXUtils.appendChild(zsmartroot, "ServiceName", dict.getServiceName());

        if (bo2XmlMode == null) {
          bo2XmlMode = BOUtils.Bo2XmlMode.Old;
        }
        if (bo2XmlMode != BOUtils.Bo2XmlMode.Old) {
          XMLStAXUtils.appendChild(zsmartroot, "__BO_TO_XML_MODE__", bo2XmlMode.getMode());
        }

        HashMap map = dict.valueMap;
        parseBo(zsmartroot, map, true, encoding, bo2XmlMode);
      }

      return parseBo(document, encoding);
    }

    public DynamicDict parseStr(Object document, String str, String encoding) throws AppException
    {
      DynamicDict dict = null;
      if (document == null) {
        document = XMLStAXUtils.fromXML(new StringReader(str), encoding);
      }

      if (document == null) {
        BOUtils.logger.error("doc null.");
        return dict;
      }

      OMElement rootElement = ((OMDocument)document).getOMDocumentElement();
      dict = new DynamicDict();

      OMElement el = XMLStAXUtils.child(rootElement, "ServiceName", true);
      if (el != null) {
        dict.setServiceName(el.getText());
      }

      BOUtils.Bo2XmlMode bo2XmlMode = null;
      OMElement modeE = XMLStAXUtils.child(rootElement, "__BO_TO_XML_MODE__", true);
      if (modeE != null) {
        bo2XmlMode = BOUtils.Bo2XmlMode.forName(modeE.getText());
      }
      if (bo2XmlMode == null) {
        bo2XmlMode = BOUtils.Bo2XmlMode.Old;
      }

      OMElement xmlElement = XMLStAXUtils.child(rootElement, "Data", true);
      if (xmlElement != null) {
        parseStr(dict, xmlElement, null, encoding, bo2XmlMode);
      }

      return dict;
    }

    private void parseBo(OMElement xmlElement, HashMap map, boolean top, String encoding, BOUtils.Bo2XmlMode bo2XmlMode) {
      OMElement curr = null;
      OMElement parent = null;
      if (top) {
        parent = XMLStAXUtils.appendChild(xmlElement, "Data", "");
      }
      else {
        parent = xmlElement;
      }

      if ((map.size() == 0) && (!(top)))
      {
        XMLStAXUtils.appendAttribute(parent, "type", "Dict");
        return;
      }
      Set s = map.entrySet();
      Map.Entry m = null;
      Object o = null;
      for (Iterator iter = s.iterator(); iter.hasNext(); ) {
        m = (Map.Entry)iter.next();
        o = m.getValue();

        if (o instanceof DynamicDict) {
          curr = XMLStAXUtils.appendChild(parent, (String)m.getKey());
          parseBo(curr, ((DynamicDict)o).valueMap, false, encoding, bo2XmlMode);
        }

        if (o instanceof HashMap) {
          curr = XMLStAXUtils.appendChild(parent, (String)m.getKey());
          parseBo(curr, (HashMap)o, false, encoding, bo2XmlMode);
        }

        if (o instanceof ArrayList) {
          parseBo(parent, (String)m.getKey(), (ArrayList)o, encoding, bo2XmlMode);
        }

        if (o instanceof Date)
        {
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

          curr = XMLStAXUtils.appendChild(parent, (String)m.getKey(), dateFormat.format((Date)o));
        }

        if (o == null) {
          o = "";
        }

        curr = XMLStAXUtils.appendChild(parent, (String)m.getKey(), o.toString());
      }
    }

    private void parseBo(OMElement xmlElement, String path, ArrayList al, String encoding, BOUtils.Bo2XmlMode bo2XmlMode)
    {
      OMElement curr = null;
      Object o = null;

      for (Iterator iter = al.iterator(); iter.hasNext(); ) {
        o = iter.next();

        if (o instanceof DynamicDict) {
          curr = XMLStAXUtils.appendChild(xmlElement, path);
          parseBo(curr, ((DynamicDict)o).valueMap, false, encoding, bo2XmlMode);
        }
        else if (o instanceof HashMap) {
          curr = XMLStAXUtils.appendChild(xmlElement, path);
          parseBo(curr, (HashMap)o, false, encoding, bo2XmlMode);
        }
        else if (o instanceof ArrayList) {
          curr = XMLStAXUtils.appendChild(xmlElement, path);
          parseBo(xmlElement, path, (ArrayList)o, encoding, bo2XmlMode);
        }
        else if (o instanceof Date) {
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
          curr = XMLStAXUtils.appendChild(xmlElement, path, dateFormat.format((Date)o));
        }
        else
        {
          if (o == null) {
            o = "";
          }
          curr = XMLStAXUtils.appendChild(xmlElement, path, o.toString());
        }

        if (bo2XmlMode == BOUtils.Bo2XmlMode.ArraySensitive);
        XMLStAXUtils.appendAttribute(curr, "al", "");
      }
    }

    public String parseBo(OMDocument document, String encoding) throws AppException
    {
      if (document == null) {
        return "";
      }
      return XMLStAXUtils.docToXML(document, encoding);
    }

    private DynamicDict parseStr(DynamicDict dict, OMElement xmlElement, HashMap tmpMap, String encoding, BOUtils.Bo2XmlMode bo2XmlMode)
      throws AppException
    {
      if (dict == null) {
        dict = new DynamicDict();
      }

      if (xmlElement == null) {
        return dict;
      }

      String elName = xmlElement.getQName().getLocalPart();

      HashMap map = null;
      OMElement child = null;

      if (xmlElement.getChildElements().hasNext()) {
        map = new HashMap();
        for (Iterator iter = xmlElement.getChildElements(); iter.hasNext(); ) {
          child = (OMElement)iter.next();

          if (child.getChildElements().hasNext()) {
            parseClusterElement(encoding, bo2XmlMode, map, child);
          }

          parseTextElem(map, child);
        }

        if (tmpMap != null) {
          DynamicDict tmpDict = new DynamicDict();
          tmpDict.valueMap = map;
          BOUtils.add2Map(tmpMap, elName, tmpDict);
        }
        else {
          dict.valueMap = map;
        }
      }

      return dict;
    }

    private void parseTextElem(HashMap<String, Object> map, OMElement child) throws AppException {
      String name = child.getQName().getLocalPart();
      String value = child.getText();
      String type = XMLStAXUtils.getAttributeAsString(child, "type", true);

      Object oValue = null;
      if ((type != null) && (!("String".equals(type)))) {
        if ("Long".equals(type)) {
          oValue = new Long(Long.parseLong(value));
          BOUtils.add2Map(map, name, oValue);
        }
        else if ("Integer".equals(type)) {
          oValue = new Integer(Integer.parseInt(value));
          BOUtils.add2Map(map, name, oValue);
        }
        else if ("Date".equals(type)) {
          try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            oValue = dateFormat.parse(value);
          }
          catch (ParseException e) {
            BOUtils.logger.error("", e);
          }
          BOUtils.add2Map(map, name, oValue);
        }
        else if ("Dict".equals(type)) {
          DynamicDict emptyBo = new DynamicDict();
          BOUtils.add2Map(map, name, emptyBo);
        }
      }
      else
        BOUtils.add2Map(map, name, value);
    }

    private void parseClusterElement(String encoding, BOUtils.Bo2XmlMode bo2XmlMode, HashMap<String, Object> map, OMElement child)
      throws AppException
    {
      DynamicDict dictTmp = new DynamicDict();
      parseStr(dictTmp, child, null, encoding, bo2XmlMode);

      String fieldName = child.getQName().getLocalPart();
      if (bo2XmlMode == BOUtils.Bo2XmlMode.Old) {
        BOUtils.add2Map(map, fieldName, dictTmp);
      }
      else if (bo2XmlMode == BOUtils.Bo2XmlMode.ArraySensitive) {
        OMAttribute attr = child.getAttribute(new QName("al"));
        if (attr != null) {
          BOUtils.add2Map(map, fieldName, dictTmp);
        }
        else
          map.put(fieldName, dictTmp);
      }
    }
  }

  private class XmlParserStrategy_Dom_Dom4j
    implements BOUtils.XmlParserStrategy
  {
    public String parseBo(DynamicDict dict, String encoding, BOUtils.Bo2XmlMode bo2XmlMode)
      throws AppException
    {
      Document doc = null;
      doc = XMLDom4jUtils.createDocument("<zsmart></zsmart>", false, encoding);

      if (doc != null) {
        Element el = doc.getRootElement();
        XMLDom4jUtils.appendChild(el, "ServiceName", dict.getServiceName());

        HashMap map = dict.valueMap;
        parseBo(el, map, true, encoding);
      }

      return parseBo(doc, encoding);
    }

    private void parseBo(Element xmlElement, HashMap map, boolean top, String encoding)
    {
      Element curr = null;
      Element parent = null;
      if (top) {
        parent = XMLDom4jUtils.appendChild(xmlElement, "Data", "");
      }
      else {
        parent = xmlElement;
      }

      Set s = map.entrySet();
      Map.Entry m = null;
      Object o = null;
      for (Iterator iter = s.iterator(); iter.hasNext(); ) {
        m = (Map.Entry)iter.next();
        o = m.getValue();

        if (o instanceof DynamicDict) {
          curr = XMLDom4jUtils.appendChild(parent, (String)m.getKey());
          parseBo(curr, ((DynamicDict)o).valueMap, false, encoding);
        }

        if (o instanceof HashMap) {
          curr = XMLDom4jUtils.appendChild(parent, (String)m.getKey());
          parseBo(curr, (HashMap)o, false, encoding);
        }

        if (o instanceof ArrayList) {
          parseBo(parent, (String)m.getKey(), (ArrayList)o, encoding);
        }

        if (o instanceof Date)
        {
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

          curr = XMLDom4jUtils.appendChild(parent, (String)m.getKey(), dateFormat.format((Date)o));
        }

        if (o == null) {
          o = "";
        }

        curr = XMLDom4jUtils.appendChild(parent, (String)m.getKey(), o.toString());
      }
    }

    private void parseBo(Element xmlElement, String path, ArrayList al, String encoding)
    {
      Element curr = null;
      Object o = null;

      for (Iterator iter = al.iterator(); iter.hasNext(); ) {
        o = iter.next();

        if (o instanceof DynamicDict) {
          curr = XMLDom4jUtils.appendChild(xmlElement, path);
          parseBo(curr, ((DynamicDict)o).valueMap, false, encoding);
        }
        if (o instanceof HashMap) {
          curr = XMLDom4jUtils.appendChild(xmlElement, path);
          parseBo(curr, (HashMap)o, false, encoding);
        }
        if (o instanceof ArrayList) {
          curr = XMLDom4jUtils.appendChild(xmlElement, path);
          parseBo(xmlElement, path, (ArrayList)o, encoding);
        }

        if (o instanceof Date) {
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
          curr = XMLDom4jUtils.appendChild(xmlElement, path, dateFormat.format((Date)o));
        }

        if (o == null) {
          o = "";
        }
        curr = XMLDom4jUtils.appendChild(xmlElement, path, o.toString());
      }
    }

    private String parseBo(Document document, String encoding)
    {
      if (document == null) {
        return ""; }
      StringWriter write = null;
      XMLWriter xmlWriter = null;
      String str;
      try {
        write = new StringWriter();
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding(encoding);

        format.setTrimText(false);
        xmlWriter = new XMLWriter(write, format);
        xmlWriter.write(document);
        str = write.getBuffer().toString();

        return str;
      }
      catch (IOException ex)
      {
        BOUtils.logger.error("parse xml str occur error !! parser return empty string! encoding{" + encoding + "}", ex);
        str = "";

        return str;
      }
      finally
      {
        if (write != null) {
          try {
            write.close();
          }
          catch (IOException ie)
          {
            BOUtils.logger.error(ie);
          }
        }
        if (xmlWriter != null)
          try {
            xmlWriter.close();
          }
          catch (IOException ie)
          {
            BOUtils.logger.error(ie);
          }
      }
    }

    public DynamicDict parseStr(Object document, String xmlSource, String encoding) throws AppException
    {
      DynamicDict dict = null;
      if (document == null) {
        document = XMLDom4jUtils.createDocument(xmlSource, false, encoding);
      }

      if (document == null) {
        BOUtils.logger.error("doc null.");
        return dict;
      }

      Element rootElement = ((Document)document).getRootElement();
      dict = new DynamicDict();

      Element el = (Element)rootElement.selectSingleNode("ServiceName");
      if (el != null) {
        dict.setServiceName(el.getTextTrim());
      }

      Element xmlElement = (Element)rootElement.selectSingleNode("Data");
      if (xmlElement != null) {
        parseStr(dict, xmlElement, null, encoding);
      }

      return dict;
    }

    private DynamicDict parseStr(DynamicDict dict, Element xmlElement, HashMap tmpMap, String encoding)
    {
      if (dict == null) {
        dict = new DynamicDict();
      }

      if (xmlElement == null) {
        return dict;
      }

      String elName = xmlElement.getName();

      String value = null;

      String name = null;

      String type = null;

      Object oValue = null;

      HashMap map = null;
      Element child = null;

      if (xmlElement.elements().size() > 0) {
        map = new HashMap();
        for (Iterator iter = xmlElement.elements().iterator(); iter.hasNext(); ) {
          child = (Element)iter.next();

          if (child.elements().size() > 0) {
            DynamicDict dictTmp = new DynamicDict();
            parseStr(dictTmp, child, null, encoding);
            BOUtils.add2Map(map, child.getName(), dictTmp);
          }

          name = child.getName();
          value = child.getText();
          type = child.attributeValue("type");
          if ((type != null) && (!("String".equals(type)))) {
            if ("Long".equals(type)) {
              oValue = new Long(Long.parseLong(value));
              BOUtils.add2Map(map, name, oValue);
            }
            if ("Integer".equals(type)) {
              oValue = new Integer(Integer.parseInt(value));
              BOUtils.add2Map(map, name, oValue);
            }
            if (!("Date".equals(type)))
              continue;
            try {
              SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
              oValue = dateFormat.parse(value);
            }
            catch (ParseException e) {
              BOUtils.logger.error("", e);
            }
            BOUtils.add2Map(map, name, oValue);
          }

          BOUtils.add2Map(map, name, value);
        }

        if (tmpMap != null)
        {
          DynamicDict tmpDict = new DynamicDict();
          tmpDict.valueMap = map;
          BOUtils.add2Map(tmpMap, elName, tmpDict);
        }
        else {
          dict.valueMap = map;
        }

      }

      return dict;
    }
  }

  private  class XmlParser
  {
    private int type = 0;

    private BOUtils.XmlParserStrategy[] strategy = { 
    		new BOUtils.XmlParserStrategy_Dom_Dom4j(), 
    		new BOUtils.XmlParserStrategy_Stax_Axiom() 
    		};

    private XmlParser(int paramInt)
    {
      if (paramInt > 0) {
        if (paramInt > this.strategy.length) {
          throw new IllegalArgumentException("this type of bo xml Parser is not exist! type=" + paramInt);
        }
        this.type = paramInt;
      }
    }

    public String bo2Xml(DynamicDict dict, String encoding, BOUtils.Bo2XmlMode bo2XmlMode) throws AppException {
      return this.strategy[this.type].parseBo(dict, encoding, bo2XmlMode);
    }

    public DynamicDict xml2Bo(Object doc, String xmlSource, String encoding) throws AppException {
      DynamicDict dict = null;
      try {
        dict = this.strategy[this.type].parseStr(doc, xmlSource, encoding);
      }
      catch (Throwable ex) {
        BOUtils.logger.error("fail to convert xml to bo,source[" + xmlSource + "]");
        throw new AppException(ex);
      }
      return dict;
    }
  }

  private static abstract interface XmlParserStrategy
  {
    public abstract String parseBo(DynamicDict paramDynamicDict, String paramString, BOUtils.Bo2XmlMode paramBo2XmlMode)
      throws AppException;

    public abstract DynamicDict parseStr(Object paramObject, String paramString1, String paramString2)
      throws AppException;
  }

  private static enum XmlParser_POLICY
  {
    DOM4J, AXIOM;
  }
}