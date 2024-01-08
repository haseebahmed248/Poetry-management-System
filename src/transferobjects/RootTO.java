package transferobjects;

public class RootTO {
	int root_id;
	String root_word;

	public RootTO(int root_id, String root_word) {
		this.root_id = root_id;
		this.root_word = root_word;
	}

	public final void setRoot_id(int root_id) {
		this.root_id = root_id;
	}

	public final void setRoot_word(String root_word) {
		this.root_word = root_word;
	}

	public final int getRoot_id() {
		return root_id;
	}

	public final String getRoot_word() {
		return root_word;
	}
}
