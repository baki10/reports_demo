package com.bakigoal.report.birt.base.dao;

import java.util.List;

public abstract class ReportDao<T>{
  public abstract List<Object[]> getList(T dto);
}
