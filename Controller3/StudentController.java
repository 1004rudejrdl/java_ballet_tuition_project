package Controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StudentController implements Initializable {
	public Stage studentStage;
	@FXML
	private TextField stuTxtSearch;
	@FXML
	private Button stuBtnSearch;
	@FXML
	private Button stuBtnImageChange;
	@FXML
	private Button stuBtnAdd;
	@FXML
	private Button stuBtnEdit;
	@FXML
	private Button stuBtnDelete;
	@FXML
	private Button stuBtnBack;
	@FXML
	private Button stuBtnClose;
	@FXML
	private TableView<Student> stuTableView;
	@FXML
	private ImageView stuImageView;

	private ObservableList<Student> stuListData = FXCollections.observableArrayList();
	private ObservableList<String> stuPartyList = FXCollections.observableArrayList();
	ArrayList<Student> stuArrayList;

	private Student selectedStudent;
	private int selectedStudentIndex;

	private File selectedFile = null;
	private String fileName = "";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 0.���̺�� ����
		stuTableViewSet();

		// 0. �޺��ڽ� (������) ����
		stuPartyList.addAll("�ʵ�����", "�ߵ�����", "�������");

		// 0. �л��߰� ��ư�� �������� ó���ϴ� �Լ�
		stuBtnAdd.setOnAction(e -> {
			handleStuBtnAddAction();
		});

		// 0. �л����� ��ư�� �������� ó���ϴ� �Լ�
		stuBtnEdit.setOnAction(e -> {
			handleStuBtnEditAction();
		});
		// 0. �л����� ��ư�� �������� ó���ϴ� �Լ�
		stuBtnDelete.setOnAction(e -> {
			handleStuBtnDeleteAction();
		});

		// 0. ���̺�並 �ѹ� Ŭ�������� �Լ�
		stuTableView.setOnMouseClicked(e -> {
			handleStuTableViewClickAction(e);
		});

		// 0. �˻��� �������� ó���ϴ� �Լ�
		stuBtnSearch.setOnAction(e -> {
			handleStuBtnSearchAction();
		});

		// 0. �˻��ؽ�Ʈ �ʵ忡�� ���͸� �������� ó���ϴ� �Լ�
		stuTxtSearch.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				handleStuBtnSearchAction();
			}
		});

		// 0. �ڷΰ��� ��ư�� �������� ó���ϴ� �Լ�
		stuBtnBack.setOnAction(e -> {
			handleStuBtnBackAction();
		});

		// 0. �����ư�� �������� ó���ϴ� �Լ�
		stuBtnClose.setOnAction(e -> {
			studentStage.close();
		});
	}

	// 0.���̺�� ����
	private void stuTableViewSet() {
		TableColumn tcStudentId = stuTableView.getColumns().get(0);
		tcStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
		tcStudentId.setStyle("-fx-alignment: CENTER");
		TableColumn tcName = stuTableView.getColumns().get(1);
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcName.setStyle("-fx-alignment: CENTER");
		TableColumn tcAge = stuTableView.getColumns().get(2);
		tcAge.setCellValueFactory(new PropertyValueFactory<>("age"));
		tcAge.setStyle("-fx-alignment: CENTER");
		TableColumn tcPhone = stuTableView.getColumns().get(3);
		tcPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		tcPhone.setStyle("-fx-alignment: CENTER");
		TableColumn tcAddress = stuTableView.getColumns().get(4);
		tcAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		tcAddress.setStyle("-fx-alignment: CENTER");
		TableColumn tcParty = stuTableView.getColumns().get(5);
		tcParty.setCellValueFactory(new PropertyValueFactory<>("party"));
		tcParty.setStyle("-fx-alignment: CENTER");
		TableColumn tcImage = stuTableView.getColumns().get(6);
		tcImage.setCellValueFactory(new PropertyValueFactory<>("image"));
		tcImage.setStyle("-fx-alignment: CENTER");
		stuTableView.setItems(stuListData);

		stuArrayList = StudentDAO.getStudentFromBalletDB();
		for (Student student : stuArrayList) {
			stuListData.add(student);
		}
	}

	// 0. �л��߰� ��ư�� �������� ó���ϴ� �Լ�
	private void handleStuBtnAddAction() {
		try {
			Stage addStage = new Stage(StageStyle.UTILITY);
			// addStage.initModality(Modality.WINDOW_MODAL);
			addStage.initOwner(studentStage);
			addStage.setTitle("�л��߰�â");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/studentadd.fxml"));
			Parent root = loader.load();
			// ******************************ID���*********************************
			TextField addTxtStudentId = (TextField) root.lookup("#addTxtStudentId");
			TextField addTxtName = (TextField) root.lookup("#addTxtName");
			TextField addTxtAge = (TextField) root.lookup("#addTxtAge");
			TextField addTxtPhone = (TextField) root.lookup("#addTxtPhone");
			TextField addTxtAddress = (TextField) root.lookup("#addTxtAddress");
			ComboBox<String> addCmbParty = (ComboBox) root.lookup("#addCmbParty");
			addCmbParty.setItems(stuPartyList);
			TextField addTxtImage = (TextField) root.lookup("#addTxtImage");
			addTxtImage.setEditable(false);
			Button addBtnImage = (Button) root.lookup("#addBtnImage");
			Button addBtnExample = (Button) root.lookup("#addBtnExample");
			Button addBtnAdd = (Button) root.lookup("#addBtnAdd");
			Button addBtnClose = (Button) root.lookup("#addBtnClose");
			ImageView addImageView = (ImageView) root.lookup("#addImageView");
			// ****************************addBtnImage �̺�Ʈ*****************************
			addBtnImage.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new ExtensionFilter("Image File", "*.jpg", "*.png", "*.gif"));
				selectedFile = fileChooser.showOpenDialog(addStage);
				if (selectedFile != null) {
					fileName = selectedFile.getName();
				}
				addImageView.setImage(new Image(getClass().getResource("../images/" + fileName).toString()));
				stuImageView.setImage(new Image(getClass().getResource("../images/" + fileName).toString()));
				addTxtImage.setText(fileName);

			});
			// ****************************addBtnExample �̺�Ʈ*****************************
			addBtnExample.setOnAction(e -> {
				addTxtStudentId.setText("mjk7485");
				addTxtName.setText("������");
				addTxtAge.setText("11");
				addTxtPhone.setText("019-3572-7485");
				addTxtAddress.setText("Ǫ������ 105�� 1202ȣ");
				addCmbParty.getSelectionModel().select("�ʵ�����");
			});
			// ****************************addBtnAdd �̺�Ʈ*****************************
			addBtnAdd.setOnAction(e -> {
				Student student = new Student(addTxtStudentId.getText(), addTxtName.getText(),
						Integer.parseInt(addTxtAge.getText()), addTxtPhone.getText(), addTxtAddress.getText(),
						addCmbParty.getSelectionModel().getSelectedItem(), fileName);

				int count = StudentDAO.insertStudentIntoBalletDB(student);
				if (count != 0) {
					stuListData.add(student);
					callAlert("�߰�:�߰��Ǿ����ϴ�.");
				}
				addTxtStudentId.clear();
				addTxtName.clear();
				addTxtAge.clear();
				addTxtPhone.clear();
				addTxtAddress.clear();
				addCmbParty.getSelectionModel().clearSelection();
				stuImageView.setImage(new Image(getClass().getResource("../images/basicImage.jpg").toString()));
				selectedFile = null;
				fileName = null;
			});
			// ****************************addBtnClose �̺�Ʈ***************************
			addBtnClose.setOnAction(e -> {
				addStage.close();
			});
			Scene scene = new Scene(root);
			addStage.setResizable(false);
			addStage.setScene(scene);
			addStage.show();
		} catch (Exception e) {
			callAlert("�л��߰�â ���� :�л��߰�â ������ �߻��߽��ϴ�.");
		}
	}

	// 0. �л����� ��ư�� �������� ó���ϴ� �Լ�
	private void handleStuBtnEditAction() {
		selectedStudent = stuTableView.getSelectionModel().getSelectedItem();
		selectedStudentIndex = stuTableView.getSelectionModel().getSelectedIndex();
		try {
			Stage editStage = new Stage(StageStyle.UTILITY);
			//editStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(studentStage);
			editStage.setTitle("�л�����â");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/studentedit.fxml"));
			Parent root = loader.load();
			// ******************************ID���*********************************
			TextField editTxtStudentId = (TextField) root.lookup("#editTxtStudentId");
			TextField editTxtName = (TextField) root.lookup("#editTxtName");
			TextField editTxtAge = (TextField) root.lookup("#editTxtAge");
			TextField editTxtPhone = (TextField) root.lookup("#editTxtPhone");
			TextField editTxtAddress = (TextField) root.lookup("#editTxtAddress");
			ComboBox<String> editCmbParty = (ComboBox) root.lookup("#editCmbParty");
			editCmbParty.setItems(stuPartyList);
			TextField editTxtImage = (TextField) root.lookup("#editTxtImage");
			editTxtImage.setEditable(false);
			Button editBtnImage = (Button) root.lookup("#editBtnImage");
			Button editBtnExample = (Button) root.lookup("#editBtnExample");
			Button editBtnEdit = (Button) root.lookup("#editBtnEdit");
			Button editBtnClose = (Button) root.lookup("#editBtnClose");
			ImageView editImageView = (ImageView) root.lookup("#editImageView");
			// ****************************����ȭ�� �ʱ�ȭ*********************************
			editTxtStudentId.setText(selectedStudent.getStudentId());
			editTxtName.setText(selectedStudent.getName());
			editTxtAge.setText(String.valueOf(selectedStudent.getAge()));
			editTxtPhone.setText(selectedStudent.getPhone());
			editTxtAddress.setText(selectedStudent.getAddress());
			editCmbParty.getSelectionModel().select(selectedStudent.getParty());
			editTxtImage.setText(selectedStudent.getImage());
			editImageView
					.setImage(new Image(getClass().getResource("../images/" + selectedStudent.getImage()).toString()));
			editTxtStudentId.setDisable(true);
			editTxtName.setDisable(true);
			// ****************************editBtnExample �̺�Ʈ*****************************
			editBtnExample.setOnAction(e -> {
				// editTxtStudentId.setText("yjy5014");
				// editTxtName.setText("������");
				editTxtAge.setText("14");
				editTxtPhone.setText("019-6414-5142");
				editTxtAddress.setText("����޸���� 101�� 201ȣ");
				editCmbParty.getSelectionModel().select("�ߵ�����");
			});
			// ****************************editBtnImage �̺�Ʈ*****************************
			editBtnImage.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new ExtensionFilter("Image File", "*.jpg", "*.png", "*.gif"));
				selectedFile = fileChooser.showOpenDialog(editStage);
				if (selectedFile != null) {
					fileName = selectedFile.getName();
				}
				editImageView.setImage(new Image(getClass().getResource("../images/" + fileName).toString()));
				stuImageView.setImage(new Image(getClass().getResource("../images/" + fileName).toString()));
				editTxtImage.setText(fileName);

			});
			// ****************************editBtnEdit �̺�Ʈ*****************************
			editBtnEdit.setOnAction(e -> {
				Student student = new Student(editTxtStudentId.getText(), editTxtName.getText(),
						Integer.parseInt(editTxtAge.getText()), editTxtPhone.getText(), editTxtAddress.getText(),
						editCmbParty.getSelectionModel().getSelectedItem(), editTxtImage.getText());

				int count = StudentDAO.updateStudentSet(student, selectedStudent);
				if (count != 0) {
					stuListData.set(selectedStudentIndex, student);
					callAlert("����:�����Ǿ����ϴ�.");
					editStage.close();
				} else {
					return;
				}
			});
			// ****************************editBtnClose �̺�Ʈ***************************
			editBtnClose.setOnAction(e -> {
				editStage.close();
			});
			Scene scene = new Scene(root);
			editStage.setResizable(false);
			editStage.setScene(scene);
			editStage.show();
		} catch (Exception e) {
			callAlert("�л����� ���� :�л����� �� �������ּ���.");
		}
	}

	// 0. �л����� ��ư�� �������� ó���ϴ� �Լ�
	private void handleStuBtnDeleteAction() {
		try {
			selectedStudent = stuTableView.getSelectionModel().getSelectedItem();
			int count = StudentDAO.deleteStudentFromDB(selectedStudent.getStudentId());
			if (count != 0) {
				stuListData.remove(selectedStudent);
				stuArrayList.remove(selectedStudent);
				callAlert("����:�����Ǿ����ϴ�.");
			} else {
				return;
			}
		} catch (Exception e) {
			callAlert("�л����� ����:�л����� �� �������ּ���.");
		}
	}

	// 0. ���̺�並 �ѹ� Ŭ�������� �Լ�
	private void handleStuTableViewClickAction(MouseEvent e) {
		try {
			selectedStudent = stuTableView.getSelectionModel().getSelectedItem();
			selectedStudentIndex = stuTableView.getSelectionModel().getSelectedIndex();
			if (e.getClickCount() == 1) {
				stuImageView.setImage(
						new Image(getClass().getResource("../images/" + selectedStudent.getImage()).toString()));
			}
		} catch (Exception e1) {
		}

	}

	// 0. �˻��� �������� ó���ϴ� �Լ�
	private void handleStuBtnSearchAction() {
		for (Student student : stuListData) {
			if (stuTxtSearch.getText().trim().equals(student.getName())) {
				stuTableView.getSelectionModel().select(student);
			}
		}
	}

	// 0. �ڷΰ��� ��ư�� �������� ó���ϴ� �Լ�
	private void handleStuBtnBackAction() {
		try {
			stuArrayList.clear();
			stuListData.clear();
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
			studentStage.close();
			choiceStage.show();

		} catch (Exception e) {
		}
	}

	// ��Ÿ : �˸�â
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":") + 1));
		alert.showAndWait();
	}

}
