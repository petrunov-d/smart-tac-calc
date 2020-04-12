package com.dp.trains.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileReader;
import java.io.IOException;

@Slf4j
public class SmartTacCalcContext {

    private Model model;

    private static SmartTacCalcContext smartTacCalcContext;

    private SmartTacCalcContext() throws IOException, XmlPullParserException {

        model = new MavenXpp3Reader().read(new FileReader("pom.xml"));
    }

    public static SmartTacCalcContext getSmartTACCalcContext() {

        if (smartTacCalcContext == null) {

            synchronized (SmartTacCalcContext.class) {

                if (smartTacCalcContext == null) {

                    try {

                        smartTacCalcContext = new SmartTacCalcContext();

                    } catch (IOException | XmlPullParserException e) {

                        log.error("Error creating SmartTACCalc context:", e);
                    }
                }
            }
        }

        return smartTacCalcContext;
    }

    public Model getModel() {

        return model;
    }
}