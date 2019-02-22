package Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import Model.Tuition;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TuitionController implements Initializable {
	public Stage tuitionStage;
	@FXML
	private TextField tuiTxtSearch;
	@FXML
	private TextField tuiTxtSum;
	@FXML
	private Button tuiBtnSearch;
	@FXML
	private Button tuiBtnAdd;
	@FXML
	private Button tuiBtnEdit;
	@FXML
	private Button tuiBtnDelete;
	@FXML
	private Button tuiBtnLineSum;
	@FXML
	private Button tuiBtnScatterOprice;
	@FXML
	private Button tuiBtnAreaIprice;
	@FXML
	private Button tuiBtnSelectedTotal;
	@FXML
	private Button tuiBtnBarTotal;
	@FXML
	private Button tuiBtnPiePIOsum;
	@FXML
	private Button tuiBtnBack;
	@FXML
	private Button tuiBtnClose;
	@FXML
	private ComboBox<String> tuiCmbPayDate;
	@FXML
	private TableView<Tuition> tuiTableView;

	private ObservableList<Tuition> tuiListData = FXCollections.observableArrayList();
	private ObservableList<String> tuiPayDateList = FXCollections.observableArrayList();
	private ObservableList<String> tuiIndividualList = FXCollections.observableArrayList();
	private ObservableList<String> tuiOpusList = FXCollections.observableArrayList();
	ArrayList<Tuition> tuiArrayList = new ArrayList<>();
	ArrayList<String> tuiNameAndParty = new ArrayList<>();
	TreeMap<String, Integer> tuiSum = new TreeMap<>();
	LinkedHashMap<String, Integer> tuiOprice = new LinkedHashMap<>();
	TreeMap<String, Integer> tuiIprice = new TreeMap<>();
	LinkedHashMap<String, Integer> tuiTotal = new LinkedHashMap<>();

	ArrayList<Integer> tuiEntireIprice;
	ArrayList<Integer> tuiSelectedTotal;

	int monthlySum;

	private Tuition selectedTuition;
	private int selectedTuitionIndex;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 0.���̺�� ����
		tuiTableViewSet();

		// 0.�޺��ڽ�(���ο�) ����
		tuiPayDateList.addAll("2019�� 2��","2019�� 1��", "2018�� 12��", "2018�� 11��", "2018�� 10��", "2018�� 9��", "2018�� 8��", "2018�� 7��",
				"2018�� 6��", "2018�� 5��", "2018�� 4��", "2018�� 3��", "2018�� 2��", "2018�� 1��");
		tuiCmbPayDate.setItems(tuiPayDateList);

		// 0.�޺��ڽ�(���η��� Ƚ��) ����
		tuiIndividualList.addAll("0ȸ", "1ȸ", "2ȸ", "3ȸ", "4ȸ", "5ȸ", "6ȸ");

		// 0.�޺��ڽ�(��ǰ��) ����
		tuiOpusList.addAll("��ǰ�̼���", "���ڸ�����", "������ȣ��", "�Ķ���", "����ǵ�", "��پߵ���", "�Ķ���ǵ�", "���̸��", "����", "ȣ�α������", "������",
				"���縮��", "��ŰŸ", "�Ҹ����Ƶ�", "�Ǻ��", "ī����", "�������̵�", "������ڵ�", "��������", "��Űȣ��", "Ż������", "ī�Ḯ��", "ŰƮ��", "�����޶���");

		// 0. �˻��� �������� ó���ϴ� �Լ�
		tuiBtnSearch.setOnAction(e -> {
			handleTuiBtnSearchAction();
		});

		// 0. �˻��ؽ�Ʈ �ʵ忡�� ���͸� �������� ó���ϴ� �Լ�
		tuiTxtSearch.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				handleTuiBtnSearchAction();
			}
		});

		// 0. �к��߰� ��ư�� �������� ó���ϴ� �Լ�
		tuiBtnAdd.setOnAction(e -> {
			handleTuiBtnAddAction();
		});

		// 0. �к���� ��ư�� �������� ó���ϴ� �Լ�
		tuiBtnEdit.setOnAction(e -> {
			handleTuiBtnEditAction();
		});

		// 0. �к���� ��ư�� �������� ó���ϴ� �Լ�
		tuiBtnDelete.setOnAction(e -> {
			handleTuiBtnDeleteAction();
		});

		// 0. �����Ѽ����� �� ��ư�� �������� ó���ϴ� �Լ�
		tuiBtnLineSum.setOnAction(e -> {
			handleTuiBtnLineSumAction();
		});

		// 0. ��������ǰ�� �� ��ư�� �������� ó���ϴ� �Լ�
		tuiBtnScatterOprice.setOnAction(e -> {
			handleTuiBtnScatterOpriceAction();
		});

		// 0. �����Ѱ��η����� �� ��ư�� �������� ó���ϴ� �Լ�
		tuiBtnAreaIprice.setOnAction(e -> {
			handleTuiBtnAreaIpriceAction();
		});

		// 0. ���������л��Ѽ����� ��ư�� �������� ó���ϴ� �Լ�
		tuiBtnSelectedTotal.setOnAction(e -> {
			handleTuiBtnSelectedTotalAction();
		});

		// 0. ����л����Ѽ����� ��ư�� �������� ó���ϴ� �Լ�
		tuiBtnBarTotal.setOnAction(e -> {
			handleTuiBtnBarTotalAction();
		});

		// 0. ����ѷ������ ��ư�� �������� ó���ϴ� �Լ�
		tuiBtnPiePIOsum.setOnAction(e -> {
			handleTuiBtnPiePIOsumAction();
		});
		// 0. ���̺�並 �ι�Ŭ�� ������ ó���ϴ� �Լ�
		tuiTableView.setOnMouseClicked(e -> {
			handleTuiTableViewDoubleClickedAction(e);
		});
		// 0. �ڷΰ��� ��ư�� �������� ó���ϴ� �Լ�
		tuiBtnBack.setOnAction(e -> {
			handleTuiBtnBackAction();
		});

		// 0. �����ư�� �������� ó���ϴ� �Լ�
		tuiBtnClose.setOnAction(e -> {
			tuitionStage.close();
		});
	}

	// 0.���̺�� ����
	private void tuiTableViewSet() {
		TableColumn tcStudentId = tuiTableView.getColumns().get(0);
		tcStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
		tcStudentId.setStyle("-fx-alignment: CENTER");
		TableColumn tcName = tuiTableView.getColumns().get(1);
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcName.setStyle("-fx-alignment: CENTER");
		TableColumn tcParty = tuiTableView.getColumns().get(2);
		tcParty.setCellValueFactory(new PropertyValueFactory<>("party"));
		tcParty.setStyle("-fx-alignment: CENTER");
		TableColumn tcIndividual = tuiTableView.getColumns().get(3);
		tcIndividual.setCellValueFactory(new PropertyValueFactory<>("individual"));
		tcIndividual.setStyle("-fx-alignment: CENTER");
		TableColumn tcOpus = tuiTableView.getColumns().get(4);
		tcOpus.setCellValueFactory(new PropertyValueFactory<>("opus"));
		tcOpus.setStyle("-fx-alignment: CENTER");
		TableColumn tcPprice = tuiTableView.getColumns().get(5);
		tcPprice.setCellValueFactory(new PropertyValueFactory<>("pprice"));
		tcPprice.setStyle("-fx-alignment: CENTER");
		TableColumn tcIprice = tuiTableView.getColumns().get(6);
		tcIprice.setCellValueFactory(new PropertyValueFactory<>("iprice"));
		tcIprice.setStyle("-fx-alignment: CENTER");
		TableColumn tcOprice = tuiTableView.getColumns().get(7);
		tcOprice.setCellValueFactory(new PropertyValueFactory<>("oprice"));
		tcOprice.setStyle("-fx-alignment: CENTER");
		TableColumn tcTotal = tuiTableView.getColumns().get(8);
		tcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		tcTotal.setStyle("-fx-alignment: CENTER");
		TableColumn tcPayDate = tuiTableView.getColumns().get(9);
		tcPayDate.setCellValueFactory(new PropertyValueFactory<>("payDate"));
		tcPayDate.setStyle("-fx-alignment: CENTER");
		tuiTableView.setItems(tuiListData);

		// �޺��ڽ�(���ο�) ���ÿ� ���� �����ͺ��̽����� �к������ ���� �����´�.
		tuiCmbPayDate.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				switch (newValue) {
				case "2019�� 2��":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.get2019FebruaryTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2019�� 1��":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.get2019JanuaryTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018�� 12��":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getDecemberTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018�� 11��":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getNovemberTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018�� 10��":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getOctoberTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018�� 9��":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getSeptemberTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018�� 8��":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getAugustTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018�� 7��":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getJulyTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018�� 6��":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getJuneTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018�� 5��":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getMayTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018�� 4��":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getAprilTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018�� 3��":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getMarchTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018�� 2��":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getFebruaryTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018�� 1��":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getJanuaryTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				}
			}
		});
	}

	// 0. �к��߰� ��ư�� �������� ó���ϴ� �Լ�
	private void handleTuiBtnAddAction() {
		try {
			Stage addStage = new Stage(StageStyle.UTILITY);
			// addStage.initModality(Modality.WINDOW_MODAL);
			addStage.initOwner(tuitionStage);
			addStage.setTitle("�к��߰�â");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/tuitionadd.fxml"));
			Parent root = loader.load();
			// ******************************ID���*********************************
			TextField addTxtStudentId = (TextField) root.lookup("#addTxtStudentId");
			Button addBtnStudentId = (Button) root.lookup("#addBtnStudentId");
			TextField addTxtName = (TextField) root.lookup("#addTxtName");
			TextField addTxtParty = (TextField) root.lookup("#addTxtParty");
			ComboBox<String> addCmbIndividual = (ComboBox) root.lookup("#addCmbIndividual");
			addCmbIndividual.setItems(tuiIndividualList);
			ComboBox<String> addCmbOpus = (ComboBox) root.lookup("#addCmbOpus");
			addCmbOpus.setItems(tuiOpusList);
			TextField addTxtPprice = (TextField) root.lookup("#addTxtPprice");
			TextField addTxtIprice = (TextField) root.lookup("#addTxtIprice");
			TextField addTxtOprice = (TextField) root.lookup("#addTxtOprice");
			TextField addTxtTotal = (TextField) root.lookup("#addTxtTotal");
			Button addBtnTotal = (Button) root.lookup("#addBtnTotal");
			DatePicker addPayDate = (DatePicker) root.lookup("#addPayDate");
			Button addBtnAdd = (Button) root.lookup("#addBtnAdd");
			Button addBtnClose = (Button) root.lookup("#addBtnClose");
			addTxtPprice.setEditable(false);
			addTxtIprice.setEditable(false);
			addTxtOprice.setEditable(false);
			addTxtTotal.setEditable(false);
			// ****************************addBtnStudentId �̺�Ʈ*****************************
			// �к��߰� â���� ���̵� ��ȸ��ư�� �������� ó���Ѵ�.
			addBtnStudentId.setOnAction(e -> {
				try {
					if (tuiNameAndParty != null) {
						tuiNameAndParty.clear();
					}
					tuiNameAndParty = TuitionDAO.getNameAndPartyWhenStudentIdSearched(addTxtStudentId.getText());
					addTxtName.setText(tuiNameAndParty.get(0));
					addTxtParty.setText(tuiNameAndParty.get(1));
				} catch (Exception e1) {
					StudentController.callAlert("�˸�:���� ���̵� �Դϴ�.");
				}
			});
			// *********************addTxtStudentId���� ���͸� ������ �̺�Ʈ******************
			addTxtStudentId.setOnKeyPressed(e -> {
				try {
					if (e.getCode().equals(KeyCode.ENTER)) {
						if (tuiNameAndParty != null) {
							tuiNameAndParty.clear();
						}
						tuiNameAndParty = TuitionDAO.getNameAndPartyWhenStudentIdSearched(addTxtStudentId.getText());
						addTxtName.setText(tuiNameAndParty.get(0));
						addTxtParty.setText(tuiNameAndParty.get(1));
					}
				} catch (Exception e1) {
					StudentController.callAlert("�˸�:���� ���̵� �Դϴ�.");
				}
			});
			// *****************************�л��߰�â �к���**********************************
			// �׷췹���� ����
			addTxtParty.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					switch (newValue) {
					case "�������":
						addTxtPprice.setText("160000");
						break;
					case "�ߵ�����":
						addTxtPprice.setText("130000");
						break;
					case "�ʵ�����":
						addTxtPprice.setText("100000");
						break;
					}
				}
			});
			// ���η����� ����
			addCmbIndividual.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (addTxtParty.getText().equals("�������")) {
						switch (newValue) {
						case "0ȸ":
							addTxtIprice.setText("0");
							break;
						case "1ȸ":
							addTxtIprice.setText("40000");
							break;
						case "2ȸ":
							addTxtIprice.setText("80000");
							break;
						case "3ȸ":
							addTxtIprice.setText("120000");
							break;
						case "4ȸ":
							addTxtIprice.setText("160000");
							break;
						case "5ȸ":
							addTxtIprice.setText("200000");
							break;
						case "6ȸ":
							addTxtIprice.setText("240000");
							break;
						}
					}
					if (addTxtParty.getText().equals("�ߵ�����")) {
						switch (newValue) {
						case "0ȸ":
							addTxtIprice.setText("0");
							break;
						case "1ȸ":
							addTxtIprice.setText("30000");
							break;
						case "2ȸ":
							addTxtIprice.setText("60000");
							break;
						case "3ȸ":
							addTxtIprice.setText("90000");
							break;
						case "4ȸ":
							addTxtIprice.setText("120000");
							break;
						case "5ȸ":
							addTxtIprice.setText("150000");
							break;
						case "6ȸ":
							addTxtIprice.setText("180000");
							break;
						}
					}
					if (addTxtParty.getText().equals("�ʵ�����")) {
						switch (newValue) {
						case "0ȸ":
							addTxtIprice.setText("0");
							break;
						case "1ȸ":
							addTxtIprice.setText("20000");
							break;
						case "2ȸ":
							addTxtIprice.setText("40000");
							break;
						case "3ȸ":
							addTxtIprice.setText("60000");
							break;
						case "4ȸ":
							addTxtIprice.setText("80000");
							break;
						case "5ȸ":
							addTxtIprice.setText("100000");
							break;
						case "6ȸ":
							addTxtIprice.setText("120000");
							break;
						}
					}
				}
			});
			// ��ǰ�� ����
			addCmbOpus.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					switch (newValue) {
					case "��ǰ�̼���":
						addTxtOprice.setText("0");
						break;
					case "���ڸ�����":
						addTxtOprice.setText("48000");
						break;
					case "������ȣ��":
						addTxtOprice.setText("49000");
						break;
					case "�Ķ���":
						addTxtOprice.setText("50000");
						break;
					case "����ǵ�":
						addTxtOprice.setText("51000");
						break;
					case "��پߵ���":
						addTxtOprice.setText("52000");
						break;
					case "�Ķ���ǵ�":
						addTxtOprice.setText("53000");
						break;
					case "���̸��":
						addTxtOprice.setText("54000");
						break;
					case "����":
						addTxtOprice.setText("55000");
						break;
					case "ȣ�α������":
						addTxtOprice.setText("56000");
						break;
					case "������":
						addTxtOprice.setText("57000");
						break;
					case "���縮��":
						addTxtOprice.setText("58000");
						break;
					case "��ŰŸ":
						addTxtOprice.setText("59000");
						break;
					case "�Ҹ����Ƶ�":
						addTxtOprice.setText("60000");
						break;
					case "�Ǻ��":
						addTxtOprice.setText("61000");
						break;
					case "ī����":
						addTxtOprice.setText("62000");
						break;
					case "�������̵�":
						addTxtOprice.setText("63000");
						break;
					case "������ڵ�":
						addTxtOprice.setText("64000");
						break;
					case "��������":
						addTxtOprice.setText("65000");
						break;
					case "��Űȣ��":
						addTxtOprice.setText("66000");
						break;
					case "Ż������":
						addTxtOprice.setText("67000");
						break;
					case "ī�Ḯ��":
						addTxtOprice.setText("68000");
						break;
					case "ŰƮ��":
						addTxtOprice.setText("69000");
						break;
					case "�����޶���":
						addTxtOprice.setText("70000");
						break;
					}
				}
			});
			// �Ѽ����� ���� => addBtnTotal �̺�Ʈ
			addBtnTotal.setOnAction(e -> {
				addTxtTotal.setText(String.valueOf(Integer.parseInt(addTxtPprice.getText())
						+ Integer.parseInt(addTxtIprice.getText()) + Integer.parseInt(addTxtOprice.getText())));
			});

			// ****************************addBtnAdd �̺�Ʈ*****************************
			addBtnAdd.setOnAction(e -> {
				Tuition tuition = new Tuition(addTxtStudentId.getText(), addTxtName.getText(), addTxtParty.getText(),
						addCmbIndividual.getSelectionModel().getSelectedItem(),
						addCmbOpus.getSelectionModel().getSelectedItem(), Integer.parseInt(addTxtPprice.getText()),
						Integer.parseInt(addTxtIprice.getText()), Integer.parseInt(addTxtOprice.getText()),
						Integer.parseInt(addTxtTotal.getText()), addPayDate.getValue().toString());

				int count = TuitionDAO.insertTuitionIntoBalletDB(tuition);
				if (count != 0) {
					tuiListData.add(tuition);
					StudentController.callAlert("�߰�:�߰��Ǿ����ϴ�.");
				}
				// **************************�߰�â �ʱ�ȭ******************************
//				addTxtStudentId.clear();
//				addTxtName.clear();
//				addTxtParty.clear();
//				addCmbIndividual.getSelectionModel().clearSelection();
//				addCmbOpus.getSelectionModel().clearSelection();
//				addTxtPprice.clear();
//				addTxtIprice.clear();
//				addTxtOprice.clear();
//				addTxtTotal.clear();
//				addPayDate.setValue(null);
			});
			// ****************************addBtnClose �̺�Ʈ***************************
			addBtnClose.setOnAction(e -> {
				addStage.close();
			});
			// ***************************************************************************
			Scene scene = new Scene(root);
			addStage.setResizable(false);
			addStage.setScene(scene);
			addStage.show();
		} catch (Exception e) {
			StudentController.callAlert("�к��߰�â ���� :�к��߰�â ������ �߻��߽��ϴ�.");
		}

	}

	// 0. �к���� ��ư�� �������� ó���ϴ� �Լ�
	private void handleTuiBtnEditAction() {
		selectedTuition = tuiTableView.getSelectionModel().getSelectedItem();
		selectedTuitionIndex = tuiTableView.getSelectionModel().getSelectedIndex();
		try {
			Stage editStage = new Stage(StageStyle.UTILITY);
			// addStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(tuitionStage);
			editStage.setTitle("�к����â");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/tuitionedit.fxml"));
			Parent root = loader.load();
			// ******************************ID���*********************************
			TextField editTxtStudentId = (TextField) root.lookup("#editTxtStudentId");
			TextField editTxtName = (TextField) root.lookup("#editTxtName");
			TextField editTxtParty = (TextField) root.lookup("#editTxtParty");
			ComboBox<String> editCmbIndividual = (ComboBox) root.lookup("#editCmbIndividual");
			editCmbIndividual.setItems(tuiIndividualList);
			ComboBox<String> editCmbOpus = (ComboBox) root.lookup("#editCmbOpus");
			editCmbOpus.setItems(tuiOpusList);
			TextField editTxtPprice = (TextField) root.lookup("#editTxtPprice");
			TextField editTxtIprice = (TextField) root.lookup("#editTxtIprice");
			TextField editTxtOprice = (TextField) root.lookup("#editTxtOprice");
			TextField editTxtTotal = (TextField) root.lookup("#editTxtTotal");
			Button editBtnTotal = (Button) root.lookup("#editBtnTotal");
			DatePicker editPayDate = (DatePicker) root.lookup("#editPayDate");
			Button editBtnEdit = (Button) root.lookup("#editBtnEdit");
			Button editBtnClose = (Button) root.lookup("#editBtnClose");
			editTxtStudentId.setDisable(true);
			editTxtName.setDisable(true);
			editTxtParty.setDisable(true);
//			editTxtStudentId.setEditable(false);
//			editTxtName.setEditable(false);
//			editTxtParty.setEditable(false);
			editTxtPprice.setEditable(false);
			editTxtIprice.setEditable(false);
			editTxtOprice.setEditable(false);
			editTxtTotal.setEditable(false);
			// ****************************����ȭ�� �ʱ�ȭ*********************************
			editTxtStudentId.setText(selectedTuition.getStudentId());
			editTxtName.setText(selectedTuition.getName());
			editTxtParty.setText(selectedTuition.getParty());
			editCmbIndividual.getSelectionModel().select(selectedTuition.getIndividual());
			editCmbOpus.getSelectionModel().select(selectedTuition.getOpus());
			editTxtPprice.setText(String.valueOf(selectedTuition.getPprice()));
			editTxtIprice.setText(String.valueOf(selectedTuition.getIprice()));
			editTxtOprice.setText(String.valueOf(selectedTuition.getOprice()));
			editTxtTotal.setText(String.valueOf(selectedTuition.getTotal()));
			editPayDate.setValue(LocalDate.parse(selectedTuition.getPayDate()));
			// *****************************�л�����â �к���**********************************
			// �׷췹���� ����
			editTxtParty.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					switch (newValue) {
					case "�������":
						editTxtPprice.setText("160000");
						break;
					case "�ߵ�����":
						editTxtPprice.setText("130000");
						break;
					case "�ʵ�����":
						editTxtPprice.setText("100000");
						break;
					}
				}
			});
			// ���η����� ����
			editCmbIndividual.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (editTxtParty.getText().equals("�������")) {
						switch (newValue) {
						case "0ȸ":
							editTxtIprice.setText("0");
							break;
						case "1ȸ":
							editTxtIprice.setText("40000");
							break;
						case "2ȸ":
							editTxtIprice.setText("80000");
							break;
						case "3ȸ":
							editTxtIprice.setText("120000");
							break;
						case "4ȸ":
							editTxtIprice.setText("160000");
							break;
						case "5ȸ":
							editTxtIprice.setText("200000");
							break;
						case "6ȸ":
							editTxtIprice.setText("240000");
							break;
						}
					}
					if (editTxtParty.getText().equals("�ߵ�����")) {
						switch (newValue) {
						case "0ȸ":
							editTxtIprice.setText("0");
							break;
						case "1ȸ":
							editTxtIprice.setText("30000");
							break;
						case "2ȸ":
							editTxtIprice.setText("60000");
							break;
						case "3ȸ":
							editTxtIprice.setText("90000");
							break;
						case "4ȸ":
							editTxtIprice.setText("120000");
							break;
						case "5ȸ":
							editTxtIprice.setText("150000");
							break;
						case "6ȸ":
							editTxtIprice.setText("180000");
							break;
						}
					}
					if (editTxtParty.getText().equals("�ʵ�����")) {
						switch (newValue) {
						case "0ȸ":
							editTxtIprice.setText("0");
							break;
						case "1ȸ":
							editTxtIprice.setText("20000");
							break;
						case "2ȸ":
							editTxtIprice.setText("40000");
							break;
						case "3ȸ":
							editTxtIprice.setText("60000");
							break;
						case "4ȸ":
							editTxtIprice.setText("80000");
							break;
						case "5ȸ":
							editTxtIprice.setText("100000");
							break;
						case "6ȸ":
							editTxtIprice.setText("120000");
							break;
						}
					}
				}
			});
			// ��ǰ�� ����
			editCmbOpus.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					switch (newValue) {
					case "��ǰ�̼���":
						editTxtOprice.setText("0");
						break;
					case "���ڸ�����":
						editTxtOprice.setText("48000");
						break;
					case "������ȣ��":
						editTxtOprice.setText("49000");
						break;
					case "�Ķ���":
						editTxtOprice.setText("50000");
						break;
					case "����ǵ�":
						editTxtOprice.setText("51000");
						break;
					case "��پߵ���":
						editTxtOprice.setText("52000");
						break;
					case "�Ķ���ǵ�":
						editTxtOprice.setText("53000");
						break;
					case "���̸��":
						editTxtOprice.setText("54000");
						break;
					case "����":
						editTxtOprice.setText("55000");
						break;
					case "ȣ�α������":
						editTxtOprice.setText("56000");
						break;
					case "������":
						editTxtOprice.setText("57000");
						break;
					case "���縮��":
						editTxtOprice.setText("58000");
						break;
					case "��ŰŸ":
						editTxtOprice.setText("59000");
						break;
					case "�Ҹ����Ƶ�":
						editTxtOprice.setText("60000");
						break;
					case "�Ǻ��":
						editTxtOprice.setText("61000");
						break;
					case "ī����":
						editTxtOprice.setText("62000");
						break;
					case "�������̵�":
						editTxtOprice.setText("63000");
						break;
					case "������ڵ�":
						editTxtOprice.setText("64000");
						break;
					case "��������":
						editTxtOprice.setText("65000");
						break;
					case "��Űȣ��":
						editTxtOprice.setText("66000");
						break;
					case "Ż������":
						editTxtOprice.setText("67000");
						break;
					case "ī�Ḯ��":
						editTxtOprice.setText("68000");
						break;
					case "ŰƮ��":
						editTxtOprice.setText("69000");
						break;
					case "�����޶���":
						editTxtOprice.setText("70000");
						break;
					}
				}
			});
			// �Ѽ����� ���� => editBtnTotal �̺�Ʈ
			editBtnTotal.setOnAction(e -> {
				editTxtTotal.setText(String.valueOf(Integer.parseInt(editTxtPprice.getText())
						+ Integer.parseInt(editTxtIprice.getText()) + Integer.parseInt(editTxtOprice.getText())));
			});

			// ****************************editBtnEdit �̺�Ʈ*****************************
			editBtnEdit.setOnAction(e -> {
				Tuition tuition = new Tuition(editTxtStudentId.getText(), editTxtName.getText(), editTxtParty.getText(),
						editCmbIndividual.getSelectionModel().getSelectedItem(),
						editCmbOpus.getSelectionModel().getSelectedItem(), Integer.parseInt(editTxtPprice.getText()),
						Integer.parseInt(editTxtIprice.getText()), Integer.parseInt(editTxtOprice.getText()),
						Integer.parseInt(editTxtTotal.getText()), editPayDate.getValue().toString());

				int count = TuitionDAO.updateTuitionSet(tuition, selectedTuition);
				if (count != 0) {
					tuiListData.set(selectedTuitionIndex, tuition);
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
			StudentController.callAlert("�к���� ���� :������ �������ּ���.");
		}

	}

	// 0. �к���� ��ư�� �������� ó���ϴ� �Լ�
	private void handleTuiBtnDeleteAction() {
		try {
			selectedTuition = tuiTableView.getSelectionModel().getSelectedItem();
			int count = TuitionDAO.deleteTuitionFromBalletDB(selectedTuition.getStudentId(),
					selectedTuition.getPayDate());
			if (count != 0) {
				tuiListData.remove(selectedTuition);
				tuiArrayList.remove(selectedTuition);
				StudentController.callAlert("����:�����Ǿ����ϴ�.");
			} else {
				return;
			}
		} catch (Exception e) {
			StudentController.callAlert("�к���� ����:���� �� �������ּ���.");
		}
	}

	// 0. �����Ѽ����� �� ��ư�� �������� ó���ϴ� �Լ�
	private void handleTuiBtnLineSumAction() {
		try {
			Stage choiceStage = new Stage();
			choiceStage.initOwner(tuitionStage);
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
				if (tuiSum != null) {
					tuiSum.clear();
				}
				try {
					Stage lineStage = new Stage();
					lineStage.initOwner(choiceStage);
					lineStage.setTitle("���� �Ѽ����� ��");
					FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../View/tuitionLineSum.fxml"));
					Parent root1 = loader1.load();
					// ID���
					LineChart tuiLineSum = (LineChart) root1.lookup("#tuiLineSum");
					Button tuiLineSumClose = (Button) root1.lookup("#tuiLineSumClose");
					ObservableList<XYChart.Data<String, Integer>> obList1 = FXCollections.observableArrayList();

					XYChart.Series<String, Integer> series1 = new XYChart.Series<String, Integer>();
					series1.setName("�Ѽ�����");

					tuiSum = TuitionDAO.getSelectedSum(choiceStart.getValue().toString(),
							choiceEnd.getValue().toString());

					Set<String> set = tuiSum.keySet();
					Iterator<String> iterator = set.iterator();
					while (iterator.hasNext()) {
						String month = iterator.next();
						Integer sum = tuiSum.get(month);
						obList1.add(new XYChart.Data<String, Integer>(month, sum));
					}

					series1.setData(obList1);
					tuiLineSum.setAnimated(false);
					tuiLineSum.getData().add(series1);
					Scene scene1 = new Scene(root1);
					lineStage.setScene(scene1);
					lineStage.show();

					tuiLineSumClose.setOnAction(e1 -> {
						lineStage.close();
					});

				} catch (Exception e1) {
					StudentController.callAlert("�˸�:�����Ͻ� �޿� �Ѽ����ᰡ ��ϵ��� �ʾҽ��ϴ�.");

				}
			});
		} catch (Exception e) {
			// StudentController.callAlert("�˸�:�����Ͻ� �޿��Ѽ����ᰡ ��ϵ��� �ʾҽ��ϴ�.");

		}
	}

	// 0. ��������ǰ�� �� ��ư�� �������� ó���ϴ� �Լ�
	private void handleTuiBtnScatterOpriceAction() {
		try {
			Stage choiceStage = new Stage();
			choiceStage.initOwner(tuitionStage);
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
				if (tuiOprice != null) {
					tuiOprice.clear();
				}
				try {
					Stage scatterStage = new Stage();
					scatterStage.initOwner(choiceStage);
					scatterStage.setTitle("���� ����ǰ�� ��");
					FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../View/tuitionScatterOprice.fxml"));
					Parent root1 = loader1.load();
					// ID���
					ScatterChart tuiScatterOprice = (ScatterChart) root1.lookup("#tuiScatterOprice");
					Button tuiScatterOpriceClose = (Button) root1.lookup("#tuiScatterOpriceClose");
					ObservableList<XYChart.Data<String, Integer>> obList1 = FXCollections.observableArrayList();

					XYChart.Series<String, Integer> series1 = new XYChart.Series<String, Integer>();
					series1.setName("����ǰ��");

					tuiOprice = TuitionDAO.getSelectedOprice(choiceStart.getValue().toString(),
							choiceEnd.getValue().toString());

					Set<String> set = tuiOprice.keySet();
					Iterator<String> iterator = set.iterator();
					while (iterator.hasNext()) {
						String month = iterator.next();
						Integer oprice = tuiOprice.get(month);
						obList1.add(new XYChart.Data<String, Integer>(month, oprice));
					}

					series1.setData(obList1);
					tuiScatterOprice.setAnimated(false);
					tuiScatterOprice.getData().add(series1);
					Scene scene1 = new Scene(root1);
					scatterStage.setScene(scene1);
					scatterStage.show();

					tuiScatterOpriceClose.setOnAction(e1 -> {
						scatterStage.close();
					});

				} catch (Exception e1) {
					StudentController.callAlert("�˸�:�����Ͻ� �޿� ����ǰ�� ��ϵ��� �ʾҽ��ϴ�.");
				}
			});
		} catch (Exception e) {
			// StudentController.callAlert("�˸�:�����Ͻ� �޿� ����ǰ�� ��ϵ��� �ʾҽ��ϴ�.");
		}
	}

	// 0. �����Ѱ��η����� �� ��ư�� �������� ó���ϴ� �Լ�
	private void handleTuiBtnAreaIpriceAction() {
		try {
			Stage choiceStage = new Stage();
			choiceStage.initOwner(tuitionStage);
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
				if (tuiIprice != null) {
					tuiIprice.clear();
				}
				try {
					Stage areaStage = new Stage();
					areaStage.initOwner(choiceStage);
					areaStage.setTitle("���� �Ѱ��η����� ��");
					FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../View/tuitionAreaIprice.fxml"));
					Parent root1 = loader1.load();
					// ID���
					AreaChart tuiAreaIprice = (AreaChart) root1.lookup("#tuiAreaIprice");
					Button tuiAreaIpriceClose = (Button) root1.lookup("#tuiAreaIpriceClose");
					ObservableList<XYChart.Data<String, Integer>> obList1 = FXCollections.observableArrayList();

					XYChart.Series<String, Integer> series1 = new XYChart.Series<String, Integer>();
					series1.setName("�Ѱ��η�����");

					tuiIprice = TuitionDAO.getSelectedIprice(choiceStart.getValue().toString(),
							choiceEnd.getValue().toString());

					Set<String> set = tuiIprice.keySet();
					Iterator<String> iterator = set.iterator();
					while (iterator.hasNext()) {
						String month = iterator.next();
						Integer iprice = tuiIprice.get(month);
						obList1.add(new XYChart.Data<String, Integer>(month, iprice));
					}

					series1.setData(obList1);
					tuiAreaIprice.setAnimated(false);
					tuiAreaIprice.getData().add(series1);
					Scene scene1 = new Scene(root1);
					areaStage.setScene(scene1);
					areaStage.show();

					tuiAreaIpriceClose.setOnAction(e1 -> {
						areaStage.close();
					});

				} catch (Exception e1) {
					StudentController.callAlert("�˸�:�����Ͻ� �޿� �Ѱ��η����� ��ϵ��� �ʾҽ��ϴ�.");
				}
			});
		} catch (Exception e) {
			// StudentController.callAlert("�˸�:�����Ͻ� �޿� ����ǰ�� ��ϵ��� �ʾҽ��ϴ�.");
		}
	}

	// 0. ���������л��Ѽ����� ��ư�� �������� ó���ϴ� �Լ�
	private void handleTuiBtnSelectedTotalAction() {
		selectedTuition = tuiTableView.getSelectionModel().getSelectedItem();
		if (selectedTuition != null) {
			try {
				Stage choiceStage = new Stage();
				choiceStage.initOwner(tuitionStage);
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
					if (tuiTotal != null) {
						tuiTotal.clear();
					}
					try {
						Stage lineStage = new Stage();
						lineStage.initOwner(choiceStage);
						lineStage.setTitle(selectedTuition.getName() + "�� ���� �Ѽ�����");
						FXMLLoader loader1 = new FXMLLoader(
								getClass().getResource("../View/tuitionLineSelectedTotal.fxml"));
						Parent root1 = loader1.load();
						// ID���
						LineChart tuiLineSelectedTotal = (LineChart) root1.lookup("#tuiLineSelectedTotal");
						Button tuiLineSelectedTotalClose = (Button) root1.lookup("#tuiLineSelectedTotalClose");
						ObservableList<XYChart.Data<String, Integer>> obList1 = FXCollections.observableArrayList();

						XYChart.Series<String, Integer> series1 = new XYChart.Series<String, Integer>();
						series1.setName(selectedTuition.getName() + "�� �Ѽ�����");

						tuiTotal = TuitionDAO.getSelectedTotal(choiceStart.getValue().toString(),
								choiceEnd.getValue().toString(), selectedTuition.getStudentId());

						Set<String> set = tuiTotal.keySet();
						Iterator<String> iterator = set.iterator();
						while (iterator.hasNext()) {
							String month = iterator.next();
							Integer total = tuiTotal.get(month);
							obList1.add(new XYChart.Data<String, Integer>(month, total));
						}

						series1.setData(obList1);
						tuiLineSelectedTotal.setAnimated(false);
						tuiLineSelectedTotal.getData().add(series1);
						Scene scene1 = new Scene(root1);
						lineStage.setScene(scene1);
						lineStage.show();

						tuiLineSelectedTotalClose.setOnAction(e1 -> {
							lineStage.close();
						});
					} catch (Exception e1) {
						StudentController.callAlert("�˸�:�����Ͻ� �޿� " + selectedTuition.getName() + "�� �Ѽ����ᰡ ��ϵ��� �ʾҽ��ϴ�.");
					}
				});

			} catch (Exception e) {
				StudentController.callAlert("�˸�:�л������� �����ֽñ� �ٶ��ϴ�.");
			}
		} else {
			StudentController.callAlert("�˸�:�л������� �����ֽñ� �ٶ��ϴ�.");
		}

	}

	// 0. ����л����Ѽ����� ��ư�� �������� ó���ϴ� �Լ�
	private void handleTuiBtnBarTotalAction() {
		try {
			Stage barStage = new Stage();
			barStage.initOwner(tuitionStage);
			barStage.setTitle(tuiCmbPayDate.getSelectionModel().getSelectedItem()+" �л��� �Ѽ����� ��");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/tuitionBarTotal.fxml"));
			Parent root = loader.load();
			BarChart tuiBarTotal = (BarChart) root.lookup("#tuiBarTotal");
			Button tuiBarTotalClose = (Button) root.lookup("#tuiBarTotalClose");

			XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
			series1.setName("�Ѽ�����");
			ObservableList obList = FXCollections.observableArrayList();
			for (Tuition tuition : tuiArrayList) {
				obList.add(new XYChart.Data<String, Integer>(tuition.getName(), tuition.getTotal()));
			}
			series1.setData(obList);
			tuiBarTotal.setAnimated(false);
			tuiBarTotal.getData().add(series1);

			Scene scene = new Scene(root);
			barStage.setScene(scene);
			barStage.show();

			tuiBarTotalClose.setOnAction(e -> {
				barStage.close();
			});

		} catch (Exception e) {
			StudentController.callAlert("��� �л��� �Ѽ����� �񱳿���:����Ʈâ ����");
		}

	}

	// 0. ����ѷ������ ��ư�� �������� ó���ϴ� �Լ�
	private void handleTuiBtnPiePIOsumAction() {
		try {
			Stage pieStage = new Stage();
			pieStage.initOwner(tuitionStage);
			pieStage.setTitle(tuiCmbPayDate.getSelectionModel().getSelectedItem() + "�� �ѷ����� ��");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/tuitionPiePIOsum.fxml"));
			Parent root = loader.load();
			PieChart tuiPiePIOsum = (PieChart) root.lookup("#tuiPiePIOsum");
			Button tuiPiePIOsumClose = (Button) root.lookup("#tuiPiePIOsumClose");

			ObservableList obList = FXCollections.observableArrayList();
			int PpriceSum = 0;
			int IpriceSum = 0;
			int OpriceSum = 0;
			for (Tuition tuition : tuiArrayList) {
				PpriceSum = PpriceSum + tuition.getPprice();
				IpriceSum = IpriceSum + tuition.getIprice();
				OpriceSum = OpriceSum + tuition.getOprice();
			}
			LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
			linkedHashMap.put("�ѱ׷췹����(" + PpriceSum + "��)", PpriceSum);
			linkedHashMap.put("�Ѱ��η�����(" + IpriceSum + "��)", IpriceSum);
			linkedHashMap.put("����ǰ��(" + OpriceSum + "��)", OpriceSum);
			Set<String> set = linkedHashMap.keySet();
			for (String priceName : set) {
				int price = linkedHashMap.get(priceName);
				obList.add(new PieChart.Data(priceName, price));
			}
			tuiPiePIOsum.setData(obList);

			Scene scene = new Scene(root);
			pieStage.setScene(scene);
			pieStage.show();

			tuiPiePIOsumClose.setOnAction(e1 -> {
				pieStage.close();
			});
		} catch (Exception e1) {
			StudentController.callAlert("�˸�:������Ʈâ ����");
		}
	}

	// 0. ���̺�並 �ι�Ŭ�� ������ ó���ϴ� �Լ�
	private void handleTuiTableViewDoubleClickedAction(MouseEvent e) {
		selectedTuition = tuiTableView.getSelectionModel().getSelectedItem();
		if (e.getClickCount() == 2) {
			try {
				Stage pieStage = new Stage();
				pieStage.initOwner(tuitionStage);
				pieStage.setTitle(selectedTuition.getName() + "�� ������ ��");
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/tuitionPiePIOprice.fxml"));
				Parent root = loader.load();
				PieChart tuiPiePIOprice = (PieChart) root.lookup("#tuiPiePIOprice");
				Button tuiPiePIOpriceClose = (Button) root.lookup("#tuiPiePIOpriceClose");

				ObservableList obList = FXCollections.observableArrayList();
				LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
				linkedHashMap.put("�׷췹����(" + selectedTuition.getPprice() + "��)", selectedTuition.getPprice());
				linkedHashMap.put("���η�����(" + selectedTuition.getIprice() + "��)", selectedTuition.getIprice());
				linkedHashMap.put("��ǰ��(" + selectedTuition.getOprice() + "��)", selectedTuition.getOprice());
				Set<String> set = linkedHashMap.keySet();
				for (String priceName : set) {
					int price = linkedHashMap.get(priceName);
					obList.add(new PieChart.Data(priceName, price));
				}
				tuiPiePIOprice.setData(obList);

				Scene scene = new Scene(root);
				pieStage.setScene(scene);
				pieStage.show();

				tuiPiePIOpriceClose.setOnAction(e1 -> {
					pieStage.close();
				});
			} catch (Exception e1) {
				StudentController.callAlert("�˸�:������Ʈâ ����");
			}
		}
	}

// 0. �˻��� �������� ó���ϴ� �Լ�
	private void handleTuiBtnSearchAction() {
		for (Tuition tuition : tuiListData) {
			if (tuiTxtSearch.getText().trim().equals(tuition.getName())) {
				tuiTableView.getSelectionModel().select(tuition);
			}
		}
	}

	// 0. �ڷΰ��� ��ư�� �������� ó���ϴ� �Լ�
	private void handleTuiBtnBackAction() {
		try {
			tuiArrayList.clear();
			tuiListData.clear();
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
			tuitionStage.close();
			choiceStage.show();

		} catch (Exception e) {
		}
	}

	// 0. ��� �Ѽ����� �ؽ�Ʈ�ʵ忡 ��Ÿ����
	private void setMonthlySum() {
		monthlySum = 0;
		for (Tuition tuition : tuiArrayList) {
			monthlySum += tuition.getTotal();
		}
		tuiTxtSum.setText(String.valueOf(monthlySum));
	}

}
