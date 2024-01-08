package bll;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dal.stub.DatabaseStubDAO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class POSTestBO {
	private static POSBO stubDAO;

	POSTestBO(){
		DatabaseStubDAO dao = new DatabaseStubDAO();
		stubDAO = new POSBO(dao);
	}
	
	@Order(1)
	@DisplayName("valide Insetion of POS")
	@Test
	void validInsertion() {
		int x = stubDAO.insertPOS(" قُرَيطُ ");
		assertTrue(x>= 0);
	}
	
	@Order(2)
	@DisplayName("Invalide Insetion of POS")
	@Test
	void invalideInsertion() {
		int x = stubDAO.insertPOS("");
		assertTrue(x== -1);
	}
	
	@Order(3)
	@DisplayName("Excpetion on Insetion of POS")
	@Test
	void exceptionInsertion() {
		int x = stubDAO.insertPOS("قال");
		assertTrue(x == -2);
	}
	
	@Order(4)
	@DisplayName("Getting valide POS ")
	@Test
	void getValideInsertion() {
		String x = stubDAO.getPOS("قال");
		assertTrue(!x.isEmpty());
	}
	
	@Order(5)
	@DisplayName("Getting invalide POS ")
	@Test
	void getInValideInsertion() {
		String x = stubDAO.getPOS("");
		assertTrue(x.isEmpty());
	}
}
