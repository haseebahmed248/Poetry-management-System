package bll;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dal.IFacadeDAO;
import transferobjects.RootTO;

public class RootsBO {

	private IFacadeDAO rootDAO;
	private static Logger log = LogManager.getLogger(BookBO.class.getName());
	public RootsBO(IFacadeDAO rootDAO) {
		this.rootDAO = rootDAO;
	}

	public int insert_Root(String root_Word)  {
		
		try {
			int id =  rootDAO.insert_Root(root_Word);
			if(id >= 0) {
				log.info("Insertion of Root was Sucess");
				return id;
			}
		} catch (SQLException e) {
			log.error("Insertion of Root was NOT Sucess and Ended with Exception");
			e.printStackTrace();
		}
		return -1;
	}

	public int delete_Root(int root_id){
		try {
			int id =  rootDAO.delete_Root(root_id);
			if(id >=0) {
				log.info("Deletion of Root was success At Id :"+ id);
				return id;
			}else {
				log.debug("Deletion of Root Ended with  error");
				return id;	
			}
		} catch (SQLException e) {
			log.error("Deletion of Root Threw Exception");
			e.printStackTrace();
		}
		return -1;
	}

	public boolean update_Root(int root_id, String updatedRootWord) {
		try {
			boolean check = rootDAO.update_Root(root_id, updatedRootWord);
			if(check) {
			log.info("Updation of Root was Sucess");
			return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			log.error("Updation of Root Threw Excetpion");
			e.printStackTrace();
		}
		return false;
	}

	public List<RootTO> Read_Root(int verse_id)  {
		try {
			List<RootTO> data = rootDAO.Read_Root(verse_id);
			if(data != null) {
				log.info("Reading Root was Sucess");
				return data;
			}else {
				log.debug("Reading Root ended with error");
				return data;
			}
		} catch (SQLException e) {
			log.error("Reading Root threw Exception");
			e.printStackTrace();
		}
		return null;
		
	}

	public List<RootTO> ReadAllRoots()  {
		try {
			List<RootTO> data = rootDAO.ReadAllRoots();
			if(data.isEmpty()) {
				log.info("Reading All Root was Sucess");
				return data;
			}else {
				log.debug("Reading ALl Root ended with error");
				return data;
			}
		} catch (SQLException e) {
			log.error("Reading all Root threw Exception");
			e.printStackTrace();
		}
		return null;
	}
	public HashMap<String,String[]> getRoots(String []tokens) {
		HashMap<String,String[]> data = rootDAO.getRoots(tokens);
		if(data != null) {
			log.info("Retreving Roots was success");
			return data;
		}else {
			log.debug("Retreving Roots was NOt success");
			return data;
		}
	}
	
	public Map<Integer,String> getRootsOfVerse(int verseId) {
		try {
			Map<Integer,String> data = rootDAO.getRootsOfVerse(verseId);
			if(data != null) {
				log.info("Reterival of verseRoots was Success");
				return data;
			}else {
				log.debug("Reterival of verseRoots was NOT Success");
				return  data;
			}
		} catch (SQLException e) {
			log.error("Reterival of verseRoots Threw Exception");
			e.printStackTrace();
		}
		return null;
	}
}
