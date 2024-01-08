package bll;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dal.IFacadeDAO;

public class RootTokenBO {
	private IFacadeDAO fascadeDAO;
	private static Logger log = LogManager.getLogger(BookBO.class.getName());
	public RootTokenBO(IFacadeDAO fascadeDAO){
		this.fascadeDAO = fascadeDAO;
	}
	public boolean insertRootToken(int tokenId, int rootId) {
		 try {
			boolean check = fascadeDAO.insertRootToken(tokenId, rootId);
			if(check) {
			log.info("Insertion of RootTOken was Success");
			return true;
			}
		} catch (SQLException e) {
			log.error("Insertion of RootTOken threw Exception");
			e.printStackTrace();
		}
		 return false;
	}
}
