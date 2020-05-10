package com.reactivestax.spring5mvc.web;

import com.reactivestax.spring5mvc.exceptions.InvalidDataException;
import com.reactivestax.spring5mvc.model.Widget;
import com.reactivestax.spring5mvc.repository.WidgetRepository;
import com.reactivestax.spring5mvc.service.WidgetService;
import com.reactivestax.spring5mvc.validators.ValidationRuleFactoryForWidgetController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class WidgetRestController {
    private static final Logger logger = LogManager.getLogger(WidgetRestController.class);
    @Autowired
    private WidgetRepository widgetRepository;

    @Autowired
    private ValidationRuleFactoryForWidgetController validationRuleFactoryForWidgetController;
    @Autowired
    private WidgetService widgetService;

    @GetMapping("/rest/widget/{widgetId}")
    public ResponseEntity<Widget> getWidget(@PathVariable Long widgetId) {
        return widgetService.findWidgetById(widgetId)
                .map(widget -> ResponseEntity.ok().body(widget))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rest/widget/v2/{widgetId}")
    public ResponseEntity<Widget> getWidgetNotOptional(@PathVariable Long widgetId) {
        return ResponseEntity.ok().body(widgetService.findWidgetByIdNotOptional(widgetId));
    }

    @GetMapping("/rest/widgets")
    //public ResponseEntity<Iterable<Widget>> getWidgets() {
    public Iterable<Widget> getWidgets() {
        return widgetRepository.findAll();
    }

    @PostMapping("/rest/widget")
    public ResponseEntity<Widget> createWidget(@RequestBody @Valid Widget widget) {
        logger.info("Received widget: name: " + widget.getName() + ", description: " + widget.getDescription());
        //
        doBusinessRulesValidation(widget);
        //
        Widget newWidget = widgetService.saveWidget(widget);
//        Widget newWidget = widgetRepository.saveWidget(widget);
        try {
            return ResponseEntity.created(new URI("/rest/widget/" + newWidget.getId())).body(newWidget);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void doBusinessRulesValidation(Widget widget) {
        Map<String, String> errorMap = new HashMap<>();

        validationRuleFactoryForWidgetController
                .getValidationRulesForBusinessRuleCheck()
                .forEach(
                        validationRule -> validationRule.validate(widget, errorMap)
                );

        if (!CollectionUtils.isEmpty(errorMap)) {
            throw new InvalidDataException(errorMap);
        }
    }

    @PutMapping("/rest/widget/{id}")
    public ResponseEntity<Widget> updateWidget(@RequestBody Widget widget, @PathVariable Long id) {
        widget.setId(id);
        return ResponseEntity.ok().body(widgetRepository.save(widget));
    }

    @PutMapping("/rest/proper/widget/{id}")
    public ResponseEntity<Widget> updateWidgetProper(@RequestBody Widget widget, @PathVariable Long id, @RequestHeader("If-Match") Long ifMatch) {
        Optional<Widget> existingWidget = widgetRepository.findById(id);
        if (existingWidget.isPresent()) {
            if (ifMatch == 7) {
                widget.setId(id);
                return ResponseEntity.ok().body(widgetRepository.save(widget));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/rest/widget/{id}")
    public ResponseEntity deleteWidget(@PathVariable Long id) {
        widgetRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
