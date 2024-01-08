package bll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dal.stub.DatabaseStubDAO;
import transferobjects.RootTO;
import transferobjects.VerseTO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RootBOTest {
	RootsBO rootsBO;
    VerseRootBO verserootBO;
    VerseBO verseBO;

	public RootBOTest() {
		DatabaseStubDAO facadeDAO = new DatabaseStubDAO();
	        rootsBO = new RootsBO(facadeDAO);
	        verserootBO = new VerseRootBO(facadeDAO);
	        verseBO = new VerseBO(facadeDAO);
	}

	@Order(2)
	@Test
	@DisplayName("Add Route  - Valid Insert")
	void testValidAddRoute() {

		String root_word = "Route7";

		int result = rootsBO.insert_Root(root_word);
		assertNotEquals(-1, result);

	}

	@Order(5)
	@Test
	@DisplayName("Add Route  - InValid Insert")
	void testInvalidAddRoute()  {
		String invalidRootWord = "";

		int result = rootsBO.insert_Root(invalidRootWord);
		assertEquals(-1, result);
	}

	@Order(4)
	@Test
	@DisplayName("Delete Route - InValid Deletion")
	void testInvalidDeleteRoot() {
		int invalidRootId = -1;

		int result = rootsBO.delete_Root(invalidRootId);
		assertEquals(-1, result);
	}
	
	@Order(10)
	@Test
	@DisplayName("Delete Route - Valid Deletion")
	void testValidDeleteRoot() {
		int validRootId = 1;

		int result = rootsBO.delete_Root(validRootId);

		assertTrue(result > 0, "Expected a positive result indicating successful deletion");
	}

	@Order(1)
	@Test
	@DisplayName("Read All Roots - InValid Read")
	void testReadAllRootsInvalide() {
		List<RootTO> roots = rootsBO.ReadAllRoots();


		assertNotNull(roots, "List of roots should not be null");
		assertTrue(roots.isEmpty(), "List of roots should be empty");

		for (RootTO root : roots) {
			assertNotNull(root.getRoot_id(), "Root_id should not be null");
			assertNotNull(root.getRoot_word(), "Root_word should not be null");
		}
	}
	
	
	@Order(5)
	@Test
	@DisplayName("Read All Roots - Valid Read")
	void testReadAllRoots() {
		List<RootTO> roots = rootsBO.ReadAllRoots();


		assertNotNull(roots, "List of roots should not be null");
		assertFalse(roots.isEmpty(), "List of roots should not be empty");

		for (RootTO root : roots) {
			assertNotNull(root.getRoot_id(), "Root_id should not be null");
			assertNotNull(root.getRoot_word(), "Root_word should not be null");
		}
	}

	@Order(6)
	 @Test
	    @DisplayName("Read All Roots - Successful Read")
	    void testReadAllRoots_Success()  {

	        List<RootTO> mockRoots = Arrays.asList(
	            new RootTO(1, "Root1"),
	            new RootTO(2, "Root2")
	        );
	        List<RootTO> result = rootsBO.ReadAllRoots();

	        assertNotNull(result, "List of roots should not be null");
	        assertFalse(result.isEmpty(), "List of roots should not be empty");
	        for (RootTO root : result) {
	            assertNotNull(root.getRoot_id(), "Root_id should not be null");
	            assertNotNull(root.getRoot_word(), "Root_word should not be null");
	        }
	    }
	 
	 

	 
	 ///
	@Order(7)
	@Test
	@DisplayName("Get Verses By Root - Valid Root ID")
	void testGetVersesByValidRootId() {
	    // Arrange
	    int selectedRootId = 1;

	    // Act
	    List<VerseTO> versesForRoot = verseBO.getVersesByRoot(selectedRootId);

	    // Assert
	    assertNotNull(versesForRoot, "List of verses for the root should not be null");
	    assertFalse(versesForRoot.isEmpty(), "List of verses for the root should not be  empty");

	    for (VerseTO verse : versesForRoot) {
	        assertNotNull(verse.getId(), "Verse ID should not be null");
	        assertNotNull(verse.getMisra_1(), "Misra 1 should not be null");
	        assertNotNull(verse.getMisra_2(), "Misra 2 should not be null");
	    }
	}

	@Order(8)
	@Test
	@DisplayName("Update Root - Valid Update")
	void testValidUpdateRoot() {
	    int rootIdToUpdate = 1;
	    String updatedRootWord = "Route7";

	    rootsBO.update_Root(rootIdToUpdate, updatedRootWord);

	    assertTrue(DatabaseStubDAO.rootMap.containsKey(rootIdToUpdate),
	            "Root with ID should exist in the rootMap after update");

	    RootTO updatedRoot = DatabaseStubDAO.rootMap.get(rootIdToUpdate);
	    assertNotNull(updatedRoot, "Updated root should not be null");
	    assertEquals(updatedRootWord, updatedRoot.getRoot_word(), "Root word should be updated");
	}
	
	@Order(9)
	@Test
	@DisplayName("Update Root - InValid Update")
	void testInValidUpdateRoot() {
	    int rootIdToUpdate = -1;
	    String updatedRootWord = "Route7";

	    rootsBO.update_Root(rootIdToUpdate, updatedRootWord);

	    assertFalse(DatabaseStubDAO.rootMap.containsKey(rootIdToUpdate),
	            "Root with ID should exist in the rootMap after update");
	}

	@Order(11)
	@Test
	@DisplayName("Get Verses By Root - Invalid Root ID")
	void testGetVersesByInvalidRootId() {
		List<VerseTO> versesList=verseBO.getVersesByRoot(1);
		assertFalse(versesList.isEmpty(), "List of verses for the root should  be empty");
	}
	
	
	@Order(12)
	@Test
	@DisplayName("Get Roost Sucess")
	void getRootsTest() {
		String y[] = {" قُرَيطُ "};
		HashMap<String,String[]> x = rootsBO.getRoots(y);
		assertTrue(x!= null);
	}
}
