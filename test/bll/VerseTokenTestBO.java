package bll;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dal.stub.DatabaseStubDAO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VerseTokenTestBO {
	private static VerseTokenBO stubDAO;

	VerseTokenTestBO(){
		DatabaseStubDAO dao = new DatabaseStubDAO();
		stubDAO = new VerseTokenBO(dao);
	}
	
	@Order(1)
	@DisplayName("Valide insertion of TokenVerse")
	@Test
	void ValideInsert() {
		boolean x = stubDAO.assignTokenVerse(0, 0);
		assertTrue(x);
	}
	
	@Order(2)
	@DisplayName("InValide insertion of TokenVerse")
	@Test
	void InValideInsert() {
		boolean x = stubDAO.assignTokenVerse(-1, 0);
		assertFalse(x);
	}

}
