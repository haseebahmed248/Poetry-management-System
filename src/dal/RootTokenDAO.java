package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class RootTokenDAO implements IRootToken{

	@Override
	public boolean insertRootToken(int tokenId, int rootId) throws SQLException{
		DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
			// Define the SQL query for inserting a book into the 'Book' table
			String insertSql = "INSERT INTO root_token (token_fk, root_fk) VALUES (?, ?)";

			PreparedStatement insertStatement = connection.prepareStatement(insertSql,
					Statement.RETURN_GENERATED_KEYS);
				// Set the values for the parameters in the SQL query
				insertStatement.setInt(1, tokenId);
				insertStatement.setInt(2, rootId);

				
				// Execute the insert operation
				insertStatement.executeUpdate();

				
				// Closing the resources
				insertStatement.close();
				connection.close();
				return true;
			}

}
