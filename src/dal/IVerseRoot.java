package dal;

import java.sql.SQLException;

public interface IVerseRoot {
	public boolean insertVerseRoot(int verseId, int rootId)throws SQLException;
}
