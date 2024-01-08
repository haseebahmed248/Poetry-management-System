package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import transferobjects.BookTO;

public class BookDataDAO implements IBookDAO{
	
    public int insertBook(String title, String authorName, String authorDeathDate) throws Exception {
    	 DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

         Connection connection = databaseConnection.getConnection();
            // Define the SQL query for inserting a book into the 'Book' table
            String insertSql = "INSERT INTO book (Title, Author_Name, `Author's_Death_Date`) VALUES (?, ?, ?)";

            PreparedStatement insertStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS); 
                // Set the values for the parameters in the SQL query
                insertStatement.setString(1, title);
                insertStatement.setString(2, authorName);
                insertStatement.setString(3, authorDeathDate);

                // Execute the insert operation
                insertStatement.executeUpdate();

                // Get the auto-generated keys
                ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int bookId = generatedKeys.getInt(1);
                    System.out.println("Inserted book_id: " + bookId);

                    //Closing the resources
                    insertStatement.close();
                    generatedKeys.close();
                    connection.close();
                    return bookId;
                }
        
        return -1;
    }

    
    public boolean updateBook(int bookId, String newTitle, String newAuthorName, String newAuthorDeathDate) throws Exception {
    	 DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

         Connection connection = databaseConnection.getConnection();
            // If bookId is provided, proceed with the update
            if (bookId != -1) {
                // Define the SQL query for updating book information
                String updateSql = "UPDATE book SET Title = ?, Author_Name = ?, `Author's_Death_Date` = ? WHERE book_id = ?";

                PreparedStatement updateStatement = connection.prepareStatement(updateSql, PreparedStatement.RETURN_GENERATED_KEYS);
                    // Set the values for the parameters in the SQL query
                    updateStatement.setString(1, newTitle);
                    updateStatement.setString(2, newAuthorName);
                    updateStatement.setString(3, newAuthorDeathDate);
                    updateStatement.setInt(4, bookId); // Set the book_id

                    // Execute the update operation
                    int rowsUpdated = updateStatement.executeUpdate();

                    // Check if the update was successful
                    if (rowsUpdated == 0) {
                        System.err.println("No record with book_id: " + bookId);
                    } else {
                        System.out.println("Updated book_id: " + bookId);
                    }
                    
                    updateStatement.close();
                    connection.close();
                    return true;
                }
             else {
                System.err.println("Invalid book_id provided for update.");
                return false;
            }
    }




    
    public boolean deleteBook(int bookId) throws Exception{
    	 DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

         Connection connection = databaseConnection.getConnection();
            // If bookId is provided, proceed with the deletion
            if (bookId != -1) {
              
                String query1 = "DELETE FROM verse_root WHERE verse_id_fk IN (SELECT verse_id FROM verse WHERE poem_id_fk IN (SELECT poem_id FROM poem WHERE book_id_fk = ?))";
                
                String query2 = "DELETE FROM verse_token WHERE verse_fk IN (SELECT verse_id FROM verse WHERE poem_id_fk IN (SELECT poem_id FROM poem WHERE book_id_fk = ?))";
                
                String query3 ="DELETE FROM root_token WHERE root_fk IN (SELECT root_id FROM root WHERE root_id NOT IN(SELECT root_id_fk FROM verse_root)) OR token_fk IN (SELECT token_id FROM token WHERE token_id NOT IN (SELECT token_fk FROM verse_token)) ";
                
                String query4="DELETE FROM root WHERE root_id NOT IN (SELECT root_fk FROM root_token)";
                
                String query5="DELETE FROM pos_token WHERE token_fk IN (SELECT token_id FROM token WHERE token_id NOT IN (SELECT token_fk FROM verse_token))";
                
                String query6="DELETE FROM pos_tags WHERE pos_id NOT IN (SELECT pos_fk FROM pos_token)";
                
                String query7="DELETE FROM token WHERE token_id NOT IN (SELECT token_fk FROM verse_token)";
                
                String query8 = "DELETE FROM verse WHERE poem_id_fk IN (SELECT poem_id FROM poem WHERE book_id_fk = ?)";

                String query9 = "DELETE FROM poem WHERE book_id_fk = ?";

                String query10 = "DELETE FROM book WHERE book_id = ?";

                PreparedStatement deleteStatement1 = connection.prepareStatement(query1);
                PreparedStatement deleteStatement2 = connection.prepareStatement(query2);
                PreparedStatement deleteStatement3 = connection.prepareStatement(query3);
                PreparedStatement deleteStatement4 = connection.prepareStatement(query4);
                PreparedStatement deleteStatement5 = connection.prepareStatement(query5);
                PreparedStatement deleteStatement6 = connection.prepareStatement(query6);
                PreparedStatement deleteStatement7 = connection.prepareStatement(query7);
                PreparedStatement deleteStatement8 = connection.prepareStatement(query8);
                PreparedStatement deleteStatement9 = connection.prepareStatement(query9);
                PreparedStatement deleteStatement10 = connection.prepareStatement(query10);

                // Set bookId parameter for all queries
                deleteStatement1.setInt(1, bookId);
                deleteStatement2.setInt(1, bookId);
                deleteStatement8.setInt(1, bookId);
                deleteStatement9.setInt(1, bookId);
                deleteStatement10.setInt(1, bookId);

                // Execute the delete operations
                deleteStatement1.executeUpdate();
                deleteStatement2.executeUpdate();
                deleteStatement3.executeUpdate();
                deleteStatement4.executeUpdate();
                deleteStatement5.executeUpdate();
                deleteStatement6.executeUpdate();
                deleteStatement7.executeUpdate();
                deleteStatement8.executeUpdate();
                deleteStatement9.executeUpdate();
                deleteStatement10.executeUpdate();
                

                System.err.println("No record with book_id: " + bookId);
                System.out.println("Deleted book_id: " + bookId);
                    
                deleteStatement1.close();
                deleteStatement2.close();
                deleteStatement3.close();
                deleteStatement4.close();
                connection.close();
                return true;
            }
             else {
                System.err.println("Invalid book_id provided for deletion.");
                return false;
            }
    }


    
   public List<BookTO> displayAllBooks() throws Exception {
        List<BookTO> books = new ArrayList<>();

        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();
            String selectAllSql = "SELECT * FROM book";
            PreparedStatement selectAllStatement = connection.prepareStatement(selectAllSql);
                ResultSet resultSet = selectAllStatement.executeQuery();

                while (resultSet.next()) {
                    int bookId = resultSet.getInt("book_id");
                    String title = resultSet.getString("Title");
                    String authorName = resultSet.getString("Author_Name");
                    String authorDeathDate = resultSet.getString("Author's_Death_Date");

                   // System.out.println(bookId+title+authorName+authorDeathDate);
                    BookTO book = new BookTO(bookId, title, authorName, authorDeathDate);
                    books.add(book);
                }
                resultSet.close();
                selectAllStatement.close();
                connection.close();

        return books;
    } 

   
}