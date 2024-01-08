package dal;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.oujda_nlp_team.entity.Result;
import transferobjects.RootTO;

public class RootDAO implements IRootDAO {
	
	@Override
	public int insert_Root(String rootWord) throws SQLException {
		DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
	    String insertSql = "INSERT INTO Root(root_word, is_verified) VALUES (?, ?)";
	    
	    int rootId = getRootIdIfExists(connection, rootWord);

	    if (rootId != -1) {
	        // Root word already exists
	        return rootId;
	    }

	    // Root word doesn't exist, insert it
	    return insertRootAndGetId(connection, insertSql, rootWord);
	}

	private int insertRootAndGetId(Connection connection, String insertSql, String rootWord) throws SQLException {
	    try (PreparedStatement insertStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
	        insertStatement.setString(1, rootWord);
	        insertStatement.setBoolean(2, false);

	        int rowsInserted = insertStatement.executeUpdate();
	        System.out.println("Rows Inserted: " + rowsInserted);

	        ResultSet generatedKeys = insertStatement.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            int key = generatedKeys.getInt(1);

	            // Close resources
	            generatedKeys.close();
	            return key;
	        }
	    }

	    return -1;
	}



	// Implemented function to delete function
	//Edit
	@Override
	public int delete_Root(int root_id) throws SQLException {
		DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
	        if (root_id != -1) {
	        	
	        	String query1 = "DELETE FROM root_token WHERE root_fk = ?";
	            String query2 = "DELETE FROM verse_root WHERE root_id_fk = ?";
	            String query3 = "DELETE FROM root WHERE root_id = ?";

	            
	            PreparedStatement deleteStatement1 = connection.prepareStatement(query1);
	                 PreparedStatement deleteStatement2 = connection.prepareStatement(query2);
	                 PreparedStatement deleteStatement3 = connection.prepareStatement(query3);

	                deleteStatement1.setInt(1, root_id);
	                deleteStatement2.setInt(1, root_id);
	                deleteStatement3.setInt(1, root_id);

	                int rowsDeleted1 = deleteStatement1.executeUpdate();
	                int rowsDeleted2 = deleteStatement2.executeUpdate();
	                int rowsDeleted3 = deleteStatement3.executeUpdate();

	                System.out.println("Rows Deleted from root_token: " + rowsDeleted1);
	                System.out.println("Rows Deleted from verse_root: " + rowsDeleted2);
	                System.out.println("Rows Deleted from root_id: " + rowsDeleted3);

	                deleteStatement1.close();
	                deleteStatement2.close();
	                deleteStatement3.close();

	                return rowsDeleted3; // Return the number of rows deleted from the 'root' table
	            
	        } else {
	            System.err.println("Invalid root_id provided for deletion.");
	        }
	    return -1;
	}

	
	@Override
	public boolean update_Root(int root_id, String updatedRootWord) throws SQLException {
		String updateSql = "UPDATE Root SET root_word = ? WHERE root_id = ?";
		DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
		PreparedStatement updateStatement = connection.prepareStatement(updateSql);
			updateStatement.setString(1, updatedRootWord);
			updateStatement.setInt(2, root_id);
			int rowsUpdated = updateStatement.executeUpdate();
			System.out.println("Rows Updated: " + rowsUpdated);
			//Close Resources
			updateStatement.close();
			return true;
		}
	

