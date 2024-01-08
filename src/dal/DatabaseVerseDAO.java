package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import transferobjects.PoemTO;
import transferobjects.VerseTO;

public class DatabaseVerseDAO implements IVerse {
	@Override
	public List<Integer> addVerses(int poemID, List<String> verses) throws SQLException {// Accepting Poem ID as a
																							// Foreign Key
		DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
		String sql = "INSERT INTO Verse (poem_id_fk,misra_1,misra_2) VALUES (?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		List<Integer> keys = new ArrayList<>();
		for (String verse : verses) {// Executing Statements One by One for each Verse
			preparedStatement.setInt(1, poemID);
			String[] misra = verse.split("\\.\\.\\.");
			preparedStatement.setString(2, misra[0]);
			preparedStatement.setString(3, misra[1]);
			int rowsAffected = preparedStatement.executeUpdate();
			System.out.println("Rows Affected:" + rowsAffected);
			// Retrieve the generated keys for this statement
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				int key = generatedKeys.getInt(1);
				keys.add(key);
				System.out.println("book_id_fk: " + key);
			}

			// Close the ResultSet for this statement
			generatedKeys.close();
		}
		preparedStatement.close();
		connection.close();
		return keys;
	}

	@Override
	public List<VerseTO> displayVerses(int poem_id) throws SQLException {
		List<VerseTO> verseList = new ArrayList<>();

		DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
		String sql = "SELECT verse_id,misra_1,misra_2 FROM verse WHERE poem_id_fk = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, poem_id);

		ResultSet resultSet = preparedStatement.executeQuery();
		// Process the results
		while (resultSet.next()) {
			int verseID = resultSet.getInt("verse_id");
			String misra_1 = resultSet.getString("misra_1");
			String misra_2 = resultSet.getString("misra_2");

			VerseTO verseTO = new VerseTO(verseID, misra_1, misra_2);
			verseList.add(verseTO);
			// System.out.println("ID: "+poemID+" Title: " + title);
		}

		// Closing the Connection
		resultSet.close();
		preparedStatement.close();
		connection.close();
		return verseList;
	}

	@Override
	public int addVerse(int poem_id, String misra_1, String misra_2) throws SQLException {
		DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
		String sql = "INSERT INTO verse (poem_id_fk,misra_1,misra_2) VALUES (?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, poem_id);
		preparedStatement.setString(2, misra_1);
		preparedStatement.setString(3, misra_2);

		int rowsAffected = preparedStatement.executeUpdate();
		System.out.println("Rows Affected: " + rowsAffected);

		ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
		if (generatedKeys.next()) {
			int key = generatedKeys.getInt(1);
			System.out.println("verse_id: " + key);
			// Closing the Connection
			generatedKeys.close();
			preparedStatement.close();
			connection.close();
			return key;
		}
		return -1;
	}

	@Override
	public boolean updateVerse(int id, String misra_1, String misra_2) throws SQLException {
		DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
		String sql = "UPDATE verse SET misra_1=?,misra_2=? WHERE verse_id=?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, misra_1);
		preparedStatement.setString(2, misra_2);
		preparedStatement.setInt(3, id);

		int rowsAffected = preparedStatement.executeUpdate();
		System.out.println("Rows Affected: " + rowsAffected);

		// Closing the Connection
		preparedStatement.close();
		connection.close();
		return rowsAffected > 0;
	}

	@Override
	public boolean deleteVerse(int id) throws SQLException {
		DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
        
		String sql1 ="DELETE FROM verse_root WHERE verse_id_fk=?";
		
		String sql2 ="DELETE FROM verse_token WHERE verse_fk =?";
        
        String sql3 ="DELETE FROM root_token WHERE root_fk IN (SELECT root_id FROM root WHERE root_id NOT IN(SELECT root_id_fk FROM verse_root)) OR token_fk IN (SELECT token_id FROM token WHERE token_id NOT IN (SELECT token_fk FROM verse_token)) ";
        
        String sql4 ="DELETE FROM root WHERE root_id NOT IN (SELECT root_fk FROM root_token)";
        
        String sql5 ="DELETE FROM pos_token WHERE token_fk IN (SELECT token_id FROM token WHERE token_id NOT IN (SELECT token_fk FROM verse_token))";
        
        String sql6 ="DELETE FROM pos_tags WHERE pos_id NOT IN (SELECT pos_fk FROM pos_token)";
        
        String sql7 ="DELETE FROM token WHERE token_id NOT IN (SELECT token_fk FROM verse_token)";
		
        String sql8 ="DELETE FROM verse WHERE verse_id=?";

		
		
		PreparedStatement deleteStatement1= connection.prepareStatement(sql1);
		PreparedStatement deleteStatement2= connection.prepareStatement(sql2);
		PreparedStatement deleteStatement3= connection.prepareStatement(sql3);
		PreparedStatement deleteStatement4= connection.prepareStatement(sql4);
		PreparedStatement deleteStatement5= connection.prepareStatement(sql5);
		PreparedStatement deleteStatement6= connection.prepareStatement(sql6);
		PreparedStatement deleteStatement7= connection.prepareStatement(sql7);
		PreparedStatement deleteStatement8= connection.prepareStatement(sql8);
	
		deleteStatement1.setInt(1, id);
		deleteStatement2.setInt(1, id);
		deleteStatement8.setInt(1, id);	
		
		
		deleteStatement1.executeUpdate();
		deleteStatement2.executeUpdate();
		deleteStatement3.executeUpdate();
		deleteStatement4.executeUpdate();
		deleteStatement5.executeUpdate();
		deleteStatement6.executeUpdate();
		deleteStatement7.executeUpdate();
		int rowsAffected=deleteStatement8.executeUpdate();
	
		
		deleteStatement1.close();
        deleteStatement2.close();
        deleteStatement3.close();
        deleteStatement4.close();
        deleteStatement5.close();
        deleteStatement6.close();
        deleteStatement7.close();
        deleteStatement8.close();
        
		connection.close();
		return rowsAffected > 0;
		
	}
	  public List<VerseTO> getVersesByRoot(int selectedRootId) throws SQLException {
	        List<VerseTO> verseList = new ArrayList<>();

	        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

	         Connection connection = databaseConnection.getConnection(); 

	            String sql="SELECT v.verse_id,v.misra_1,v.misra_2"
	            		+ " FROM verse v"
	            		+ " JOIN verse_root vt ON v.verse_id=vt.verse_id_fk"
	            		+ " JOIN root r ON vt.root_id_fk=r.root_id"
	            		+ " WHERE r.root_id=?";
	           // String sql = "SELECT verse_id, misra_1, misra_2 FROM verse WHERE verse_id IN (SELECT verse_id_fk FROM verse_root WHERE root_id_fk = ?)";
	            PreparedStatement preparedStatement = connection.prepareStatement(sql);

	                // Set the parameter value
	                preparedStatement.setInt(1, selectedRootId);

	                // Execute the query
	                ResultSet resultSet = preparedStatement.executeQuery();
	                    while (resultSet.next()) {
	                        int verseId = resultSet.getInt("verse_id");
	                        String misra1 = resultSet.getString("misra_1");
	                        String misra2 = resultSet.getString("misra_2");
	                        System.out.println(verseId+" "+ misra1+" "+misra2+" ");
	                        // Create VerseTO object and add to the list
	                        VerseTO verseTO = new VerseTO(verseId, misra1, misra2);
	                        verseList.add(verseTO);
	                    }

	        return verseList;
	    }
	 
	 
	  public List<PoemTO> getPoemsByVerseId(int verseId) throws SQLException {
		    List<PoemTO> poems = new ArrayList<>();

		    DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

	         Connection connection = databaseConnection.getConnection();
		        String sql = "SELECT poem.poem_id,poem.title FROM poem"
		        		   + " JOIN verse ON  poem.poem_id = verse.poem_id_fk"
		        		   + " WHERE verse.verse_id=?";

		         PreparedStatement statement = connection.prepareStatement(sql);
		            statement.setInt(1, verseId);

		            ResultSet resultSet = statement.executeQuery();
		                while (resultSet.next()) {
		                	int poemId = resultSet.getInt("poem_id");
		                    String poemTitle = resultSet.getString("title");
		                    poems.add(new PoemTO(poemId,poemTitle));
		                }

		    return poems;
		}


	
}