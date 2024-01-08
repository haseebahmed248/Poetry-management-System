package bll;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dal.IFacadeDAO;

public class POSBO {
	private IFacadeDAO POS;
	private static Logger log = LogManager.getLogger(BookBO.class.getName());
	public POSBO(IFacadeDAO POS) {
		this.POS = POS;
	}
	
	public int insertPOS(String word) {
		try {
			int id =POS.insertPOS(word);
			log.info("Inserted in POS table successFully");
			return id;
		}catch(Exception e) {
			log.error("Insertion at POS table threw  exception");
			e.printStackTrace();
		}
		return -1;
	}
	public String getPOS(String token){
		try {
			String data = POS.getPOS(token);
			log.info("Getting POS");
			return data;
		}catch(Exception e) {
			log.error("Exception in getting POS");
			e.printStackTrace();
		}
		return "";
	}
}
