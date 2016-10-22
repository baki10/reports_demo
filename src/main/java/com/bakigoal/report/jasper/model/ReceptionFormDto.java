package com.bakigoal.report.jasper.model;

/**
 * Dto для печати справки о приеме заявления о выдаче заграничного паспорта нового поколения
 */
public class ReceptionFormDto implements Dto {

  private Integer anketId;
  private String number;
  private String messageCode;
  private String messageNumber;
  private Integer executorId;
  private Object dataSource;

  public Object getDataSource() {
    return dataSource;
  }

  public void setDataSource(Object dataSource) {
    this.dataSource = dataSource;
  }

  public Integer getAnketId() {
    return anketId;
  }

  public void setAnketId(Integer anketId) {
    this.anketId = anketId;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getMessageCode() {
    return messageCode;
  }

  public void setMessageCode(String messageCode) {
    this.messageCode = messageCode;
  }

  public String getMessageNumber() {
    return messageNumber;
  }

  public void setMessageNumber(String messageNumber) {
    this.messageNumber = messageNumber;
  }

  public Integer getExecutorId() {
    return executorId;
  }

  public void setExecutorId(Integer executorId) {
    this.executorId = executorId;
  }
}
