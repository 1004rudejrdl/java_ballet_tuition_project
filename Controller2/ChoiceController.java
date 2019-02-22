package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ChoiceController implements Initializable {
	public Stage choiceStage;
	@FXML
	private Button choiceStudent;
	@FXML
	private Button choiceTuition;
	@FXML
	private Button choiceBody;
//	@FXML
//	private Button choiceScore;
	@FXML
	private Button choiceLogin;
//	@FXML
//	private Button choiceClose;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 0. �л����� ��ư�� �������� ó���ϴ� �Լ�
		choiceStudent.setOnAction(e -> {
			handleBtnChoiceStudent();
		});
		// 0. �к���� ��ư�� �������� ó���ϴ� �Լ�
		choiceTuition.setOnAction(e -> {
			handleBtnChoiceTuition();
		});
		// 0. ��ü���� ��ư�� �������� ó���ϴ� �Լ�
		choiceBody.setOnAction(e -> {
			handleBtnBody();
		});
		// 0. �α���â ��ư�� �������� ó���ϴ� �Լ�
		choiceLogin.setOnAction(e -> {
			handleBtnChoiceLogin();
		});
//		// 0. �����ݱ� ��ư�� �������� ó���ϴ� �Լ�
//		choiceClose.setOnAction(e -> {
//			choiceStage.close();
//		});
	}
	
	// 0. �л����� ��ư�� �������� ó���ϴ� �Լ�
	private void handleBtnChoiceStudent() {
		try {
			Stage studentStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/student.fxml"));
			Parent root = loader.load();
			StudentController studentController = loader.getController();
			studentController.studentStage = studentStage;
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../Application/app.css").toString());
			studentStage.setTitle("HYHBALLET �л�����");
			studentStage.setResizable(false);
			studentStage.setScene(scene);
			choiceStage.close();
			studentStage.show();
			LoginController.callAlert("�л�����:�л����� â�Դϴ�.");
		} catch (Exception e) {
			LoginController.callAlert("�л���������:�л����� â����");
		}
	}

	// 0. �к���� ��ư�� �������� ó���ϴ� �Լ�
	private void handleBtnChoiceTuition() {
		try {
			Stage tuitionStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/tuition.fxml"));
			Parent root = loader.load();
			TuitionController tuitionController = loader.getController();
			tuitionController.tuitionStage = tuitionStage;
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../Application/app.css").toString());
			tuitionStage.setTitle("HYHBALLET �к����");
			tuitionStage.setResizable(false);
			tuitionStage.setScene(scene);
			choiceStage.close();
			tuitionStage.show();
			LoginController.callAlert("�к����:�к���� â�Դϴ�.");
		} catch (Exception e) {
			LoginController.callAlert("�к��������:�к���� â����");
		}

	}
	
	// 0. ��ü���� ��ư�� �������� ó���ϴ� �Լ�
	private void handleBtnBody() {
		try {
			Stage bodyStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/bodyfit.fxml"));
			Parent root = loader.load();
			BodyController bodyController = loader.getController();
			bodyController.bodyStage = bodyStage;
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../Application/app.css").toString());
			bodyStage.setTitle("HYHBALLET ��ü����");
			bodyStage.setResizable(false);
			bodyStage.setScene(scene);
			choiceStage.close();
			bodyStage.show();
			LoginController.callAlert("��ü����:��ü���� â�Դϴ�.");
		} catch (Exception e) {
			LoginController.callAlert("��ü��������:��ü���� â����");
		}
	}

	// 0. �α���â ��ư�� �������� ó���ϴ� �Լ�
	private void handleBtnChoiceLogin() {
		try {
			Stage loginStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/login.fxml"));
			Parent root = loader.load();
			LoginController loginController = loader.getController();
			loginController.loginStage = loginStage;
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../Application/app.css").toString());
			loginStage.setTitle("HYHBALLET �α���");
			loginStage.setResizable(false);
			loginStage.setScene(scene);
			choiceStage.close();
			loginStage.show();
			LoginController.callAlert("�α���â:�α���â �Դϴ�.");
		} catch (Exception e) {
			LoginController.callAlert("�α���â ����:�α���â ����");
		}
	}

}
