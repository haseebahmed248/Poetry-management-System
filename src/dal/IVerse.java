package dal;

import java.sql.SQLException;
import java.util.List;

import transferobjects.PoemTO;
import transferobjects.VerseTO;

public interface IVerse {
    public List<Integer> addVerses(int poemID, List<String> verses) throws SQLException;
    public List<VerseTO> displayVerses(int poem_id) throws SQLException;
    public int addVerse(int poem_id,String misra_1,String misra_2) throws SQLException;
    public boolean updateVerse(int id, String misra_1,String misra_2) throws SQLException;
    public boolean deleteVerse(int id) throws SQLException;
	public List<VerseTO> getVersesByRoot(int selectedRootId) throws SQLException;
	 public List<PoemTO> getPoemsByVerseId(int verseId) throws SQLException;
}
