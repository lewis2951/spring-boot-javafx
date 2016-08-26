package hello.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.dialog.ExceptionDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import hello.configs.BootInitializable;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

@Component
public class HomeController implements BootInitializable {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private ApplicationContext springContext;

	@Autowired
	private LoginController loginForm;
	@FXML
	private BorderPane contentLayout;

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

	}

	public void setLayout(Node anNode) {
		DoubleProperty opacity = anNode.opacityProperty();
		anNode.setOpacity(0.0);
		contentLayout.setCenter(anNode);
		Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
				new KeyFrame(new Duration(1000), new KeyValue(opacity, 1.0)));
		fadeIn.play();
		contentLayout.getCenter().autosize();
	}

	@FXML
	public void showLoginForm() {
		try {
			setLayout(loginForm.initView());
			loginForm.initConstuct();

		} catch (Exception e) {
			logger.error("登录页面加载异常", e);

			Stage stage = springContext.getBean(Stage.class);

			ExceptionDialog ex = new ExceptionDialog(e);
			ex.initOwner(stage);
			ex.show();

		}
	}

}
