
package com.retailmanagement.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.retailmanagement.bean.ShopBean;

public class RetailStore {
	//public static Map<String, List> datamap = new TreeMap<String, List>();
	
	public static List<ShopBean> shopList = new ArrayList<ShopBean>();

	public static List<ShopBean> getShopList() {
		return shopList;
	}

	public static void setShopList(List<ShopBean> shopList) {
		RetailStore.shopList = shopList;
	}
}
