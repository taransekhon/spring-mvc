package com.reactivestax.spring5mvc;

import com.reactivestax.spring5mvc.exceptions.NoDataFoundException;
import com.reactivestax.spring5mvc.model.Widget;
import com.reactivestax.spring5mvc.repository.WidgetRepository;
import com.reactivestax.spring5mvc.service.WidgetService;
import com.reactivestax.spring5mvc.validators.ValidationRuleFactoryForWidgetController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WidgetServiceTest {

	@MockBean
	WidgetRepository widgetRepository;

	@MockBean
	ValidationRuleFactoryForWidgetController validationRuleFactoryForWidgetController;

	@Autowired
	WidgetService widgetService;


//	@Before
//	public void setup(){
//		 widgetService = new WidgetService(widgetRepository);
//	}


	@Test
	public void testGetWidget() throws Exception {
		BDDMockito.given(widgetRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(new Widget("name123","description123")));

		Optional<Widget> widgetById = widgetService.findWidgetById(123L);

		Assert.assertTrue(widgetById.isPresent());
	}


	@Test(expected = NoDataFoundException.class)
	public void testGetWidgetWithNoDataFoundException() throws Exception {
		BDDMockito.given(widgetRepository.findById(ArgumentMatchers.anyLong())).willThrow(new NoDataFoundException());

		Widget widgetByIdNotOptional = widgetService.findWidgetByIdNotOptional(123L);

	}

}
