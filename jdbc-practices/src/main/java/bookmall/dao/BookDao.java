package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bookmall.vo.BookVo;

public class BookDao {

	public Boolean insert(BookVo vo) {
		boolean result = false;

		try (Connection conn = UserDao.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("insert into book values (null, ?, ?, ?)");
				PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");) {

			pstmt.setString(1, vo.getTitle());
			pstmt.setInt(2, vo.getPrice());
			pstmt.setLong(3, vo.getCategoryNo());

			int count = pstmt.executeUpdate();

			ResultSet rs = pstmt2.executeQuery();
			vo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();

			result = count == 1;
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

		return result;

	}

	public void deleteByNo(Long no) {

		try (Connection conn = UserDao.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from book where no = ?");) {

			pstmt.setLong(1, no);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

	}
}
