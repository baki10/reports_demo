package com.bakigoal.report.birt.base.printer;

import com.bakigoal.report.birt.base.dao.ReportDao;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class BaseReportPrinter<T extends Serializable, D extends ReportDao> extends BasePrinter<T> {
  private static final Integer TWO = 2;
  private static final Integer DEFAULT_GROUPING_SIZE = 3;

  protected D dao;

  protected BaseReportPrinter(Class<? extends T> dtoClass, D dao) {
    super(dtoClass);
    this.dao = dao;
  }

  protected BaseReportPrinter(D dao) {
    this.dao = dao;
  }

  @Override
  public PrintModel build() {
    if (getPrintDto() != null && dao != null) {
      Element xml = createXml(dao.getList(getPrintDto()), getPrintDto());
      return new PrintModel(xml, getParameters(getPrintDto()));
    }
    return null;
  }

  protected abstract Map<String, Object> getParameters(T dto);

  protected abstract Element createXml(List<Object[]> queryResultSet, T dto);


  public String safeToString(Object raw) {
    return safeToString(raw, TWO);
  }

  public String safeToString(Object raw, Integer countDigitAfterPoint) {
    return safeToString(raw, countDigitAfterPoint, DEFAULT_GROUPING_SIZE);
  }

  public String safeToString(Object raw, Integer countDigitAfterPoint, Integer countGroupingSize) {
    if (raw == null){
      return "";
    }

    if (!(raw instanceof BigDecimal)){
      return raw.toString();
    }

    return getBigDecimalPropertyFormat(countDigitAfterPoint, countGroupingSize).format(raw);
  }

  private static Format getBigDecimalPropertyFormat(int numberFractionDigits, int groupingSize) {
    DecimalFormat format = new DecimalFormat();
    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
    if (numberFractionDigits != 0) {
      symbols.setDecimalSeparator(',');
    } else {
      symbols.setDecimalSeparator(' ');
    }
    symbols.setGroupingSeparator(' ');
    format.setDecimalFormatSymbols(symbols);
    format.setGroupingUsed(true);
    format.setGroupingSize(groupingSize);
    format.setMaximumFractionDigits(numberFractionDigits);
    format.setMinimumFractionDigits(numberFractionDigits);
    format.setRoundingMode(RoundingMode.HALF_UP);
    format.setDecimalSeparatorAlwaysShown(true);
    format.setParseBigDecimal(true);
    return format;
  }
}
