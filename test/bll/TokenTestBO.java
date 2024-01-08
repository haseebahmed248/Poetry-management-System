package bll;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dal.stub.DatabaseStubDAO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TokenTestBO {
	private static TokenBO stubDAO;

	TokenTestBO(){
		DatabaseStubDAO dao = new DatabaseStubDAO();
		stubDAO = new TokenBO(dao);
	}

	@Order(1)
	@DisplayName("Valide insertion of Token")
	@Test
	void valideTokenInsertion() {
		int x = stubDAO.assignRootToken("قال");
		assertTrue(x >= 0);
	}
	
	@Order(2)
	@DisplayName("InValide insertion of Token")
	@Test
	void invalideTokenInsertion() {
		int x = stubDAO.assignRootToken("");
		assertFalse(x >= 0);
	}

}
