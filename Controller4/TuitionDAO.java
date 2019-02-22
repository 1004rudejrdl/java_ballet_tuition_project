package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import Model.Tuition;

public class TuitionDAO {
	public static ArrayList<Tuition> tuiArrayList = new ArrayList<>();
	public static ArrayList<String> tuiNameAndParty = new ArrayList<>();

	public static TreeMap<String, Integer> tuiSum = new TreeMap<String, Integer>();
	public static LinkedHashMap<String, Integer> tuiOprice = new LinkedHashMap<String, Integer>();
	public static TreeMap<String, Integer> tuiIprice = new TreeMap<String, Integer>();
	public static LinkedHashMap<String,Integer> tuiTotal = new LinkedHashMap<>();

	public static String selectSum;
	public static String selectOprice;
	public static String selectIprice;
	public static String selectTotal;
	
	// 1. �к������� ����ϴ� �Լ�
	public static int insertTuitionIntoBalletDB(Tuition tuition) {
		StringBuffer insertTuition = new StringBuffer();
		insertTuition.append("insert into tuitiontbl ");
		insertTuition.append("(studentId,individual,opus,pprice,iprice,oprice,total,payDate) ");
		insertTuition.append("values ");
		insertTuition.append("(?,?,?,?,?,?,?,?) ");

		// 1.1 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 1.2 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(insertTuition.toString());
			// 1.3 �������� ���������͸� �����Ѵ�.
			ps.setString(1, tuition.getStudentId());
			ps.setString(2, tuition.getIndividual());
			ps.setString(3, tuition.getOpus());
			ps.setInt(4, tuition.getPprice());
			ps.setInt(5, tuition.getIprice());
			ps.setInt(6, tuition.getOprice());
			ps.setInt(7, tuition.getTotal());
			ps.setString(8, tuition.getPayDate());

			// 1.4 ���������͸� ������ �������� �����Ѵ�.
			// executeUpdate() => �����ͺ��̽��� �����Ҷ� ����ϴ� ������
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
	// 2. �����ͺ��̽����� 2019�� 2�� �к������ �������� �Լ�
	public static ArrayList<Tuition> get2019FebruaryTuitionFromBalletDb() {
		// 2.1 �����ͺ��̽��� ��Ʃ��Ʈ ���̺�� Ʃ�̼����̺��� �̳������ؼ� �����͸� �������� ������
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2019-02-%%' order by studenttbl.age desc");
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB����:Ȯ�ιٶ�");
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
		return tuiArrayList;
	}
	
	// 2. �����ͺ��̽����� 2019�� 1�� �к������ �������� �Լ�
	public static ArrayList<Tuition> get2019JanuaryTuitionFromBalletDb() {
		// 2.1 �����ͺ��̽��� ��Ʃ��Ʈ ���̺�� Ʃ�̼����̺��� �̳������ؼ� �����͸� �������� ������
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2019-01-%%' order by studenttbl.age desc");
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB����:Ȯ�ιٶ�");
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
		return tuiArrayList;
	}

	// 2. �����ͺ��̽����� 2018�� 12�� �к������ �������� �Լ�
	public static ArrayList<Tuition> getDecemberTuitionFromBalletDb() {
		// 2.1 �����ͺ��̽��� ��Ʃ��Ʈ ���̺�� Ʃ�̼����̺��� �̳������ؼ� �����͸� �������� ������
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-12-%%' order by studenttbl.age desc");
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB����:Ȯ�ιٶ�");
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
		return tuiArrayList;
	}

	// 2. �����ͺ��̽����� 2018�� 11�� �к������ �������� �Լ�
	public static ArrayList<Tuition> getNovemberTuitionFromBalletDb() {
		// 2.1 �����ͺ��̽��� ��Ʃ��Ʈ ���̺�� Ʃ�̼����̺��� �̳������ؼ� �����͸� �������� ������
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-11-%%' order by studenttbl.age desc");
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB����:Ȯ�ιٶ�");
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
		return tuiArrayList;
	}

	// 2. �����ͺ��̽����� 2018�� 10�� �к������ �������� �Լ�
	public static ArrayList<Tuition> getOctoberTuitionFromBalletDb() {
		// 2.1 �����ͺ��̽��� ��Ʃ��Ʈ ���̺�� Ʃ�̼����̺��� �̳������ؼ� �����͸� �������� ������
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-10-%%' order by studenttbl.age desc");
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB����:Ȯ�ιٶ�");
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
		return tuiArrayList;
	}

	// 2. �����ͺ��̽����� 2018�� 9�� �к������ �������� �Լ�
	public static ArrayList<Tuition> getSeptemberTuitionFromBalletDb() {
		// 2.1 �����ͺ��̽��� ��Ʃ��Ʈ ���̺�� Ʃ�̼����̺��� �̳������ؼ� �����͸� �������� ������
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-09-%%' order by studenttbl.age desc");
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB����:Ȯ�ιٶ�");
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
		return tuiArrayList;
	}
	
	// 2. �����ͺ��̽����� 2018�� 8�� �к������ �������� �Լ�
	public static ArrayList<Tuition> getAugustTuitionFromBalletDb() {
		// 2.1 �����ͺ��̽��� ��Ʃ��Ʈ ���̺�� Ʃ�̼����̺��� �̳������ؼ� �����͸� �������� ������
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-08-%%' order by studenttbl.age desc");
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB����:Ȯ�ιٶ�");
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
		return tuiArrayList;
	}
	
	// 2. �����ͺ��̽����� 2018�� 7�� �к������ �������� �Լ�
	public static ArrayList<Tuition> getJulyTuitionFromBalletDb() {
		// 2.1 �����ͺ��̽��� ��Ʃ��Ʈ ���̺�� Ʃ�̼����̺��� �̳������ؼ� �����͸� �������� ������
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-07-%%' order by studenttbl.age desc");
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB����:Ȯ�ιٶ�");
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
		return tuiArrayList;
	}
	
	// 2. �����ͺ��̽����� 2018�� 6�� �к������ �������� �Լ�
	public static ArrayList<Tuition> getJuneTuitionFromBalletDb() {
		// 2.1 �����ͺ��̽��� ��Ʃ��Ʈ ���̺�� Ʃ�̼����̺��� �̳������ؼ� �����͸� �������� ������
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-06-%%' order by studenttbl.age desc");
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB����:Ȯ�ιٶ�");
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
		return tuiArrayList;
	}
	
	// 2. �����ͺ��̽����� 2018�� 5�� �к������ �������� �Լ�
	public static ArrayList<Tuition> getMayTuitionFromBalletDb() {
		// 2.1 �����ͺ��̽��� ��Ʃ��Ʈ ���̺�� Ʃ�̼����̺��� �̳������ؼ� �����͸� �������� ������
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-05-%%' order by studenttbl.age desc");
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB����:Ȯ�ιٶ�");
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
		return tuiArrayList;
	}
	
	// 2. �����ͺ��̽����� 2018�� 4�� �к������ �������� �Լ�
	public static ArrayList<Tuition> getAprilTuitionFromBalletDb() {
		// 2.1 �����ͺ��̽��� ��Ʃ��Ʈ ���̺�� Ʃ�̼����̺��� �̳������ؼ� �����͸� �������� ������
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-04-%%' order by studenttbl.age desc");
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB����:Ȯ�ιٶ�");
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
		return tuiArrayList;
	}
	
	// 2. �����ͺ��̽����� 2018�� 3�� �к������ �������� �Լ�
	public static ArrayList<Tuition> getMarchTuitionFromBalletDb() {
		// 2.1 �����ͺ��̽��� ��Ʃ��Ʈ ���̺�� Ʃ�̼����̺��� �̳������ؼ� �����͸� �������� ������
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-03-%%' order by studenttbl.age desc");
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB����:Ȯ�ιٶ�");
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
		return tuiArrayList;
	}
	
	// 2. �����ͺ��̽����� 2018�� 2�� �к������ �������� �Լ�
	public static ArrayList<Tuition> getFebruaryTuitionFromBalletDb() {
		// 2.1 �����ͺ��̽��� ��Ʃ��Ʈ ���̺�� Ʃ�̼����̺��� �̳������ؼ� �����͸� �������� ������
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-02-%%' order by studenttbl.age desc");
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB����:Ȯ�ιٶ�");
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
		return tuiArrayList;
	}
	
	// 2. �����ͺ��̽����� 2018�� 1�� �к������ �������� �Լ�
	public static ArrayList<Tuition> getJanuaryTuitionFromBalletDb() {
		// 2.1 �����ͺ��̽��� ��Ʃ��Ʈ ���̺�� Ʃ�̼����̺��� �̳������ؼ� �����͸� �������� ������
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-01-%%' order by studenttbl.age desc");
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB����:Ȯ�ιٶ�");
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
		return tuiArrayList;
	}

	// �к��߰�â���� ���̵� ��ȸ������ �߷������ͺ��̽��� ��Ʃ��Ʈ���̺��� ���̵� ���ǿ� �´� �л��̸��� �׷췹�������� �������� �Լ�
	public static ArrayList<String> getNameAndPartyWhenStudentIdSearched(String studentId) {
		String getNameAndParty = "select name, party from studenttbl where studentId='" + studentId + "'";
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(getNameAndParty);
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				tuiNameAndParty.add(rs.getString(1));
				tuiNameAndParty.add(rs.getString(2));
			}
		} catch (Exception e) {
			StudentController.callAlert("getNameAndPartyWhenStudentIdSearched����:Ȯ�ιٶ�");
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

		return tuiNameAndParty;
	}