// Useless
	@Override
	public List<RootTO> ReadAllRoots() throws SQLException {
		List<RootTO> allData = new ArrayList<>();
		DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
		PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM Root");
			ResultSet resultSet = selectStatement.executeQuery();
			while (resultSet.next()) {
				int rootId = resultSet.getInt("root_id");
				String rootWord = resultSet.getString("root_word");
				allData.add(new RootTO(rootId, rootWord));
			}
			//Close Resources
			resultSet.close();
			selectStatement.close();
		return allData;
	}

	///

	@Override
	public List<RootTO> Read_Root(int verse_id) throws SQLException {
	    List<RootTO> selectedRoots = new ArrayList<>();
	    String query = "SELECT root.root_id, root.root_word " +
	                   "FROM root " +
	                   "INNER JOIN verse_root ON root.root_id = verse_root.root_id_fk " +
	                   "WHERE verse_root.verse_id_fk = ?";
	    
	    DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
	         PreparedStatement selectStatement = connection.prepareStatement(query);

	        selectStatement.setInt(1, verse_id);
	        ResultSet resultSet = selectStatement.executeQuery();

	        while (resultSet.next()) {
	            int selectedRootId = resultSet.getInt("root_id");
	            String selectedRootWord = resultSet.getString("root_word");
	            RootTO root = new RootTO(selectedRootId, selectedRootWord);
	            selectedRoots.add(root);
	        }
	        
	        // Close Resources
	        resultSet.close();
	        selectStatement.close();
	    return selectedRoots;
	}
	
	
	@Override
	 public HashMap<String,String[]> getRoots(String[] tokens) {
	        String[] roots = new String[tokens.length];
	        HashMap<String,String[]> rootsMap = new HashMap<>();
	        for (int i = 0; i < tokens.length; i++) {
	            // Extract the roots using the AlKhalil2Analyzer
	        	if(tokens[i]!= null) {
	            roots[i] = net.oujda_nlp_team.AlKhalil2Analyzer.getInstance().processToken(tokens[i]).getAllRootString();

	            // Remove non-Arabic characters using Normalizer
	            roots[i] = removeNonArabicCharacters(roots[i]);

	            System.out.println(tokens[i] + " : " + roots[i]);
	            String sepreated[] = getRootArrays(roots[i]);
	            for(int j=0;j<sepreated.length;j++) {
	            	System.out.println("Sepreated no "+i+" :"+ sepreated[j]);
	            }
	            rootsMap.put(tokens[i], sepreated);
	        	}
	        }

	        System.out.println("Size of roots are: " + roots.length);
	        return rootsMap;
	    }
	 	private String[] getRootArrays(String root) {
	 		String arr[] = root.split(" ");
	 		return arr;
	 	}
	    private String removeNonArabicCharacters(String input) {
	        // Remove non-Arabic characters using Normalizer
	        String cleanedString = Normalizer.normalize(input, Normalizer.Form.NFD)
	                .replaceAll("[^\\p{InArabic}]+", " ") // Replace non-Arabic characters with spaces
	                .replaceAll("\\p{M}", ""); // Remove diacritics

	        // Remove leading and trailing spaces
	        cleanedString = cleanedString.trim();

	        return cleanedString;
	    }
	@Override	
	public Map<Integer,String> getRootsOfVerse(int verseId) throws SQLException{
		Map<Integer,String> rootMap=new HashMap<>();
		String sql="SELECT r.root_id,r.root_word"
				+  " FROM root r"
				+  " JOIN root_token rt ON r.root_id=rt.root_fk"
				+  " JOIN verse_token vt ON rt.token_fk=vt.token_fk"
				+  " WHERE vt.verse_fk=? AND r.is_verified=0";
		DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, verseId);
		ResultSet resultSet=statement.executeQuery();
	
		 while (resultSet.next()) {
             int rootFk = resultSet.getInt("root_id");
             String rootText = resultSet.getString("root_word");
             rootMap.put(rootFk, rootText);
         }
		statement.close();
		resultSet.close();
		return rootMap;
	}
	private int getRootIdIfExists(Connection connection, String rootWord) throws SQLException {
	    String selectSql = "SELECT root_id FROM root WHERE root_word = ?";
	    PreparedStatement selectStatement = connection.prepareStatement(selectSql);
	        selectStatement.setString(1, rootWord);
	        ResultSet resultSet = selectStatement.executeQuery();
	        if (resultSet.next()) {
	            // Root word already exists, return its ID
	            return resultSet.getInt("root_id");
	        }
	    return -1; // Root word does not exist
	}

	
	public RootTO getRootById(int selectedRootId) throws SQLException {
	    RootTO rootTO = new RootTO(selectedRootId, null); // Assuming you have a no-argument constructor

	    // Your database interaction code to retrieve RootTO by root_id
	    String selectSql = "SELECT * FROM Root WHERE root_id = ?";
		DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
	    PreparedStatement selectStatement = connection.prepareStatement(selectSql);
	        selectStatement.setInt(1, selectedRootId);
	        ResultSet resultSet = selectStatement.executeQuery();

	        if (resultSet.next()) {
	            // Assuming RootTO has setter methods
	            rootTO.setRoot_id(resultSet.getInt("root_id"));
	            rootTO.setRoot_word(resultSet.getString("root_word"));
	            // Set other properties as needed
	        }
	        resultSet.close();
	    return rootTO;
	}
	
}

