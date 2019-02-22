package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Model.Student;

public class StudentDAO {
	public static ArrayList<Student> stuArrayList = new ArrayList<>();

	// 1. �л����� ����ϴ� �Լ�
	public static int insertStudentIntoBalletDB(Student student) {
		StringBuffer insertStudent = new StringBuffer();
		insertStudent.append("insert into studenttbl ");
		insertStudent.append("(studentId,name,age,phone,address,party,image) ");
		insertStudent.append("values ");
		insertStudent.append("(?,?,?,?,?,?,?) ");

		// 1.1 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 1.2 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(insertStudent.toString());
			// 1.3 �������� ���������͸� �����Ѵ�.
			ps.setString(1, student.getStudentId());
			ps.setString(2, student.getName());
			ps.setInt(3, student.getAge());
			ps.setString(4, student.getPhone());
			ps.setString(5, student.getAddress());
			ps.setString(6, student.getParty());
			ps.setString(7, student.getImage());

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

	// 2. �����ͺ��̽����� ��ü������ ��� �������� �Լ�
	public static ArrayList<Student> getStudentFromBalletDB() {
		// 2.1 �����ͺ��̽��� ��Ʃ��Ʈ���̺� �� �ִ� �����͸� ��� �������� ������
		String selectStudent = "select studentId, name, age, phone, address, party, image from studenttbl order by age desc ";
		// 2.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 2.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		// 2.4 �������� �����ϰ� ���� ������ �����͸� ����ִ� �÷��������ӿ�ũ
		// ResultSet �� �����.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectStudent);
			// 2.5���������͸� ������ �������� �����Ѵ�.
			// executeQuery() => �����ͺ��̽����� ����� �����ö� ����ϴ� ������
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet �������� ����:select ������ ����");
				return null;
			}
			while (rs.next()) {
				Student student = new Student(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7));
				stuArrayList.add(student);
			}
		} catch (Exception e) {
			StudentController.callAlert("getStudentFromBalletDB����:Ȯ�ιٶ�");
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

		return stuArrayList;
	}
	
	// 3. ���̺�信�� ������ ���ڵ带 �����ͺ��̽����� �����ϴ� �Լ�
	public static int deleteStudentFromDB(String studentId) {
		// 3.1 �����ͺ��̽��� ��Ʃ��Ʈ���̺� �ִ� �����͸� �����ϴ� ������
		String deleteStudent = "delete from studenttbl where studentId = ? ";
		// 3.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 3.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(deleteStudent);
			// 3.4 �������� ���������͸� �����Ѵ�.
			ps.setString(1, studentId);
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
	
	// 4. ���̺�� ������ ������ �����ͺ��̽� �����͸� �����ϴ� �Լ�
	public static int updateStudentSet(Student student, Student selectedStudent) {
		// 4.1 �����ͺ��̽��� ��Ʃ��Ʈ���̺��� �����ϴ� ������
		StringBuffer updateStudent = new StringBuffer();
		updateStudent.append("update studenttbl set ");
		updateStudent.append(	"studentId=?,name=?, age=?,phone=?,address=?,party=?,image=? where studentId=? ");
		// 4.2 �����ͺ��̽� Connection�� �����´�.
		Connection connection = null;
		// 4.3 �������� ������ Statement�� �����.
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(updateStudent.toString());
			// 4.4 �������� ���������͸� �����Ѵ�.
			ps.setString(1, student.getStudentId());
			ps.setString(2, student.getName());
			ps.setInt(3, student.getAge());
			ps.setString(4, student.getPhone());
			ps.setString(5, student.getAddress());
			ps.setString(6, student.getParty());
			ps.setString(7, student.getImage());
			ps.setString(8, selectedStudent.getStudentId());

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
