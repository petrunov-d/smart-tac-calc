package com.dp.trains.ui.components.reports;

import com.dp.trains.ui.components.common.BaseSmartTacCalcView;
import com.dp.trains.ui.components.dialogs.ReportGenerationInProgressDialog;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseReportView extends BaseSmartTacCalcView {

    public ReportGenerationInProgressDialog getDialog() {

        return new ReportGenerationInProgressDialog();
    }
}
