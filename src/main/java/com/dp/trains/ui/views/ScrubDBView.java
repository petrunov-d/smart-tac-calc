package com.dp.trains.ui.views;

import com.dp.trains.services.DBScrubService;
import com.dp.trains.ui.layout.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@UIScope
@SpringComponent
@Route(value = ScrubDBView.NAV_SECRET_DB_SCRUBBER, layout = MainLayout.class)
public class ScrubDBView extends Composite<Div> {

    static final String NAV_SECRET_DB_SCRUBBER = "scrub_db";
    private String masterPassword = "KosovoESerbia2020";

    private final DBScrubService dbScrubService;
    private final Button scrubDb;
    private final TextArea masterPasswordTextArea;

    public ScrubDBView(@Autowired DBScrubService dbScrubService) {

        this.dbScrubService = dbScrubService;

        this.masterPasswordTextArea = new TextArea();

        this.scrubDb = new Button();
        this.scrubDb.addClickListener(clickEvent -> {

            if (masterPasswordTextArea.getValue().equals(masterPassword)) {

                this.dbScrubService.scrubDb();
            }
        });

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        verticalLayout.add(masterPasswordTextArea);
        verticalLayout.add(scrubDb);

        getContent().add(verticalLayout);
    }
}