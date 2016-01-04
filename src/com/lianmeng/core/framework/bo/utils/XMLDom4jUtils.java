package com.lianmeng.core.framework.bo.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.io.XPP3Reader;

import com.lianmeng.core.framework.exceptions.AppException;
import com.lianmeng.core.framework.exceptions.ExceptionHandler;

public final class XMLDom4jUtils
{
  private static final Logger logger = Logger.getLogger(XMLDom4jUtils.class);
  public static final String DEFAULT_ENCODING = "UTF-8";

  public static Element child(Element element, String name)
  {
    return element.element(new QName(name, element.getNamespace()));
  }

  public static Element child(Element element, String name, boolean optional)
    throws AppException
  {
    Element child = element.element(new QName(name, element.getNamespace()));
    if ((child == null) && (!(optional))) {
      ExceptionHandler.publish("UTIL-0001", name + " element expected as child of " + element.getName() + ".");
    }
    return child;
  }

  public static List<Element> children(Element element, String name)
  {
    return element.elements(new QName(name, element.getNamespace()));
  }

  public static String getAttribute(Element element, String name, boolean optional)
    throws AppException
  {
    Attribute attr = element.attribute(name);
    if ((attr == null) && (!(optional))) {
      ExceptionHandler.publish("UTIL-0001", "Attribute " + name + " of " + element.getName() + " expected.");
    } else {
      if (attr != null) {
        return attr.getValue();
      }

      return null;
    }
    return attr.getValue();
  }

