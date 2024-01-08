package bll;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dal.stub.DatabaseStubDAO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PoemBOTest {

	private static PoemBO poemBO;
	private static int POEM_ID;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		poemBO=new PoemBO(new DatabaseStubDAO());
	}

	@Test
	@Order(1)
	void testAddPoemWithNewTitle() {
		POEM_ID=poemBO.addPoem(1, "Poem XYZ");
		assertTrue(POEM_ID>0);
	}
	@Test
	@Order(2)
	void testAddPoemWithDuplicateTitle() {
		assertEquals(poemBO.addPoem(1, "Poem XYZ"),-1);
	}

	@Test
	@Order(4)
	void testDisplayPoemsWithinExistingBook() {
		assertNotNull(poemBO.displayPoems(1));
	}

	@Test
	@Order(3)
	void testUpdateWithExistingPoem() {
		assertEquals(poemBO.updatePoem(POEM_ID, "Poem ABC"),true);
	}
	
	@Test
	void testUpdateWithNonExistingPoem() {
		assertEquals(poemBO.updatePoem(10, "Poem ABC"),false);
	}
	
	@Test
	void testDisplayPoemsWithNonExistingBook() {
		assertNull(poemBO.displayPoems(5));
	}
	
	@Test
	void testDeleteWithExistingPoem() {
		assertEquals(poemBO.deletePoem(POEM_ID),true);
	}
	
	@Test
	void testDeleteWithNonExistingPoem() {
		assertEquals(poemBO.deletePoem(9),false);
	}
}