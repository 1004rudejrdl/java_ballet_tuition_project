package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import Model.Body;

public class BodyDAO {
	public static ArrayList<Body> bodyArrayList = new ArrayList<>();
	public static ArrayList<String> bodyStudentId = new ArrayList<>();
	public static ArrayList<String> bodyNameAndAge = new ArrayList<>();
	public static TreeMap<String, Double> bodySelectedWeight = new TreeMap<>();
	public static LinkedHashMap<String, Double> bodySelectedMuscle = new LinkedHashMap<>();
	public static TreeMap<String, Double> bodySelectedFat = new TreeMap<>();

	public static String selectWeight;
	public static String selectMuscle;
	public static String selectFat;

	// 1. ��ü������ ����ϴ� �Լ�
	public static int insertBodyIntoBalletDB(Body body) {
		StringBuffer insertBody = new StringBuffer();
		insertBody.append("insert into bodytbl ");
		insertBody.append("(studentId,height,weight,muscle,fat,measureDate) ");
		insertBody.append("values ");
		insertBody.append("(?,?,?,?,?,?) ");

		Connection connection = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(insertBody.toString());
			ps.setString(1, body.getStudentId());
			ps.setDouble(2, body.getHeight());
			ps.setDouble(3, body.getWeight());
			ps.setDouble(4, body.getMuscle());
			ps.setDouble(5, body.getFat());
			ps.setString(6, body.getMeasureDate());

			// 1.4 ���������͸� ������ �������� �����Ѵ�.
			count = ps.executeUpdate();
			if (count == 0) {
				StudentController.callAlert("���Խ���:���������� ����");
				return count;
			}
		} catch (Exception e) {
			StudentController.callAlert("���Խ���:�����ͺ��̽� ���������� ����");
		} finally {
			// 1.5 �ڿ���ü�� �ݴ´�.
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				StudentController.callAlert("�ݱ����:�ڿ��ݱ����");
			}
		}
		return count;
	}
	// 2. �����ͺ��̽����� 2019�� ��ü������ �������� �Լ�
	public static ArrayList<Body> get2019BodyFromBalletDb(String studentId) {
		// 2.1 �����ͺ��̽��� ��Ʃ��Ʈ ���̺�� �ٵ����̺��� �̳������ؼ� �����͸� �������� ������
		StringBuffer selectBody = new StringBuffer();
		selectBody.append("select studenttbl.studentID, studenttbl.name, studenttbl.age, bodytbl.height, ");
		selectBody.append("bodytbl.weight, bodytbl.muscle, bodytbl.fat, bodytbl.measureDate from studenttbl ");
		selectBody.append("inner join bodytbl on studenttbl.studentId = bodytbl.studentId ");
		selectBody.append("where bodytbl.measureDate like '2019-%%-%%' and studenttbl.studentId='" + studentId + "' ");
		selectBody.append("order by bodytbl.measureDate desc ");
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectBody.toString());
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				Body body = new Body(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getDouble(4), rs.getDouble(5),
						rs.getDouble(6), rs.getDouble(7), rs.getString(8));
				bodyArrayList.add(body);
			}
		} catch (Exception e) {
			StudentController.callAlert("get2018BodyFromBalletDb����:Ȯ�ιٶ�");
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				StudentController.callAlert("�ڿ��ݱ����:����");
			}
		}
		return bodyArrayList;
	}

	// 2. �����ͺ��̽����� 2018�� ��ü������ �������� �Լ�
	public static ArrayList<Body> get2018BodyFromBalletDb(String studentId) {
		// 2.1 �����ͺ��̽��� ��Ʃ��Ʈ ���̺�� �ٵ����̺��� �̳������ؼ� �����͸� �������� ������
		StringBuffer selectBody = new StringBuffer();
		selectBody.append("select studenttbl.studentID, studenttbl.name, studenttbl.age, bodytbl.height, ");
		selectBody.append("bodytbl.weight, bodytbl.muscle, bodytbl.fat, bodytbl.measureDate from studenttbl ");
		selectBody.append("inner join bodytbl on studenttbl.studentId = bodytbl.studentId ");
		selectBody.append("where bodytbl.measureDate like '2018-%%-%%' and studenttbl.studentId='" + studentId + "' ");
		selectBody.append("order by bodytbl.measureDate desc ");
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectBody.toString());
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				Body body = new Body(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getDouble(4), rs.getDouble(5),
						rs.getDouble(6), rs.getDouble(7), rs.getString(8));
				bodyArrayList.add(body);
			}
		} catch (Exception e) {
			StudentController.callAlert("get2018BodyFromBalletDb����:Ȯ�ιٶ�");
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				StudentController.callAlert("�ڿ��ݱ����:����");
			}
		}
		return bodyArrayList;
	}

	// 2. �����ͺ��̽����� 2017�� ��ü������ �������� �Լ�
	public static ArrayList<Body> get2017BodyFromBalletDb(String studentId) {
		// 2.1 �����ͺ��̽��� ��Ʃ��Ʈ ���̺�� �ٵ����̺��� �̳������ؼ� �����͸� �������� ������
		StringBuffer selectBody = new StringBuffer();
		selectBody.append("select studenttbl.studentID, studenttbl.name, studenttbl.age, bodytbl.height, ");
		selectBody.append("bodytbl.weight, bodytbl.muscle, bodytbl.fat, bodytbl.measureDate from studenttbl ");
		selectBody.append("inner join bodytbl on studenttbl.studentId = bodytbl.studentId ");
		selectBody.append("where bodytbl.measureDate like '2017-%%-%%' and studenttbl.studentId='" + studentId + "' ");
		selectBody.append("order by bodytbl.measureDate desc ");
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectBody.toString());
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				Body body = new Body(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getDouble(4), rs.getDouble(5),
						rs.getDouble(6), rs.getDouble(7), rs.getString(8));
				bodyArrayList.add(body);
			}
		} catch (Exception e) {
			StudentController.callAlert("get2018BodyFromBalletDb����:Ȯ�ιٶ�");
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				StudentController.callAlert("�ڿ��ݱ����:����");
			}
		}
		return bodyArrayList;
	}

	// 0. ��ü����â���� �̸��� �˻� ������ �߷������ͺ��̽��� ��Ʃ��Ʈ���̺��� �̸��� �´� �л����̵� �������� �Լ�
	public static ArrayList<String> getStudentIdWhenNameSearched(String nameSearch) {
		// String getStudentId = "select studenttbl.studentId from studenttbl inner join
		// bodytbl on studenttbl.studentId = bodytbl.studentId where studenttbl.name =
		// '"+nameSearch+"'";
		String getStudentId = "select studentId from studenttbl where name like '" + nameSearch + "' ";
		// �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(getStudentId);
			// ���������͸� ������ �������� �����Ѵ�.
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				bodyStudentId.add(rs.getString(1));
			}
		} catch (Exception e) {
			StudentController.callAlert("getStudentIdWhenNameSearched����:Ȯ�ιٶ�");
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				StudentController.callAlert("�ڿ��ݱ����:����");
			}
		}
		return bodyStudentId;
	}

	// 0. ��ü�߰�â���� ���̵� ��ȸ������ �߷������ͺ��̽��� ��Ʃ��Ʈ���̺��� ���̵� ���ǿ� �´� �л��̸��� ���̸� �������� �Լ�
	public static ArrayList<String> getNameAndAgeWhenStudentIdSearched(String studentId) {
		String getNameAndAge = "select name, age from studenttbl where studentId= '" + studentId + "' ";
		// �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(getNameAndAge);
			// ���������͸� ������ �������� �����Ѵ�.
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				bodyNameAndAge.add(rs.getString(1));
				bodyNameAndAge.add(String.valueOf(rs.getInt(2)));
			}
		} catch (Exception e) {
			StudentController.callAlert("getNameAndAgeWhenStudentIdSearched����:Ȯ�ιٶ�");
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				StudentController.callAlert("�ڿ��ݱ����:����");
			}
		}
		return bodyNameAndAge;
	}

	// ���ۿ��� ������ �����Ͽ� �� �Ⱓ������ ���õ� �л��� ü���� �������� �Լ�
	public static TreeMap<String, Double> getSelectedWeight(String choiceStart, String choiceEnd, String studentId) {
		int startMonth = 0;
		int endMonth = 0;
		int startYear = 0;
		int endYear = 0;
		String[] splitedChoiceStart = choiceStart.split("-");
		String[] splitedChoiceEnd = choiceEnd.split("-");

		startYear = Integer.parseInt(splitedChoiceStart[0]);
		endYear = Integer.parseInt(splitedChoiceEnd[0]);

		if ((Integer.parseInt(splitedChoiceStart[1]) != 12) && (Integer.parseInt(splitedChoiceStart[1]) != 11)
				&& (Integer.parseInt(splitedChoiceStart[1]) != 10)) {
			startMonth = Integer.parseInt(splitedChoiceStart[1].substring(1));
		} else {
			startMonth = Integer.parseInt(splitedChoiceStart[1]);
		}

		if ((Integer.parseInt(splitedChoiceEnd[1]) != 12) && (Integer.parseInt(splitedChoiceEnd[1]) != 11)
				&& (Integer.parseInt(splitedChoiceEnd[1]) != 10)) {
			endMonth = Integer.parseInt(splitedChoiceEnd[1].substring(1));
		} else {
			endMonth = Integer.parseInt(splitedChoiceEnd[1]);
		}

		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			int j = 0;
			int i = 0;
			for (i = startYear; i < endYear + 1; i++) {
				if (i > startYear) {
					startMonth = 1;
				}
				if (i < endYear) {
					endMonth = 12;
				} else {
					if ((Integer.parseInt(splitedChoiceEnd[1]) != 12) && (Integer.parseInt(splitedChoiceEnd[1]) != 11)
							&& (Integer.parseInt(splitedChoiceEnd[1]) != 10)) {
						endMonth = Integer.parseInt(splitedChoiceEnd[1].substring(1));
					} else {
						endMonth = Integer.parseInt(splitedChoiceEnd[1]);
					}
				}
				for (j = startMonth; j < endMonth + 1; j++) {
					if (j >= 1 && j <= 9) {
						selectWeight = "select weight, date_format(measureDate, '%Y-%m') from bodytbl where measureDate like '"
								+ i + "-0" + j + "-%%' and studentId ='" + studentId + "'";
					} else {
						selectWeight = "select weight, date_format(measureDate, '%Y-%m') from bodytbl where measureDate like '" + i + "-" + j
								+ "-%%' and studentId ='" + studentId + "'";
					}
					ps = connection.prepareStatement(selectWeight);
					rs = ps.executeQuery();
					while (rs.next()) {
						bodySelectedWeight.put(rs.getString(2), rs.getDouble(1));
					}
				}
			}
			if (rs == null) {
				StudentController.callAlert("�˸�:���ۿ��� �������� �ʽ��ϴ�.");
				return null;
			}
		} catch (Exception e) {
			StudentController.callAlert("�˸�:�����Ͻ� �޿� ü�������� ��ϵ��� �ʾҽ��ϴ�.");
		}
		return bodySelectedWeight;
	}

	// ���ۿ��� ������ �����Ͽ� �� �Ⱓ������ ���õ� �л��� �������� �������� �Լ�
	public static LinkedHashMap<String, Double> getSelectedMuscle(String choiceStart, String choiceEnd,
			String studentId) {
		int startMonth = 0;
		int endMonth = 0;
		int startYear = 0;
		int endYear = 0;
		String[] splitedChoiceStart = choiceStart.split("-");
		String[] splitedChoiceEnd = choiceEnd.split("-");

		startYear = Integer.parseInt(splitedChoiceStart[0]);
		endYear = Integer.parseInt(splitedChoiceEnd[0]);

		if ((Integer.parseInt(splitedChoiceStart[1]) != 12) && (Integer.parseInt(splitedChoiceStart[1]) != 11)
				&& (Integer.parseInt(splitedChoiceStart[1]) != 10)) {
			startMonth = Integer.parseInt(splitedChoiceStart[1].substring(1));
		} else {
			startMonth = Integer.parseInt(splitedChoiceStart[1]);
		}

		if ((Integer.parseInt(splitedChoiceEnd[1]) != 12) && (Integer.parseInt(splitedChoiceEnd[1]) != 11)
				&& (Integer.parseInt(splitedChoiceEnd[1]) != 10)) {
			endMonth = Integer.parseInt(splitedChoiceEnd[1].substring(1));
		} else {
			endMonth = Integer.parseInt(splitedChoiceEnd[1]);
		}

		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			int j = 0;
			int i = 0;
			for (i = startYear; i < endYear + 1; i++) {
				if (i > startYear) {
					startMonth = 1;
				}
				if (i < endYear) {
					endMonth = 12;
				} else {
					if ((Integer.parseInt(splitedChoiceEnd[1]) != 12) && (Integer.parseInt(splitedChoiceEnd[1]) != 11)
							&& (Integer.parseInt(splitedChoiceEnd[1]) != 10)) {
						endMonth = Integer.parseInt(splitedChoiceEnd[1].substring(1));
					} else {
						endMonth = Integer.parseInt(splitedChoiceEnd[1]);
					}
				}
				for (j = startMonth; j < endMonth + 1; j++) {
					if (j >= 1 && j <= 9) {
						selectMuscle = "select muscle, date_format(measureDate, '%Y-%m') from bodytbl where measureDate like '" + i + "-0" + j
								+ "-%%' and studentId ='" + studentId + "'";
					} else {
						selectMuscle = "select muscle, date_format(measureDate, '%Y-%m') from bodytbl where measureDate like '" + i + "-" + j
								+ "-%%' and studentId ='" + studentId + "'";
					}
					ps = connection.prepareStatement(selectMuscle);
					rs = ps.executeQuery();
					while (rs.next()) {
						bodySelectedMuscle.put(rs.getString(2), rs.getDouble(1));
					}
				}
			}
			if (rs == null) {
				StudentController.callAlert("�˸�:���ۿ��� �������� �ʽ��ϴ�.");
				return null;
			}
		} catch (Exception e) {
			StudentController.callAlert("�˸�:�����Ͻ� �޿� ������ ������ ��ϵ��� �ʾҽ��ϴ�.");
		}
		return bodySelectedMuscle;
	}

	// ���ۿ��� ������ �����Ͽ� �� �Ⱓ������ ���õ� �л��� ü������� �������� �Լ�
	public static TreeMap<String, Double> getSelectedFat(String choiceStart, String choiceEnd, String studentId) {
		int startMonth = 0;
		int endMonth = 0;
		int startYear = 0;
		int endYear = 0;
		String[] splitedChoiceStart = choiceStart.split("-");
		String[] splitedChoiceEnd = choiceEnd.split("-");

		startYear = Integer.parseInt(splitedChoiceStart[0]);
		endYear = Integer.parseInt(splitedChoiceEnd[0]);

		if ((Integer.parseInt(splitedChoiceStart[1]) != 12) && (Integer.parseInt(splitedChoiceStart[1]) != 11)
				&& (Integer.parseInt(splitedChoiceStart[1]) != 10)) {
			startMonth = Integer.parseInt(splitedChoiceStart[1].substring(1));
		} else {
			startMonth = Integer.parseInt(splitedChoiceStart[1]);
		}

		if ((Integer.parseInt(splitedChoiceEnd[1]) != 12) && (Integer.parseInt(splitedChoiceEnd[1]) != 11)
				&& (Integer.parseInt(splitedChoiceEnd[1]) != 10)) {
			endMonth = Integer.parseInt(splitedChoiceEnd[1].substring(1));
		} else {
			endMonth = Integer.parseInt(splitedChoiceEnd[1]);
		}

		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			int j = 0;
			int i = 0;
			for (i = startYear; i < endYear + 1; i++) {
				if (i > startYear) {
					startMonth = 1;
				}
				if (i < endYear) {
					endMonth = 12;
				} else {
					if ((Integer.parseInt(splitedChoiceEnd[1]) != 12) && (Integer.parseInt(splitedChoiceEnd[1]) != 11)
							&& (Integer.parseInt(splitedChoiceEnd[1]) != 10)) {
						endMonth = Integer.parseInt(splitedChoiceEnd[1].substring(1));
					} else {
						endMonth = Integer.parseInt(splitedChoiceEnd[1]);
					}
				}
				for (j = startMonth; j < endMonth + 1; j++) {
					if (j >= 1 && j <= 9) {
						selectFat = "select fat, date_format(measureDate, '%Y-%m') from bodytbl where measureDate like '" + i + "-0" + j
								+ "-%%' and studentId ='" + studentId + "'";
					} else {
						selectFat = "select fat, date_format(measureDate, '%Y-%m') from bodytbl where measureDate like '" + i + "-" + j
								+ "-%%' and studentId ='" + studentId + "'";
					}
					ps = connection.prepareStatement(selectFat);
					rs = ps.executeQuery();
					while (rs.next()) {
						bodySelectedFat.put(rs.getString(2), rs.getDouble(1));
					}
				}
			}
			if (rs == null) {
				StudentController.callAlert("�˸�:���ۿ��� �������� �ʽ��ϴ�.");
				return null;
			}
		} catch (Exception e) {
			StudentController.callAlert("�˸�:�����Ͻ� �޿� ü����� ������ ��ϵ��� �ʾҽ��ϴ�.");
		}
		return bodySelectedFat;
	}

	// 3. ��ü������ �����ϴ� �Լ�
	public static int deleteBodyFromBalletDB(String studentId, String measureDate) {
		// 3.1 �����ͺ��̽��� �ٵ����̺� �ִ� �����͸� �����ϴ� ������
		String deleteBody = "delete from bodytbl where studentId=? and measureDate=? ";
		// 3.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 3.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(deleteBody);
			// 3.4 �������� ���������͸� �����Ѵ�.
			ps.setString(1, studentId);
			ps.setString(2, measureDate);
			// 3.5 ���������͸� ������ �������� �����Ѵ�.
			count = ps.executeUpdate();
			if (count == 0) {
				StudentController.callAlert("delete����:delete ������ ����");
				return count;
			}
		} catch (Exception e) {
			StudentController.callAlert("delete����:�����ͺ��̽� ��������");
		} finally {
			// 3.6 �ڿ���ü�� �ݴ´�.
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				StudentController.callAlert("�ڿ��ݱ����:����");
			}
		}
		return count;
	}

	// 4. ��ü������ �����ϴ� �Լ�
	public static int updateBodySet(Body body, Body selectedBody) {
		// 4.1 �����ͺ��̽��� Ʃ�̼����̺��� �����ϴ� ������
		StringBuffer updateBody = new StringBuffer();
		updateBody.append("update bodytbl set ");
		updateBody.append("height=?,weight=?,muscle=?,fat=?,measureDate=? where studentId=? and measureDate=? ");
		// 4.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 4.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(updateBody.toString());
			// 4.4 �������� ���������͸� �����Ѵ�.
			ps.setDouble(1, body.getHeight());
			ps.setDouble(2, body.getWeight());
			ps.setDouble(3, body.getMuscle());
			ps.setDouble(4, body.getFat());
			ps.setString(5, body.getMeasureDate());
			ps.setString(6, selectedBody.getStudentId());
			ps.setString(7, selectedBody.getMeasureDate());

			// 4.5 ���������͸� ������ �������� �����Ѵ�.
			count = ps.executeUpdate();
			if (count == 0) {
				StudentController.callAlert("update����:update������ ����");
				return count;
			}
		} catch (Exception e) {
			StudentController.callAlert("update����:�����ͺ��̽� ��������");
		} finally {
			// 4.6 �ڿ���ü�� �ݴ´�.
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				StudentController.callAlert("�ڿ��ݱ����:����");
			}
		}
		return count;
	}

}
