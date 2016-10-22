package com.bakigoal.report.birt.base.printer;

import org.apache.commons.lang3.builder.Builder;

public interface Printer extends Builder<PrintModel> {

  @Override
  PrintModel build();
}
