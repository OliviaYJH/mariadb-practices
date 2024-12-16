package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.OrderBookVo;
import bookmall.vo.OrderVo;

public class OrderDao {

	public int insert(OrderVo vo) {
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		int result = 0;

		try {
			conn = UserDao.getConnection();

			String sql1 = "insert into orders values (null, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql1);

			pstmt.setString(1, vo.getStatus());
			pstmt.setInt(2, vo.getPayment());
			pstmt.setString(3, vo.getShipping());
			pstmt.setString(4, vo.getNumber());
			pstmt.setLong(5, vo.getUserNo());

			result = pstmt.executeUpdate();

			String sql2 = "select last_insert_id()";
			pstmt2 = conn.prepareStatement(sql2);

			rs = pstmt2.executeQuery();
			if (rs.next())
				vo.setNo(rs.getLong(1));

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

		return result;
	}

	public OrderVo findByNoAndUserNo(long orderNo, Long userNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		OrderVo vo = null;
		ResultSet rs = null;

		try {
			conn = UserDao.getConnection();

			String sql = "select no, status, payment, shipping, number from orders where no = ? and user_no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, orderNo);
			pstmt.setLong(2, userNo);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new OrderVo();

				Long no = rs.getLong(1);
				String status = rs.getString(2);
				int payment = rs.getInt(3);
				String shipping = rs.getString(4);
				String number = rs.getString(5);

				vo.setNo(no);
				vo.setStatus(status);
				vo.setPayment(payment);
				vo.setShipping(shipping);
				vo.setNumber(number);
			}

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

		return vo;
	}

	public void deleteByNo(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = UserDao.getConnection();

			String sql = "delete from orders where no = ?";
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

	public void deleteBooksByNo(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = UserDao.getConnection();

			String sql = "delete from orders_book where orders_no = ?";
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

	public void insertBook(OrderBookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = UserDao.getConnection();

			String sql = "insert into orders_book values(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, vo.getQuantity());
			pstmt.setInt(2, vo.getPrice());
			pstmt.setLong(3, vo.getOrderNo());
			pstmt.setLong(4, vo.getBookNo());

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

	public List<OrderBookVo> findBooksByNoAndUserNo(Long orderNo, Long userNo) {
		List<OrderBookVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = UserDao.getConnection();

			String sql = "select ob.quantity, ob.price, ob.book_no, o.no, b.title "
					+ "from orders o, orders_book ob, book b "
					+ "where o.no = ob.orders_no and ob.book_no = b.no and ob.orders_no = ? and o.user_no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, orderNo);
			pstmt.setLong(2, userNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int quantity = rs.getInt(1);
				int price = rs.getInt(2);
				Long bookNo = rs.getLong(3);
				Long order_no = rs.getLong(4);
				String bookTitle = rs.getString(5);

				OrderBookVo vo = new OrderBookVo();
				vo.setQuantity(quantity);
				vo.setPrice(price);
				vo.setBookNo(bookNo);
				vo.setOrderNo(order_no);
				vo.setBookTitle(bookTitle);

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

}
