package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CartVo;

public class CartDao {

	public void insert(CartVo vo) {
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = UserDao.getConnection();

			String sql1 = "insert into cart values (?, ?, ?)";
			pstmt = conn.prepareStatement(sql1);

			pstmt.setInt(1, vo.getQuantity());
			pstmt.setLong(2, vo.getUserNo());
			pstmt.setLong(3, vo.getBookNo());
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
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	public List<CartVo> findByUserNo(Long no) {
		List<CartVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = UserDao.getConnection();

			String sql = "select c.quantity, c.book_no, b.title from cart c, book b where c.book_no = b.no and c.user_no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int quantity = rs.getInt(1);
				Long bookNo = rs.getLong(2);
				String title = rs.getString(3);

				CartVo vo = new CartVo();
				vo.setBookNo(bookNo);
				vo.setBookTitle(title);
				vo.setQuantity(quantity);

				result.add(vo);
			}

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

	public void deleteByUserNoAndBookNo(Long userNo, Long bookNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = UserDao.getConnection();

			String sql = "delete from cart where user_no = ? and book_no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, userNo);
			pstmt.setLong(2, bookNo);
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