  public static Date getAttributeAsDate(Element element, String name, boolean optional)
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
      ExceptionHandler.publish("UTIL-0001", element.getName() + "/@" + name + " attribute: value format error.", exception);
    }

    return null;
  }

  public static String getAttributeAsString(Element element, String name, boolean optional)
    throws AppException
  {
    return getAttribute(element, name, optional);
  }

  public static int getAttributeAsInt(Element element, String name, boolean optional)
    throws AppException
  {
    try
    {
      return Integer.parseInt(getAttribute(element, name, optional));
    }
    catch (NumberFormatException exception) {
      ExceptionHandler.publish("UTIL-0001", element.getName() + "/@" + name + " attribute: value format error.", exception);
    }

    return -1;
  }

  public static int getAttributeAsInt(Element element, String name, int defaultValue, boolean optional)
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
      ExceptionHandler.publish("UTIL-0001", element.getName() + "/@" + name + " attribute: value format error.", exception);
    }

    return defaultValue;
  }

  public static float getAttributeAsFloat(Element element, String name, boolean optional)
    throws AppException
  {
    try
    {
      return Float.parseFloat(getAttribute(element, name, optional));
    }
    catch (NumberFormatException exception) {
      ExceptionHandler.publish("UTIL-0001", element.getName() + "/@" + name + " attribute: value format error.", exception);
    }

    return -1.0F;
  }

  public static float getAttributeAsFloat(Element element, String name, float defaultValue, boolean optional)
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
      ExceptionHandler.publish("UTIL-0001", element.getName() + "/@" + name + " attribute: value format error.", exception);
    }

    return defaultValue;
  }

  public static long getAttributeAsLong(Element element, String name, boolean optional)
    throws AppException
  {
    try
    {
      return Long.parseLong(getAttribute(element, name, optional));
    }
    catch (NumberFormatException exception) {
      ExceptionHandler.publish("UTIL-0001", element.getName() + "/@" + name + " attribute: value format error.", exception);
    }

    return -1L;
  }

  public static long getAttributeAsLong(Element element, String name, long defaultValue, boolean optional)
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
      ExceptionHandler.publish("UTIL-0001", element.getName() + "/@" + name + " attribute: value format error.", exception);
    }

    return defaultValue;
  }

  public static Element getFirstChild(Element element, String name, boolean optional)
    throws AppException
  {
    List list = element.elements(new QName(name, element.getNamespace()));

    if (list.size() > 0) {
      return ((Element)list.get(0));
    }

    if (!(optional)) {
      ExceptionHandler.publish("UTIL-0001", name + " element expected as first child of " + element.getName() + ".");
    }
    else
    {
      return null;
    }

    return null;
  }

  public static Element getSibling(Element element, boolean optional)
    throws AppException
  {
    return getSibling(element, element.getName(), optional);
  }

  public static Element getSibling(Element element, String name, boolean optional)
    throws AppException
  {
    List list = element.getParent().elements(name);
    if (list.size() > 0) {
      return ((Element)list.get(0));
    }

    if (!(optional)) {
      ExceptionHandler.publish("UTIL-0001", name + " element expected after " + element.getName() + ".");
    }
    else {
      return null;
    }

    return null;
  }

  public static String getContent(Element element, boolean optional)
    throws AppException
  {
    String content = element.getText();
    if ((content == null) && (!(optional))) {
      ExceptionHandler.publish("UTIL-0001", element.getName() + " element: content expected.");
    }
    else {
      return content;
    }
    return null;
  }

  public static String getContentAsString(Element element, boolean optional)
    throws AppException
  {
    return getContent(element, optional);
  }

  public static int getContentAsInt(Element element, boolean optional)
    throws AppException
  {
    try
    {
      return Integer.parseInt(getContent(element, optional));
    }
    catch (NumberFormatException exception) {
      ExceptionHandler.publish("UTIL-0001", element.getName() + " element: content format error.", exception);
    }
    return -1;
  }

  public static int getContentAsInt(Element element, int defaultValue, boolean optional)
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
      ExceptionHandler.publish("UTIL-0001", element.getName() + " element: content format error.", exception);
    }

    return defaultValue;
  }

  public static long getContentAsLong(Element element, boolean optional)
    throws AppException
  {
    try
    {
      return Long.parseLong(getContent(element, optional));
    }
    catch (NumberFormatException exception) {
      ExceptionHandler.publish("UTIL-0001", element.getName() + " element: content format error.", exception);
    }
    return -1L;
  }

  public static long getContentAsLong(Element element, long defaultValue, boolean optional)
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
      ExceptionHandler.publish("UTIL-0001", element.getName() + " element: content format error.", exception);
    }

    return defaultValue;
  }

  public static float getContentAsFloat(Element element, boolean optional)
    throws AppException
  {
    try
    {
      return Float.parseFloat(getContent(element, optional));
    }
    catch (NumberFormatException exception) {
      ExceptionHandler.publish("UTIL-0001", element.getName() + " element: content format error.", exception);
    }
    return -1.0F;
  }

  public static float getContentAsFloat(Element element, float defaultValue, boolean optional)
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
      ExceptionHandler.publish("UTIL-0001", element.getName() + " element: content format error.", exception);
    }

    return defaultValue;
  }

  public static Date getContentAsDate(Element element, boolean optional)
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
      ExceptionHandler.publish("UTIL-0001", element.getName() + " element: content format error.", exception);
    }

    return null;
  }

  public static String getSubTagValue(Element root, String subTagName)
  {
    String returnString = root.elementText(subTagName);
    return returnString;
  }

  public static String getSubTagValue(Element root, String tagName, String subTagName)
  {
    Element child = root.element(tagName);
    String returnString = child.elementText(subTagName);
    return returnString;
  }

  public static Element appendChild(Element parent, String name, String value)
  {
    Element element = parent.addElement(new QName(name, parent.getNamespace()));
    if (value != null) {
      element.addText(value);
    }
    return element;
  }

  public static Element appendChild(Element parent, String name)
  {
    return parent.addElement(new QName(name, parent.getNamespace()));
  }

  public static Element appendChild(Element parent, String name, int value)
  {
    return appendChild(parent, name, String.valueOf(value));
  }

  public static Element appendChild(Element parent, String name, long value)
  {
    return appendChild(parent, name, String.valueOf(value));
  }

  public static Element appendChild(Element parent, String name, float value)
  {
    return appendChild(parent, name, String.valueOf(value));
  }

  public static Element appendChild(Element parent, String name, Date value)
  {
    return appendChild(parent, name, DateUtilBase.date2String(value));
  }

  public static boolean checkDocumentType(Document document, String dtdPublicId)
  {
    DocumentType documentType = document.getDocType();
    if (documentType != null) {
      String publicId = documentType.getPublicID();
      return ((publicId != null) && (publicId.equals(dtdPublicId)));
    }
    return true;
  }

  public static Document createDocument()
  {
    DocumentFactory factory = new DocumentFactory();
    Document document = factory.createDocument();
    return document;
  }

  

  public static Document fromXML(InputStream inputSource, String encoding)
    throws AppException
  {
    try
    {
      if ((encoding == null) || (encoding.equals(""))) {
        encoding = "UTF-8";
      }
      SAXReader reader = new SAXReader();
      Document document = reader.read(inputSource, encoding);
      return document;
    }
    catch (Exception ex) {
      ExceptionHandler.publish("UTIL-0001", ex);
    }
    return null;
  }

  

  public static void toXML(Document document, Writer outWriter, String encoding)
    throws AppException
  {
    OutputFormat outformat = OutputFormat.createPrettyPrint();
    if ((encoding == null) || (encoding.trim().equals(""))) {
      encoding = "UTF-8";
    }

    outformat.setEncoding(encoding);
    XMLWriter xmlWriter = null;
    try {
      xmlWriter = new XMLWriter(outWriter, outformat);
      xmlWriter.write(document);
      xmlWriter.flush();
    }
    catch (IOException ex) {
      ExceptionHandler.publish("UTIL-0001", ex);
    }
    finally {
      if (xmlWriter != null)
        try {
          xmlWriter.close();
        }
        catch (IOException ex) {
          logger.error(ex);
        }
    }
  }

  public static void toXML(Document document, OutputStream outStream, String encoding)
    throws AppException
  {
    OutputFormat outformat = new OutputFormat();
    outformat.setIndentSize(2);
    outformat.setNewlines(true);
    outformat.setTrimText(false);

    if ((encoding == null) || (encoding.trim().equals(""))) {
      encoding = "UTF-8";
    }

    outformat.setEncoding(encoding);
    XMLWriter xmlWriter = null;
    try {
      xmlWriter = new XMLWriter(new OutputStreamWriter(outStream), outformat);
      xmlWriter.write(document);
      xmlWriter.flush();
    }
    catch (IOException ex) {
      ExceptionHandler.publish("UTIL-0001", ex);
    }
    finally {
      if (xmlWriter != null)
        try {
          xmlWriter.close();
        }
        catch (IOException ex) {
          logger.error(ex);
        }
    }
  }

  public static byte[] toXML(Document document, String encoding)
    throws AppException
  {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();

    toXML(document, stream, encoding);
    if (stream != null) {
      try {
        stream.close();
      }
      catch (IOException ex) {
        logger.error(ex);
      }
    }
    return stream.toByteArray();
  }

  public static Document createDocument(Object xmlSource, boolean validate, String encoding)
    throws AppException
  {
    if (xmlSource instanceof Document) {
      return ((Document)xmlSource);
    }
    Document doc = null;
    SAXReader saxReader = new SAXReader(true);
    saxReader.setValidation(validate);
    if ((encoding == null) || (encoding.equals(""))) {
      encoding = "UTF-8";
    }

    if (xmlSource instanceof StringBuffer)
      xmlSource = ((StringBuffer)xmlSource).toString();
    try
    {
      if (xmlSource instanceof String) {
        if (((String)xmlSource).startsWith("<"))
        {
          StringReader reader = new StringReader(xmlSource.toString());
          DocumentHelper.parseText(xmlSource.toString());
          doc = saxReader.read(reader, encoding);
        }
        else {
          doc = saxReader.read(new File((String)xmlSource));
        }
      }
      else if (xmlSource instanceof File) {
        doc = saxReader.read((File)xmlSource);
      }
      else if (xmlSource instanceof InputStream) {
        doc = saxReader.read((InputStream)xmlSource);
      }
      else if (xmlSource instanceof Reader) {
        doc = saxReader.read((Reader)xmlSource);
      }
      else if (xmlSource instanceof URL) {
        doc = saxReader.read((URL)xmlSource);
      }
    }
    catch (Exception ex)
    {
      ExceptionHandler.publish("UTIL-0001", ex);
    }

    return doc;
  }

  public static StringBuffer generateXMLStringBuffer(Object xmlObj, String encoding)
    throws AppException
  {
    StringWriter writer = new StringWriter();
    OutputFormat outformat = OutputFormat.createPrettyPrint();

    if ((encoding == null) || (encoding.trim().equals(""))) {
      encoding = "UTF-8";
    }
    outformat.setEncoding(encoding);

    XMLWriter xmlWriter = null;
    xmlWriter = new XMLWriter(writer, outformat);
    try
    {
      xmlWriter.write(xmlObj);
      xmlWriter.flush();
    }
    catch (Exception ex) {
      ExceptionHandler.publish("UTIL-0002", ex);
    }

    return writer.getBuffer();
  }

  public static boolean generateXMLFile(Object xmlObj, String encoding, String filename)
    throws AppException
  {
    FileWriter writer = null;
    OutputFormat outformat = OutputFormat.createPrettyPrint();

    if ((encoding == null) || (encoding.trim().equals(""))) {
      encoding = "UTF-8";
    }
    outformat.setEncoding(encoding);
    try
    {
      writer = new FileWriter(filename);
      XMLWriter xmlWriter = null;
      xmlWriter = new XMLWriter(writer, outformat);
      xmlWriter.write(xmlObj);
      xmlWriter.flush();
    }
    catch (Exception ex) {
      ExceptionHandler.publish("UTIL-0004", ex);
    }
    finally {
      if (writer != null) {
        try {
          writer.close();
        }
        catch (IOException e) {
          logger.error(e);
        }
      }
    }

    return true;
  }

  public static String getElementContext(Element element)
  {
    if (element == null) {
      return null;
    }
    String str = element.getText();
    Element tmp = null;
    for (Iterator i = element.elementIterator(); i.hasNext(); ) {
      tmp = (Element)i.next();
      str = str + tmp.asXML();
    }

    return str;
  }

  public static String getElementContext(Element element, String path, String attr)
  {
    Element el = element.element(path);
    if ((attr == null) || (attr.trim().equals(""))) {
      return el.getText();
    }

    return el.attributeValue(attr);
  }

  public static String getElementContext(Element element, String path)
    throws AppException
  {
    if ((element == null) || (path == null)) {
      return null;
    }

    Object o = element.selectSingleNode(path);
    if (o == null) {
      return null;
    }

    if (o instanceof Element) {
      Element e = (Element)o;
      if (e.isTextOnly()) {
        return e.getText();
      }

      return generateXMLStringBuffer(e, "").toString();
    }

    if (o instanceof Attribute) {
      return ((Attribute)o).getValue();
    }

    return generateXMLStringBuffer(o, "").toString();
  }

  public static String[] getElementContextArray(Element element, String path)
    throws AppException
  {
    if ((element == null) || (path == null)) {
      return null;
    }
    List nodes = element.selectNodes(path);
    String[] eleContext = new String[nodes.size()];
    Iterator iter = nodes.iterator();
    int length = 0;
    Object o = null;
    Element e = null;
    while (iter.hasNext()) {
      o = iter.next();
      if (o instanceof Element) {
        e = (Element)o;
        if (e.isTextOnly()) {
          eleContext[length] = e.getText();
          ++length;
        }

        eleContext[length] = generateXMLStringBuffer(e, "").toString();
        ++length;
      }

      if (o instanceof Attribute) {
        eleContext[length] = ((Attribute)o).getValue();
        ++length;
      }

      eleContext[length] = generateXMLStringBuffer(o, "").toString();
      ++length;
    }

    return eleContext;
  }

  public static Element appendAttribute(Element ele, String attributeName, String attributeValue)
  {
    if (ele == null) {
      return null;
    }

    ele.addAttribute(attributeName, attributeValue);
    return ele;
  }

  public static Element removeAttribute(Element ele, String attributeName)
  {
    if (ele == null) {
      return null;
    }

    Attribute att = ele.attribute(attributeName);
    ele.remove(att);
    return ele;
  }

  public static Element setAttribute(Element ele, String attributeName, String attributeValue)
  {
    if (ele == null) {
      return null;
    }

    Attribute att = ele.attribute(attributeName);
    if (att != null) {
      att.setText(attributeValue);
    }
    else {
      appendAttribute(ele, attributeName, attributeValue);
    }
    return ele;
  }
}