package transferobjects;

public class BookTO {
 private int bookId;
 private String title;
 private String authorName;
 private String authorDeathDate;

 public BookTO() {}
 
 public  BookTO(int bookId, String title, String authorName, String authorDeathDate) {
     this.bookId = bookId;
     this.title = title;
     this.authorName = authorName;
     this.authorDeathDate = authorDeathDate;
 }

 // Getters and setters for the fields (if needed)

 public int getBookId() {
     return bookId;
 }

 public void setBookId(int bookId) {
     this.bookId = bookId;
 }

 public String getTitle() {
     return title;
 }

 public void setTitle(String title) {
     this.title = title;
 }

 public String getAuthorName() {
     return authorName;
 }

 public void setAuthorName(String authorName) {
     this.authorName = authorName;
 }

 public String getAuthorDeathDate() {
     return authorDeathDate;
 }

 public void setAuthorDeathDate(String authorDeathDate) {
     this.authorDeathDate = authorDeathDate;
 }
}

