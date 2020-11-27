package com.parlonscode.utilities;

public class Api {
	private static final String FORECAST_API_BASE_URL= "https://api.darksky.net/forecast/";
	private static final String FORECAST_API_KEY = "34e2147edd2e6a952b16063d8db5f1fd";
	
	
	public static String getForecastUrl(double latitude, double longitude) {
		return FORECAST_API_BASE_URL + FORECAST_API_KEY + "/" + latitude + "," + longitude + "?units=si&lang=fr";
	}
}
