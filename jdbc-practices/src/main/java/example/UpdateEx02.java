package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateEx02 {

	public static void main(String[] args) {
		update(new DepartmentVo(1L, "경영지원"));
	}

	public static boolean update(DepartmentVo vo) {
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
			String sql = "update department set name = ? where id = ?"; // ?는 변수
			pstmt = conn.prepareStatement(sql);

			// 4. Parameter Binding
			pstmt.setString(1, vo.getName());
			pstmt.setLong(2, vo.getId());

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
