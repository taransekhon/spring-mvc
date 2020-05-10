package com.reactivestax.spring5mvc.validators;

import com.reactivestax.spring5mvc.model.Widget;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DescriptionValidationRule implements  ValidationRule<Widget> {
    @Override
    public void validate(Widget widget, Map<String, String> errorMap){
        if(!StringUtils.startsWith(widget.getDescription(),"desc")){
            errorMap.put("description", "description also has to start with \'desc\'");
        }
    }
}
