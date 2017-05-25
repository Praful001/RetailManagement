package com.retailmanagement.controller;


import static org.assertj.core.api.BDDAssertions.then;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retailmanagement.bean.ShopAddress;
import com.retailmanagement.bean.ShopBean;
import com.retailmanagement.main.RetailManagerLauncher;

/**
 * @author Praful Itapure
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RetailManagerLauncher.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
public class RetailManagerControllerTests {

	@LocalServerPort
	private int port;

	@Value("${server.port}")
	private int mgt;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void postShopDetailsTest1() throws Exception {
		HttpHeaders headers = new HttpHeaders();

		headers.set("Content-Type", "application/json");
		ShopBean shopBean = new ShopBean();
		ShopAddress shopAddress = new ShopAddress();
		shopAddress.setShopNumber(12345);
		shopAddress.setShopPostCode(458990);
		shopAddress.setShopPlace("Pune");
		shopBean.setShopAddress(shopAddress);
		shopBean.setShopName("Lee Cooper");
		HttpEntity httpEntity = new HttpEntity(shopBean, headers);
		@SuppressWarnings("rawtypes")
		ResponseEntity entity = this.testRestTemplate
				.postForEntity("http://localhost:" + this.port + "/retail/shopDetail", httpEntity, ShopBean.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	public void postShopDetailsTest2() throws Exception {
		HttpHeaders headers = new HttpHeaders();

		headers.set("Content-Type", "application/json");
		ShopBean shopBean = new ShopBean();
		ShopAddress shopAddress = new ShopAddress();
		shopAddress.setShopNumber(12345);
		shopAddress.setShopPostCode(458990);
		shopAddress.setShopPlace("Nagpur");
		shopBean.setShopAddress(shopAddress);
		shopBean.setShopName("Wrangler");
		HttpEntity httpEntity = new HttpEntity(shopBean, headers);
		@SuppressWarnings("rawtypes")
		ResponseEntity entity = this.testRestTemplate
				.postForEntity("http://localhost:" + this.port + "/retail/shopDetail", httpEntity, ShopBean.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	public void getShopDetails() throws Exception {
		@SuppressWarnings("rawtypes")
		ResponseEntity entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/retail/shopDetail?shopLat=20.745319&shopLong=78.60219459999999",
				ShopBean.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}


}