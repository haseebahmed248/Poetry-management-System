package bll;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dal.stub.DatabaseStubDAO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class POSTokenTestBO {
	private static POSTokenBO stubDAO;

	POSTokenTestBO(){
		DatabaseStubDAO dao = new DatabaseStubDAO();
		stubDAO = new POSTokenBO(dao);
	}
	
	@Order(1)
	@DisplayName("Valide insetion of PosToken ")
	@Test
	void valideInsertPosToken() {
		assertTrue(stubDAO.insertPOSToken(0, 0));
	}

	@Order(2)
	@DisplayName("InValide insetion of PosToken ")
	@Test
	void invalideInsertPosToken() {
		assertFalse(stubDAO.insertPOSToken(0, -1));
	}
}
