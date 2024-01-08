package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TokensDAO implements IToken {
	@Override
	public int insertToken(String tokenizedWord)throws SQLException {
		int tokenId = -1;
		DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
			 int existingId = getTokenIdIfExists(connection, tokenizedWord);
		        if (existingId != -1) {
		            return existingId;
		        }
			// Define the SQL query for inserting a book into the 'Book' table
			String insertSql = "INSERT INTO Token (tokenized_words) VALUES (?)";

			PreparedStatement insertStatement = connection.prepareStatement(insertSql,
					Statement.RETURN_GENERATED_KEYS);
				// Set the values for the parameters in the SQL query
				insertStatement.setString(1, tokenizedWord);

				
				// Execute the insert operation
				insertStatement.executeUpdate();

				ResultSet generatedKeys = insertStatement.getGeneratedKeys();
				if (generatedKeys.next()) {
					int tokenKey = generatedKeys.getInt(1);
					
					//Close Resources
					generatedKeys.close();
					insertStatement.close();
					return tokenKey;
				}
				
				// Closing the resources
				insertStatement.close();
				connection.close();
		return tokenId;
	}
	private int getTokenIdIfExists(Connection connection, String tokenWord) throws SQLException {
	    String selectSql = "SELECT token_id FROM token WHERE tokenized_words = ?";
	    PreparedStatement selectStatement = connection.prepareStatement(selectSql);
	        selectStatement.setString(1, tokenWord);
	        ResultSet resultSet = selectStatement.executeQuery();
	        if (resultSet.next()) {
	            // Root word already exists, return its ID
	            return resultSet.getInt("token_id");
	        }
	    return -1; // Root word does not exist
	}
}
