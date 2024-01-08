package bll;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dal.IFacadeDAO;

public class VerseRootBO {
	private IFacadeDAO facadeDAO;
	private static Logger log = LogManager.getLogger(BookBO.class.getName());

	public VerseRootBO(IFacadeDAO facadeDAO) {
		this.facadeDAO = facadeDAO;
	}
	
	public boolean insertVerseRoot(int verseId, int rootId) {
		boolean check=false;
		try {
			check=facadeDAO.insertVerseRoot(verseId, rootId);
			if(check)
			log.info("Insertion of VerseRoot was success");
			else
			log.info("Insertion of duplicate VereRoot was not success");
			
		} catch (SQLException e) {
			log.error("Insertion of VerseRoot Threw Exception");
			e.printStackTrace();
		}
		return check;
	}
}