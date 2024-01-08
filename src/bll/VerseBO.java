package bll;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dal.IFacadeDAO;
import transferobjects.PoemTO;
import transferobjects.VerseTO;

public class VerseBO {
    private IFacadeDAO facadeDAO;
	private static Logger log = LogManager.getLogger(BookBO.class.getName());

    public VerseBO(IFacadeDAO facadeDAO) {
        this.facadeDAO = facadeDAO;
    }

    public List<Integer> addVerses(int poemID, List<String> verses) {
    	List<String> newVerses=new ArrayList<>();
    	 StringBuilder str = new StringBuilder("");   	
    	for(int i=0; i<verses.size(); i+=2) {	
    		str.append(verses.get(i));
    		str.append("...");
    		str.append(verses.get(i+1));
    		newVerses.add(str.toString());
    		str.setLength(0);
    	}
    	List<Integer> data=null;
        try {
			data  = facadeDAO.addVerses(poemID, newVerses);
			if(data!=null) {
				log.info("Addition of verses was success");
			}else {
				log.debug("Addition of verses was NOT success");
			}
		} catch (SQLException e) {
			log.error("Addition of verses Threw Exception");
			e.printStackTrace();
		}
		return data;
    }
    public List<VerseTO> displayVerses(int poem_id) {
    	List<VerseTO> data=null;
        try {
			data = facadeDAO.displayVerses(poem_id);
			if(data != null) {
				log.info("Data Retirval for Verses was success");
			}else {
				log.debug("Data Retirval for Verses was NOT success");
			}
		} catch (SQLException e) {
			log.error("Data Retirval for Verses Threw Excpetion");
			e.printStackTrace();
		}
		return data;
    }
    public int addVerse(int poem_id,String misra_1,String misra_2) {
    	int id=-1;
        try {
			id = facadeDAO.addVerse(poem_id, misra_1,misra_2);
			if(id > 0) {
				log.info("Verse addition was success");
			}else {
				log.debug("Verse addition was NOT success");
			}
		} catch (SQLException e) {
			log.error("Verse addition threw Exception");
			e.printStackTrace();
		}
		return id;
    }
    public boolean updateVerse(int id, String misra_1,String misra_2) {
    	boolean check=false;
        try {
        	check=facadeDAO.updateVerse(id, misra_1,misra_2);
        	if(check)
			log.info("Verse Updation was success");
        	else
    		log.info("Verse Updation was not success");
      
		} catch (SQLException e) {
			log.error("Verse Updation Threw Excpetion");
			e.printStackTrace();
		}
        return check;
    }
    public boolean deleteVerse(int id) {
    	boolean check=false;
        try {
        	check=facadeDAO.deleteVerse(id);
        	
        	if(check)
			log.info("Verse Deletion was success");
        	else
        	log.info("Verse Deletion was not a success");	
        		
		} catch (SQLException e) {
			log.error("Verse addition threw Excpetion");
			e.printStackTrace();
		}
        return check;
    } 
    public List<VerseTO> getVersesByRoot(int selectedRootId) {
		try {
			List<VerseTO> data = facadeDAO.getVersesByRoot(selectedRootId);
			if(data !=null) {
				log.info("Retrival of VerseRoot was success");
				return data;
			}else {
				log.debug("Retrival of VerseRoot was NOTsuccess");
				return data;
			}
		} catch (SQLException e) {
			log.error("Retrival of VerseRoot threw Exception");
			e.printStackTrace();
		}
		return null;
    }
    public List<PoemTO> getPoemsByVerseId(int verseId) {
    	try {
			List<PoemTO> data = facadeDAO.getPoemsByVerseId(verseId);
			if(data!=null) {
				log.info("Retrival of PoemsByVerse was success");
				return data;
			}else {
				log.debug("Retrival of PoemsByVerse was NOT success");
				return data;
			}
		} catch (SQLException e) {
			log.error("Retrival of PoemsByVerse threw Exception");
			e.printStackTrace();
		}
		return null;
    }
}