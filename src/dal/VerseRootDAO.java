package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VerseRootDAO implements IVerseRoot {

	@Override
	public boolean insertVerseRoot(int verseId, int rootId) throws SQLException {
		if(!isVerified(rootId)) {
			DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

	        Connection connection = databaseConnection.getConnection();
			// Define the SQL query for inserting a book into the 'Book' table
			String insertSql = "INSERT INTO verse_root (verse_id_fk, root_id_fk) VALUES (?, ?)";

			PreparedStatement insertStatement = connection.prepareStatement(insertSql,
					Statement.RETURN_GENERATED_KEYS);
			// Set the values for the parameters in the SQL query
			insertStatement.setInt(1, verseId);
			insertStatement.setInt(2, rootId);
			// Execute the insert operation
			insertStatement.executeUpdate();
				
			String updateSql="UPDATE root SET is_verified=1 WHERE root_id=?";
			PreparedStatement updateStatement=connection.prepareStatement(updateSql);
			updateStatement.setInt(1, rootId);
			updateStatement.executeUpdate();
			
			// Closing the resources
			updateStatement.close();
			insertStatement.close();
			connection.close();
			return true;

		}		
		return false;
	}
	
	private boolean isVerified(int rootId) throws SQLException{
		DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
		String selectSql = "SELECT is_verified FROM root WHERE root_id=?";
		PreparedStatement selectStatement = connection.prepareStatement(selectSql);
		selectStatement.setInt(1, rootId);
		ResultSet resultSet=selectStatement.executeQuery();
		
		if (resultSet.next()) {
	        int flag = resultSet.getInt("is_verified");
	        if(flag==0) {
				selectStatement.close();
				connection.close();
				return false;
			}
		}
		
		selectStatement.close();
		connection.close();
		return true;
	}

}