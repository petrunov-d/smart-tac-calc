package com.dp.trains.ui.validators;

import com.vaadin.flow.data.validator.DoubleRangeValidator;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;

public class ValidatorFactory {

    public static StringLengthValidator requiredVarcharStringValidator(String errorMessage) {

        return new StringLengthValidator(errorMessage, 1,
                255);
    }

    public static StringLengthValidator requiredStringValidator(String errorMessage) {

        return new StringLengthValidator(errorMessage, 1, null);
    }

    public static StringLengthValidator passwordValidator(String errorMessage) {

        return new StringLengthValidator(errorMessage, 6, 255);
    }

    public static IntegerRangeValidator defaultIntRangeValidator(String errorMessage) {

        return new IntegerRangeValidator(errorMessage, 0, Integer.MAX_VALUE);
    }

    public static DoubleRangeValidator defaultDoubleRangeValidator(String errorMessage) {

        return new DoubleRangeValidator(errorMessage, 0.0, Double.MAX_VALUE);
    }
}
