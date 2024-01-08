package dal;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import transferobjects.BookTO;
import transferobjects.PoemTO;
import transferobjects.RootTO;
import transferobjects.VerseTO;

public class FacadeDAO implements IFacadeDAO {
    private IPoem poemDAO;
    private IVerse verseDAO;
    private IBookDAO bookDataDAO;
    private IRootDAO rootDAO;
    private IImportFileDAO importFileDAO;
    private IToken assignToken;
	private IVerseToken verseTokenTable;
	private IRootToken rootToken;
	private IVerseRoot verseRoot;
	private IPOS pos;
	private IPOSTokenDAO posToken;
    public FacadeDAO(IPoem poemDAO, IVerse verseDAO,IBookDAO bookDataDAO,IRootDAO rootDAO,IImportFileDAO importFileDAO,IToken assignToken,IVerseToken verseTokenTable,IRootToken rootToken,IVerseRoot verseRoot,IPOSTokenDAO posToken,IPOS pos) {
        this.poemDAO = poemDAO;
        this.verseDAO = verseDAO;
        this.bookDataDAO=bookDataDAO;
        this.rootDAO=rootDAO;
        this.importFileDAO = importFileDAO;
        this.assignToken = assignToken;
        this.verseTokenTable = verseTokenTable;
        this.rootToken = rootToken;
        this.verseRoot=verseRoot;
        this.pos = pos;
        this.posToken = posToken;
    }

    @Override
    public int addPoem(int bookID, String title) throws SQLException{
        return this.poemDAO.addPoem(bookID, title);
    }

    @Override
    public List<Integer> addVerses(int poemID, List<String> verses) throws SQLException{
        return this.verseDAO.addVerses(poemID, verses);
    }
    
    @Override
    public List<PoemTO> displayPoems(int bookID) throws SQLException{
        return this.poemDAO.displayPoems(bookID);
    }
    @Override
    public List<VerseTO> displayVerses(int poem_id) throws SQLException{
        return this.verseDAO.displayVerses(poem_id);
    }

    @Override
    public boolean updatePoem(int id, String title) throws SQLException{
        return poemDAO.updatePoem(id, title);
    }
    @Override
    public boolean deletePoem(int id) throws SQLException{
        return poemDAO.deletePoem(id);
    }

    @Override
    public int addVerse(int poem_id,String misra_1,String misra_2) throws SQLException{
        return verseDAO.addVerse(poem_id,misra_1,misra_2);
    }
    
    @Override
    public boolean updateVerse(int id, String misra_1,String misra_2) throws SQLException{
        return verseDAO.updateVerse(id, misra_1, misra_2);
    }
    @Override
    public boolean deleteVerse(int id) throws SQLException{
        return verseDAO.deleteVerse(id);
    }
    
    @Override
    public int insertBook(String title, String authorName, String authorDeathDate) throws Exception {
    	return bookDataDAO.insertBook(title, authorName, authorDeathDate);
    }

    @Override
	public boolean updateBook(int bookId, String newTitle, String newAuthorName, String newAuthorDeathDate)throws Exception {
		return bookDataDAO.updateBook(bookId, newTitle, newAuthorName, newAuthorDeathDate);
	}
    
    @Override
	public boolean deleteBook(int bookId)throws Exception {
		 return bookDataDAO.deleteBook(bookId);
	}

    @Override
	public List<BookTO> displayAllBooks()throws Exception{
		return bookDataDAO.displayAllBooks();
	}
    
    @Override
    public int insert_Root(String root_Word) throws SQLException{
    	return rootDAO.insert_Root(root_Word);
    }
    @Override
	public int delete_Root(int root_id) throws SQLException{
		return rootDAO.delete_Root(root_id);
	}
    @Override
	public boolean update_Root(int root_id, String updatedRootWord) throws SQLException{
		return rootDAO.update_Root(root_id, updatedRootWord);
	}
    @Override
	public List<RootTO> Read_Root(int verse_id) throws SQLException{
		return rootDAO.Read_Root(verse_id);
	}
    @Override
	public List<RootTO> ReadAllRoots() throws SQLException{
		return rootDAO.ReadAllRoots();
	}

	@Override
	public boolean importFile(String name,DefaultTableModel bookTableModel,DefaultTableModel poemTableModel,DefaultTableModel verseTableModel) throws Exception {
		return importFileDAO.importFromFile(name, bookTableModel, poemTableModel, verseTableModel);
	}
	@Override
	public int insertToken( String tokenizedWord) throws SQLException{
		return assignToken.insertToken( tokenizedWord);
	}

	@Override
	public HashMap<String,String[]> getRoots(String[] tokens) {
		return rootDAO.getRoots(tokens);
	}

	@Override
	public boolean assignTokenVerse(int verseId, int tokenId) throws SQLException{
		return verseTokenTable.assignTokenVerse(verseId, tokenId);
	}

	@Override
	public String getPOS(String word) throws Exception {
		return pos.getPOS(word);
	}

	@Override
	public boolean insertRootToken(int tokenId, int rootId)throws SQLException {
		return rootToken.insertRootToken(tokenId, rootId);
	}
	@Override
	public Map<Integer,String> getRootsOfVerse(int verseId) throws SQLException{
		return rootDAO.getRootsOfVerse(verseId);
	}
	@Override
	public boolean insertVerseRoot(int verseId, int rootId)throws SQLException {
		return verseRoot.insertVerseRoot(verseId, rootId);
	}

	@Override
	 public List<VerseTO> getVersesByRoot(int selectedRootId) throws SQLException {
		return verseDAO.getVersesByRoot(selectedRootId);
	}

	@Override
	 public List<PoemTO> getPoemsByVerseId(int verseId) throws SQLException {
		return verseDAO.getPoemsByVerseId(verseId);
	}

	@Override
	public boolean insertPOSToken(int posID, int tokenId)throws SQLException  {
		return posToken.insertPOSToken(posID, tokenId);
	}

	@Override
	public int insertPOS(String word)throws SQLException {
		return pos.insertPOS(word);
	}

}
