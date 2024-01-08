package dal;

import java.sql.SQLException;

public interface IToken {
	public int insertToken(String tokenizedWord) throws SQLException;
	
}