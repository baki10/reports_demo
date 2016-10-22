package com.bakigoal.rest;

import com.bakigoal.report.birt.BirtReportService;
import com.bakigoal.report.jasper.JasperReportService;
import com.bakigoal.report.jasper.ReportNameEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер отчетов
 */

@RestController
@RequestMapping(value = "/report")
public class ReportController {

  @Autowired
  private JasperReportService jasperReportService;

  @Autowired
  private BirtReportService birtReportService;

  @RequestMapping(value = "/jasper/sign", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
  public byte[] jasperPrint() {
    return jasperReportService.print(ReportNameEnum.SIGN_FRAME.name(), null);
  }

  @RequestMapping(value = "/birt/car", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
  public byte[] birtPrint() {
    return birtReportService.print("car");
  }
}
