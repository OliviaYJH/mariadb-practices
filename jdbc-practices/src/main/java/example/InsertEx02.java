package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertEx02 {
	public static void main(String[] args) {
		System.out.println(insert(new DepartmentVo("개발1팀")));
		System.out.println(insert(new DepartmentVo("개발2팀")));
	}

	public static boolean insert(DepartmentVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver"); // driver 로딩

			// 2. 연결하기
			String url = "jdbc:mariadb://192.168.0.19:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb"); // (url, username, pw)

			// 3. Statement 준비하기
			String sql = "insert into department values(null, ?)";
			pstmt = conn.prepareStatement(sql);

			// 4. Parameter Binding
			pstmt.setString(1, vo.getName());

			// 5. SQL 실행
			int count = pstmt.executeUpdate(); // data 변경

			result = count == 1;
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return result;
	}

}
