package bll;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dal.IFacadeDAO;
import transferobjects.BookTO;

public class BookBO {
	private static Logger log = LogManager.getLogger(BookBO.class.getName());
	private IFacadeDAO BOOKData;

	public BookBO(IFacadeDAO BOOKData) {
		this.BOOKData = BOOKData;
	}
  //insert Book in database
	public int insertBook(String title, String authorName, String authorDeathDate) {
		try {
			int check = BOOKData.insertBook(title, authorName, authorDeathDate);
			if(check >=0) {
				log.info("Insertion in book was success! ");
				return check;
			}else {
				log.debug("Insertion in book was NOT success! ");
				return check;
			}
		} catch (Exception e) {
			log.error("Insertion in book Ended with Exception! ");
			e.printStackTrace();
		}
		return -1;
	}
	//Update Book From database
	public boolean  updateBook(int bookId, String newTitle, String newAuthorName, String newAuthorDeathDate) {
		try {
			boolean check = BOOKData.updateBook(bookId, newTitle, newAuthorName, newAuthorDeathDate);
			if(check ) {
			log.info("UPDATION in book was success! ");
            return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			log.error("UPDATION in book was NOT success! ");
			e.printStackTrace();

		}
        return false;

	}
	//Delete Book in database
	public boolean deleteBook(int bookId) {
		try {
			boolean check = BOOKData.deleteBook(bookId);
			if(check) {
			log.info("Deletion in book was success! with Book ID "+ bookId);
            return true;
			}else {
				return false;
			}

		} catch (Exception e) {
			log.error("Deletion in book was NOT success! In Book ID "+ bookId);
			e.printStackTrace();
			return false;
		}
	}
	//Display all Books available in database
	public List<BookTO> displayAllBooks() {
		try {
			List<BookTO> data = BOOKData.displayAllBooks();
			if(!data.isEmpty()) {
				log.info("Data Retrival Form Book was sucess");
				return data;
			}else {
				log.debug("Data Retrival Form Book was NOT sucess");
			}
		} catch (Exception e) {

			log.error("Data Retrival Form Book was NOT sucess And Ended with Exception");
		    e.printStackTrace();
		    
		}
		return null;

	}

}
