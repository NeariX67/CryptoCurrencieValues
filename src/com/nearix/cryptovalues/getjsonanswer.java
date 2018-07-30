package com.nearix.cryptovalues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.*;

public class getjsonanswer {
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	  InputStream is = new URL(url).openStream();
	  try {
	    BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	    String jsonText = readAll(rd);
	    JSONObject json = new JSONObject(jsonText);
	    return json;
	  } 
	  finally {
	    is.close();
      }
	}
	public static String getJSON(int ID, String currency) throws IOException, JSONException {
		JSONObject json;
		json = readJsonFromUrl("https://api.coinmarketcap.com/v2/ticker/" + ID + "/?convert=" + currency);
		String answer = json.get("data").toString();
		return answer;
	}
	public static String getValue(int ID, String currency, int afterdot) throws IOException, JSONException {
		String json = getJSON(ID, currency);
		//System.out.println(json);
		String buffer = json.substring(json.indexOf(",\"price\":") + 9, json.indexOf(",\"volume_24h\":"));
		String filtered = buffer.substring(0, buffer.indexOf(".") + afterdot + 1);	
		return filtered;
	}
	public static String getProfit(int ID, String currency) throws IOException, JSONException {
		String json = getJSON(ID, currency);
		String buffer = json.substring(json.indexOf(",\"percent_change_24h\":") + 22, json.indexOf(",\"price"));
		if(buffer.startsWith("-")) {
			return buffer;
		}
		else {
			return "+" + buffer;
		}
	}
}