package com.bakigoal.report.jasper.printer;

import com.bakigoal.report.jasper.model.Dto;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Принтер использующий jsonDataSource
 */
public abstract class JsonDSPrinter<T extends Dto> extends BasePrinter<T> {

    JsonDSPrinter(Class<T> dtoClass) {
        super(dtoClass);
    }

    @Override
    public List<Map<String, Object>> getObjectList(T dto) {
        return null;
    }

    @Override
    protected Map<String, Object> getParams() {
        Map<String, Object> params = new HashMap<>();
        params.put(JsonQueryExecuterFactory.JSON_DATE_PATTERN, "dd.MM.yyyy");
        params.put(JsonQueryExecuterFactory.JSON_NUMBER_PATTERN, "#,##0.##");
        params.put(JsonQueryExecuterFactory.JSON_LOCALE, Locale.ENGLISH);
        params.put(JRParameter.REPORT_LOCALE, new Locale("ru_RU"));
        return params;
    }

    @Override
    protected JRDataSource getDataSource(String json) throws JRException {
        InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        return new JsonDataSource(stream);
    }
}
