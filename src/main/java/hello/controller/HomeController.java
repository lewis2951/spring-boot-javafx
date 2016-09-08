package hello.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.StatusBar;
import org.controlsfx.dialog.ExceptionDialog;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.GlyphFont;
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
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

@Component
public class HomeController implements BootInitializable {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private ApplicationContext springContext;

	@Autowired
	private LoginController loginController;
	@Autowired
	private WelcomeController welcomeController;

	@FXML
	private ToolBar toolBar;
	@FXML
	private StatusBar statusBar;

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
		initToolBar();
	}

	private void initToolBar() {
		GlyphFont fontAwesome = springContext.getBean(GlyphFont.class);

		Button home = new Button("", fontAwesome.create(FontAwesome.Glyph.HOME));

		Button bars = new Button("", fontAwesome.create(FontAwesome.Glyph.BARS));

		Button globe = new Button("", fontAwesome.create(FontAwesome.Glyph.GLOBE));

		Button sign_in = new Button("", fontAwesome.create(FontAwesome.Glyph.SIGN_IN));

		Button sign_out = new Button("", fontAwesome.create(FontAwesome.Glyph.SIGN_OUT));

		Button power_off = new Button("", fontAwesome.create(FontAwesome.Glyph.POWER_OFF).color(Color.BROWN));
		power_off.setOnAction(event -> {
			close();
		});

		toolBar.getItems().clear();
		toolBar.getItems().addAll(home, bars, globe, sign_in, sign_out, power_off);
	}

	public void setLayout(Node node) {
		DoubleProperty opacity = node.opacityProperty();
		node.setOpacity(0.0);
		// context.setCenter(node);
		Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
				new KeyFrame(new Duration(1000), new KeyValue(opacity, 1.0)));
		fadeIn.play();
		// context.getCenter().autosize();
	}

	@FXML
	public void showWelcome() {
		try {
			setLayout(welcomeController.initView());
			welcomeController.initConstuct();

		} catch (Exception e) {
			logger.error("欢迎页面加载异常", e);

			Stage stage = springContext.getBean(Stage.class);

			ExceptionDialog ex = new ExceptionDialog(e);
			ex.initOwner(stage);
			ex.show();

		}
	}

	@FXML
	public void showLogin() {
		try {
			setLayout(loginController.initView());
			loginController.initConstuct();

		} catch (Exception e) {
			logger.error("登录页面加载异常", e);

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
