package dal;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import transferobjects.BookTO;
import transferobjects.PoemTO;
import transferobjects.RootTO;
import transferobjects.VerseTO;

public interface IFacadeDAO extends IPoem, IVerse,IBookDAO,IRootDAO,IRootToken,IVerseToken,IToken,IVerseRoot,IPOS,IPOSTokenDAO {
    public int addPoem(int bookID, String title) throws SQLException;
    public List<Integer> addVerses(int poemID, List<String> verses) throws SQLException;

    public List<PoemTO> displayPoems(int bookID) throws SQLException;
    public List<VerseTO> displayVerses(int poem_id) throws SQLException;
   

    public boolean updatePoem(int id, String title) throws SQLException;
    public boolean deletePoem(int id) throws SQLException;

    public int addVerse(int poem_id,String misra_1,String misra_2) throws SQLException;
    public boolean updateVerse(int id, String misra_1,String misra_2) throws SQLException;
    public boolean deleteVerse(int id) throws SQLException; 
    
    
    public int insertBook(String title, String authorName, String authorDeathDate) throws Exception;
	public boolean updateBook(int bookId, String newTitle, String newAuthorName, String newAuthorDeathDate)throws Exception;
	public boolean deleteBook(int bookId)throws Exception;
	public List<BookTO> displayAllBooks()throws Exception;
	
	public int insert_Root(String root_Word) throws SQLException;
	public int delete_Root(int root_id) throws SQLException;
	public boolean update_Root(int root_id, String updatedRootWord) throws SQLException;
	public List<RootTO> Read_Root(int verse_id) throws SQLException;
	public List<RootTO> ReadAllRoots() throws SQLException;
	
	public boolean importFile(String name,DefaultTableModel bookTableModel,DefaultTableModel poemTableModel,DefaultTableModel verseTableModel) throws Exception;
	
	
	public int insertToken(String tokenizedWord)throws SQLException;
	
	public boolean insertRootToken(int tokenId,int rootId)throws SQLException;
	
	public Map<Integer,String> getRootsOfVerse(int verseId) throws SQLException;
	public boolean insertVerseRoot(int verseId, int rootId)throws SQLException;

	public List<VerseTO> getVersesByRoot(int selectedRootId)throws SQLException;
	 public List<PoemTO> getPoemsByVerseId(int verseId) throws SQLException;
	 
	 public boolean insertPOSToken(int posID,int tokenId)throws SQLException ;
	 public String getPOS(String token) throws Exception;
	 public int insertPOS(String word)throws SQLException;
	 public boolean assignTokenVerse(int verseId, int tokenId) throws SQLException;
}
