package bll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import transferobjects.BookTO;
import transferobjects.PoemTO;
import transferobjects.RootTO;
import transferobjects.VerseTO;

public class FacadeBO implements IFacadeBO {
    private PoemBO poemBO;
    private VerseBO verseBO;
    private BookBO bookBO;
    private RootsBO rootsBO;
    private ImportBO importBO;
    private TokenBO tokenBO;
	private VerseTokenBO verseTokenBO;
	private RootTokenBO rootTokenBO;
	private VerseRootBO verseRootBO;
	private POSBO posBO;
	private POSTokenBO posToken;
	
    public FacadeBO(PoemBO poemBO, VerseBO verseBO,BookBO bookBO,RootsBO rootsBO, ImportBO bo,TokenBO tokenBO,VerseTokenBO verseTokenBO, RootTokenBO rootTokenBO,VerseRootBO verseRootBO,POSTokenBO posToken,POSBO posBO) {
        this.poemBO = poemBO;
        this.verseBO = verseBO;
        this.bookBO=bookBO;
        this.rootsBO=rootsBO;
        this.importBO=bo;
        this.tokenBO = tokenBO;
        this.verseTokenBO = verseTokenBO;
        this.rootTokenBO = rootTokenBO;
        this.verseRootBO=verseRootBO;
        this.posBO = posBO;
        this.posToken = posToken;
    }

    public int addPoem(int bookID, String title) {

			return poemBO.addPoem(bookID, title);
	
    }

    public List<Integer> addVerses(int poemID, List<String> verses) {

			return verseBO.addVerses(poemID, verses);

    }

    public List<PoemTO> displayPoems(int bookID) {

			return poemBO.displayPoems(bookID);

    }

    public List<VerseTO> displayVerses(int poem_id) {

			return verseBO.displayVerses(poem_id);
	
    }


    public boolean updatePoem(int id, String title) {

			return poemBO.updatePoem(id, title);

    }

    public boolean deletePoem(int id) {

			return poemBO.deletePoem(id);

    }

    public int addVerse(int poem_id, String misra_1,String misra_2) {

			return verseBO.addVerse(poem_id, misra_1,misra_2);
	
    }

    public boolean updateVerse(int id, String misra_1,String misra_2) {

			return verseBO.updateVerse(id, misra_1,misra_2);

    }

    public boolean deleteVerse(int id) {

			return verseBO.deleteVerse(id);

    }
    
    
    public int insertBook(String title, String authorName, String authorDeathDate) {
    	return bookBO.insertBook(title, authorName, authorDeathDate);
    }
	public void updateBook(int bookId, String newTitle, String newAuthorName, String newAuthorDeathDate) {
		bookBO.updateBook(bookId, newTitle, newAuthorName, newAuthorDeathDate);
	}
	public boolean deleteBook(int bookId) {
		return bookBO.deleteBook(bookId);
	}
	public List<BookTO> displayAllBooks(){
		return bookBO.displayAllBooks();
	}
	
	
	public int insert_Root(String root_Word) {

			return rootsBO.insert_Root(root_Word);
	
	}
	public int delete_Root(int root_id) {

			return rootsBO.delete_Root(root_id);

	}
	public boolean update_Root(int root_id, String updatedRootWord) {

			return rootsBO.update_Root(root_id, updatedRootWord);

	}
	public List<RootTO> Read_Root(int verse_id) {

			return rootsBO.Read_Root(verse_id);

	}
	public List<RootTO> ReadAllRoots(){

			return rootsBO.ReadAllRoots();

	}

	public boolean uploadFile(String name,DefaultTableModel bookTableModel,DefaultTableModel poemTableModel,DefaultTableModel verseTableModel) {
		return importBO.uploadFile(name, bookTableModel, poemTableModel, verseTableModel);
	}

	@Override
	public HashMap<String,String[]> getRoots(String[] tokens) {
		return rootsBO.getRoots(tokens);
	}

	@Override
	public int insertToken( String tokenizedWord) {
		return tokenBO.assignRootToken( tokenizedWord);
	}

	@Override
	public boolean assignTokenVerse(int verseId, int tokenId) {
		return verseTokenBO.assignTokenVerse(verseId, tokenId);
	}

	@Override
	public String getPOS(String word){
		return posBO.getPOS(word);
	}

	@Override
	public boolean insertRootToken(int tokenId, int rootId) {
		return rootTokenBO.insertRootToken(tokenId, rootId);
	}

	@Override
	public Map<Integer,String> getRootsOfVerse(int verseId){

			return rootsBO.getRootsOfVerse(verseId);

	}
	@Override
	public boolean insertVerseRoot(int verseId, int rootId) {
		return verseRootBO.insertVerseRoot(verseId, rootId);
	}
	public List<VerseTO> getVersesByRoot(int selectedRootId) {

			return verseBO.getVersesByRoot(selectedRootId);
	}
	
	 public List<PoemTO> getPoemsByVerseId(int verseId){

			return verseBO.getPoemsByVerseId(verseId);

		
	}

	@Override
	public int insertPOS(String word) {
		return posBO.insertPOS(word);
	}

	@Override
	public boolean insertPOSToken(int posId, int tokenId) {
		return posToken.insertPOSToken(posId, tokenId);
	}

	   
}
