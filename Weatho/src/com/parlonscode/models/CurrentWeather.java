package com.parlonscode.models;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class CurrentWeather {
	private String timezone;
	private long time;
	private double temperatue;
	private double	humidity;
	private double precipProbability;
	private String summary;
	
	
	
	
	
	
	
	
	
	public String getTimezone() {
		return timezone;
	}
	
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	
	public long getTime() {
		return time;
	}
	
	public String getFormatedTime() {
		Date date = new Date(getTime()*1000L); 
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm"); 
		formatter.setTimeZone(TimeZone.getTimeZone("UTC+1")); 
		String timeString = formatter.format(date);
		return timeString;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public int getTemperatue() {
		return (int) Math.round(temperatue);
	}
	
	public void setTemperatue(double temperatue) {
		this.temperatue = temperatue;
	}
	
	public double getHumidity() {
		return humidity*100;
	}
	
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	
	public int getPrecipProbability() {
		return (int) Math.round(precipProbability);
	}
	
	public void setPrecipProbability(double precipProbability) {
		this.precipProbability = precipProbability;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
}
