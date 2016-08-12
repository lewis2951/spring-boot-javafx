package hello;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

@SpringBootApplication
public class MainApplication extends Application {

	private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);

	private ConfigurableApplicationContext springContext;
	private static String[] mainArgs;

	public static void main(String[] args) {
		Locale.setDefault(new Locale("zh", "CN"));
		MainApplication.mainArgs = args;
		Application.launch(MainApplication.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Task<Object> worker = new Task<Object>() {
			@Override
			protected Object call() throws Exception {
				springContext = SpringApplication.run(MainApplication.class, MainApplication.mainArgs);
				return null;
			}
		};
		worker.run();

		worker.setOnSucceeded(event -> {
			try {
				logger.info("Spring content is loading successful! Application will come soon.");

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Info");
				// alert.setHeaderText("HeaderText");
				alert.setContentText("ContentText");
				alert.show();

			} catch (Exception ex) {
				logger.error("Gagal load JavaFX Application", ex);
			}
		});

		worker.setOnFailed(event -> {
			try {
				logger.error("Spring 容器加载失败，开始退出程序 ......");

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				// alert.setHeaderText("HeaderText");
				alert.setContentText("ContentText");
				alert.show();

			} catch (Exception ex) {
				logger.error("退出程序出现异常 ......", ex);
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
