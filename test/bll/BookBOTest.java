package bll;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dal.stub.DatabaseStubDAO;
import transferobjects.BookTO;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookBOTest {
	private static BookBO bookDataDAOStub; 
    private List<BookTO> bookList = new ArrayList<>();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		DatabaseStubDAO stub = new DatabaseStubDAO();
		bookDataDAOStub=new BookBO(stub);
	}

	 
	@Test
	@Order(1)
	void testEmptyDisplayAllBooks() {
	   
	        List<BookTO> retrievedBooks = bookDataDAOStub.displayAllBooks();
	        assertTrue(retrievedBooks == null);

	}
	
	 @Test
	 @Order(3)
	void testInsertBook_Success() 
	 {
        int result1 = bookDataDAOStub.insertBook("مؤلف عينة", "عنوان عينة", "2023-12-31");
        assertEquals(1, result1); // Assuming the stub always returns 1 as the book ID
        int result2 = bookDataDAOStub.insertBook("كاتب مثالي", "عنوان مثالي", "2023-12-31");
        assertEquals(2, result2); // Assuming the stub always returns 1 as the book ID
        int result3 = bookDataDAOStub.insertBook("مؤلف رائع", "عنوان رائع", "2023-12-31");
        assertEquals(3, result3); // Assuming the stub always returns 1 as the book ID
    }
	
	 @Test
	 @Order(4)
    void testInsertBook_EmptyFields() {
		 int result = bookDataDAOStub.insertBook("", "عنوان مدهش", "2023-12-31");
        assertEquals(-1, result);
    }
	 
	 @Test
	 @Order(5)
	 void testDisplayAllBooks() {

	         List<BookTO> retrievedBooks = bookDataDAOStub.displayAllBooks();
	         List<BookTO> expectedBooks = bookDataDAOStub.displayAllBooks();
	         assertEquals(expectedBooks.size(), retrievedBooks.size());
	 }
	
	 
	 @Order(6)
	 @Test
	    void testUpdateBook_Success() {
	        // Assuming bookId 1 exists in the list
		 boolean x = bookDataDAOStub.updateBook(1, "عنوان محدث", "مؤلف محدث", "2023-12-31");
	        assertTrue(x); 
	    }

	 @Test
	 @Order(7)
	    void testUpdateBook_NonExistentRecord() {
		 boolean result = bookDataDAOStub.updateBook(100, "عنوان محدث", "مؤلف محدث", "2023-12-31");
	        assertFalse(result); 
	    }

	 @Test
	 @Order(8)
	    void testUpdateBook_EmptyFields() {
	        // Assuming bookId 1 exists in the list
	    	
		 boolean result = bookDataDAOStub.updateBook(1, "", "مؤلف محدث", "2023-12-31");
		 assertFalse(result); 

		 result = bookDataDAOStub.updateBook(1, "عنوان محدث", "", "2023-12-31");
		 assertFalse(result); 

		 result = bookDataDAOStub.updateBook(1, "عنوان محدث", "مؤلف محدث", "");
		 assertFalse(result); 

	    }

	 @Test
	 @Order(9)
	    void testUpdateBook_NullFields() {
	    	
	        // Assuming bookId 1 exists in the list
	    	
	        boolean result = bookDataDAOStub.updateBook(1, null, "Updated Author", "2023-12-31");
	        assertFalse(result); // Expecting false for null title

	        result = bookDataDAOStub.updateBook(1, "عنوان محدث", null, "2023-12-31");
	        assertFalse(result); // Expecting false for null authorName

	        result = bookDataDAOStub.updateBook(1,"عنوان محدث", "مؤلف محدث", null);
	        assertFalse(result); // Expecting false for null authorDeathDate
	    }
	
	 
	

	
	 @Test
	 @Order(10)
	    void testDeleteBook_Success() {
	        boolean result = false;
	        System.out.println(result);
	        assertTrue(bookDataDAOStub.deleteBook(1));
	    }

	 @Test
	 @Order(11)
	    void testDeleteBook_NonExistentRecord() {
	        boolean result = bookDataDAOStub.deleteBook(-1);
	        assertFalse(result); 
	    }
	 
	    }
	   


