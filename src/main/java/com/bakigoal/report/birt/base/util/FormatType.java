package com.bakigoal.report.birt.base.util;

/**
* <p></p>
*
* @author Eduard Grinchenko
*/
public enum FormatType {
  XLS,
  XLSX,
  DOC,
  DOCX,
  PDF;

  public String getFileExtension() {
    return name().toLowerCase();
  }
}
