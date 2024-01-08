package bll;

import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dal.IFacadeDAO;

public class ImportBO {
	private IFacadeDAO importData;
	private static Logger log = LogManager.getLogger(BookBO.class.getName());
	public ImportBO(IFacadeDAO importData) {
		this.importData = importData;
	}
	public boolean uploadFile(String name,DefaultTableModel bookTableModel,DefaultTableModel poemTableModel,DefaultTableModel verseTableModel) {
		try {
			boolean check  = importData.importFile(name, bookTableModel, poemTableModel, verseTableModel);
			if(check) {
			log.info("Import Data was Success");
			return true;
			}
			
		} catch (Exception e) {
			log.error("Import Data was Not Success and Ended with Exception");
			e.printStackTrace();
		}
		return false;
	}
}
