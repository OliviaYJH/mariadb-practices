package bookshop.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import bookshop.vo.AuthorVo;

public class AuthorDaoTest {
	private static AuthorVo vo = new AuthorVo();
	private static AuthorDao dao = new AuthorDao();
	
	@Test
	public void insertTest() {
		vo.setName("칼세이건");
		dao.insert(vo);
		
		assertNotNull(vo.getId());
	}
	
	@AfterAll
	public static void cleanup() {
		dao.deleteById(vo.getId());
	}
}
