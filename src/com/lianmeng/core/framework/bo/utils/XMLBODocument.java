package com.lianmeng.core.framework.bo.utils;

import org.apache.axiom.om.OMDocument;
import org.dom4j.Document;

public class XMLBODocument
{
  private Document document;
  private OMDocument omDocument;
  private int choice = -1;

  public XMLBODocument(Document document) {
    setDocument(document);
  }

  public XMLBODocument(OMDocument omDocument) {
    setOmDocument(omDocument);
  }

  public int getChoice() {
    return this.choice;
  }

  public Document getDocument() {
    return this.document;
  }

  public void setDocument(Document document) {
    this.choice = 1;
    this.document = document;
  }

  public OMDocument getOmDocument() {
    return this.omDocument;
  }

  public void setOmDocument(OMDocument omDocument) {
    this.choice = 2;
    this.omDocument = omDocument;
  }
}