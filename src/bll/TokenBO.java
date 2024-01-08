package bll;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dal.IFacadeDAO;

public class TokenBO {
	private IFacadeDAO fascadeDAO;
	private static Logger log = LogManager.getLogger(BookBO.class.getName());
	public TokenBO(IFacadeDAO fascadeDAO){
		this.fascadeDAO = fascadeDAO;
	}
	public int assignRootToken( String tokenizedWord) {
		try {
			int id = fascadeDAO.insertToken( tokenizedWord);
			if(id >=0) {
				log.info("Assign Root Token was success");
				return id;
			}else {
				log.debug("Assign Root Token was Not success");
				return id;
			}
		} catch (SQLException e) {
			log.debug("Assign Root Token Threw Exception");
			e.printStackTrace();
		}
		return -1;
	}
}
