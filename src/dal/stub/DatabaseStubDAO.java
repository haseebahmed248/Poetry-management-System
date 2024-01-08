package dal.stub;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.table.DefaultTableModel;

import dal.IFacadeDAO;
import transferobjects.BookTO;
import transferobjects.PoemTO;
import transferobjects.RootTO;
import transferobjects.VerseRootTO;
import transferobjects.VerseTO;

public class DatabaseStubDAO implements IFacadeDAO {
	//Basic Required Variables
	private static int posId = -1;
	private static int tokenId = -1;
	private static List<String>pos;
	private static HashMap<Integer,Integer>posToken;
	private static HashMap<Integer,Integer>rootToken;
	private static HashMap<Integer,Integer>tokenVerse;
	private static List<String>token;
	public static Map<Integer,RootTO>rootMap=new HashMap<>();
	public Map<Integer,VerseRootTO>verseRootMap=new HashMap<>();
	public Map<Integer,VerseTO>verseMap=new HashMap<>();
	private static int bookIdCounter = 1; 
    private List<BookTO> bookList = new ArrayList<>();
    private static int PoemId=0;
	private static int VerseId=0;
    private Map<Integer, List<PoemTO>> poemTable = new HashMap<>();
    private Map<Integer, List<VerseTO>> verseTable = new HashMap<>();
    private Map<Integer,Integer> rootTable= new HashMap<>();
    private Map<Integer, Set<Integer>> verseRootTable = new HashMap<>();
	
	public DatabaseStubDAO(){
		pos = new ArrayList<>();
		posToken = new HashMap<>();
		rootToken = new HashMap<>();
		tokenVerse = new HashMap<>();
		token = new ArrayList<>();
		pos.add("قال");
		
//		// Initialize rootMap with sample data
        verseMap.put(1, new VerseTO(1, "Verse 1 - Misra 1", "Verse 1 - Misra 2"));
        verseMap.put(2, new VerseTO(2, "Verse 2 - Misra 1", "Verse 2 - Misra 2"));
        verseMap.put(3, new VerseTO(3, "Verse 3 - Misra 1", "Verse 3 - Misra 2"));
        verseRootMap.put(1, new VerseRootTO(1, 1, 1));
        verseRootMap.put(2, new VerseRootTO(2, 2, 1));
        verseRootMap.put(3, new VerseRootTO(3, 3, 2));
		
	}
	
	@Override
	public String getPOS(String word) {
		if(word.isEmpty()) {
			return "";
		}
		Random random = new Random();
		int randomInRange = random.nextInt(posId - -1 + 1) + 0;
		String x = pos.get(randomInRange);
		return x;
	}

	@Override
	public HashMap<String, String[]> getRoots(String[] tokens) {
		HashMap<String,String[] > x = new HashMap<>();
		String [] arr = new String[1];
		for(int i=0;i<1;i++) {
			if(rootMap.isEmpty()) {
				break;
			}
			arr[i] = rootMap.get(tokens).getRoot_word();
		}
		x.put(tokens[0], arr);
		return x;
	}

	@Override
	public boolean assignTokenVerse(int verseId, int tokenId) {
		if(verseId >= 0 && tokenId >= 0) {
			tokenVerse.put(verseId, tokenId);
			return true;
		}
		return false;
	}

	@Override
	public int addPoem(int bookID, String title) throws SQLException {
		 List<PoemTO> poemList = poemTable.computeIfAbsent(bookID, k -> new ArrayList<>());

		    // Check for duplicate title within the same bookID
		    boolean isDuplicate = poemList.stream().anyMatch(poem -> poem.getTitle().equals(title));

		    if (!isDuplicate) {
		        poemList.add(new PoemTO(++PoemId, title));
		        return PoemId;
		    }
		    return -1;
	}

	@Override
	public List<Integer> addVerses(int poemID, List<String> verses) throws SQLException {
		if(poemID >= 0) {
			List<VerseTO> verseList = verseTable.computeIfAbsent(poemID, k -> new ArrayList<>());
			verseTable.put(poemID, verseList);
			List<Integer >x = new ArrayList<>();
			x.add(verseTable.size());
			return x;
		}
		return null;
	}

