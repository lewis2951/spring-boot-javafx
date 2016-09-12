package hello.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.dialog.ExceptionDialog;
import org.controlsfx.glyphfont.Glyph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import hello.configs.BootInitializable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

@Component
public class HomeController implements BootInitializable {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private ApplicationContext springContext;

	@Autowired
	private LoginController loginController;
	@Autowired
	private WelcomeController welcomeController;
	@Autowired
	private WebsiteController websiteController;

	@FXML
	private ToolBar toolBar;
	@FXML
	private ScrollPane content;

	@FXML
	private Glyph closeBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logger.info("HomeController.initialize()");
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
		logger.info("HomeController.initView()");

		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/scenes/home.fxml"));
		loader.setController(springContext.getBean(this.getClass()));

		return loader.load();
	}

	@Override
	public void initConstuct() {
		logger.info("HomeController.initConstuct()");
		initToolBars();
	}

	private void initToolBars() {
		closeBtn.color(Color.BROWN);
	}

	@FXML
	public void showWebsite() {
		try {
			Node node = websiteController.initView();
			content.setContent(node);
			websiteController.initConstuct();
		} catch (Exception e) {
			Stage stage = springContext.getBean(Stage.class);

			ExceptionDialog ex = new ExceptionDialog(e);
			ex.initOwner(stage);
			ex.show();
		}
	}

	@FXML
	public void close() {
		Platform.exit();
	}

}
