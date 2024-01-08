package transferobjects;

public class PoemTO {
    int id;
    String title;
    public PoemTO(int id,String title){
        this.id=id;
        this.title=title;
    }
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
    	this.title = title;
    }
}
