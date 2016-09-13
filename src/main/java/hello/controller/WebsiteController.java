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
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

@Component
public class WebsiteController implements BootInitializable {

	private static final Logger logger = LoggerFactory.getLogger(WebsiteController.class);

	private ApplicationContext springContext;
	private WebEngine webEngine;

	@FXML
	private HBox toolBox;
	@FXML
	private Button backBtn;
	@FXML
	private Button forwardBtn;
	@FXML
	private Button reloadBtn;
	@FXML
	private Button stopLoadingBtn;

	@FXML
	private TextField locationField;

	@FXML
	private WebView webView;

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
		logger.info("WebsiteController.initConstuct()");

		webEngine = webView.getEngine();
		backBtn.setDisable(true);
		forwardBtn.setDisable(true);
		toolBox.getChildren().remove(stopLoadingBtn);

		webEngine.getLoadWorker().stateProperty()
				.addListener((ObservableValue<? extends State> ov, State oldState, State newState) -> {
					int currentIndex = webEngine.getHistory().getCurrentIndex();
					int maxIndex = webEngine.getHistory().getEntries().size() == 0 ? 0
							: webEngine.getHistory().getEntries().size() - 1;

					if (newState == State.SUCCEEDED) {
						backBtn.setDisable(currentIndex == 0);
						forwardBtn.setDisable(currentIndex == maxIndex);

						changeButton(stopLoadingBtn, reloadBtn);
					}

					if (newState == State.RUNNING) {
						changeButton(reloadBtn, stopLoadingBtn);
					}
				});

		webEngine.locationProperty()
				.addListener((ObservableValue<? extends String> ov, String oldValue, String newValue) -> {
					locationField.setText(newValue);
				});
	}

	private void changeButton(Button oldButton, Button newButton) {
		int btnIndex = toolBox.getChildren().indexOf(oldButton);
		if (btnIndex == -1) {
			return;
		}

		toolBox.getChildren().add(btnIndex, newButton);
		toolBox.getChildren().remove(oldButton);
	}

	@FXML
	public void backAction() {
		webEngine.getHistory().go(-1);
	}

	@FXML
	public void forwardAction() {
		webEngine.getHistory().go(+1);
	}

	@FXML
	public void reloadAction() {
		webEngine.reload();
	}

	@FXML
	public void stopLoadingAction() {
		webEngine.getLoadWorker().cancel();
	}

	@FXML
	public void goAction() {
		String url = locationField.getText();
		if (!url.startsWith("http://") && !url.startsWith("https://")) {
			url = "http://" + url;
		}

		webEngine.load(url);
	}

}
