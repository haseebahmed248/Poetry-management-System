package bll;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dal.stub.DatabaseStubDAO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RootTokenTestBo {

	private static RootTokenBO stubDAO;

	RootTokenTestBo(){
		DatabaseStubDAO dao = new DatabaseStubDAO();
		stubDAO = new RootTokenBO(dao);
	}
	
	
	@Order(1)
	@DisplayName("Valide insertion of Root Token")
	@Test
	void valideRootTokenInsertion() {
		boolean x = stubDAO.insertRootToken(0, 0);
		assertTrue(x);
	}
	
	@Order(2)
	@DisplayName("InValide insertion of Root Token")
	@Test
	void invalideRootTokenInsertion() {
		boolean x = stubDAO.insertRootToken(-1, 0);
		assertFalse(x);
	}

}
