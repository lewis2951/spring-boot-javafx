package hello.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import hello.configs.BootInitializable;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

@Component
public class WebsiteController implements BootInitializable {

	private static final Logger logger = LoggerFactory.getLogger(WebsiteController.class);

	private ApplicationContext springContext;
	private WebEngine webEngine;

	@FXML
	private WebView webView;
	@FXML
	private TextField locationField;
	@FXML
	private Button lastBtn;
	@FXML
	private Button nextBtn;
	@FXML
	private Button repeatBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logger.info("WebsiteController.initialize()");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		springContext = applicationContext;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {

	}

	@Override
	public Node initView() throws IOException {
		logger.info("WebsiteController.initView()");

		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/scenes/website.fxml"));
		loader.setController(springContext.getBean(this.getClass()));

		return loader.load();
	}

	@Override
	public void initConstuct() {
		webEngine = webView.getEngine();
		webEngine.locationProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					locationField.setText(newValue);
				});

		webEngine.getLoadWorker().stateProperty()
				.addListener((ObservableValue<? extends State> ov, State oldState, State newState) -> {
					if (newState == State.SUCCEEDED) {
						// repeatBtn.setGraphic(value);
					} else {

					}
				});

	}

	@FXML
	public void goAction() {
		webEngine.load(locationField.getText().startsWith("http://") ? locationField.getText()
				: "http://" + locationField.getText());
	}

}
