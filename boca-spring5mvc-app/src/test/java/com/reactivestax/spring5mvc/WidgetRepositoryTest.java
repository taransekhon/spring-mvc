package com.reactivestax.spring5mvc;

import com.reactivestax.spring5mvc.exceptions.NoDataFoundException;
import com.reactivestax.spring5mvc.model.Widget;
import com.reactivestax.spring5mvc.repository.WidgetRepository;
import com.reactivestax.spring5mvc.service.WidgetService;
import com.reactivestax.spring5mvc.validators.ValidationRuleFactoryForWidgetController;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
//@Transactional  -- no need to add this @DataJpaTest does it automatically for you
public class WidgetRepositoryTest {

	@Autowired
	WidgetRepository widgetRepository;


	@Test
	public void getWidgetReturnWidgetDetails(){

		Widget save1 = widgetRepository.save(new Widget("name6677", "description6677"));
		Widget save2 = widgetRepository.save(new Widget("name6678", "description6678"));


		Optional<Widget> widgetById = widgetRepository.findById(save1.getId());
		Optional<Widget> widgetById2 = widgetRepository.findById(save2.getId());

		Assertions.assertThat(widgetById).isPresent();
		Assertions.assertThat(widgetById2).isPresent();

	}
}
