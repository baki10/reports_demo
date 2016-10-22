package com.bakigoal.report.jasper;

import com.bakigoal.report.jasper.printer.BasePrinter;
import com.bakigoal.report.jasper.printer.SignFramePrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Фабрика для получения объектов {@link BasePrinter#}
 */
@Component("reportPrinterFactory")
public class ReportPrinterFactory {

  @Autowired
  private SignFramePrinter signFramePrinter;


  public BasePrinter getPrinter(ReportNameEnum reportName) {
    switch (reportName) {
      case SIGN_FRAME:
        return signFramePrinter;
      default:
        return null;
    }
  }
}