	// ���ۿ��� ������ �����Ͽ� �� �Ⱓ������ ���õ� ���� �Ѽ����Ḧ �������� �Լ�
	public static TreeMap<String, Integer> getSelectedSum(String choiceStart, String choiceEnd) {
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
						selectSum = "select sum(total), date_format(payDate, '%Y-%m') from tuitiontbl where payDate like '"
								+ i + "-0" + j + "-%%'";
					} else {
						selectSum = "select sum(total), date_format(payDate, '%Y-%m') from tuitiontbl where payDate like '"
								+ i + "-" + j + "-%%' ";
					}
					ps = connection.prepareStatement(selectSum);
					rs = ps.executeQuery();
					while (rs.next()) {
						tuiSum.put(rs.getString(2), rs.getInt(1));
					}
				}
			}
			if (rs == null) {
				StudentController.callAlert("�˸�:���ۿ��� �������� �ʽ��ϴ�.");
				return null;
			}
		} catch (Exception e) {
			StudentController.callAlert("�˸�:�����Ͻ� �޿� �Ѽ����ᰡ ��ϵ��� �ʾҽ��ϴ�.");
		}
		return tuiSum;
	}

	// ���ۿ��� ������ �����Ͽ� �� �Ⱓ������ ���õ� ���� ����ǰ�� �������� �Լ�
	public static LinkedHashMap<String, Integer> getSelectedOprice(String choiceStart, String choiceEnd) {
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
						selectOprice = "select sum(oprice), date_format(payDate, '%Y-%m') from tuitiontbl where payDate like '"
								+ i + "-0" + j + "-%%'";
					} else {
						selectOprice = "select sum(oprice), date_format(payDate, '%Y-%m') from tuitiontbl where payDate like '"
								+ i + "-" + j + "-%%' ";
					}
					ps = connection.prepareStatement(selectOprice);
					rs = ps.executeQuery();
					while (rs.next()) {
						tuiOprice.put(rs.getString(2), rs.getInt(1));
					}
				}
			}
			if (rs == null) {
				StudentController.callAlert("�˸�:���ۿ��� �������� �ʽ��ϴ�.");
				return null;
			}
		} catch (Exception e) {
			StudentController.callAlert("�˸�:�����Ͻ� �޿� ����ǰ�� ��ϵ��� �ʾҽ��ϴ�.");
		}
		return tuiOprice;
	}

	// ���ۿ��� ������ �����Ͽ� �� �Ⱓ������ ���õ� ���� �Ѱ��η����� �������� �Լ�
	public static TreeMap<String, Integer> getSelectedIprice(String choiceStart, String choiceEnd) {
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
						selectIprice = "select sum(iprice), date_format(payDate, '%Y-%m') from tuitiontbl where payDate like '"
								+ i + "-0" + j + "-%%'";
					} else {
						selectIprice = "select sum(iprice), date_format(payDate, '%Y-%m') from tuitiontbl where payDate like '"
								+ i + "-" + j + "-%%' ";
					}
					ps = connection.prepareStatement(selectIprice);
					rs = ps.executeQuery();
					while (rs.next()) {
						tuiIprice.put(rs.getString(2), rs.getInt(1));
					}
				}
			}
			if (rs == null) {
				StudentController.callAlert("�˸�:���ۿ��� �������� �ʽ��ϴ�.");
				return null;
			}
		} catch (Exception e) {
			StudentController.callAlert("�˸�:�����Ͻ� �޿� �Ѱ��η����� ��ϵ��� �ʾҽ��ϴ�.");
		}
		return tuiIprice;
	}
	
	// ���ۿ��� ������ �����Ͽ� �� �Ⱓ������ ���õ� �л��� �Ѽ����Ḧ �������� �Լ�
	public static LinkedHashMap<String, Integer> getSelectedTotal(String choiceStart, String choiceEnd, String studentId) {
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
						selectTotal = "select total, date_format(payDate, '%Y-%m') from tuitiontbl where payDate like '"
								+ i + "-0" + j + "-%%' and studentId ='" + studentId + "'";
					} else {
						selectTotal = "select total, date_format(payDate, '%Y-%m') from tuitiontbl where payDate like '"
								+ i + "-" + j + "-%%' and studentId ='" + studentId +"'";
					}
					ps = connection.prepareStatement(selectTotal);
					rs = ps.executeQuery();
					while (rs.next()) {
						tuiTotal.put(rs.getString(2), rs.getInt(1));
					}
				}
			}
			if (rs == null) {
				StudentController.callAlert("�˸�:���ۿ��� �������� �ʽ��ϴ�.");
				return null;
			}
		} catch (Exception e) {
			StudentController.callAlert("�˸�:�����Ͻ� �޿� �Ѽ����ᰡ ��ϵ��� �ʾҽ��ϴ�.");
		}
		return tuiTotal;
	}


	// 3. �к������� �����ϴ� �Լ�
	public static int deleteTuitionFromBalletDB(String studentId, String payDate) {
		// 3.1 �����ͺ��̽��� Ʃ�̼����̺� �ִ� �����͸� �����ϴ� ������
		String deleteTuition = "delete from tuitiontbl where studentId=? and payDate=? ";
		// 3.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 3.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(deleteTuition);
			// 3.4 �������� ���������͸� �����Ѵ�.
			ps.setString(1, studentId);
			ps.setString(2, payDate);
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

	// 4. �к������� �����ϴ� �Լ�
	public static int updateTuitionSet(Tuition tuition, Tuition selectedTuition) {
		// 4.1 �����ͺ��̽��� Ʃ�̼����̺��� �����ϴ� ������
		StringBuffer updateTuition = new StringBuffer();
		updateTuition.append("update tuitiontbl set ");
		updateTuition.append(
				"individual=?,opus=?,pprice=?,iprice=?,oprice=?,total=?,payDate=? where studentId=? and payDate=? ");
		// 4.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 4.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(updateTuition.toString());
			// 4.4 �������� ���������͸� �����Ѵ�.
			ps.setString(1, tuition.getIndividual());
			ps.setString(2, tuition.getOpus());
			ps.setInt(3, tuition.getPprice());
			ps.setInt(4, tuition.getIprice());
			ps.setInt(5, tuition.getOprice());
			ps.setInt(6, tuition.getTotal());
			ps.setString(7, tuition.getPayDate());
			ps.setString(8, tuition.getStudentId());
			ps.setString(9, selectedTuition.getPayDate());

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
