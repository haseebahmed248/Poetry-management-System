package bll;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dal.stub.DatabaseStubDAO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VerseBOTest {
	private static VerseBO verseBO;
	private static int VERSE_ID;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		verseBO=new VerseBO(new DatabaseStubDAO());
	}
	
	@Test
	@Order(1)
	void testAddWithNewVerse() {
		VERSE_ID=verseBO.addVerse(1, "Misra XYZ","Misra LMN" );
		assertTrue(VERSE_ID > 0);
	}
	
	@Test
	@Order(2)
	void testAddWithDuplicateVerse() {
		assertEquals(verseBO.addVerse(1, "Misra XYZ","Misra LMN"),-1);
	}

	@Test
	@Order(3)
	void testUpdateWithExistingVerse() {
		assertEquals(verseBO.updateVerse(VERSE_ID, "Misra ABC","Misra DEF"),true);
	}
	
	@Test
	@Order(4)
	void testDisplayVersesWithinExistingPoem() {
		assertNotNull(verseBO.displayVerses(1));
	}
	
	@Test
	void testUpdateWithNonExistingVerse() {
		assertEquals(verseBO.updateVerse(10, "Misra ABC","Misra DEF"),false);
	}

	@Test
	void testDisplayVersesWithinNonExistingPoem() {
		assertNull(verseBO.displayVerses(9));
	}
	
	@Test
	void testDeleteWithExistingVerse() {
		assertEquals(verseBO.deleteVerse(VERSE_ID),true);
	}
	
	@Test
	void testDeleteWithNonExistingVerse() {
		assertEquals(verseBO.deleteVerse(9),false);
	}
	
	@Test
	void testInsertionMultipleVerses() {
		List<String> x = new ArrayList<>();
		x.add("1");
		x.add("2");
		List<Integer> y =verseBO.addVerses(1, x); 
		assertNotNull(y);
	}
	
	@Test
	void testInsertionMultipleVersesInvalide() {
		List<String> x = new ArrayList<>();
		x.add("1");
		x.add("2");
		List<Integer> y =verseBO.addVerses(-1, x); 
		assertNull(y);
	}
	

}