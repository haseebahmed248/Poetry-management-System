package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import net.oujda_nlp_team.entity.Result;

public class POSDAO implements IPOS {
	@Override
	public int insertPOS(String word) throws SQLException {
	    DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

	    Connection connection = databaseConnection.getConnection();
	        String selectSql = "SELECT pos_id FROM pos_tags WHERE tags = ?";
	        PreparedStatement selectStatement = connection.prepareStatement(selectSql);
	            selectStatement.setString(1, word);

	            ResultSet resultSet = selectStatement.executeQuery();
	                if (resultSet.next()) {
	                    return resultSet.getInt("pos_id");
	                }

	        String insertSql = "INSERT INTO pos_tags(tags) VALUES (?)";
	        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
	            insertStatement.setString(1, word);

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

	@Override
	public String getPOS(String word) {
		List<Result> pos = net.oujda_nlp_team.AlKhalil2Analyzer.getInstance().processToken(word).getAllResults();
		if(!pos.isEmpty()) {
		String y =  pos.get(0).getPartOfSpeech();
		String x[] = y.split("\\|");
		return x[0];
		}
		else {
			return "";
		}
	}
}


