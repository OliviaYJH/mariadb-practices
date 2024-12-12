package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectEx02 {

	public static void main(String[] args) {
		List<DepartmentVo> result = search("팀");
		for (DepartmentVo vo : result) {
			System.out.println(vo);
		}
	}

	public static List<DepartmentVo> search(String keyword) {
		List<DepartmentVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver"); // driver 로딩

			// 2. 연결하기
			String url = "jdbc:mariadb://192.168.0.19:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb"); // (url, username, pw)

			// 3. Statement 준비하기
			String sql = "select id, name from department where name like ?"; // ?는 변수
			pstmt = conn.prepareStatement(sql);

			// 4. Parameter Binding
			pstmt.setString(1, "%" + keyword + "%");

			// 5.SQL 실행
			rs = pstmt.executeQuery(); // data 변경

			// 6. 결과 처리
			while (rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);

				// 객체에 담기
				DepartmentVo vo = new DepartmentVo(id, name);
				result.add(vo);
			}

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
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
