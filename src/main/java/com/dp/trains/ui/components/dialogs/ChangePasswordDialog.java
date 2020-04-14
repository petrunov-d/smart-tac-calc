package com.dp.trains.ui.components.dialogs;

import com.dp.trains.model.dto.PasswordChangeDto;
import com.dp.trains.services.TrainsUserDetailService;
import com.dp.trains.ui.validators.ValidatorFactory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.extern.slf4j.Slf4j;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class ChangePasswordDialog extends SmartTACCalcDialogBase {

    public ChangePasswordDialog(String loggedInUserUserName, TrainsUserDetailService trainsUserDetailService) {

        super();

        FormLayout layoutWithBinder = new FormLayout();
        Binder<PasswordChangeDto> binder = new Binder<>();

        PasswordChangeDto passwordChangeDto = new PasswordChangeDto();
        passwordChangeDto.setUsername(loggedInUserUserName);

        PasswordField oldPassword = new PasswordField();
        oldPassword.setValueChangeMode(ValueChangeMode.EAGER);
        oldPassword.addValueChangeListener(event -> binder.validate());
        oldPassword.setRequiredIndicatorVisible(true);

        PasswordField newPassword = new PasswordField();
        newPassword.setValueChangeMode(ValueChangeMode.EAGER);
        newPassword.setRequiredIndicatorVisible(true);

        PasswordField newPasswordConfirm = new PasswordField();
        newPasswordConfirm.setValueChangeMode(ValueChangeMode.EAGER);
        newPasswordConfirm.setRequiredIndicatorVisible(true);

        newPassword.addValueChangeListener(valueChangeEvent -> {

            if (!valueChangeEvent.getValue().equals(newPasswordConfirm.getValue())) {
                newPassword.setInvalid(true);
            } else {
                newPassword.setInvalid(false);
            }
        });

        newPasswordConfirm.addValueChangeListener(valueChangeEvent -> {

            if (!valueChangeEvent.getValue().equals(newPassword.getValue())) {
                newPasswordConfirm.setInvalid(true);
            } else {
                newPasswordConfirm.setInvalid(false);
            }
        });

        binder.forField(oldPassword)
                .asRequired()
                .withValidator(ValidatorFactory.passwordValidator(getTranslation(ADD_USER_DIALOG_VALIDATION_ERROR_USERNAME)))
                .bind(PasswordChangeDto::getOldPassword, PasswordChangeDto::setOldPassword);

        binder.forField(newPassword)
                .asRequired()
                .withValidator(ValidatorFactory.passwordValidator(getTranslation(ADD_USER_DIALOG_VALIDATION_ERROR_PASSWORD)))
                .bind(PasswordChangeDto::getNewPassword, PasswordChangeDto::setNewPassword);

        binder.forField(newPasswordConfirm)
                .withValidator(ValidatorFactory.passwordValidator(getTranslation(ADD_USER_DIALOG_VALIDATION_ERROR_NEW_PASSWORD)))
                .bind(PasswordChangeDto::getNewPasswordConfirm, PasswordChangeDto::setNewPasswordConfirm);

        Button save = new Button(getTranslation(SHARED_BUTTON_TEXT_SAVE), new Icon(VaadinIcon.UPLOAD));
        Button reset = new Button(getTranslation(SHARED_BUTTON_TEXT_RESET), new Icon(VaadinIcon.RECYCLE));
        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        layoutWithBinder.addFormItem(oldPassword, getTranslation(ADD_USER_DIALOG_FORM_ITEM_LABEL_PASSOWORD));
        layoutWithBinder.addFormItem(newPassword, getTranslation(CHANGE_PASSWORD_DIALOG_FORM_ITEM_NEW_PASSWORD));
        layoutWithBinder.addFormItem(newPasswordConfirm, getTranslation(CHANGE_PASSWORD_DIALOG_FORM_ITEM_NEW_PASSWORD_CONFIRM));

        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(passwordChangeDto)) {

                trainsUserDetailService.changePassword(passwordChangeDto);

            } else {

                log.error("");
            }
        });

        cancel.addClickListener(event -> {

            reset(binder, oldPassword, newPassword, newPasswordConfirm);
            this.close();
        });

        reset.addClickListener(event -> reset(binder, oldPassword, newPassword, newPasswordConfirm));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset, cancel);

        H3 h3Heading = new H3(getTranslation(CHANGE_PASSWORD_DIALOG_TITLE));

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(h3Heading);
        verticalLayout.add(layoutWithBinder);
        verticalLayout.add(actions);

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        this.add(verticalLayout);
    }

    private void reset(Binder<PasswordChangeDto> binder, PasswordField oldPassword, PasswordField newPassword,
                       PasswordField newPasswordConfirm) {

        binder.readBean(null);
        oldPassword.setValue("");
        newPasswordConfirm.setValue("");
        newPassword.setValue("");
    }
}
