package bll;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dal.IFacadeDAO;
import transferobjects.PoemTO;

public class PoemBO {
    private IFacadeDAO facadeDAO;
    private static Logger log = LogManager.getLogger(BookBO.class.getName());
    public PoemBO(IFacadeDAO facadeDAO) {
        this.facadeDAO = facadeDAO;
    }

    public int addPoem(int bookID, String title) {
    	int data=-1;
        try {
			data = facadeDAO.addPoem(bookID, title);
			if(data > 0) {
				log.info("Poem added SuccessFully with ID "+data);
			}else {
				log.debug("Duplicate entry not allowed for Poem "+data);
			}
		} catch (SQLException e) {
			log.debug("Poem added Not SuccessFul And Ended With Exception");
			e.printStackTrace();
		}
		return data;
    }

    public List<PoemTO> displayPoems(int bookID) {
    	List<PoemTO> data=null;
        try {
			data = facadeDAO.displayPoems(bookID);
			if(data != null) {
				log.info("Retrival of poem at Book Id "+ bookID+" was sucess");
			}else {
				log.debug("Retrival of poem at Book Id "+ bookID+" was NOT a sucess");
			}
			
		} catch (SQLException e) {
			log.error("Retrival of poem at Book Id "+ bookID+" was NOT a sucess and Ended with Exception");
			e.printStackTrace();
		}
		return data;
    } 
    
    
    public boolean updatePoem(int id, String title) {
    	boolean check=false;
        try {
			check=facadeDAO.updatePoem(id, title);
			
			if(check)
			log.info("Poem Updation was success at Title : "+ title);
			else
			log.info("Poem Updation was not a success at Title : "+ title);

		} catch (SQLException e) {
			log.error("Poem Updation was Not success at Title : "+ title+" And Ended with Exception");
			e.printStackTrace();
		}
        return check;
    }
    public boolean deletePoem(int id){
    	boolean check=false;
        try {
        	check=facadeDAO.deletePoem(id);
        	
			if(check)
			log.info("Poem Deletion was success at ID : "+ id);
			else
			log.info("Poem Deletion was not a success at ID : "+ id);
			
		} catch (SQLException e) {
			log.info("Poem Updation was Not success at Title : "+ id+" And Ended with Exception");
			e.printStackTrace();
		}
        return check;
    }
}