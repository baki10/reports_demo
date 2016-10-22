package com.bakigoal.report.birt.printer;

import com.bakigoal.report.birt.base.printer.BaseReportPrinter;
import com.bakigoal.report.birt.base.util.DomUtils;
import com.bakigoal.report.birt.dao.CarReportDao;
import com.bakigoal.report.birt.dto.CarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CarReportPrinter extends BaseReportPrinter<CarDto, CarReportDao> {

  @Autowired
  public CarReportPrinter(CarReportDao dao) {
    super(dao);
    setDtoClass(CarDto.class);
  }

  @Override
  protected Map<String, Object> getParameters(CarDto dto) {
    return new HashMap<>();
  }

  @Override
  protected Element createXml(List<Object[]> queryResultSet, CarDto dto) {
    Document document = DomUtils.createDocument("<data-source></data-source>");
    Element table = DomUtils.createChildElement(document.getDocumentElement(), "table");
    for (Object[] item : queryResultSet) {
      Element rowElement = DomUtils.createChildElement(table, "row");
      DomUtils.createChildElement(rowElement, "make").setTextContent((String) item[0]);
      DomUtils.createChildElement(rowElement, "model").setTextContent((String) item[1]);
      DomUtils.createChildElement(rowElement, "year").setTextContent((String) item[2]);
    }
    return document.getDocumentElement();
  }
}