	@Override
	public List<PoemTO> displayPoems(int bookID) throws SQLException {
		return poemTable.get(bookID);
	}

	@Override
	public List<VerseTO> displayVerses(int poem_id) throws SQLException {
		return verseTable.get(poem_id);
	}

	@Override
	public boolean updatePoem(int id, String title) throws SQLException {
		for (List<PoemTO> poemList : poemTable.values()) {
	        for (PoemTO poem : poemList) {
	            if (poem.getId() == id) {
	                poem.setTitle(title);
	                return true; // Successfully updated
	            }
	        }
	    }
	    return false; // Poem with the specified id not found
	}

	@Override
	public boolean deletePoem(int id) throws SQLException {
		for (List<PoemTO> poemList : poemTable.values()) {
	        Iterator<PoemTO> iterator = poemList.iterator();
	        while (iterator.hasNext()) {
	            PoemTO poem = iterator.next();
	            if (poem.getId() == id) {
	                iterator.remove(); // Remove the matching PoemTO
	                return true; // Successfully deleted
	            }
	        }
	    }
	    return false; // Poem with the specified id not found

	}

	@Override
	public int addVerse(int poem_id, String misra1, String misra2) throws SQLException {
		 List<VerseTO> verseList = verseTable.computeIfAbsent(poem_id, k -> new ArrayList<>());

		    // Check for duplicate misra_1 and misra_2 within the same poem_id
		    boolean isDuplicate = verseList.stream().anyMatch(verse -> verse.getMisra_1().equals(misra1) && verse.getMisra_2().equals(misra2));

		    if (!isDuplicate) {
		        verseList.add(new VerseTO(++VerseId,misra1,misra2));
		        return VerseId;
		    }

		    System.out.println("Duplicate entry not allowed for Verse: " + misra1 + " " + misra2);
		    return -1;
	}

	@Override
	public boolean updateVerse(int id, final String misra_1,final  String misra_2) throws SQLException {
		for (List<VerseTO> verseList : verseTable.values()) {
	        for (VerseTO verse : verseList) {
	            if (verse.getId() == id) {
	            	verse.setMisra_1(misra_1);
	            	verse.setMisra_2(misra_2);
	                return true; // Successfully updated
	            }
	        }
	    }
	    return false; // Verse with the specified id not found

	}

	@Override
	public boolean deleteVerse(int id) throws SQLException {
		for (List<VerseTO> verseList : verseTable.values()) {
	        Iterator<VerseTO> iterator = verseList.iterator();
	        while (iterator.hasNext()) {
	            VerseTO verse = iterator.next();
	            if (verse.getId() == id) {
	                iterator.remove(); // Remove the matching VerseTO
	                return true; // Successfully deleted
	            }
	        }
	    }
	    return false; // Verse with the specified id not found

	}

	@Override
	public int insertBook(String title, String authorName, String authorDeathDate) {
		        if (title.isEmpty() || authorName.isEmpty() || authorDeathDate.isEmpty()) {
		            throw new IllegalArgumentException("Fields cannot be empty");
		        }
		        
		        BookTO book = new BookTO(bookIdCounter, title, authorName, authorDeathDate);
		        bookList.add(book);
		        return bookIdCounter++;
	}

	@Override
	public boolean updateBook(int bookId, String newTitle, String newAuthorName, String newAuthorDeathDate) {
		if (newTitle == null || newAuthorName == null || newAuthorDeathDate == null ||
		        newTitle.isEmpty() || newAuthorName.isEmpty() || newAuthorDeathDate.isEmpty()) {
		        return false;
		    }

		    for (BookTO book : bookList) {
		        if (book.getBookId() == bookId) {
		            book.setTitle(newTitle);
		            book.setAuthorName(newAuthorName);
		            book.setAuthorDeathDate(newAuthorDeathDate);
		            System.out.println("Updated book_id: " + bookId);
		            return true;
		        }
		    }
		    System.err.println("No record with book_id: " + bookId);
		    return false;

	}

	@Override
	public boolean deleteBook(int bookId) {
		for (BookTO book : bookList) {
	        if (book.getBookId() == bookId) {
	            bookList.remove(book);
	            return true; // Book was found and removed
	        }
	    }
	    return false; // Book with the given ID was not found

	}

