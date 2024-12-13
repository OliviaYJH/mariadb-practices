package driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDriverTest {

	public static void main(String[] args) {
		Connection conn = null;

		try {
			// 1. JDBC Driver 로딩
			Class.forName("driver.MyDriver"); // driver 로딩

			// 2. 연결하기
			 String url = "jdbc:mydb://127.0.0.1:1234/webdb";
			 conn = DriverManager.getConnection(url, "webdb", "webdb");

			 System.out.println("연결성공!!!");

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}


	}

}
