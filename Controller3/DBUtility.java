package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtility {
	public static Connection getConnection() {
		Connection connection = null;
		// 1. MySql database class�� �ε��Ѵ�.
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 2. �ּ�, ���̵�, ��й�ȣ�� ���ؼ� ���ӿ�û�Ѵ�.
			connection = DriverManager.getConnection("jdbc:mysql://localhost/balletdb", "root", "123456");
			//StudentController.callAlert("���Ἲ��:�����ͺ��̽� ����");
		} catch (Exception e) {
			StudentController.callAlert("�������:�����ͺ��̽� ����");
			return null;
		}
		return connection;
	}
}
