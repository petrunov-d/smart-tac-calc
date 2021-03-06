package com.dp.trains.ui.components.dialogs.add;

import com.dp.trains.model.dto.Authority;
import com.dp.trains.model.dto.UserDto;
import com.dp.trains.services.TrainsUserDetailService;
import com.dp.trains.ui.components.common.UserPermissionsContainer;
import com.dp.trains.ui.components.dialogs.SmartTACCalcDialogBase;
import com.dp.trains.ui.validators.ValidatorFactory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class AddUserDialog extends SmartTACCalcDialogBase {

    public AddUserDialog(String loggedInUserUserName, TrainsUserDetailService trainsUserDetailService) {

        super();

        FormLayout layoutWithBinder = new FormLayout();
        Binder<UserDto> binder = new Binder<>();

        UserDto userDto = new UserDto();

        userDto.setUsername(loggedInUserUserName);

        PasswordField password = new PasswordField();
        password.setValueChangeMode(ValueChangeMode.EAGER);
        password.addValueChangeListener(event -> binder.validate());
        password.setRequiredIndicatorVisible(true);

        PasswordField passwordConfirm = new PasswordField();
        passwordConfirm.setValueChangeMode(ValueChangeMode.EAGER);
        passwordConfirm.addValueChangeListener(event -> binder.validate());
        passwordConfirm.setRequiredIndicatorVisible(true);

        TextField username = new TextField();
        username.setValueChangeMode(ValueChangeMode.EAGER);
        username.addValueChangeListener(event -> binder.validate());
        username.setRequiredIndicatorVisible(true);

        password.addValueChangeListener(valueChangeEvent -> {

            password.setInvalid(!valueChangeEvent.getValue().equals(passwordConfirm.getValue()));
        });

        passwordConfirm.addValueChangeListener(valueChangeEvent -> {

            passwordConfirm.setInvalid(!valueChangeEvent.getValue().equals(password.getValue()));
        });

        Checkbox userRole = new Checkbox();
        userRole.setValue(false);

        Checkbox adminRole = new Checkbox();
        adminRole.setValue(false);

        binder.forField(username)
                .asRequired()
                .withValidator(ValidatorFactory.passwordValidator(getTranslation(ADD_USER_DIALOG_VALIDATION_ERROR_USERNAME)))
                .bind(UserDto::getUsername, UserDto::setUsername);

        binder.forField(password)
                .asRequired()
                .withValidator(ValidatorFactory.passwordValidator(getTranslation(ADD_USER_DIALOG_VALIDATION_ERROR_PASSWORD)))
                .bind(UserDto::getPassword, UserDto::setPassword);

        binder.forField(passwordConfirm)
                .withValidator(ValidatorFactory.passwordValidator(getTranslation(ADD_USER_DIALOG_VALIDATION_ERROR_NEW_PASSWORD)))
                .bind(UserDto::getPasswordConfirm, UserDto::setPasswordConfirm);

        UserPermissionsContainer userPermissionsContainer = new UserPermissionsContainer(Lists.newArrayList());

        Button save = new Button(getTranslation(SHARED_BUTTON_TEXT_SAVE), new Icon(VaadinIcon.UPLOAD));
        Button reset = new Button(getTranslation(SHARED_BUTTON_TEXT_RESET), new Icon(VaadinIcon.RECYCLE));
        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        layoutWithBinder.addFormItem(username, getTranslation(ADD_USER_DIALOG_FORM_ITEM_LABEL_USERNAME));
        layoutWithBinder.addFormItem(password, getTranslation(ADD_USER_DIALOG_FORM_ITEM_LABEL_PASSOWORD));
        layoutWithBinder.addFormItem(passwordConfirm, getTranslation(ADD_USER_DIALOG_FORM_ITEM_LABEL_CONFIRM_PASSWORD));
        layoutWithBinder.addFormItem(userRole, getTranslation(ADD_USER_DIALOG_FORM_ITEM_LABEL_IS_USER));
        layoutWithBinder.addFormItem(adminRole, getTranslation(ADD_USER_DIALOG_FORM_ITEM_LABEL_IS_ADMIN));

        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(userDto)) {

                if (userRole.getValue()) {

                    userDto.getAuthorities().add(Authority.ADMIN);
                }

                if (adminRole.getValue()) {

                    userDto.getAuthorities().add(Authority.USER);
                }

                userDto.setUserAccesses(userPermissionsContainer.getData());

                trainsUserDetailService.add(userDto);

                this.close();

            } else {

                log.error(" ");
            }
        });

        cancel.addClickListener(event -> {

            reset(binder, password, passwordConfirm, username, userRole, adminRole);
            this.close();
        });

        reset.addClickListener(event -> reset(binder, password, passwordConfirm, username, userRole, adminRole));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset, cancel);

        H3 h3Heading = new H3(getTranslation(ADD_USER_DIALOG_TITLE));

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(h3Heading);
        verticalLayout.add(layoutWithBinder);
        verticalLayout.add(userPermissionsContainer);
        verticalLayout.add(actions);

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        this.add(verticalLayout);
    }

    private void reset(Binder<UserDto> binder, PasswordField password, PasswordField passwordConfirm,
                       TextField username, Checkbox userRole, Checkbox adminRole) {

        binder.readBean(null);
        password.setValue("");
        passwordConfirm.setValue("");
        username.setValue("");
        userRole.setValue(false);
        adminRole.setValue(false);
    }
}
