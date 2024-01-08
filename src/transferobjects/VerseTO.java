package transferobjects;

public class VerseTO {
	private int id;
	private String misra_1;
	private String misra_2;
	public VerseTO(int id,String misra_1,String misra_2){
		this.id=id;
		this.misra_1=misra_1;
		this.misra_2=misra_2;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMisra_1() {
		return misra_1;
	}
	public void setMisra_1(String misra_1) {
		this.misra_1 = misra_1;
	}
	public String getMisra_2() {
		return misra_2;
	}
	public void setMisra_2(String misra_2) {
		this.misra_2 = misra_2;
	}
	
}
