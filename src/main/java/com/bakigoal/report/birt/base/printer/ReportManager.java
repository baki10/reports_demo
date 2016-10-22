package com.bakigoal.report.birt.base.printer;

import com.bakigoal.report.birt.base.util.DomUtils;
import com.bakigoal.report.birt.base.util.FileStorage;
import com.bakigoal.report.birt.base.util.FormatType;
import org.apache.commons.lang3.CharEncoding;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("reportManager")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class ReportManager {
  public static final String DB_URL = "dburl";

  @Autowired
  private BeanFactory beanFactory;

  private ReportManager() {
  }

  protected InputStream getDesignStreamFromResources(String designName) {
    return getClass().getClassLoader().getResourceAsStream("reports/birt_templates/" + designName);
  }

  public File createReport(File xml, FormatType formatType, String designName, Map<String, Object> parameters) {
    try {
      return createReport(formatType, designName, getDesignStreamFromResources(designName), parameters);
    } finally {
      xml.delete();
    }
  }

  public File createReport(FormatType formatType, String designName, InputStream designStream,
                           Map<String, Object> parameters) {
    return createReport(formatType, designName, designStream, parameters, null);
  }

  public File createReport(FormatType formatType, String designName, InputStream designStream,
                           Map<String, Object> parameters, Map<String, Object> appContext) {
    File tmpFile;
    IReportEngine birtReportEngine = BirtEngine.get();
    try {
      tmpFile = beanFactory.getBean(FileStorage.class).createTemporaryFile("." + formatType.getFileExtension());
      IRunAndRenderTask task;

      IReportRunnable design = birtReportEngine.openReportDesign(designName, designStream);

      task = birtReportEngine.createRunAndRenderTask(design);
      RenderOption options;
      options = getRenderOptions(formatType);
      options.setOutputFileName(tmpFile.getAbsolutePath());
      if (appContext != null) {
        task.setAppContext(appContext);
      }
      task.setRenderOption(options);
      task.setParameterValues(parameters);
      task.run();
      task.close();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }

    return tmpFile;
  }

  /**
   * Настройки для формата отчета. Если output format такой же как и расширение файла, то
   * он будет взять из
   *
   * @param formatType формат отчета
   * @return вернет настроенные опции по формату, если формат не поддерживается,
   * @throws IllegalStateException если формат не поддерживается
   */
  protected RenderOption getRenderOptions(FormatType formatType) {
    RenderOption options;
    switch (formatType) {
      case XLS:
        options = getXlsRenderOption();
        break;
      case XLSX:
        options = new EXCELRenderOption();
        break;
      case PDF:
        options = new PDFRenderOption();
        break;
      case DOC:
        options = new RenderOption();
        options.setOption(IRenderOption.EMITTER_ID, "org.eclipse.birt.report.engine.emitter.word");
        break;
      default:
        throw new IllegalStateException("Unexpected format");
    }
    if (options.getOutputFormat() == null) {
      options.setOutputFormat(formatType.getFileExtension());
    }
    return options;
  }

  private RenderOption getXlsRenderOption() {
    return new EXCELRenderOption();
  }

  public File createFile(String content, FormatType formatType) {
    String extension = formatType.getFileExtension();

    File xml = beanFactory.getBean(FileStorage.class).createTemporaryFile("." + extension);

    try (Writer writer = FileStorage.getOutputStreamWriter(xml)) {
      writer.write(content);
      writer.flush();
      return xml;
    } catch (IOException ex) {
      throw new IllegalStateException(ex);
    }
  }

  public String create(Element xml, Map<String, Object> parameters, FormatType format, String designName) {
    return create(xml, parameters, format, designName, getDesignStreamFromResources(designName));
  }

  public String create(Element xml, Map<String, Object> parameters, FormatType format, String designName,
                       InputStream designStream) {
    String xmlContext = DomUtils.getFormattedXMLText(xml, CharEncoding.UTF_8);
    File xmlFile = createFile(xmlContext, format);
    Map<String, Object> newParams = new HashMap<>(parameters);
    newParams.put(DB_URL, xmlFile.getAbsolutePath());

    return createReport(format, designName, designStream, newParams).getName();

  }

  public String create(Map pojoList, Map<String, Object> parameters, FormatType format, String designName,
                       InputStream designStream) {
    Map<String, Object> newParams = new HashMap<>(parameters);
    return createReport(format, designName, designStream, newParams, pojoList).getName();
  }

  public String create(Printer printer, FormatType format, String designName) {
    return create(printer, format, designName, getDesignStreamFromResources(designName));
  }

  public String create(Printer printer, FormatType format, String designName, InputStream designStream) {
    PrintModel printModel = printer.build();
    if (printModel instanceof PojoPrintModel) {
      return create(
          ((PojoPrintModel) printModel).getPojoMap(), printModel.getParameters(), format, designName, designStream
      );
    } else {
      return create(printModel.getXml(), printModel.getParameters(), format, designName, designStream);
    }
  }

  public String create(List<? extends Printer> printers, FormatType format, String designName,
                       InputStream designStream) {
    List<File> xmlFiles = new ArrayList<>();
    Map<String, Object> newParams = new HashMap<>();
    for (int i = 0; i < printers.size(); i++) {
      PrintModel printModel = printers.get(i).build();
      String xmlContext = DomUtils.getFormattedXMLText(printModel.getXml(), CharEncoding.UTF_8);
      File xmlFile = createFile(xmlContext, format);
      xmlFiles.add(xmlFile);
      newParams.putAll(printModel.getParameters());
      newParams.put(DB_URL + (i > 0 ? i + 1 : ""), xmlFile.getAbsolutePath());
    }

    try {
      return createReport(format, designName, designStream, newParams).getName();
    } finally {
      for (File file : xmlFiles) {
        file.delete();
      }
    }
  }
}
