package bll;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dal.stub.DatabaseStubDAO;

class ImportFileTestBO {
	private static ImportBO stubDAO;

	ImportFileTestBO(){
		DatabaseStubDAO dao = new DatabaseStubDAO();
		stubDAO = new ImportBO(dao);
	}
	@DisplayName("File Uploaded")
	@Test
	void correctPath() {
		boolean x = stubDAO.uploadFile("D:\\poem.txt", null, null, null);
		assertTrue(x);
	}
	
	@DisplayName("File Not Uploaded")
	@Test
	void incorrectPath() {
		boolean x = stubDAO.uploadFile("D:\\p.txt", null, null, null);
		assertFalse(x);
	}

}
