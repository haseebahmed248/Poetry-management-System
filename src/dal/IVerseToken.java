package dal;

import java.sql.SQLException;

public interface IVerseToken {
	public boolean assignTokenVerse(int verseId,int tokenId)throws SQLException;
}
