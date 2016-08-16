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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;

@Component
public class HomeController implements BootInitializable {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private ApplicationContext springContext;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logger.info("HomeController.initialize()");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.springContext = applicationContext;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {

	}

	@Override
	public Node initView() throws IOException {
		logger.info("HomeController.initView()");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/home.fxml"));
		loader.setController(this.springContext.getBean(this.getClass()));
		return loader.load();
	}

	@Override
	public void setStage(Stage stage) {

	}

	@Override
	public void initConstuct() {

	}

}
