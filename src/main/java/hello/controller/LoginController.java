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

import hello.configs.BootFormInitializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

@Component
public class LoginController implements BootFormInitializable {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	private ApplicationContext springContext;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	public Node initView() throws IOException {
		logger.info("LoginController.initView()");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/login.fxml"));
		loader.setController(this.springContext.getBean(this.getClass()));
		return loader.load();
	}

	@Override
	public void initConstuct() {

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.springContext = applicationContext;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {

	}

	@Override
	public void initValidator() {

	}

}
