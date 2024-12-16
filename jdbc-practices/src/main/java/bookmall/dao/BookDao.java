package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bookmall.vo.BookVo;

public class BookDao {

	public Boolean insert(BookVo vo) {
		boolean result = false;
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;

		try {
			conn = UserDao.getConnection();

			String sql1 = "insert into book values (null, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql1);

			pstmt.setString(1, vo.getTitle());
			pstmt.setInt(2, vo.getPrice());
			pstmt.setLong(3, vo.getCategoryNo());

			int count = pstmt.executeUpdate();

			String sql2 = "select last_insert_id() from dual";
			pstmt2 = conn.prepareStatement(sql2);

			rs = pstmt2.executeQuery();
			if (rs.next())
				vo.setNo(rs.getLong(1));

			result = count == 1;
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (pstmt2 != null)
					pstmt2.close();
				if (conn != null) {
					conn.close();
				}
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return result;

	}

	public void deleteByNo(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = UserDao.getConnection();

			String sql = "delete from book where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);
			pstmt.executeUpdate();

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

	}
}
