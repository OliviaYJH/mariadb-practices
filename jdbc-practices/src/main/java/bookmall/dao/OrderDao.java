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
		int result = 0;

		try (Connection conn = UserDao.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("insert into orders values (null, ?, ?, ?, ?, ?)");
				PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id()");) {

			pstmt.setString(1, vo.getStatus());
			pstmt.setInt(2, vo.getPayment());
			pstmt.setString(3, vo.getShipping());
			pstmt.setString(4, vo.getNumber());
			pstmt.setLong(5, vo.getUserNo());

			result = pstmt.executeUpdate();

			ResultSet rs = pstmt2.executeQuery();
			vo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

		return result;
	}

	public OrderVo findByNoAndUserNo(long orderNo, Long userNo) {
		OrderVo vo = null;

		try (Connection conn = UserDao.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"select no, status, payment, shipping, number from orders where no = ? and user_no = ?");) {

			pstmt.setLong(1, orderNo);
			pstmt.setLong(2, userNo);

			ResultSet rs = pstmt.executeQuery();

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
			rs.close();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

		return vo;
	}

	public void deleteByNo(Long no) {

		try (Connection conn = UserDao.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from orders where no = ?");) {

			pstmt.setLong(1, no);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

	}

	public void deleteBooksByNo(Long no) {

		try (Connection conn = UserDao.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from orders_book where orders_no = ?");) {

			pstmt.setLong(1, no);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

	}

	public void insertBook(OrderBookVo vo) {

		try (Connection conn = UserDao.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("insert into orders_book values(?, ?, ?, ?)");) {

			pstmt.setInt(1, vo.getQuantity());
			pstmt.setInt(2, vo.getPrice());
			pstmt.setLong(3, vo.getOrderNo());
			pstmt.setLong(4, vo.getBookNo());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

	}

	public List<OrderBookVo> findBooksByNoAndUserNo(Long orderNo, Long userNo) {
		List<OrderBookVo> result = new ArrayList<>();

		try (Connection conn = UserDao.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("select ob.quantity, ob.price, ob.book_no, o.no, b.title "
								+ "from orders o, orders_book ob, book b "
								+ "where o.no = ob.orders_no and ob.book_no = b.no and ob.orders_no = ? and o.user_no = ?");) {

			pstmt.setLong(1, orderNo);
			pstmt.setLong(2, userNo);
			ResultSet rs = pstmt.executeQuery();

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
			rs.close();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

		return result;
	}

}
