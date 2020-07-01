package com.dp.trains.ui.components.common;

import com.google.common.collect.Lists;
import com.vaadin.flow.component.dialog.Dialog;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DialogRegistry {

    private static DialogRegistry dialogRegistry;
    private final List<Dialog> openDialogs;

    private DialogRegistry() {

        this.openDialogs = Lists.newCopyOnWriteArrayList();
    }

    public static DialogRegistry get() {

        if (dialogRegistry == null) {

            synchronized (DialogRegistry.class) {

                if (dialogRegistry == null) {

                    dialogRegistry = new DialogRegistry();
                }
            }
        }

        return dialogRegistry;
    }

    public void addDialog(Dialog dialog) {

        this.openDialogs.add(dialog);
    }

    public void removeDialog(Dialog dialog) {

        this.openDialogs.remove(dialog);
    }

    public void closeAllDialogs() {

        for (Dialog dialog : this.openDialogs) {

            dialog.close();
        }
    }
}