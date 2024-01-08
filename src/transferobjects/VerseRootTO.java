package transferobjects;

public class VerseRootTO {

    private int verseRootId;
    private int rootId;
    private int verseId;

    public VerseRootTO(int verseRootId, int rootId, int verseId) {
        this.verseRootId = verseRootId;
        this.rootId = rootId;
        this.verseId = verseId;
    }

    public int getVerseRootId() {
        return verseRootId;
    }

    public void setVerseRootId(int verseRootId) {
        this.verseRootId = verseRootId;
    }

    public int getRootId() {
        return rootId;
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }

    public int getVerseId() {
        return verseId;
    }

    public void setVerseId(int verseId) {
        this.verseId = verseId;
    }

    
}
