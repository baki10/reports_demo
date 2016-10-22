package com.bakigoal.report.jasper.printer;

import com.bakigoal.report.jasper.ReportNameEnum;
import org.springframework.stereotype.Component;

/**
 * Принтер для печати Бланка с прямоугольником для подписи
 */
@Component
public class SignFramePrinter extends StaticPagePrinter {

    @Override
    protected ReportNameEnum initFileName() {
        return ReportNameEnum.SIGN_FRAME;
    }
}
