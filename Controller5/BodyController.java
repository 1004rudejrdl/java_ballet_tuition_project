package Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import Model.Body;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BodyController implements Initializable {
	public Stage bodyStage;
	@FXML
	private TextField bodyTxtSearch;
	@FXML
	private ComboBox<String> bodyCmbStudentId;
	@FXML
	private ComboBox<String> bodyCmbMeasureDate;
	@FXML
	private Button bodyBtnSearch;
	@FXML
	private Button bodyBtnAdd;
	@FXML
	private Button bodyBtnEdit;
	@FXML
	private Button bodyBtnDelete;
	@FXML
	private Button bodyBtnWeight;
	@FXML
	private Button bodyBtnMuscle;
	@FXML
	private Button bodyBtnFat;
	@FXML
	private Button bodyBtnBack;
	@FXML
	private Button bodyBtnClose;
	@FXML
	private TableView<Body> bodyTableView;
	private ObservableList<Body> bodyListData = FXCollections.observableArrayList();
	private ObservableList<String> bodyMeasureDateList = FXCollections.observableArrayList();
	ArrayList<Body> bodyArrayList = new ArrayList<>();
	ArrayList<String> bodyStudentId = new ArrayList<>();
	ArrayList<String> bodyNameAndAge = new ArrayList<>();
	TreeMap<String, Double> bodySelectedWeight = new TreeMap<>();
	LinkedHashMap<String, Double> bodySelectedMuscle = new LinkedHashMap<>();
	TreeMap<String, Double> bodySelectedFat = new TreeMap<>();

	private Body selectedBody;
	private int selectedBodyIndex;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 0.���̺�� ����
		bodyTableViewSet();

		// 0.�޺��ڽ�(������) ����
		bodyMeasureDateList.addAll("2019��","2018��", "2017��");
		bodyCmbMeasureDate.setItems(bodyMeasureDateList);

		// 0. �̸��Է¶��� �̸��� �Է��ϰ� �˻���ư�� �������� ó���ϴ� �Լ�
		bodyBtnSearch.setOnAction(e -> {
			handleBodyBtnSearchAction();
		});
		// 0. �̸��Է¶��� �̸��� �Է��ϰ� ���͸� ������ ó���ϴ� �Լ�
		bodyTxtSearch.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				handleBodyBtnSearchAction();
			}
		});

		// 0. �߰� ��ư�� �������� ó���ϴ� �Լ�
		bodyBtnAdd.setOnAction(e -> {
			handleBodyBtnAddAction();
		});

		// 0. ���� ��ư�� �������� ó���ϴ� �Լ�
		bodyBtnEdit.setOnAction(e -> {
			handleBodyBtnEditAction();
		});

		// 0. ���� ��ư�� �������� ó���ϴ� �Լ�
		bodyBtnDelete.setOnAction(e -> {
			handleBodyBtnDeleteAction();
		});

		// 0. ü�ߺ� ��ư�� �������� ó���ϴ� �Լ�
		bodyBtnWeight.setOnAction(e -> {
			handleBodyBtnWeightAction();
		});

		// 0. ������ �� ��ư�� �������� ó���ϴ� �Լ�
		bodyBtnMuscle.setOnAction(e -> {
			handleBodyBtnMuscleAction();
		});

		// 0. ü����� �� ��ư�� �������� ó���ϴ� �Լ�
		bodyBtnFat.setOnAction(e -> {
			handleBodyBtnFatAction();
		});

		// 0. �ڷΰ��� ��ư�� �������� ó���ϴ� �Լ�
		bodyBtnBack.setOnAction(e -> {
			handleBodyBtnBackAction();
		});

		// 0. �����ư�� �������� ó���ϴ� �Լ�
		bodyBtnClose.setOnAction(e -> {
			bodyStage.close();
		});

	}

	// 0.���̺�� ����
	private void bodyTableViewSet() {
		TableColumn tcStudentId = bodyTableView.getColumns().get(0);
		tcStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
		tcStudentId.setStyle("-fx-alignment: CENTER");
		TableColumn tcName = bodyTableView.getColumns().get(1);
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcName.setStyle("-fx-alignment: CENTER");
		TableColumn tcAge = bodyTableView.getColumns().get(2);
		tcAge.setCellValueFactory(new PropertyValueFactory<>("age"));
		tcAge.setStyle("-fx-alignment: CENTER");
		TableColumn tcHeight = bodyTableView.getColumns().get(3);
		tcHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
		tcHeight.setStyle("-fx-alignment: CENTER");
		TableColumn tcWeight = bodyTableView.getColumns().get(4);
		tcWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
		tcWeight.setStyle("-fx-alignment: CENTER");
		TableColumn tcMuscle = bodyTableView.getColumns().get(5);
		tcMuscle.setCellValueFactory(new PropertyValueFactory<>("muscle"));
		tcMuscle.setStyle("-fx-alignment: CENTER");
		TableColumn tcFat = bodyTableView.getColumns().get(6);
		tcFat.setCellValueFactory(new PropertyValueFactory<>("fat"));
		tcFat.setStyle("-fx-alignment: CENTER");
		TableColumn tcMeasureDate = bodyTableView.getColumns().get(7);
		tcMeasureDate.setCellValueFactory(new PropertyValueFactory<>("measureDate"));
		tcMeasureDate.setStyle("-fx-alignment: CENTER");
		bodyTableView.setItems(bodyListData);

		// �����ͺ��̽����� ���õ� �⵵�� ��ü������ �����´�.
		bodyCmbMeasureDate.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				switch (newValue) {
				case "2019��":
					if (bodyArrayList != null) {
						bodyArrayList.clear();
					}
					if (bodyListData != null) {
						bodyListData.clear();
					}
					bodyArrayList = BodyDAO
							.get2019BodyFromBalletDb(bodyCmbStudentId.getSelectionModel().getSelectedItem());
					for (Body body : bodyArrayList) {
						bodyListData.add(body);
					}
					break;
				case "2018��":
					if (bodyArrayList != null) {
						bodyArrayList.clear();
					}
					if (bodyListData != null) {
						bodyListData.clear();
					}
					bodyArrayList = BodyDAO
							.get2018BodyFromBalletDb(bodyCmbStudentId.getSelectionModel().getSelectedItem());
					for (Body body : bodyArrayList) {
						bodyListData.add(body);
					}
					break;
				case "2017��":
					if (bodyArrayList != null) {
						bodyArrayList.clear();
					}
					if (bodyListData != null) {
						bodyListData.clear();
					}
					bodyArrayList = BodyDAO
							.get2017BodyFromBalletDb(bodyCmbStudentId.getSelectionModel().getSelectedItem());
					for (Body body : bodyArrayList) {
						bodyListData.add(body);
					}
					break;
				}
			}
		});
	}

	// 0. �̸��Է¶��� �̸��� �Է��ϰ� �˻���ư�� �������� ó���ϴ� �Լ�
	private void handleBodyBtnSearchAction() {
		try {
			if (bodyStudentId != null) {
				bodyStudentId.clear();
			}
			if (bodyCmbStudentId != null) {
				bodyCmbStudentId.setItems(null);
			}
			bodyStudentId = BodyDAO.getStudentIdWhenNameSearched(bodyTxtSearch.getText());
			if (bodyStudentId == null) {
				StudentController.callAlert("�̸��˻� ���� :���� �̸��Դϴ�.");
			}
			ObservableList<String> obList = FXCollections.observableArrayList();
			for (String name : bodyStudentId) {
				obList.add(name);
			}
			bodyCmbStudentId.setItems(obList);
		} catch (Exception e) {
			StudentController.callAlert("�̸��˻� ���� :���� �̸��Դϴ�.");
		}
	}

	// 0. �߰� ��ư�� �������� ó���ϴ� �Լ�
	private void handleBodyBtnAddAction() {
		try {
			Stage addStage = new Stage(StageStyle.UTILITY);
			addStage.initOwner(bodyStage);
			addStage.setTitle("��ü�߰�â");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/bodyadd.fxml"));
			Parent root = loader.load();
			// ******************************ID���*********************************
			TextField addTxtStudentId = (TextField) root.lookup("#addTxtStudentId");
			Button addBtnStudentId = (Button) root.lookup("#addBtnStudentId");
			TextField addTxtName = (TextField) root.lookup("#addTxtName");
			TextField addTxtAge = (TextField) root.lookup("#addTxtAge");
			TextField addTxtHeight = (TextField) root.lookup("#addTxtHeight");
			TextField addTxtWeight = (TextField) root.lookup("#addTxtWeight");
			TextField addTxtMuscle = (TextField) root.lookup("#addTxtMuscle");
			TextField addTxtFat = (TextField) root.lookup("#addTxtFat");
			DatePicker addMeasureDate = (DatePicker) root.lookup("#addMeasureDate");
			Button addBtnExample = (Button) root.lookup("#addBtnExample");
			Button addBtnAdd = (Button) root.lookup("#addBtnAdd");
			Button addBtnClose = (Button) root.lookup("#addBtnClose");
			addTxtName.setEditable(false);
			addTxtAge.setEditable(false);
			// ****************************addBtnExample �̺�Ʈ*****************************
			addBtnExample.setOnAction(e -> {
				addTxtHeight.setText("163.7");
				addTxtWeight.setText("46.3");
				addTxtMuscle.setText("33.2");
				addTxtFat.setText("19.7");
			});
			// ****************************addBtnStudentId �̺�Ʈ*****************************
			// �к��߰� â���� ���̵� ��ȸ��ư�� �������� ó���Ѵ�.
			addBtnStudentId.setOnAction(e -> {
				try {
					if (bodyNameAndAge != null) {
						bodyNameAndAge.clear();
					}
					bodyNameAndAge = BodyDAO.getNameAndAgeWhenStudentIdSearched(addTxtStudentId.getText());
					addTxtName.setText(bodyNameAndAge.get(0));
					addTxtAge.setText(bodyNameAndAge.get(1));
				} catch (Exception e1) {
					StudentController.callAlert("�˸�:���� ���̵� �Դϴ�.");
				}
			});
			// *********************addTxtStudentId���� ���͸� ������ �̺�Ʈ******************
			addTxtStudentId.setOnKeyPressed(e -> {
				try {
					if (e.getCode().equals(KeyCode.ENTER)) {
						if (bodyNameAndAge != null) {
							bodyNameAndAge.clear();
						}
						bodyNameAndAge = BodyDAO.getNameAndAgeWhenStudentIdSearched(addTxtStudentId.getText());
						addTxtName.setText(bodyNameAndAge.get(0));
						addTxtAge.setText(bodyNameAndAge.get(1));
					}
				} catch (Exception e1) {
					StudentController.callAlert("�˸�:���� ���̵� �Դϴ�.");
				}
			});
			// *****************************addBtnAdd �̺�Ʈ**********************************
			addBtnAdd.setOnAction(e -> {
				Body body = new Body(addTxtStudentId.getText(), addTxtName.getText(),
						Integer.parseInt(addTxtAge.getText()), Double.parseDouble(addTxtHeight.getText()),
						Double.parseDouble(addTxtWeight.getText()), Double.parseDouble(addTxtMuscle.getText()),
						Double.parseDouble(addTxtFat.getText()), addMeasureDate.getValue().toString());
				int count = BodyDAO.insertBodyIntoBalletDB(body);
				if (count != 0) {
					bodyListData.add(body);
					StudentController.callAlert("�߰�:�߰��Ǿ����ϴ�.");
				}
				// **************************�߰�â �ʱ�ȭ******************************
//				addTxtStudentId.clear();
//				addTxtName.clear();
//				addTxtAge.clear();
//				addTxtHeight.clear();
//				addTxtWeight.clear();
//				addTxtMuscle.clear();
//				addTxtFat.clear();
//				addMeasureDate.setValue(null);
			});
			// *****************************addBtnClose�̺�Ʈ**********************************
			addBtnClose.setOnAction(e -> {
				addStage.close();
			});
			// **********************************************************************************
			Scene scene = new Scene(root);
			addStage.setResizable(false);
			addStage.setScene(scene);
			addStage.show();
		} catch (Exception e) {
			StudentController.callAlert("��ü�߰�â ���� :��ü�߰�â ������ �߻��߽��ϴ�.");
		}
	}

	// 0. ���� ��ư�� �������� ó���ϴ� �Լ�
	private void handleBodyBtnEditAction() {
		selectedBody = bodyTableView.getSelectionModel().getSelectedItem();
		selectedBodyIndex = bodyTableView.getSelectionModel().getSelectedIndex();
		try {
			Stage editStage = new Stage(StageStyle.UTILITY);
			// addStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(bodyStage);
			editStage.setTitle("��ü����â");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/bodyedit.fxml"));
			Parent root = loader.load();
			// ******************************ID���*********************************
			TextField editTxtStudentId = (TextField) root.lookup("#editTxtStudentId");
			TextField editTxtName = (TextField) root.lookup("#editTxtName");
			TextField editTxtAge = (TextField) root.lookup("#editTxtAge");
			TextField editTxtHeight = (TextField) root.lookup("#editTxtHeight");
			TextField editTxtWeight = (TextField) root.lookup("#editTxtWeight");
			TextField editTxtMuscle = (TextField) root.lookup("#editTxtMuscle");
			TextField editTxtFat = (TextField) root.lookup("#editTxtFat");
			DatePicker editMeasureDate = (DatePicker) root.lookup("#editMeasureDate");
			Button editBtnEdit = (Button) root.lookup("#editBtnEdit");
			Button editBtnClose = (Button) root.lookup("#editBtnClose");
			editTxtStudentId.setDisable(true);
			editTxtName.setDisable(true);
			editTxtAge.setDisable(true);
			// ****************************����ȭ�� �ʱ�ȭ*********************************
			editTxtStudentId.setText(selectedBody.getStudentId());
			editTxtName.setText(selectedBody.getName());
			editTxtAge.setText(String.valueOf(selectedBody.getAge()));
			editTxtHeight.setText(String.valueOf(selectedBody.getHeight()));
			editTxtWeight.setText(String.valueOf(selectedBody.getWeight()));
			editTxtMuscle.setText(String.valueOf(selectedBody.getMuscle()));
			editTxtFat.setText(String.valueOf(selectedBody.getFat()));
			editMeasureDate.setValue(LocalDate.parse(selectedBody.getMeasureDate()));
			// ***************************editBtnEdit �̺�Ʈ*********************************
			editBtnEdit.setOnAction(e -> {
				Body body = new Body(editTxtStudentId.getText(), editTxtName.getText(),
						Integer.parseInt(editTxtAge.getText()), Double.parseDouble(editTxtHeight.getText()),
						Double.parseDouble(editTxtWeight.getText()), Double.parseDouble(editTxtMuscle.getText()),
						Double.parseDouble(editTxtFat.getText()), editMeasureDate.getValue().toString());

				int count = BodyDAO.updateBodySet(body, selectedBody);
				if (count != 0) {
					bodyListData.set(selectedBodyIndex, body);
					StudentController.callAlert("����:�����Ǿ����ϴ�.");
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
			StudentController.callAlert("�˸�:������ �������ּ���.");
		}
	}

	// 0. ���� ��ư�� �������� ó���ϴ� �Լ�
	private void handleBodyBtnDeleteAction() {
		try {
			selectedBody = bodyTableView.getSelectionModel().getSelectedItem();
			int count = BodyDAO.deleteBodyFromBalletDB(selectedBody.getStudentId(), selectedBody.getMeasureDate());
			if (count != 0) {
				bodyListData.remove(selectedBody);
				bodyArrayList.remove(selectedBody);
				StudentController.callAlert("����:�����Ǿ����ϴ�.");
			} else {
				return;
			}
		} catch (Exception e) {
			StudentController.callAlert("�˸�:���� �� �������ּ���.");
		}

	}

	// 0. ü�ߺ� ��ư�� �������� ó���ϴ� �Լ�
	private void handleBodyBtnWeightAction() {
		selectedBody = bodyTableView.getSelectionModel().getSelectedItem();
		if (selectedBody != null) {
			try {
				Stage choiceStage = new Stage();
				choiceStage.initOwner(bodyStage);
				choiceStage.setTitle("�� ����â");
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/monthChoice.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);
				choiceStage.setResizable(false);
				choiceStage.setScene(scene);
				choiceStage.show();
				// ******************************ID���*********************************
				DatePicker choiceStart = (DatePicker) root.lookup("#choiceStart");
				DatePicker choiceEnd = (DatePicker) root.lookup("#choiceEnd");
				Button choiceBtnChart = (Button) root.lookup("#choiceBtnChart");
				// **************************choiceBtnChart �̺�Ʈ*********************
				choiceBtnChart.setOnAction(e -> {
					if (bodySelectedWeight != null) {
						bodySelectedWeight.clear();
					}
					try {
						Stage scatterStage = new Stage();
						scatterStage.initOwner(choiceStage);
						scatterStage.setTitle(selectedBody.getName() + "�� ���� ü�ߺ�");
						FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../View/bodyScatterWeight.fxml"));
						Parent root1 = loader1.load();
						// ID���
						ScatterChart bodyScatterWeight = (ScatterChart) root1.lookup("#bodyScatterWeight");
						Button bodyScatterWeightClose = (Button) root1.lookup("#bodyScatterWeightClose");
						ObservableList<XYChart.Data<String, Double>> obList1 = FXCollections.observableArrayList();

						XYChart.Series<String, Double> series1 = new XYChart.Series<String, Double>();
						series1.setName(selectedBody.getName() + "�� ü��(kg)");

						bodySelectedWeight = BodyDAO.getSelectedWeight(choiceStart.getValue().toString(),
								choiceEnd.getValue().toString(), selectedBody.getStudentId());

						Set<String> set = bodySelectedWeight.keySet();
						Iterator<String> iterator = set.iterator();
						while (iterator.hasNext()) {
							String month = iterator.next();
							Double weight = bodySelectedWeight.get(month);
							obList1.add(new XYChart.Data<String, Double>(month, weight));
						}

						series1.setData(obList1);
						bodyScatterWeight.setAnimated(false);
						bodyScatterWeight.getData().add(series1);
						Scene scene1 = new Scene(root1);
						scatterStage.setScene(scene1);
						scatterStage.show();

						bodyScatterWeightClose.setOnAction(e1 -> {
							scatterStage.close();
						});
					} catch (Exception e1) {
//						StudentController.callAlert("�˸�:ü�ߺ�â ����");
					}
				});

			} catch (Exception e) {
				StudentController.callAlert("�˸�:�л������� �����ֽñ� �ٶ��ϴ�.");
			}
		} else {
			StudentController.callAlert("�˸�:�л������� �����ֽñ� �ٶ��ϴ�.");
		}

	}

	// 0. ������ �� ��ư�� �������� ó���ϴ� �Լ�
	private void handleBodyBtnMuscleAction() {
		selectedBody = bodyTableView.getSelectionModel().getSelectedItem();
		if (selectedBody != null) {
			try {
				Stage choiceStage = new Stage();
				choiceStage.initOwner(bodyStage);
				choiceStage.setTitle("�� ����â");
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/monthChoice.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);
				choiceStage.setResizable(false);
				choiceStage.setScene(scene);
				choiceStage.show();
				// ******************************ID���*********************************
				DatePicker choiceStart = (DatePicker) root.lookup("#choiceStart");
				DatePicker choiceEnd = (DatePicker) root.lookup("#choiceEnd");
				Button choiceBtnChart = (Button) root.lookup("#choiceBtnChart");
				// **************************choiceBtnChart �̺�Ʈ*********************
				choiceBtnChart.setOnAction(e -> {
					if (bodySelectedMuscle != null) {
						bodySelectedMuscle.clear();
					}
					try {
						Stage lineStage = new Stage();
						lineStage.initOwner(choiceStage);
						lineStage.setTitle(selectedBody.getName() + "�� ���� ������ ��");
						FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../View/bodyLineMuscle.fxml"));
						Parent root1 = loader1.load();
						// ID���
						LineChart bodyLineMuscle = (LineChart) root1.lookup("#bodyLineMuscle");
						Button bodyLineMuscleClose = (Button) root1.lookup("#bodyLineMuscleClose");
						ObservableList<XYChart.Data<String, Double>> obList1 = FXCollections.observableArrayList();

						XYChart.Series<String, Double> series1 = new XYChart.Series<String, Double>();
						series1.setName(selectedBody.getName() + "�� ������(%)");

						bodySelectedMuscle = BodyDAO.getSelectedMuscle(choiceStart.getValue().toString(),
								choiceEnd.getValue().toString(), selectedBody.getStudentId());

						Set<String> set = bodySelectedMuscle.keySet();
						Iterator<String> iterator = set.iterator();
						while (iterator.hasNext()) {
							String month = iterator.next();
							Double muscle = bodySelectedMuscle.get(month);
							obList1.add(new XYChart.Data<String, Double>(month, muscle));
						}

						series1.setData(obList1);
						bodyLineMuscle.setAnimated(false);
						bodyLineMuscle.getData().add(series1);
						Scene scene1 = new Scene(root1);
						lineStage.setScene(scene1);
						lineStage.show();

						bodyLineMuscleClose.setOnAction(e1 -> {
							lineStage.close();
						});
					} catch (Exception e1) {
//						StudentController.callAlert("�˸�:������ ��â ����");
					}
				});

			} catch (Exception e) {
				StudentController.callAlert("�˸�:�л������� �����ֽñ� �ٶ��ϴ�.");
			}
		} else {
			StudentController.callAlert("�˸�:�л������� �����ֽñ� �ٶ��ϴ�.");
		}

	}

	// 0. ü����� �� ��ư�� �������� ó���ϴ� �Լ�
	private void handleBodyBtnFatAction() {
		selectedBody = bodyTableView.getSelectionModel().getSelectedItem();
		if (selectedBody != null) {
			try {
				Stage choiceStage = new Stage();
				choiceStage.initOwner(bodyStage);
				choiceStage.setTitle("�� ����â");
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/monthChoice.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);
				choiceStage.setResizable(false);
				choiceStage.setScene(scene);
				choiceStage.show();
				// ******************************ID���*********************************
				DatePicker choiceStart = (DatePicker) root.lookup("#choiceStart");
				DatePicker choiceEnd = (DatePicker) root.lookup("#choiceEnd");
				Button choiceBtnChart = (Button) root.lookup("#choiceBtnChart");
				// **************************choiceBtnChart �̺�Ʈ*********************
				choiceBtnChart.setOnAction(e -> {
					if (bodySelectedFat != null) {
						bodySelectedFat.clear();
					}
					try {
						Stage areaStage = new Stage();
						areaStage.initOwner(choiceStage);
						areaStage.setTitle(selectedBody.getName() + "�� ���� ü����� ��");
						FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../View/bodyAreaFat.fxml"));
						Parent root1 = loader1.load();
						// ID���
						AreaChart bodyAreaFat = (AreaChart) root1.lookup("#bodyAreaFat");
						Button bodyAreaFatClose = (Button) root1.lookup("#bodyAreaFatClose");
						ObservableList<XYChart.Data<String, Double>> obList1 = FXCollections.observableArrayList();

						XYChart.Series<String, Double> series1 = new XYChart.Series<String, Double>();
						series1.setName(selectedBody.getName() + "�� ü�����(%)");

						bodySelectedFat = BodyDAO.getSelectedFat(choiceStart.getValue().toString(),
								choiceEnd.getValue().toString(), selectedBody.getStudentId());

						Set<String> set = bodySelectedFat.keySet();
						Iterator<String> iterator = set.iterator();
						while (iterator.hasNext()) {
							String month = iterator.next();
							Double fat = bodySelectedFat.get(month);
							obList1.add(new XYChart.Data<String, Double>(month, fat));
						}

						series1.setData(obList1);
						bodyAreaFat.setAnimated(false);
						bodyAreaFat.getData().add(series1);
						Scene scene1 = new Scene(root1);
						areaStage.setScene(scene1);
						areaStage.show();

						bodyAreaFatClose.setOnAction(e1 -> {
							areaStage.close();
						});
					} catch (Exception e1) {
//						StudentController.callAlert("�˸�:������ ��â ����");
					}
				});

			} catch (Exception e) {
				StudentController.callAlert("�˸�:�л������� �����ֽñ� �ٶ��ϴ�.");
			}
		} else {
			StudentController.callAlert("�˸�:�л������� �����ֽñ� �ٶ��ϴ�.");
		}

	}

	// 0. �ڷΰ��� ��ư�� �������� ó���ϴ� �Լ�
	private void handleBodyBtnBackAction() {
		try {
			if (bodyArrayList != null) {
				bodyArrayList.clear();
			}
			if (bodyListData != null) {
				bodyListData.clear();
			}
			if (bodyCmbStudentId != null) {
				bodyCmbStudentId.setItems(null);
			}
			if (bodyStudentId != null) {
				bodyStudentId.clear();
			}
			bodyListData.clear();
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
			bodyStage.close();
			choiceStage.show();
		} catch (Exception e) {
		}
	}
}
