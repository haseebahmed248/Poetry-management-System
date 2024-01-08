package dal;

import java.sql.SQLException;
import java.util.List;

import transferobjects.PoemTO;

public interface IPoem {
    public int addPoem(int bookID, String title) throws SQLException;
    public List<PoemTO> displayPoems(int bookID) throws SQLException;
    public boolean updatePoem(int id, String title) throws SQLException;
    public boolean deletePoem(int id) throws SQLException;
}