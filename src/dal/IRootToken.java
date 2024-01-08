package dal;

import java.sql.SQLException;

public interface IRootToken {
	public boolean insertRootToken(int tokenId,int rootId) throws SQLException;
}
