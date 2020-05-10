package com.reactivestax.spring5mvc;

import com.google.gson.Gson;
import com.reactivestax.spring5mvc.model.Widget;
import com.reactivestax.spring5mvc.utils.dto.ErrorInfo;
import com.reactivestax.spring5mvc.validators.DescriptionValidationRule;
import com.reactivestax.spring5mvc.validators.NameValidationRule;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WidgetIntegrationTest {

	@Autowired
	TestRestTemplate testRestTemplate;

	@LocalServerPort
	int randomServerPort;

	@Bean
	public Gson createGson(){
		return new Gson();
	}

	@Autowired
	Gson gson;

	@Test
	public void testPostWidget() throws URISyntaxException {
		//arrange
		Widget widget = new Widget();
		widget.setName("name");
		widget.setDescription("desc123");

		Widget widget2 = new Widget();
		widget2.setName("name");
		widget2.setDescription("123desc123");

		//act

		final String baseUrl = "http://localhost:"+randomServerPort+"/rest/widget";
		System.out.println("baseUrl = " + baseUrl);
		URI uri = new URI(baseUrl);

		HttpHeaders headers = new HttpHeaders();
		headers.set("client-metadata", "{\"appOrg\":\"com.banking\",\"language\":\"en\",\"appCode\":\"ABC0\",\"appVersion\":\"3.2\",\"physicalLocationId\":\"123\",\"assetId\":\"laptop-123\",\"legacyId\":\"123\",\"requestUniqueId\":\"123e4567-e89b-12d3-a456-556642440000\"}");

		HttpEntity<Widget> request = new HttpEntity<>(widget, headers);

		ResponseEntity<String> result = this.testRestTemplate.postForEntity(uri, request, String.class);

		//Verify request succeed
		Assert.assertEquals(201, result.getStatusCodeValue());
	}

	@Test
	public void testPostWidgetWithMissingClientMetadataRequestHeader() throws URISyntaxException {
		//arrange
		Widget widget = new Widget();
		widget.setName("name");
		widget.setDescription("desc123");

		Widget widget2 = new Widget();
		widget2.setName("name");
		widget2.setDescription("123desc123");

		//act

		final String baseUrl = "http://localhost:"+randomServerPort+"/rest/widget";
		URI uri = new URI(baseUrl);

		HttpHeaders headers = new HttpHeaders();
//		headers.set("client-metadata", "{\"appOrg\":\"com.banking\",\"language\":\"en\",\"appCode\":\"ABC0\",\"appVersion\":\"3.2\",\"physicalLocationId\":\"123\",\"assetId\":\"laptop-123\",\"legacyId\":\"123\",\"requestUniqueId\":\"123e4567-e89b-12d3-a456-556642440000\"}");

		HttpEntity<Widget> request = new HttpEntity<>(widget, headers);

		ResponseEntity<String> result = this.testRestTemplate.postForEntity(uri, request, String.class);

		//Verify request succeed
		Assert.assertEquals(400, result.getStatusCodeValue());
		Assert.assertTrue(gson!=null);
		ErrorInfo errorInfo = gson.fromJson(result.getBody().toString(), ErrorInfo.class);
		Assert.assertTrue(errorInfo!=null && StringUtils.equalsIgnoreCase(errorInfo.getDetailedMessages().get(0).getMessage(),"client_metadata request header cannot be missing or have blank value"));
	}

//	RestTemplate restTemplate() throws Exception {
//		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
//				.build();
//		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
//		HttpClient httpClient = HttpClients.custom()
//				.setSSLSocketFactory(socketFactory)
//				.build();
//		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
//		return new RestTemplate(factory);
//	}


}
