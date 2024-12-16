package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CategoryVo;

public class CategoryDao {

	public int insert(CategoryVo vo) {
		int result = 0;

		try (Connection conn = UserDao.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("insert into category values (null, ?) ");
				PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");) {

			pstmt.setString(1, vo.getName());

			result = pstmt.executeUpdate();

			ResultSet rs = pstmt2.executeQuery();
			vo.setNo(rs.next() ? rs.getLong(1) : null);

			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;

	}

	public List<CategoryVo> findAll() {
		List<CategoryVo> result = new ArrayList<>();

		try (Connection conn = UserDao.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("select no, category from category order by no desc");) {

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);

				CategoryVo vo = new CategoryVo();
				vo.setNo(no);
				vo.setName(name);

				result.add(vo);
			}
			rs.close();

		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

		return result;
	}

	public void deleteByNo(Long no) {
		try (Connection conn = UserDao.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from category where no = ?");) {

			pstmt.setLong(1, no);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

	}

}
