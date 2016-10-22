package com.bakigoal.report.jasper;

import com.bakigoal.report.jasper.printer.BasePrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JasperReportService {

  @Autowired
  private ReportPrinterFactory reportPrinterFactory;

  public byte[] print(String fileName, String json) {
    if (fileName == null || fileName.isEmpty()) {
      return new byte[0];
    }
    BasePrinter printer = reportPrinterFactory.getPrinter(ReportNameEnum.valueOf(fileName));
    if (printer == null) {
      return new byte[0];
    }
    return printer.printPdf(json);
  }
}
