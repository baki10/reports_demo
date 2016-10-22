package com.bakigoal.report.birt.base.printer;

import org.eclipse.birt.report.engine.api.IReportEngine;
import org.springframework.stereotype.Component;

@Component
public class BirtEngine {

  public static IReportEngine get() {
    return ReportEngineHolder.REPORT_ENGINE;
  }

  private static class ReportEngineHolder {
    static final IReportEngine REPORT_ENGINE = new BirtEngineFactory().getObject();
  }

}