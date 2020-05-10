package com.reactivestax.spring5mvc.repository;

import com.reactivestax.spring5mvc.model.Widget;
import org.springframework.data.repository.CrudRepository;

public interface WidgetRepository extends CrudRepository<Widget, Long> {
}
