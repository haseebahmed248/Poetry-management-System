package dal;

import javax.swing.table.DefaultTableModel;

public interface IImportFileDAO {
	public boolean importFromFile(String fileName,DefaultTableModel bookTableModel,DefaultTableModel poemTableModel,DefaultTableModel verseTableModel)throws Exception;
}
