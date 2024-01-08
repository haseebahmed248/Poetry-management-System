package dal;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import transferobjects.RootTO;

public interface IRootDAO {
	public int insert_Root(String root_Word) throws SQLException;
	public int delete_Root(int root_id) throws SQLException;
	public boolean update_Root(int root_id, String updatedRootWord) throws SQLException;
	public List<RootTO> Read_Root(int verse_id) throws SQLException;
	public List<RootTO> ReadAllRoots() throws SQLException;
	public HashMap<String,String[]> getRoots(String []tokens);
	public Map<Integer,String> getRootsOfVerse(int verseId) throws SQLException;
}
