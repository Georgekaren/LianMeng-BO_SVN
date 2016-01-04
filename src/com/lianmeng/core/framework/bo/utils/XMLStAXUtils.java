package com.lianmeng.core.framework.bo.utils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMContainer;
import org.apache.axiom.om.OMDocument;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMSerializable;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axiom.om.util.StAXUtils;
import org.apache.log4j.Logger;

import com.lianmeng.core.framework.exceptions.AppException;
import com.lianmeng.core.framework.exceptions.ExceptionHandler;

public final class XMLStAXUtils
{
  public static final String DEFAULT_ENCODING = "UTF-8";
  private static final Logger logger = Logger.getLogger(XMLStAXUtils.class);

  public static OMElement child(OMElement element, String name)
  {
    return element.getFirstChildWithName(new QName(name));
  }

  public static OMElement child(OMElement element, String name, boolean optional)
    throws AppException
  {
    OMElement child = element.getFirstChildWithName(new QName(name));
    if ((child == null) && (!(optional))) {
      ExceptionHandler.publish("UTIL-0001", name + " element expected as child of " + element.getLocalName() + ".");
    }

    return child;
  }

  public static List<OMElement> children(OMElement element, String name)
  {
    Iterator iter = element.getChildrenWithName(new QName(name));
    List list = new ArrayList();
    while ((iter != null) && 
      (iter.hasNext())) {
      list.add((OMElement)iter.next());
    }

    return list;
  }

  public static String getAttribute(OMElement element, String name, boolean optional)
    throws AppException
  {
    OMAttribute attr = element.getAttribute(new QName(name));
    if ((attr == null) && (!(optional))) {
      ExceptionHandler.publish("UTIL-0001", "Attribute " + name + " of " + element.getLocalName() + " expected.");
      return null;
    }
    if (attr != null) {
      return attr.getAttributeValue();
    }

    return null;
  }

  public static Date getAttributeAsDate(OMElement element, String name, boolean optional)
    throws AppException
  {
    String value = getAttribute(element, name, optional);
    if ((optional) && (((value == null) || (value.equals(""))))) {
      return null;
    }
    try
    {
      return DateUtilBase.string2SQLDate(value);
    }
    catch (Exception exception) {
      ExceptionHandler.publish("UTIL-0001", element.getLocalName() + "/@" + name + " attribute: value format error.", exception);
    }

    return null;
  }

  public static String getAttributeAsString(OMElement element, String name, boolean optional)
    throws AppException
  {
    return getAttribute(element, name, optional);
  }

  public static int getAttributeAsInt(OMElement element, String name, boolean optional)
    throws AppException
  {
    try
    {
      return Integer.parseInt(getAttribute(element, name, optional));
    }
    catch (NumberFormatException exception) {
      ExceptionHandler.publish("UTIL-0001", element.getLocalName() + "/@" + name + " attribute: value format error.", exception);
    }

    return -1;
  }

  public static int getAttributeAsInt(OMElement element, String name, int defaultValue, boolean optional)
    throws AppException
  {
    String value = getAttribute(element, name, optional);
    if ((optional) && (((value == null) || (value.equals(""))))) {
      return defaultValue;
    }
    try
    {
      return Integer.parseInt(value);
    }
    catch (NumberFormatException exception) {
      ExceptionHandler.publish("UTIL-0001", element.getLocalName() + "/@" + name + " attribute: value format error.", exception);
    }

    return defaultValue;
  }

  public static float getAttributeAsFloat(OMElement element, String name, boolean optional)
    throws AppException
  {
    try
    {
      return Float.parseFloat(getAttribute(element, name, optional));
    }
    catch (NumberFormatException exception) {
      ExceptionHandler.publish("UTIL-0001", element.getLocalName() + "/@" + name + " attribute: value format error.", exception);
    }

    return -1.0F;
  }

  public static float getAttributeAsFloat(OMElement element, String name, float defaultValue, boolean optional)
    throws AppException
  {
    String value = getAttribute(element, name, optional);
    if ((optional) && (((value == null) || (value.equals(""))))) {
      return defaultValue;
    }
    try
    {
      return Float.parseFloat(value);
    }
    catch (NumberFormatException exception) {
      ExceptionHandler.publish("UTIL-0001", element.getLocalName() + "/@" + name + " attribute: value format error.", exception);
    }

    return defaultValue;
  }

  public static long getAttributeAsLong(OMElement element, String name, boolean optional)
    throws AppException
  {
    try
    {
      return Long.parseLong(getAttribute(element, name, optional));
    }
    catch (NumberFormatException exception) {
      ExceptionHandler.publish("UTIL-0001", element.getLocalName() + "/@" + name + " attribute: value format error.", exception);
    }

    return -1L;
  }

