package dal;

import java.sql.SQLException;

public interface IPOSTokenDAO {
	public boolean insertPOSToken(int posID,int tokenId)throws SQLException ;
}
