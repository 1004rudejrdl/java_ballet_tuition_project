package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	public Stage loginStage;
	@FXML
	private TextField txtId;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Button btnDirectLogin;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnExample;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtPassword.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				handleBtnDirectLoginAction();
			}
		});
		btnDirectLogin.setOnAction(e -> {
			handleBtnDirectLoginAction();
		});
		btnExit.setOnAction(e -> {
			loginStage.close();
		});
		btnExample.setOnAction(e -> {
			txtId.setText("cow7485");
			txtPassword.setText("mjh145");
		});
	}

	private void handleBtnDirectLoginAction() {
		if (!(txtId.getText().equals("cow7485") && txtPassword.getText().equals("mjh145"))) {
			callAlert("�α��ν���:���� ���̵�,�н����� ����");
			txtId.clear();
			txtPassword.clear();
			return;
		}
		try {
			Stage choiceStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/choice.fxml"));
			Parent root = loader.load();
			ChoiceController choiceController = loader.getController();
			choiceController.choiceStage = choiceStage;
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../Application/app.css").toString());
			choiceStage.setTitle("HYHBALLET �޴�����");
			choiceStage.setResizable(false);
			choiceStage.setScene(scene);
			loginStage.close();
			choiceStage.show();
			callAlert("�α��μ���:�����ڸ�� �Դϴ�.");
		} catch (Exception e) {
			callAlert("�α��ν���:�ٽ� �õ����ּ���.");
		}
	}

	// ��Ÿ
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":") + 1));
		alert.showAndWait();
	}

}
