package com.reactivestax.spring5mvc;

import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.reactivestax.spring5mvc.model.Widget;
import com.reactivestax.spring5mvc.repository.WidgetRepository;
import com.reactivestax.spring5mvc.service.WidgetService;
import com.reactivestax.spring5mvc.utils.dto.ClientMetaData;
import com.reactivestax.spring5mvc.utils.dto.ErrorInfo;
import com.reactivestax.spring5mvc.validators.ValidationRuleFactoryForWidgetController;
import com.reactivestax.spring5mvc.web.WidgetRestController;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)

public class WidgetControllerRestAssuredTest {

//	@Value("${trust.store}")
//	private Resource trustStore;
//
//	@Value("${trust.store.password}")
//	private String trustStorePassword;


	@Value("${local.server.port}")
	int port;

	@MockBean
	WidgetRepository widgetRepository;

	@MockBean
	WidgetService widgetService;

	@MockBean
	ValidationRuleFactoryForWidgetController validationRuleFactoryForWidgetController;

	String baseUrl;

	@Before
	public void setup(){
		RestAssured.port = port;
		baseUrl = "http://localhost:"+port+"/rest/widget";

	}


	@Test
	public void testPostWidget() throws Exception {
		BDDMockito.given(widgetService.saveWidget(ArgumentMatchers.anyObject())).willReturn(new Widget("name123","description123"));


		Gson gson = new Gson();
		String widgetJsonString = gson.toJson(new Widget("name123", "description123"));

		ClientMetaData clientMetaData = gson.fromJson("{\"appOrg\":\"com.banking\",\"language\":\"en\",\"appCode\":\"ABC0\",\"appVersion\":\"3.2\",\"physicalLocationId\":\"123\",\"assetId\":\"laptop-123\",\"legacyId\":\"123\",\"requestUniqueId\":\"123e4567-e89b-12d3-a456-556642440000\"}", ClientMetaData.class);

		System.out.println("baseUrl = " + baseUrl);
		Response response =

				RestAssured
						.given()
						.header("client-metadata", clientMetaData)
						.contentType(ContentType.JSON)
						.request()
						.body("{\n" +
								"\"name\": \"name23324\",\n" +
								"\"description\":\"description234234\"\n" +
								"}")
						.when()
						.post(baseUrl)
						.then()
						.statusCode(SC_CREATED)
						.contentType(ContentType.JSON)
//						.assertThat()
//						.body(Matchers.notNull())
						.extract().response();
		String responseString = response.asString();
		System.out.println("responseString:"+responseString);

		Widget widget = gson.fromJson(responseString, Widget.class);
		Assertions.assertThat(StringUtils.equalsIgnoreCase(widget.getDescription(),"description234234"));
		Assertions.assertThat(StringUtils.equalsIgnoreCase(widget.getName(),"name23324"));
	}

	@Ignore
	public void testPostWidgetMissingClientMetadata() throws Exception {
		BDDMockito.given(widgetService.saveWidget(ArgumentMatchers.anyObject())).willReturn(new Widget("name123","description123"));


		Gson gson = new Gson();
		String widgetJsonString = gson.toJson(new Widget("name123", "description123"));

		ClientMetaData clientMetaData = gson.fromJson("{\"appOrg\":\"com.banking\",\"language\":\"en\",\"appCode\":\"ABC0\",\"appVersion\":\"3.2\",\"physicalLocationId\":\"123\",\"assetId\":\"laptop-123\",\"legacyId\":\"123\",\"requestUniqueId\":\"123e4567-e89b-12d3-a456-556642440000\"}", ClientMetaData.class);

		System.out.println("baseUrl = " + baseUrl);
		Response response =

				RestAssured
						.given()
						//.header("client-metadata", clientMetaData)
						.contentType(ContentType.JSON)
						.request()
						.body("{\n" +
								"\"name\": \"name23324\",\n" +
								"\"description\":\"description234234\"\n" +
								"}")
						.when()
						.post(baseUrl)
						.then()
						.statusCode(SC_BAD_REQUEST)
						.contentType(ContentType.JSON)

						.extract().response();

		String responseString = response.asString();
		System.out.println("responseString:"+responseString);

	ErrorInfo errorInfo= gson.fromJson(responseString, ErrorInfo.class);
		Assertions.assertThat(StringUtils.equalsIgnoreCase(errorInfo.getDetailedMessages().get(0).getMessage(),"client_metadata request header cannot be missing or have blank value"));

}

	private ClientMetaData retrieveClientMetadataFromFile(String fileName) throws IOException {
		InputStreamReader reader = new InputStreamReader(WidgetControllerRestAssuredTest.class.getResourceAsStream(fileName),"UTF-8");
		Gson gson = new Gson();
		ClientMetaData clientMetaData = gson.fromJson(reader, ClientMetaData.class);
		reader.close();
		return clientMetaData;
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
