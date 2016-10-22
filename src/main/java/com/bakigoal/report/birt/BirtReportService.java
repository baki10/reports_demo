package com.bakigoal.report.birt;

import com.bakigoal.report.birt.base.printer.BaseReportPrinter;
import com.bakigoal.report.birt.base.printer.ReportManager;
import com.bakigoal.report.birt.base.util.FileStorage;
import com.bakigoal.report.birt.base.util.FormatType;
import com.bakigoal.report.birt.dto.CarDto;
import com.bakigoal.report.birt.printer.CarReportPrinter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class BirtReportService {
  @Autowired
  private ReportManager reportManager;
  @Autowired
  private CarReportPrinter printer;
  @Autowired
  private BeanFactory beanFactory;

  public byte[] print(String reportName) {
    String pdf = getReportName(FormatType.PDF, reportName);
    return pdf != null ? getFilesBytes(pdf) : new byte[0];
  }

  private byte[] getFilesBytes(String fileName) {
    FileStorage fileStorage = beanFactory.getBean(FileStorage.class);
    File file = fileStorage.getTemporaryFile(fileName);
    try {
      return file != null ? IOUtils.toByteArray(new FileInputStream(file)) : null;
    } catch (IOException e) {
      return new byte[0];
    }
  }

  public String getReportName(FormatType format, String fileName) {
    Validate.isInstanceOf(BaseReportPrinter.class, printer, "Некорректный объект принтера для этого отчета");
    BaseReportPrinter reportPrinter = printer;
    CarDto bean = new CarDto();
    reportPrinter.setPrintDto(bean);
    return reportManager.create(reportPrinter, format, fileName + ".rptdesign");
  }
}
