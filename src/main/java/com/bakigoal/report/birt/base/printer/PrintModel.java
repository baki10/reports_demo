package com.bakigoal.report.birt.base.printer;

import org.w3c.dom.Element;

import java.util.Map;

public class PrintModel {
  private final Element xml;
  private final Map<String, Object> parameters;

  public PrintModel(Element xml, Map<String, Object> parameters) {
    this.xml = xml;
    this.parameters = parameters;
  }

  public Element getXml() {
    return xml;
  }


  public Map<String, Object> getParameters() {
    return parameters;
  }

}
