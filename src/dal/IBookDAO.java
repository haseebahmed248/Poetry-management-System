package dal;

import java.util.List;

import transferobjects.BookTO;

public interface IBookDAO {
	public int insertBook(String title, String authorName, String authorDeathDate) throws Exception;
	public boolean updateBook(int bookId, String newTitle, String newAuthorName, String newAuthorDeathDate)throws Exception;
	public boolean deleteBook(int bookId)throws Exception;
	public List<BookTO> displayAllBooks()throws Exception;
}
