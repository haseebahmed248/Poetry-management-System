package bll;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dal.stub.DatabaseStubDAO;

class VerseRootTest {

	private static VerseRootBO verseRootBO;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		verseRootBO=new VerseRootBO(new DatabaseStubDAO());
	}

	@Test
	void testInsertVerseRootWithUnverifiedRoot() {
		assertEquals(verseRootBO.insertVerseRoot(1, 1),true);
	}
	
	@Test
	void testInsertVerseRootWithVerifiedRoot() {
		assertEquals(verseRootBO.insertVerseRoot(1, 1),false);
	}
}