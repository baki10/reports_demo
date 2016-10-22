package com.bakigoal.report.jasper.printer;

import com.bakigoal.report.jasper.ReportNameEnum;
import com.bakigoal.report.jasper.model.Dto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Базовый абстрактный класс принтер
 *
 * @param <T> тип передаваемого json объекта с клиента
 */
public abstract class BasePrinter<T extends Dto> {

    private static final String REPORTS_PATH = "reports/";

    private String fileName;
    private Class<T> dtoClass;
    private T dto;

    BasePrinter(Class<T> dtoClass) {

        this.dtoClass = dtoClass;
    }

    protected abstract ReportNameEnum initFileName();

    protected abstract List<Map<String, Object>> getObjectList(T dto);

    protected abstract Map<String, Object> getParams();

    protected JRDataSource getDataSource(String json) throws JRException {
        return new JREmptyDataSource();
    }

    protected T getDto() {
        return dto;
    }

    private JasperPrint generateJasperPrint(String json) {
        try {

            ReportNameEnum nameEnum = initFileName();
            this.fileName = nameEnum != null ? nameEnum.getFileName() : null;
            if (fileName == null) {
                return null;
            }

            this.dto = convertJsonToDto(json);

            // иницализируем datasource
            JRDataSource dataSource = getDataSource(json);
            List<Map<String, Object>> objectList = getObjectList(dto);
            if (objectList != null && !objectList.isEmpty()) {
                dataSource = new JRBeanCollectionDataSource(objectList);
            }
            // заполняем параметры
            Map<String, Object> params = getParams();
            if (params == null) {
                params = new HashMap<>();
            }
            params.put("REAL_PATH", getReportsRealPath());

            // компилируем шаблон
            JasperReport jasperReport = compileReport(fileName);
            // заполняем шаблон данными
            return JasperFillManager.fillReport(jasperReport, params, dataSource);
        } catch (JRException e) {
            System.err.println("Ошибка в формировании отчета: " + e.getLocalizedMessage());
        }
        return null;
    }

    public byte[] printPdf(String json) {

        // сгенерим шаблон и заполним его данными
        JasperPrint jasperPrint = generateJasperPrint(json);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Экспортируем ПДФ в стрим
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            // Возвращаем в виде массива байтов
            return outputStream.toByteArray();
        } catch (Exception e) {
            System.err.println("Ошибка в формировании отчета: " + e.getLocalizedMessage());
        }
        return null;
    }

    private T convertJsonToDto(String json) {
        if (dtoClass == null) {
            return null;
        }
        Gson gson = new GsonBuilder().create();
        return (T) gson.fromJson(json, dtoClass);
    }

    private JasperReport compileReport(final String location) throws JRException {
        return JasperCompileManager.compileReport(getInputStream(location));
    }

    private InputStream getInputStream(String location) throws JRException {
        return JRLoader.getLocationInputStream(REPORTS_PATH + location);
    }

    private String getReportsRealPath() {
        return new File(JRLoader.getResource(REPORTS_PATH).getFile()).getAbsolutePath();
    }

    protected String notNull(String s) {
        return s == null ? "" : s;
    }
}
