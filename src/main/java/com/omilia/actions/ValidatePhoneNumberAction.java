package com.omilia.actions;

import com.omilia.diamant.dialog.components.fields.ApiField;
import com.omilia.diamant.dialog.components.fields.FieldStatus;
import com.omilia.diamant.loggers.DialogLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
public class ValidatePhoneNumberAction extends AbstractAction {
    public ValidatePhoneNumberAction(Map<String,ApiField> input, DialogLogger logger){
        super(input,logger,ValidatePhoneNumberAction.class.getSimpleName());
    }

    @Override
    public void process(){
        String number = input.get("Ani").getValue().replaceFirst("^\\+","");
        String street = ((ApiField) input.get("askStreet")).getValue();
        String houseNumber = ((ApiField) input.get("askHouseNumber")).getValue();
        logger.logGreen("Olymp_custom:ValidatePhoneNumberAction->extracted Ani:"+number);
        ArrayList<String> operators = new ArrayList<String>(Arrays.asList("700", "708", "705", "771", "776", "777", "701", "702", "775", "778", "707", "747", "706"));
        boolean result = (
                number.matches("\\d+")
                        &&
                        number.length() == 11
                        &&
                        (number.startsWith("7") || number.startsWith("8"))
                        &&
                        operators.contains(number.substring(1,4))
        );
        logger.logGreen("Olymp_custom:ValidatePhoneNumberAction->validation:"+result);
        output.put("isPhoneNumber", ApiField.builder().status(FieldStatus.DEFINED).value(String.valueOf(result)).build());
    }
}


