package hello.configs;

import java.io.IOException;

import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSourceAware;

import javafx.fxml.Initializable;
import javafx.scene.Node;

public interface BootInitializable extends Initializable, ApplicationContextAware, MessageSourceAware {

	public Node initView() throws IOException;

	// public void setStage(Stage stage);

	public void initConstuct();

	// public void initIcons();

}
