package com.reactivestax.spring5mvc;

import com.reactivestax.spring5mvc.model.Widget;
import com.reactivestax.spring5mvc.validators.DescriptionValidationRule;
import com.reactivestax.spring5mvc.validators.NameValidationRule;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Spring5MvcApplicationTests {

	@Autowired
	DescriptionValidationRule descriptionValidationRule;

	@Autowired
	NameValidationRule nameValidationRule;

	@Test
	public void testWithCorrectNameAndWrongDescription() {
		//setup
		Widget widget = new Widget();
		Map<String, String> errorMap = new HashMap<>();
		widget.setName("name");
		widget.setDescription("123desc123");

		//actual SUT
		descriptionValidationRule.validate(widget, errorMap);
		nameValidationRule.validate(widget, errorMap);

		//assertions
		Assert.assertTrue("there has to be one error", errorMap.size() == 1);
		Assert.assertTrue("there should be an error related to description", errorMap.get("description") != null);
		Assert.assertTrue("there should be an error related to description", StringUtils.startsWith(errorMap.get("description"), "description also has to start with"));
	}

	@Test
	public void testWithWrongNameAndCorrectDescription() {
		//setup
		Widget widget = new Widget();
		Map<String, String> errorMap = new HashMap<>();
		widget.setName("213name");
		widget.setDescription("desc123");

		//actual SUT
		descriptionValidationRule.validate(widget, errorMap);
		nameValidationRule.validate(widget, errorMap);

		//assertions
		Assert.assertTrue("there has to be one error", errorMap.size() == 1);
		Assert.assertTrue("there should be an error related to name", errorMap.get("name") != null);
		Assert.assertTrue("there should be an error related to name", StringUtils.startsWith(errorMap.get("name"), "name also has to start with"));
	}

	@Test
	public void testWithNameAndDescriptionWrongDescription() {
		//setup
		Widget widget = new Widget();
		Map<String, String> errorMap = new HashMap<>();
		widget.setName("213name");
		widget.setDescription("123desc123");

		//actual SUT
		descriptionValidationRule.validate(widget, errorMap);
		nameValidationRule.validate(widget, errorMap);

		//assertions for name
		Assert.assertTrue("there has to be one error", errorMap.size() == 2);
		Assert.assertTrue("there should be an error related to name", errorMap.get("name") != null);
		Assert.assertTrue("there should be an error related to name", StringUtils.startsWith(errorMap.get("name"), "name also has to start with"));
		//
		//assertions for description

		Assert.assertTrue("there should be an error related to name", errorMap.get("name") != null);
		Assert.assertTrue("there should be an error related to name", StringUtils.startsWith(errorMap.get("name"), "name also has to start with"));
	}

	@Test
	public void testWithCorrectNameAndDescription() {
		//setup
		Widget widget = new Widget();
		Map<String, String> errorMap = new HashMap<>();
		widget.setName("name123");
		widget.setDescription("desc123");
		//actual SUT
		descriptionValidationRule.validate(widget, errorMap);
		nameValidationRule.validate(widget, errorMap);

		//assertions
		Assert.assertTrue("there has to be one error", errorMap.size() == 0);
		//
	}


}