  public static long getAttributeAsLong(OMElement element, String name, long defaultValue, boolean optional)
    throws AppException
  {
    String value = getAttribute(element, name, optional);
    if ((optional) && (((value == null) || (value.equals(""))))) {
      return defaultValue;
    }
    try
    {
      return Long.parseLong(value);
    }
    catch (NumberFormatException exception) {
      ExceptionHandler.publish("UTIL-0001", element.getLocalName() + "/@" + name + " attribute: value format error.", exception);
    }

    return defaultValue;
  }

  public static OMElement getFirstChild(OMElement element, String name, boolean optional)
    throws AppException
  {
    return child(element, name, optional);
  }

  public static OMElement getSibling(OMElement element, boolean optional)
    throws AppException
  {
    return getSibling(element, element.getLocalName(), optional);
  }

  public static OMElement getSibling(OMElement element, String name, boolean optional)
    throws AppException
  {
    OMElement ole = element.getParent().getFirstChildWithName(new QName(name));
    if ((ole == null) && (!(optional))) {
      ExceptionHandler.publish("UTIL-0001", name + " element expected after " + element.getLocalName() + ".");
    }
    return ole;
  }

  public static String getContent(OMElement element, boolean optional)
    throws AppException
  {
    String content = element.getText();
    if ((content == null) && (!(optional))) {
      ExceptionHandler.publish("UTIL-0001", element.getLocalName() + " element: content expected.");
    }
    else {
      return content;
    }
    return null;
  }

  public static String getContentAsString(OMElement element, boolean optional)
    throws AppException
  {
    return getContent(element, optional);
  }

  public static int getContentAsInt(OMElement element, boolean optional)
    throws AppException
  {
    try
    {
      return Integer.parseInt(getContent(element, optional));
    }
    catch (NumberFormatException exception) {
      ExceptionHandler.publish("UTIL-0001", element.getLocalName() + " element: content format error.", exception);
    }

    return -1;
  }

  public static int getContentAsInt(OMElement element, int defaultValue, boolean optional)
    throws AppException
  {
    String value = getContent(element, optional);
    if ((optional) && (((value == null) || (value.equals(""))))) {
      return defaultValue;
    }
    try
    {
      return Integer.parseInt(value);
    }
    catch (NumberFormatException exception) {
      ExceptionHandler.publish("UTIL-0001", element.getLocalName() + " element: content format error.", exception);
    }

    return defaultValue;
  }

  public static long getContentAsLong(OMElement element, boolean optional)
    throws AppException
  {
    try
    {
      return Long.parseLong(getContent(element, optional));
    }
    catch (NumberFormatException exception) {
      ExceptionHandler.publish("UTIL-0001", element.getLocalName() + " element: content format error.", exception);
    }

    return -1L;
  }

  public static long getContentAsLong(OMElement element, long defaultValue, boolean optional)
    throws AppException
  {
    String value = getContent(element, optional);
    if ((optional) && (((value == null) || (value.equals(""))))) {
      return defaultValue;
    }
    try
    {
      return Long.parseLong(value);
    }
    catch (NumberFormatException exception) {
      ExceptionHandler.publish("UTIL-0001", element.getLocalName() + " element: content format error.", exception);
    }

    return defaultValue;
  }

  public static float getContentAsFloat(OMElement element, boolean optional)
    throws AppException
  {
    try
    {
      return Float.parseFloat(getContent(element, optional));
    }
    catch (NumberFormatException exception) {
      ExceptionHandler.publish("UTIL-0001", element.getLocalName() + " element: content format error.", exception);
    }

    return -1.0F;
  }

  public static float getContentAsFloat(OMElement element, float defaultValue, boolean optional)
    throws AppException
  {
    String value = getContent(element, optional);
    if ((optional) && (((value == null) || (value.equals(""))))) {
      return defaultValue;
    }
    try
    {
      return Float.parseFloat(value);
    }
    catch (NumberFormatException exception) {
      ExceptionHandler.publish("UTIL-0001", element.getLocalName() + " element: content format error.", exception);
    }

    return defaultValue;
  }

  public static Date getContentAsDate(OMElement element, boolean optional)
    throws AppException
  {
    String value = getContent(element, optional);
    if ((optional) && (((value == null) || (value.equals(""))))) {
      return null;
    }
    try
    {
      return DateUtilBase.string2SQLDate(value);
    }
    catch (Exception exception) {
      ExceptionHandler.publish("UTIL-0001", element.getLocalName() + " element: content format error.", exception);
    }

    return null;
  }

