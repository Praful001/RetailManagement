package com.retailmanagement.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.retailmanagement.bean.ShopBean;
import com.retailmanagement.exceptions.NoStoreException;
import com.retailmanagement.store.RetailStore;
import com.retailmanagement.util.DistanceCalculator;
import com.retailmanagement.util.JSONUtil;

@Controller
@RequestMapping(value = "/retail")
public class RetailManagerController {

	/*
	 * method to add shop details.
	 * 
	 * @param shopBean ShopBean : to be added
	 */
	@RequestMapping(value = "/shopDetail", method = RequestMethod.POST)
	public ResponseEntity addShopDetails(@RequestBody ShopBean shopBean) throws Exception {

		if (shopBean != null) {
			try {
				String latLong = JSONUtil.readLatLongFromAddress(shopBean.getShopAddress().getShopPlace());
				String[] latLongAry = latLong.split(":");
				shopBean.setShopLongitude(Double.parseDouble(latLongAry[0]));
				shopBean.setShopLatitude(Double.parseDouble(latLongAry[1]));
				RetailStore.getShopList().add(shopBean);

			} catch (Exception e) {
				throw new Exception("Invalid Deatils");
			}
		}

		return new ResponseEntity(shopBean, HttpStatus.CREATED);
	}

	/*
	 * method to get nearest shop details from current long and lat.
	 */
	@RequestMapping(value = "/shopDetail", method = RequestMethod.GET)
	public ResponseEntity<ShopBean> getShopDeatails(@RequestParam("shopLat") String shopLat,
			@RequestParam("shopLong") String shopLong) {
		System.out.println();
		List<ShopBean> shopBeanList = RetailStore.getShopList();
		Double minDist = null;
		ShopBean nearestShop = null;
		int cnt = 0;
		for (ShopBean shopBean : shopBeanList) {
			Double dist = DistanceCalculator.distance(shopBean.getShopLatitude(), shopBean.getShopLongitude(),
					Double.parseDouble(shopLat), Double.parseDouble(shopLong));
			if (cnt == 0) {
				minDist = dist;
				nearestShop = shopBean;
			} else if (dist < minDist) {
				minDist = dist;
				nearestShop = shopBean;
			}
			cnt++;

		}
		return new ResponseEntity(nearestShop, HttpStatus.OK);

	}

	/*
	 * method to get all shop details
	 */

	@RequestMapping(value = "/shopDetails", method = RequestMethod.GET)
	public ResponseEntity<List> getAllShopDeatails() {
System.out.println("call here");
		List<ShopBean> shopBeanList = RetailStore.getShopList();
		return new ResponseEntity<List>(shopBeanList, HttpStatus.OK);
	}
}
