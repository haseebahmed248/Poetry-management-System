package bll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import transferobjects.BookTO;
import transferobjects.PoemTO;
import transferobjects.RootTO;
import transferobjects.VerseTO;

public interface IFacadeBO{
     public int addPoem(int bookID, String title) ;
     public List<Integer> addVerses(int poemID, List<String> verses);
     public List<PoemTO> displayPoems(int bookID) ;
     public List<VerseTO> displayVerses(int poem_id);
   

     public boolean updatePoem(int id, String title);
     public boolean deletePoem(int id) ;

     public int addVerse(int poem_id,String misra_1,String misra_2) ;
     public boolean updateVerse(int id, String misra_1,String misra_2) ;
     public boolean deleteVerse(int id) ; 
     
     public int insertBook(String title, String authorName, String authorDeathDate);
 	 public void updateBook(int bookId, String newTitle, String newAuthorName, String newAuthorDeathDate);
 	 public boolean deleteBook(int bookId);
 	 public List<BookTO> displayAllBooks();
 	 
 	 public int insert_Root(String root_Word);
	 public int delete_Root(int root_id) ;
	 public boolean update_Root(int root_id, String updatedRootWord) ;
	 public List<RootTO> Read_Root(int verse_id) ;
	 public List<RootTO> ReadAllRoots() ;
	 
	 public boolean uploadFile(String name,DefaultTableModel bookTableModel,DefaultTableModel poemTableModel,DefaultTableModel verseTableModel);
	 
	 
	 public HashMap<String,String[]> getRoots(String []tokens);
		public int insertToken(String tokenizedWord);
		public boolean assignTokenVerse(int verseId, int tokenId);
		public boolean insertRootToken(int tokenId, int rootId);
		public Map<Integer,String> getRootsOfVerse(int verseId) ;
		public boolean insertVerseRoot(int verseId, int rootId);

	 public List<VerseTO> getVersesByRoot(int selectedRootId) ;
	 public List<PoemTO> getPoemsByVerseId(int verseId) ;
	 
	 public int insertPOS(String word);
	 public String getPOS(String token);
	 public boolean insertPOSToken(int posId,int tokenId); 
}
