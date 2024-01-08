package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class POSTokenDAO implements IPOSTokenDAO {

	@Override
	public boolean insertPOSToken(int posID, int tokenId)throws SQLException {
		DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
			// Define the SQL query for inserting a book into the 'Book' table
			String insertSql = "INSERT INTO pos_token (pos_fk, token_fk) VALUES (?, ?)";

			PreparedStatement insertStatement = connection.prepareStatement(insertSql,
					Statement.RETURN_GENERATED_KEYS);
				// Set the values for the parameters in the SQL query
				insertStatement.setInt(1, posID);
				insertStatement.setInt(2, tokenId);

				
				// Execute the insert operation
				insertStatement.executeUpdate();

				
				// Closing the resources
				insertStatement.close();
				connection.close();
				return true;
	}

}