	@Override
	public List<BookTO> displayAllBooks() {
		  return bookList;
	}

	@Override
	public int insert_Root(String root_Word) throws SQLException {
		 if (!root_Word.isEmpty()) {
		        int id = rootMap.size() + 1;
		        rootMap.put(id, new RootTO(id, root_Word));
		        return id; // Return the generated ID
		    } else {
		        throw new exceptionExe("Root word is empty"); 
		    }
	}

	@Override
	public int delete_Root(int root_id) throws SQLException {
		  if (rootMap.containsKey(root_id)) {
		        rootMap.remove(root_id);
		        return root_id; // Return the deleted root_id
		    } else if(root_id < 0){
		    	return -1;
		    }
		    else {
		        throw new SQLException("Root with ID " + root_id + " not found"); 
		    }
	}

	@Override
	public boolean update_Root(int root_id, String updatedRootWord) throws SQLException {
		 if (rootMap.containsKey(root_id)) {
		        RootTO rootTO = rootMap.get(root_id);
		        rootTO.setRoot_word(updatedRootWord);
		        return true;
		    } else if(root_id < 0 || updatedRootWord.isEmpty()) {
		    	return false;
		    }
		    else{
		        throw new SQLException("Root with ID " + root_id + " not found");
		    }

	}

	@Override
	public List<RootTO> Read_Root(int verse_id) throws SQLException {
		List<RootTO> x = new ArrayList<>();
		return x;
	}

	@Override
	public List<RootTO> ReadAllRoots() throws SQLException {
		  List<RootTO> allRoots = new ArrayList<>(rootMap.values());
		    return allRoots;
	}

	@Override
	public boolean importFile(String name, DefaultTableModel bookTableModel, DefaultTableModel poemTableModel,
			DefaultTableModel verseTableModel) {
		Path path = Paths.get(name);
        if(Files.exists(path) && Files.isRegularFile(path)) {
        	return true;  //true
        }
        return false; //false
	}



	@Override
	public boolean insertRootToken(int tokenId, int rootId) {
		if(tokenId >= 0 && rootId >= 0) {
			rootToken.put(tokenId, rootId);
			return true;
		}
		return false;

	}

	@Override
	public Map<Integer, String> getRootsOfVerse(int verseId) throws SQLException {
		Map<Integer,String> x = new HashMap<>();
		return x;
	}

	@Override
	public boolean insertVerseRoot(int verseId, int rootId) {
		if(rootTable.containsKey(rootId) && rootTable.get(rootId) == 1) {
			Set<Integer> setofVerses=verseRootTable.get(rootId);	
            if (setofVerses.contains(verseId)) {
                return false;
            }
		}	
		rootTable.put(rootId, 1);//verified
		verseRootTable.computeIfAbsent(rootId, k -> new HashSet<>()).add(verseId);
		return true;
	}

	@Override
	public List<VerseTO> getVersesByRoot(int selectedRootId) throws SQLException {
		 List<VerseTO> versesForRoot = new ArrayList<>();

		    for (VerseRootTO verseRootTO : verseRootMap.values()) {
		        if (verseRootTO.getRootId() == selectedRootId) {
		            int verseId = verseRootTO.getVerseId();
		            if (verseMap.containsKey(verseId)) {
		                versesForRoot.add(verseMap.get(verseId));
		            } else {
		                throw new SQLException("Verse with ID " + verseId + " not found"); 
		            }
		        }
		    }

		    return versesForRoot;
	}

	@Override
	public List<PoemTO> getPoemsByVerseId(int verseId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertPOSToken(int posID, int tokenId) {
		if(posID >= 0 && tokenId >= 0) {
			posToken.put(posID, tokenId);
			return true;
		}
		return false;
		
	}

	@Override
	public int insertPOS(String word) {
		if(word.isEmpty()) {
			return -1;
		}else if(pos.contains(word)) {
			return -2;
		}
		pos.add(word);
		posId++;
		return posId;
	}

	@Override
	public int insertToken( String tokenizedWord){
		if(tokenizedWord.isEmpty()) {
			return -1;
		}
		token.add(tokenizedWord);
		tokenId++;
		return tokenId;
	}

}


class exceptionExe extends SQLException{
	public exceptionExe(String message) {
	super(message);
	}
}
