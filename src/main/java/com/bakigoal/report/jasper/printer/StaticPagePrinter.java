package com.bakigoal.report.jasper.printer;

import com.bakigoal.report.jasper.model.Dto;

import java.util.List;
import java.util.Map;

/**
 * Печать статического отчета
 */
public abstract class StaticPagePrinter extends BasePrinter<Dto> {

    public StaticPagePrinter() {
        super(null);
    }

    @Override
    public List<Map<String, Object>> getObjectList(Dto dto) {
        return null;
    }

    @Override
    protected Map<String, Object> getParams() {
        return null;
    }
}
