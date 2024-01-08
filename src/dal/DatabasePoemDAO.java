package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import transferobjects.PoemTO;

public class DatabasePoemDAO implements IPoem {

    @Override
    public int addPoem(int bookID, String title) throws SQLException {// Accepting Book ID as a Foreign Key
    	DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
        String sql = "INSERT INTO Poem (book_id_fk,title) VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setInt(1, bookID);
        preparedStatement.setString(2, title);

        // Executing Statement
        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println("Rows Affected:" + rowsAffected);

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            int key = generatedKeys.getInt(1);
            System.out.println("book_id_fk: " + key);
            // Close the Connection
            generatedKeys.close();
            preparedStatement.close();
            connection.close();
            return key;
        }
        return -1;
    }

    @Override
    public List<PoemTO> displayPoems(int bookID) throws SQLException {
        List<PoemTO> poemList = new ArrayList<>();

        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
        String sql = "SELECT poem_id,title FROM poem WHERE book_id_fk = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, bookID);

        ResultSet resultSet = preparedStatement.executeQuery();
        // Process the results
        while (resultSet.next()) {
            int poemID = resultSet.getInt("poem_id");
            String title = resultSet.getString("title");

            PoemTO poemVerseTO = new PoemTO(poemID, title);
            poemList.add(poemVerseTO);

            // System.out.println("ID: "+poemID+" Title: " + title);
        }

        // Close the Connection
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return poemList;
    }

    @Override
    public boolean updatePoem(int id, String title) throws SQLException {
    	DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
        String sql = "UPDATE poem SET title=? WHERE poem_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, title);
        preparedStatement.setInt(2, id);

        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println("Rows Affected: " + rowsAffected);
        
        //Close the Connection
        preparedStatement.close();
        connection.close();
        return rowsAffected > 0;
    }

    @Override
    public boolean deletePoem(int id) throws SQLException {
    	DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        Connection connection = databaseConnection.getConnection();
        
        String sql1 ="DELETE FROM verse_root WHERE verse_id_fk IN (SELECT verse_id FROM verse WHERE poem_id_fk=?)";
        
        String sql2 ="DELETE FROM verse_token WHERE verse_fk IN (SELECT verse_id FROM verse WHERE poem_id_fk =?)";
        
        String sql3 ="DELETE FROM root_token WHERE root_fk IN (SELECT root_id FROM root WHERE root_id NOT IN(SELECT root_id_fk FROM verse_root)) OR token_fk IN (SELECT token_id FROM token WHERE token_id NOT IN (SELECT token_fk FROM verse_token)) ";
        
        String sql4 ="DELETE FROM root WHERE root_id NOT IN (SELECT root_fk FROM root_token)";
        
        String sql5 ="DELETE FROM pos_token WHERE token_fk IN (SELECT token_id FROM token WHERE token_id NOT IN (SELECT token_fk FROM verse_token))";
        
        String sql6 ="DELETE FROM pos_tags WHERE pos_id NOT IN (SELECT pos_fk FROM pos_token)";
        
        String sql7 ="DELETE FROM token WHERE token_id NOT IN (SELECT token_fk FROM verse_token)";
        
        String sql8 ="DELETE FROM verse WHERE poem_id_fk=?";
        
        String sql9 ="DELETE FROM poem WHERE poem_id=?";
        
        PreparedStatement deleteStatement1 = connection.prepareStatement(sql1);
        PreparedStatement deleteStatement2 = connection.prepareStatement(sql2);
        PreparedStatement deleteStatement3 = connection.prepareStatement(sql3);
       
        PreparedStatement deleteStatement4 = connection.prepareStatement(sql4);
        PreparedStatement deleteStatement5 = connection.prepareStatement(sql5);
        PreparedStatement deleteStatement6 = connection.prepareStatement(sql6);
        
        PreparedStatement deleteStatement7 = connection.prepareStatement(sql7);
        PreparedStatement deleteStatement8 = connection.prepareStatement(sql8);
        PreparedStatement deleteStatement9 = connection.prepareStatement(sql9);

        // Set the parameter values for each prepared statement
        deleteStatement1.setInt(1, id);
        deleteStatement2.setInt(1, id);
        deleteStatement8.setInt(1, id);
        deleteStatement9.setInt(1, id);
        
        // Execute the DELETE statements in the desired order
        deleteStatement1.executeUpdate();
        deleteStatement2.executeUpdate();
        deleteStatement3.executeUpdate();
        deleteStatement4.executeUpdate();
        deleteStatement5.executeUpdate();
        deleteStatement6.executeUpdate();
        deleteStatement7.executeUpdate();
        deleteStatement8.executeUpdate();
        int rowsAffected=deleteStatement9.executeUpdate();
        
        
        // Close the prepared statements and the database connection
        deleteStatement1.close();
        deleteStatement2.close();
        deleteStatement3.close();
        
        deleteStatement4.close();
        deleteStatement5.close();
        deleteStatement6.close();
        
        deleteStatement7.close();
        deleteStatement8.close();
        deleteStatement9.close();
        
        connection.close();
        
        return rowsAffected > 0;
    }
}