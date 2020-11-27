package com.parlonscode;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import com.parlonscode.models.CurrentWeather;
import com.parlonscode.utilities.Alert;
import com.parlonscode.utilities.Api;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 384680530404900478L;
	private static final String GENERIC_ERROR_MESSAGE = "Oooops une erreur est survenue, Veuillez SVP réssayer.";
	private static final String INERNET_CONNECTIVITY_ERROR_MESSAGE = "Veuillez vérifierque vous êtes bel et bien connecté à Internet.";
	
	
	private static final Color BLUE_COLOR = Color.decode("#8EA2C6");
	private static final Color WHITE_COLOR = Color.WHITE;
	private static final Color LIGHT_GRAY_COLOR = new Color(255, 255, 255, 128);
	private static final Font DEFAULT_FONT = new Font("Prixima Nova",Font.PLAIN,24);
	
	private JLabel locationLabel;
	private JLabel timeLabel;
	private JLabel temperatureLabel;
	private JPanel otherInfoPanel;
	private JLabel humidityLabel;
	private JLabel humidityValue;
	private JLabel precipLabel;
	private JLabel precipValue;
	private JLabel summaryLabel;
	
	private CurrentWeather currentWeather;
	

	public MainFrame(String title) {
		super(title);
		
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));;
		contentPane.setBackground(BLUE_COLOR);
		
		
		locationLabel = new JLabel("Tunis, TN", SwingConstants.CENTER);
		locationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		locationLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		locationLabel.setFont(DEFAULT_FONT);
		locationLabel.setForeground(WHITE_COLOR);
		
		
		temperatureLabel = new JLabel("...", SwingConstants.CENTER);
		temperatureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		temperatureLabel.setForeground(WHITE_COLOR);
		temperatureLabel.setFont(DEFAULT_FONT.deriveFont(160f));
		
		
		timeLabel = new JLabel("Il est 02:23 et la temperature est de: ", SwingConstants.CENTER);
		timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		timeLabel.setFont(DEFAULT_FONT.deriveFont(18f));
		timeLabel.setForeground(LIGHT_GRAY_COLOR);
		
		
		otherInfoPanel = new JPanel(new GridLayout(2,2));
		otherInfoPanel.setBackground(BLUE_COLOR);
		
		humidityLabel = new JLabel("Humidité".toUpperCase(), SwingConstants.CENTER);
		humidityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		humidityLabel.setForeground(LIGHT_GRAY_COLOR);
		
		humidityValue= new JLabel("--", SwingConstants.CENTER);
		humidityValue.setAlignmentX(Component.CENTER_ALIGNMENT);
		humidityValue.setForeground(WHITE_COLOR);
		humidityValue.setFont(DEFAULT_FONT);
		
		precipLabel = new JLabel("Risque de pluie".toUpperCase(), SwingConstants.CENTER);
		precipLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		precipLabel.setForeground(LIGHT_GRAY_COLOR);
		
		precipValue= new JLabel("--", SwingConstants.CENTER);
		precipValue.setAlignmentX(Component.CENTER_ALIGNMENT);
		precipValue.setForeground(WHITE_COLOR);
		precipValue.setFont(DEFAULT_FONT);
		
		
		otherInfoPanel.add(humidityLabel);
		otherInfoPanel.add(precipLabel);
		otherInfoPanel.add(humidityValue);
		otherInfoPanel.add(precipValue);
		
		
		summaryLabel = new JLabel("Récupérationde la tepmerature actuelle...", SwingConstants.CENTER);
		summaryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		summaryLabel.setForeground(WHITE_COLOR);
		summaryLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
		summaryLabel.setFont(DEFAULT_FONT.deriveFont(14f));
		
		
		
		contentPane.add(locationLabel);
		contentPane.add(timeLabel);
		contentPane.add(temperatureLabel);
		contentPane.add(otherInfoPanel);
		contentPane.add(summaryLabel);
		

		setContentPane(contentPane);
		
		
		double latitude = 36.800070;
		double longitude = 10.187060;
		// String forecastUrl =
		// String.format("https://api.darksky.net/forecast/%s/%.4f/%.4f",apiKey,latitude,longitude)
		// ;

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(Api.getForecastUrl(latitude, longitude)).build();
		Call call = client.newCall(request);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response) {

				try(ResponseBody body = response.body()) {
					if (response.isSuccessful()) {
						String jsonData = body.string();
						
						currentWeather= getCurrentWeatherDetails(jsonData);
						
						
						EventQueue.invokeLater(()->updateScreen());
						
					} else {
						Alert.error(MainFrame.this, GENERIC_ERROR_MESSAGE);
					}
				} catch (ParseException | IOException e) {
					Alert.error(MainFrame.this, GENERIC_ERROR_MESSAGE);
				}
			}

			@Override
			public void onFailure(Call call, IOException e) {
				Alert.error(MainFrame.this, INERNET_CONNECTIVITY_ERROR_MESSAGE);
			}
		});

	}

	private void updateScreen() {
		timeLabel.setText("Il est " + currentWeather.getFormatedTime() + " et la temperature actuelle est de: ");
		temperatureLabel.setText(currentWeather.getTemperatue()+"°");
		humidityValue.setText(currentWeather.getHumidity()+"");
		precipValue.setText("30%");
		summaryLabel.setText(currentWeather.getSummary());
		
		
	}

	private CurrentWeather getCurrentWeatherDetails(String jsonData) throws ParseException  {
		CurrentWeather currentWeather = new CurrentWeather();
		JSONObject forecast = (JSONObject) JSONValue.parseWithException(jsonData);
		JSONObject currently = (JSONObject) forecast.get("currently");
		currentWeather.setTimezone((String)forecast.get("timezone"));
		currentWeather.setTime((long) currently.get("time"));
		currentWeather.setTemperatue(Double.parseDouble(currently.get("temperature")+""));
		currentWeather.setHumidity(Double.parseDouble(currently.get("humidity")+""));
		currentWeather.setPrecipProbability(Double.parseDouble(currently.get("precipProbability")+""));
		currentWeather.setSummary((String) currently.get("summary"));
		return currentWeather;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
}