  public static String getSubTagValue(OMElement root, String subTagName)
  {
    OMElement ele = child(root, subTagName);
    if (ele != null) {
      return ele.getText();
    }

    return null;
  }

  public static String getSubTagValue(OMElement root, String tagName, String subTagName)
  {
    OMElement ele = child(root, tagName);
    if (ele != null) {
      OMElement ele1 = child(ele, subTagName);
      if (ele1 != null) {
        return ele1.getText();
      }
      return null;
    }

    return null;
  }

  public static OMElement appendChild(OMDocument parent, String name, String value)
  {
    if ((name == null) || (name.trim().equals(""))) {
      name = "__NULL_KEY__";
    }
    OMFactory factory = OMAbstractFactory.getOMFactory();
    OMElement ele = factory.createOMElement(new QName(name));
    ele.setText(value);
    parent.addChild(ele);
    return ele;
  }

  public static OMElement appendChild(OMElement parent, String name, String value)
  {
    if ((name == null) || (name.trim().equals(""))) {
      name = "__NULL_KEY__";
    }
    OMFactory factory = OMAbstractFactory.getOMFactory();
    OMElement ele = factory.createOMElement(name, parent.getNamespace());
    ele.setText(value);
    parent.addChild(ele);
    return ele;
  }

  public static OMElement appendChild(OMElement parent, String name)
  {
    if ((name == null) || (name.trim().equals(""))) {
      name = "__NULL_KEY__";
    }
    OMFactory factory = OMAbstractFactory.getOMFactory();
    OMElement ele = factory.createOMElement(name, parent.getNamespace());
    parent.addChild(ele);
    return ele;
  }

  public static OMElement appendChild(OMElement parent, String name, int value)
  {
    if ((name == null) || (name.trim().equals(""))) {
      name = "__NULL_KEY__";
    }
    return appendChild(parent, name, String.valueOf(value));
  }

  public static OMElement appendChild(OMElement parent, String name, long value)
  {
    return appendChild(parent, name, String.valueOf(value));
  }

  public static OMElement appendChild(OMElement parent, String name, float value)
  {
    return appendChild(parent, name, String.valueOf(value));
  }

  public static OMElement appendChild(OMElement parent, String name, Date value)
  {
    return appendChild(parent, name, DateUtilBase.date2String(value));
  }

  public static OMDocument createDocument()
  {
    return OMAbstractFactory.getOMFactory().createOMDocument();
  }

  public static OMDocument fromXML(Reader in, String encoding)
    throws AppException
  {
    try
    {
      XMLInputFactory xif = XMLInputFactory.newInstance();
      XMLStreamReader reader = xif.createXMLStreamReader(in);

      StAXOMBuilder builder = new StAXOMBuilder(reader);
      OMDocument ele = builder.getDocument();
      return ele;
    }
    catch (Exception ex) {
      ExceptionHandler.publish("UTIL-0001", ex);
    }
    return null;
  }

  public static OMDocument fromXML(InputStream inputSource, String encoding)
    throws AppException
  {
    try
    {
      if ((encoding == null) || (encoding.equals(""))) {
        encoding = "UTF-8";
      }
      XMLInputFactory xif = XMLInputFactory.newInstance();
      XMLStreamReader reader = xif.createXMLStreamReader(inputSource, encoding);
      StAXOMBuilder builder = new StAXOMBuilder(reader);
      OMDocument ele = builder.getDocument();
      return ele;
    }
    catch (Exception ex) {
      ExceptionHandler.publish("UTIL-0001", ex);
    }
    return null;
  }

  public static OMDocument fromXML(String source, String encoding)
    throws AppException
  {
    return fromXML(new StringReader(source), encoding);
  }

  public static OMDocument fromURL(URL url, String encoding) throws AppException {
    if ((encoding == null) || (encoding.equals(""))) {
      encoding = "UTF-8";
    }

    ExceptionHandler.publish("UTIL-0001", "Unsupported.");
    return null;
  }

  public static void toXML(OMDocument document, OutputStream outStream, String encoding)
    throws AppException
  {
    if ((encoding == null) || ("".equals(encoding.trim())))
      encoding = "UTF-8";
    try
    {
      document.serialize(outStream);
    }
    catch (XMLStreamException e) {
      ExceptionHandler.publish("UTIL-0001", e);
    }
  }

  public static byte[] toXML(OMDocument document, String encoding)
    throws AppException
  {
    if ((encoding == null) || ("".equals(encoding.trim()))) {
      encoding = "UTF-8";
    }
    return serializeOM(document, encoding);
  }

