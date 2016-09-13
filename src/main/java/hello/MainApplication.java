package hello;

import java.util.Locale;

import org.controlsfx.control.Notifications;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import hello.controller.HomeController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

@SpringBootApplication
public class MainApplication extends Application {

	private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);

	private ConfigurableApplicationContext springContext;
	private static String[] mainArgs;

	// @Bean
	// @Scope(value = "prototype")
	// public ValidationSupport validation() {
	// return new ValidationSupport();
	// }

	@Bean
	public GlyphFont getGlyphFont() {
		GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");
		return fontAwesome;
	}

	@Bean
	public Stage getStage() {
		Stage newStage = new Stage(StageStyle.DECORATED);
		newStage.setTitle("JavaFX by Spring Boot");
		return newStage;
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.CHINA);
		mainArgs = args;
		Application.launch(MainApplication.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Task<Object> worker = new Task<Object>() {
			@Override
			protected Object call() throws Exception {
				springContext = SpringApplication.run(MainApplication.class, mainArgs);
				return null;
			}
		};
		worker.run();

		worker.setOnSucceeded(event -> {
			try {
				logger.info("Loading Spring successful, Application will come soon.");

				HomeController homeController = springContext.getBean(HomeController.class);
				Stage stage = springContext.getBean(Stage.class);

				Parent root = (Parent) homeController.initView();
				stage.setScene(new Scene(root, 960, 680));
				stage.centerOnScreen();
				stage.show();

				homeController.initConstuct();
				// homeController.showWelcome();

				Notifications.create().title("标题").text("内容").position(Pos.BOTTOM_RIGHT).hideAfter(Duration.seconds(5))
						.showInformation();
			} catch (Exception ex) {
				logger.error("Loading Application Error.", ex);
			}
		});

		worker.setOnFailed(event -> {
			try {
				logger.error("Loading Spring Failing, Application will shutdown now.");

				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Loading Spring Failing, Application will shutdown now.");
				alert.show();
			} catch (Exception ex) {
				logger.error("Shutdown Application Error.", ex);
			}
		});
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		Platform.exit();
		springContext.close();
	}

}
