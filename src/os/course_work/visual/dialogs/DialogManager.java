package os.course_work.visual.dialogs;

import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.controlsfx.dialog.ExceptionDialog;

public class DialogManager {
	
	private static ResourceBundle bundle = ResourceBundle.getBundle("os.course_work.bundles.ResBundle");
		
	public static void showErrorDialog(String text) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(bundle.getString("error"));
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(("file:resources/icons/error.png")));
		alert.setContentText(text);
		alert.setHeaderText("");
		alert.showAndWait();
	}

	public static void showErrorDialog(String text, Exception e){
		ExceptionDialog exceptionDialog = new ExceptionDialog(e);
		exceptionDialog.setTitle(bundle.getString("error"));
		Stage stage = (Stage) exceptionDialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(("file:resources/icons/error.png")));
		exceptionDialog.setContentText("");
		exceptionDialog.setHeaderText(text);
		exceptionDialog.showAndWait();
	}

	public static void showInformationDialog(String text, String title){
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(("file:resources/icons/information.png")));
		alert.setContentText(text);
		alert.setHeaderText("");
		alert.showAndWait();
	}
}
