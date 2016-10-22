package com.bakigoal.report.jasper;

/**
 * Названия отчетов
 */
public enum ReportNameEnum {

  SIGN_FRAME("Бланк с прямоугольником для подписи", "sign_frame.jrxml");

  private final String reportName;
  private final String fileName;

  ReportNameEnum(String reportName, String fileName) {
    this.reportName = reportName;
    this.fileName = fileName;
  }

  public String getReportName() {
    return reportName;
  }

  public String getFileName() {
    return fileName;
  }
}
