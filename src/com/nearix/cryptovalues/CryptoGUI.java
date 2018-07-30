package com.nearix.cryptovalues;

import java.io.File;
import java.io.IOException;
import org.json.JSONException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CryptoGUI extends Application {	
	Button brefresh = new Button("Refresh");
	
	ObservableList<String> currency = FXCollections.observableArrayList(
			"CHF",
			"CZK",
			"EUR",
			"USD"
		);
	ComboBox<String> cbCurrency = new ComboBox<String>(currency);
	ObservableList<String> afterdot = FXCollections.observableArrayList(
			"1",
			"2",
			"3",
			"4",
			"5",
			"6"
		);
	ComboBox<String> cbDot = new ComboBox<String>(afterdot);
	
	
	Label lBTC = new Label("BTC");
	Label lBTCValue = new Label();
	Label lBTCProfit = new Label();
	
	Label lETH = new Label("ETH");
	Label lETHValue = new Label();
	Label lETHProfit = new Label();
	
	Label lLTC = new Label("LTC");
	Label lLTCValue = new Label();
	Label lLTCProfit = new Label();
	
	Label lRVN = new Label("RVN");
	Label lRVNValue = new Label();
	Label lRVNProfit = new Label();
	
	Label lInfoCurr = new Label("Currency:");
	Label lInfoDot = new Label("Digits after dot:");
	
	Pane pane = new Pane();
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage stage) {
		
		File f = new File("config.txt");
		if(!f.exists()) {
			try {
				f.createNewFile();
				filemanager.writeFile("EUR,2");
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		stage.setTitle("Cryptocurrencies Values");
		stage.setWidth(400);
		stage.setHeight(390);
		stage.setMinHeight(390);
		stage.setMinWidth(400);
		stage.setMaxHeight(800);
		stage.setMaxWidth(800);
		
		brefresh.setLayoutX(stage.getWidth() - 75);
		brefresh.setLayoutY(stage.getHeight() - 70);
		
		lInfoCurr.setLayoutX(5);
		lInfoCurr.setLayoutY(10);
		
		lInfoDot.setLayoutX(5);
		lInfoDot.setLayoutY(50);
		
		lBTC.setLayoutX(5);
		lBTC.setLayoutY(85);
		lBTCValue.setLayoutX(50);
		lBTCValue.setLayoutY(85);
		lBTCProfit.setLayoutX(150);
		lBTCProfit.setLayoutY(85);
		
		lETH.setLayoutX(5);
		lETH.setLayoutY(110);
		lETHValue.setLayoutX(50);
		lETHValue.setLayoutY(110);
		lETHProfit.setLayoutX(150);
		lETHProfit.setLayoutY(110);
		
		lLTC.setLayoutX(5);
		lLTC.setLayoutY(135);
		lLTCValue.setLayoutX(50);
		lLTCValue.setLayoutY(135);
		lLTCProfit.setLayoutX(150);
		lLTCProfit.setLayoutY(135);
		
		lRVN.setLayoutX(5);
		lRVN.setLayoutY(160);
		lRVNValue.setLayoutX(50);
		lRVNValue.setLayoutY(160);
		lRVNProfit.setLayoutX(150);
		lRVNProfit.setLayoutY(160);
		
		cbCurrency.setLayoutX(100);
		cbCurrency.setLayoutY(5);
		cbCurrency.setValue(filemanager.readFile().split(",")[0]);
		
		cbDot.setLayoutX(100);
		cbDot.setLayoutY(45);
		cbDot.setValue(filemanager.readFile().split(",")[1]);
		
		refreshValues();
		
		pane.getChildren().addAll(brefresh, cbCurrency, cbDot, lInfoCurr, lInfoDot, lBTC, lBTCValue, lBTCProfit, lETH, lETHValue, lETHProfit, lLTC, lLTCValue, lLTCProfit, lRVN, lRVNValue, lRVNProfit);
		stage.setScene(new Scene(pane, 300, 400));
		stage.show();
		
		
		brefresh.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				startTask();
				refreshValues();
			}
		});
		cbCurrency.valueProperty().addListener(new ChangeListener<String>() {
	        @Override public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, String t, String t1) {
	        	filemanager.writeFile(t1 + "," + cbDot.getValue());
	        	refreshValues();
	        }    
	    });
		cbDot.valueProperty().addListener(new ChangeListener<String>() {
			public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, String t, String t1) {
				filemanager.writeFile(cbCurrency.getValue() + "," + t1);
				refreshValues();
			}
		});
		
		stage.widthProperty().addListener((obs, oldVal, newVal) -> {
			
		});
		stage.heightProperty().addListener((obs, oldVal, newVal) -> {
		});
		
		stage.setOnCloseRequest(event -> {
			Platform.exit();
		});
	}

	public void startTask() {
		Runnable task = new Runnable() {
			public void run() {
				runTask();
			}
		};
		Thread backgroundThread = new Thread(task);
		backgroundThread.setDaemon(true);
		backgroundThread.start();
	}

	public void runTask() {
		
	}
	public void refreshValues() {
		try {
			if(cbCurrency.getValue().equals("USD") && Integer.parseInt(cbDot.getValue()) > 2) {
				lBTCValue.setText(getjsonanswer.getValue(1, cbCurrency.getValue(), 2) + currencySym(cbCurrency.getValue()));
				lETHValue.setText(getjsonanswer.getValue(1027, cbCurrency.getValue(), 2) + currencySym(cbCurrency.getValue()));
				lLTCValue.setText(getjsonanswer.getValue(2, cbCurrency.getValue(), 2) + currencySym(cbCurrency.getValue()));
				lRVNValue.setText(getjsonanswer.getValue(2577, cbCurrency.getValue(), 2) + currencySym(cbCurrency.getValue()));
				lBTCProfit.setText(getjsonanswer.getProfit(1, cbCurrency.getValue()) + "%");
				lETHProfit.setText(getjsonanswer.getProfit(1027, cbCurrency.getValue()) + "%");
				lLTCProfit.setText(getjsonanswer.getProfit(2, cbCurrency.getValue()) + "%");
				lRVNProfit.setText(getjsonanswer.getProfit(2577, cbCurrency.getValue()) + "%");
			}
			else {
				lBTCValue.setText(getjsonanswer.getValue(1, cbCurrency.getValue(), Integer.parseInt(cbDot.getValue())) + currencySym(cbCurrency.getValue()));
				lETHValue.setText(getjsonanswer.getValue(1027, cbCurrency.getValue(), Integer.parseInt(cbDot.getValue())) + currencySym(cbCurrency.getValue()));
				lLTCValue.setText(getjsonanswer.getValue(2, cbCurrency.getValue(), Integer.parseInt(cbDot.getValue())) + currencySym(cbCurrency.getValue()));
				lRVNValue.setText(getjsonanswer.getValue(2577, cbCurrency.getValue(), Integer.parseInt(cbDot.getValue())) + currencySym(cbCurrency.getValue()));
				lBTCProfit.setText(getjsonanswer.getProfit(1, cbCurrency.getValue()) + "%");
				lETHProfit.setText(getjsonanswer.getProfit(1027, cbCurrency.getValue()) + "%");
				lLTCProfit.setText(getjsonanswer.getProfit(2, cbCurrency.getValue()) + "%");
				lRVNProfit.setText(getjsonanswer.getProfit(2577, cbCurrency.getValue()) + "%");
			}
			if(lBTCProfit.getText().startsWith("+")) {
				lBTCProfit.setTextFill(Color.web("#00aa00"));
			}
			else {
				lBTCProfit.setTextFill(Color.web("#aa0000"));
			}
			
			if(lETHProfit.getText().startsWith("+")) {
				lETHProfit.setTextFill(Color.web("#00aa00"));
			}
			else {
				lETHProfit.setTextFill(Color.web("#aa0000"));
			}
			
			if(lLTCProfit.getText().startsWith("+")) {
				lLTCProfit.setTextFill(Color.web("#00aa00"));
			}
			else {
				lLTCProfit.setTextFill(Color.web("#aa0000"));
			}
			
			if(lRVNProfit.getText().startsWith("+")) {
				lRVNProfit.setTextFill(Color.web("#00aa00"));
			}
			else {
				lRVNProfit.setTextFill(Color.web("#aa0000"));
			}
		} 
		catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
	public String currencySym(String Currency) {
		switch(Currency) {
		case "EUR":
			return "€";
		case "USD": 
			return "$";
		case "CHF":
			return " CHF";
		case "CZK":
			return " CZK";
		default:
			return null;
		}
	}
}