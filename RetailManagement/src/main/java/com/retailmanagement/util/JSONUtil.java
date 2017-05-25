package com.retailmanagement.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtil {


	public static String readLatLongFromAddress(String address) throws Exception {
	
		String url = "http://maps.googleapis.com/maps/api/geocode/json?address="+address+"&sensor=true";
		String jsonText = getJSON(url);
		JSONObject json = new JSONObject(jsonText);
		JSONArray mainAry = json.getJSONArray("results");
		JSONObject jsonAry = (JSONObject) mainAry.get(0);
		JSONObject geoObj = (JSONObject) jsonAry.get("geometry");
		JSONObject locaObj = (JSONObject) geoObj.get("location");
		return locaObj.getDouble("lng") + ":" + locaObj.getDouble("lat");
	}

	public static String getJSON(String geoUrl) {
		HttpURLConnection httpUrlConnection = null;
		try {
			URL url = new URL(geoUrl);
			httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.setRequestProperty("Content-length", "0");
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setAllowUserInteraction(false);

			httpUrlConnection.connect();
			int status = httpUrlConnection.getResponseCode();

			switch (status) {
			case 200:
			case 201:
				BufferedReader br = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				return sb.toString();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (httpUrlConnection != null) {
				try {
					httpUrlConnection.disconnect();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return null;
	}
}
