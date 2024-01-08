package bll;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dal.IFacadeDAO;

public class VerseTokenBO {
	private IFacadeDAO fascadeDAO;
	private static Logger log = LogManager.getLogger(BookBO.class.getName());
	public VerseTokenBO(IFacadeDAO fascadeDAO){
		this.fascadeDAO = fascadeDAO;
	}
	public boolean assignTokenVerse(int verseId, int tokenId) {
		try {
			boolean check = fascadeDAO.assignTokenVerse(verseId, tokenId);
			if(check) {
			log.info("Assing Token to Verse was success");
			return true;
			}
		} catch (SQLException e) {
			log.error("Assing Token to Verse Threw Exception");
			e.printStackTrace();
		}
		return false;
	}
}
