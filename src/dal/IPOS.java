package dal;

import java.sql.SQLException;

public interface IPOS {
	public int insertPOS(String word) throws SQLException;
	public String getPOS(String token) throws Exception;
}
