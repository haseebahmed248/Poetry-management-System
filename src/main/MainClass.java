package main;

import java.sql.SQLException;

import bll.BookBO;
import bll.FacadeBO;
import bll.ImportBO;
import bll.POSBO;
import bll.POSTokenBO;
import bll.PoemBO;
import bll.RootTokenBO;
import bll.RootsBO;
import bll.TokenBO;
import bll.VerseBO;
import bll.VerseRootBO;
import bll.VerseTokenBO;
import dal.BookDataDAO;
import dal.DatabasePoemDAO;
import dal.DatabaseVerseDAO;
import dal.FacadeDAO;
import dal.IFacadeDAO;
import dal.ImportFileDAO;
import dal.POSDAO;
import dal.POSTokenDAO;
import dal.RootDAO;
import dal.RootTokenDAO;
import dal.TokensDAO;
import dal.VerseRootDAO;
import dal.VerseTokenDAO;
import pl.PoetryManagementApp;

public class MainClass {
    public static void main(String[] args) throws SQLException {
        IFacadeDAO facadeDAO = new FacadeDAO(new DatabasePoemDAO(), new DatabaseVerseDAO(),new BookDataDAO(),new RootDAO(), new ImportFileDAO(),new TokensDAO(), new VerseTokenDAO(), new RootTokenDAO(),new VerseRootDAO(),new POSTokenDAO(),new POSDAO());
        PoemBO poemBO = new PoemBO(facadeDAO);
        VerseBO verseBO = new VerseBO(facadeDAO);
        BookBO  bookBO= new BookBO(facadeDAO);
        RootsBO rootsBO=new RootsBO(facadeDAO);
        ImportBO importBO = new ImportBO(facadeDAO);
        RootTokenBO rootTokenBO = new RootTokenBO(facadeDAO);
        TokenBO tokenBO = new TokenBO(facadeDAO);
        VerseTokenBO verseTokenBO = new VerseTokenBO(facadeDAO);
        VerseRootBO verseRootBO=new VerseRootBO(facadeDAO);
        POSBO posBO = new  POSBO(facadeDAO);
        POSTokenBO posToken = new POSTokenBO(facadeDAO);
        PoetryManagementApp myApp = new PoetryManagementApp(new FacadeBO(poemBO, verseBO,bookBO,rootsBO,importBO,tokenBO,verseTokenBO,rootTokenBO,verseRootBO,posToken,posBO));
    }
}
 