  private static byte[] serializeOM(OMSerializable om, String encoding) throws AppException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    XMLStreamWriter streamWrite = null;
    try {
      streamWrite = StAXUtils.createXMLStreamWriter(outputStream, encoding);
      om.serialize(streamWrite);
    }
    catch (XMLStreamException e) {
      ExceptionHandler.publish("UTIL-0001", e);
    }
    finally {
      if (streamWrite != null) {
        try {
          streamWrite.close();
        }
        catch (XMLStreamException e) {
          ExceptionHandler.publish("UTIL-0001", e);
        }
      }
    }
    return outputStream.toByteArray();
  }

  public static String docToXML(OMDocument document, String encoding) throws AppException {
    if (document == null) {
      return "";
    }
    if ((encoding == null) || ("".equals(encoding.trim()))) {
      encoding = "UTF-8";
    }
    byte[] bytes = toXML(document, encoding);
    String xml = null;
    try {
      xml = new String(bytes, encoding);
    }
    catch (UnsupportedEncodingException e) {
      ExceptionHandler.publish("UTIL-0001", e);
    }
    return xml;
  }

  public static String toXML(OMElement element, String encoding)
    throws AppException
  {
    if ((encoding == null) || (encoding.trim().equals(""))) {
      encoding = "UTF-8";
    }
    byte[] bytes = serializeOM(element, encoding);
    String xml = null;
    try {
      xml = new String(bytes, encoding);
    }
    catch (UnsupportedEncodingException e) {
      ExceptionHandler.publish("UTIL-0001", e);
    }
    return xml;
  }

  public static OMDocument createDocument(Object xmlSource, boolean validate, String encoding)
    throws AppException
  {
    if (xmlSource instanceof OMDocument) {
      return ((OMDocument)xmlSource);
    }
    OMDocument doc = null;
    if ((encoding == null) || (encoding.equals(""))) {
      encoding = "UTF-8";
    }

    if (xmlSource instanceof StringBuffer) {
      xmlSource = ((StringBuffer)xmlSource).toString();
    }
    try
    {
      if (xmlSource instanceof String) {
        if (((String)xmlSource).startsWith("<")) {
          doc = fromXML(xmlSource.toString(), encoding);
        }
        else {
          File file = new File(xmlSource.toString());
          FileInputStream fis = new FileInputStream(file);
          doc = fromXML(fis, encoding);
        }
      }
      else if (xmlSource instanceof File) {
        FileInputStream fis = new FileInputStream((File)xmlSource);
        doc = fromXML(fis, encoding);
      }
      else if (xmlSource instanceof InputStream) {
        doc = fromXML((InputStream)xmlSource, encoding);
      }
      else if (xmlSource instanceof Reader) {
        doc = fromXML((Reader)xmlSource, encoding);
      }
      else if (xmlSource instanceof URL) {
        doc = fromURL((URL)xmlSource, encoding);
      }

    }
    catch (Exception ex)
    {
      ExceptionHandler.publish("UTIL-0001", ex);
    }

    return doc;
  }

  public static String getElementContext(OMElement element)
    throws AppException
  {
    if (element == null) {
      return null;
    }
    String str = element.getText();
    OMElement tmp = null;
    for (Iterator i = element.getChildElements(); i.hasNext(); ) {
      tmp = (OMElement)i.next();
      str = str + toXML(tmp, null);
    }

    return str;
  }

  public static String getElementContext(OMElement element, String path, String attr)
    throws AppException
  {
    ExceptionHandler.publish("UTIL-0004", "Unsupported");
    return null;
  }

  public static String getElementContext(OMElement element, String path)
    throws AppException
  {
    ExceptionHandler.publish("UTIL-0004", "Unsupported");
    return null;
  }

  public static String[] getElementContextArray(OMElement element, String path)
    throws AppException
  {
    ExceptionHandler.publish("UTIL-0004", "Unsupported");
    return null;
  }

  public static OMElement appendAttribute(OMElement ele, String attributeName, String attributeValue)
  {
    if (ele == null) {
      return null;
    }
    ele.addAttribute(attributeName, attributeValue, ele.getNamespace());
    return ele;
  }

  public static OMElement removeAttribute(OMElement ele, String attributeName)
  {
    if (ele == null) {
      return null;
    }
    OMAttribute att = ele.getAttribute(new QName(attributeName));
    ele.removeAttribute(att);
    return ele;
  }

  public static OMElement setAttribute(OMElement ele, String attributeName, String attributeValue)
  {
    if (ele == null) {
      return null;
    }

    OMAttribute att = ele.getAttribute(new QName(attributeName));
    if (att != null) {
      att.setAttributeValue(attributeValue);
    }
    else {
      appendAttribute(ele, attributeName, attributeValue);
    }
    return ele;
  }
}