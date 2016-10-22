package com.bakigoal.report.birt.base.printer;

import java.util.Map;

public class PojoPrintModel extends PrintModel {
  private final Map pojoMap;

  public PojoPrintModel(Map<String, Object> parameters, Map pojoMap) {
    super(null, parameters);
    this.pojoMap = pojoMap;
  }

  public Map getPojoMap() {
    return pojoMap;
  }
}
