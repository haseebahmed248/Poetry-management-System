package bll;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dal.IFacadeDAO;

public class POSTokenBO {
	private IFacadeDAO POSDAO;
	private static Logger log = LogManager.getLogger(BookBO.class.getName());
	public POSTokenBO(IFacadeDAO POSDAO) {
		this.POSDAO = POSDAO;
	}
	
	public boolean insertPOSToken(int posId,int tokenId) {
		try {
			boolean check = POSDAO.insertPOSToken(posId, tokenId);
			if(check) {
			log.info("Inserted in POSToken table successFully");
			return true;
			}
		}catch(Exception e) {
			log.error("Exception at POSToken table insetion");
			e.printStackTrace();
		}
		return false;
	}
}
