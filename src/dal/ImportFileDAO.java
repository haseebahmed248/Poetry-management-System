package dal;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.swing.table.DefaultTableModel;

 interface FileState {
    void processLine(String line) throws Exception;
}

public class ImportFileDAO implements IImportFileDAO {
    private static FileState currentState;
    static int bookId = -1; // Set bookId to -1 
    static int poemId = -1; // Set poemId to -1
    public boolean importFromFile(String fileName,DefaultTableModel bookTableModel,DefaultTableModel poemTableModel,DefaultTableModel verseTableModel) throws Exception {

        	FileInputStream str = new FileInputStream(fileName);
        	InputStreamReader inputStream = new InputStreamReader(str,StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(inputStream);
            String line;
            boolean shouldProcess = true;
            currentState = new BookState(bookId,poemId,bookTableModel,poemTableModel,verseTableModel);

            while ((line = br.readLine()) != null) {
                if (line.contains("==========")) {
                    shouldProcess = true;
                } else if (line.contains("_________ ")) {
                    shouldProcess = false;
                }

                if (shouldProcess) {
                    currentState.processLine(line);
                }
            }
            br.close();
            return true;
    }
    
    
    //States

    private static class BookState implements FileState {
    	private static int bookId;
    	private static int poemId;
    	private static DefaultTableModel bookTableModel;
    	private static DefaultTableModel poemTableModel;
    	private static DefaultTableModel verseTableModel;
    	BookState(int bookId,int poemId,DefaultTableModel bookTableModel,DefaultTableModel poemTableModel,DefaultTableModel verseTableModel){
    		this.bookId = bookId;
    		this.poemId = poemId;
    		this.bookTableModel = bookTableModel;
    		this.poemTableModel = poemTableModel;
    		this.verseTableModel = verseTableModel;
    	}
        @Override
        public void processLine(String line) throws Exception {
            if (line.contains("الكتاب :")) {
                String subString = line.substring(1, line.length());
                String[] splitter = subString.split(":");
                
                BookDataDAO bookDataDAO = new BookDataDAO();
                bookId = bookDataDAO.insertBook(splitter[1], "", "");
                bookTableModel.addRow(new Object[] { bookId, splitter[1] });
                System.out.println("Book is "+splitter[1]);
                currentState = this;
               
            }else if(line.contains("[")) {
            	currentState = new PoemState(this.bookId, poemId, bookTableModel, poemTableModel, verseTableModel);
            	currentState.processLine(line);
            }else if(line.contains("(")){
            	currentState = new VerseState(this.bookId, poemId, bookTableModel, poemTableModel, verseTableModel);
            	currentState.processLine(line);
            }
        }
    }

    private static class PoemState implements FileState {
    	private static int bookId;
    	private static int poemId;
    	private static DefaultTableModel bookTableModel;
    	private static DefaultTableModel poemTableModel;
    	private static DefaultTableModel verseTableModel;
    	PoemState(int bookId,int poemId,DefaultTableModel bookTableModel,DefaultTableModel poemTableModel,DefaultTableModel verseTableModel){
    		this.bookId = bookId;
    		this.poemId = poemId;
    		this.bookTableModel = bookTableModel;
    		this.poemTableModel = poemTableModel;
    		this.verseTableModel = verseTableModel;
    	}
        @Override
        public void processLine(String line) throws Exception {
            if (line.contains("[")) {
                String subString = line.substring(1, line.length() - 2);

                DatabasePoemDAO poemDAO = new DatabasePoemDAO();
                poemId = poemDAO.addPoem(this.bookId, subString);
                poemTableModel.addRow(new Object[] { poemId, subString });
                System.out.println("Poem is "+subString);
                currentState = new VerseState(this.bookId, this.poemId, bookTableModel, poemTableModel, verseTableModel);
                
            }
            else if(line.contains("الكتاب :")) {
            	currentState = new BookState(this.bookId, this.poemId, bookTableModel, poemTableModel, verseTableModel);
            	currentState.processLine(line);
            }else if(line.contains("(")){
            	currentState = new VerseState(this.bookId, this.poemId, bookTableModel, poemTableModel, verseTableModel);
            	currentState.processLine(line);
            }
        }
    }

    private static class VerseState implements FileState {
    	private static int bookId;
    	private static int poemId;
    	private static DefaultTableModel bookTableModel;
    	private static DefaultTableModel poemTableModel;
    	private static DefaultTableModel verseTableModel;
    	VerseState(int bookId,int poemId,DefaultTableModel bookTableModel,DefaultTableModel poemTableModel,DefaultTableModel verseTableModel){
    		this.bookId = bookId;
    		this.poemId = poemId;
    		this.bookTableModel = bookTableModel;
    		this.poemTableModel = poemTableModel;
    		this.verseTableModel = verseTableModel;
    	}
        @Override
        public void processLine(String line) throws Exception {
            if (line.contains("(")) {
                String subString = line.substring(1, line.length() - 2);
                String[] splitter = subString.split("\\.\\.\\.");

                DatabaseVerseDAO verseDAO = new DatabaseVerseDAO();
                if (splitter.length == 2) {
                        int verseId = verseDAO.addVerse(this.poemId, splitter[0],splitter[1]);
                        poemTableModel.addRow(new Object[] { verseId, splitter[0],splitter[1] });
                    	System.out.println("Verse is "+splitter[0]+" : "+splitter[1] );
                } else {
                   int verseId =  verseDAO.addVerse(this.poemId, splitter[0], "");
                    poemTableModel.addRow(new Object[] { verseId, splitter[0],"" });
                    System.out.println("verse is "+splitter[0]);
                }
            }

            else if(line.contains("الكتاب :")) {
            	currentState = new BookState(this.bookId, this.poemId, bookTableModel, poemTableModel, verseTableModel);
            	currentState.processLine(line);
            }else if(line.contains("[")){
            	currentState = new PoemState(this.bookId, this.poemId, bookTableModel, poemTableModel, verseTableModel);
            	currentState.processLine(line);
            }
        }
    }
}