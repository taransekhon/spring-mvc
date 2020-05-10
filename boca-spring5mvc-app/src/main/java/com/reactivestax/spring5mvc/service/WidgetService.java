package com.reactivestax.spring5mvc.service;

import com.reactivestax.spring5mvc.exceptions.NoDataFoundException;
import com.reactivestax.spring5mvc.model.Widget;
import com.reactivestax.spring5mvc.repository.WidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

@Component
public class WidgetService {

    @Autowired
    WidgetRepository widgetRepository;

    public WidgetService(WidgetRepository widgetRepository) {
        this.widgetRepository = widgetRepository;
    }

    public Widget saveWidget(Widget widget) {
        return widgetRepository.save(widget);
    }

    public Optional<Widget> findWidgetById(Long widgetId) {
        return widgetRepository.findById(widgetId);
    }

    public Widget findWidgetByIdNotOptional(Long widgetId) {
        Optional<Widget> foundWidget = widgetRepository.findById(widgetId);
        if(foundWidget.isPresent()){
            return foundWidget.get();
        }else{
            throw new NoDataFoundException(MessageFormat.format("widget with widgetId : {0} cannot be found",widgetId));
        }

    }
}
