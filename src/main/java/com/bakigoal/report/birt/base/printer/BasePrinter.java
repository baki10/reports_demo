package com.bakigoal.report.birt.base.printer;

import java.io.Serializable;

public abstract class BasePrinter<D extends Serializable> implements Printer {
  private D printDto;
  private Class<? extends D> dtoClass;

  public BasePrinter() {
  }

  /**
   * @param dtoClass  класс dto с настройками печати
   */
  public BasePrinter(Class<? extends D> dtoClass) {
    this.dtoClass = dtoClass;
  }

  /**
   * Получить класс dto с настройками печати
   * @return
   */
  public Class<? extends D> getDtoClass() {
    return dtoClass;
  }

  /**
   * Задать класс dto с настройками печати
   * @param dtoClass
   */
  public void setDtoClass(Class<? extends D> dtoClass) {
    this.dtoClass = dtoClass;
  }

  public void setPrintDto(D printDto) {
    this.printDto = printDto;
  }

  public D getPrintDto() {
    return printDto;
  }
}
