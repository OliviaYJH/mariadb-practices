package bookshop.example;

public class Book {
	private int no;
	private String title;
	private String author;
	private int stateCode;
	
	public Book(int no, String title, String author) {
		this.no = no;
		this.title = title;
		this.author = author;
		stateCode = 1;
	}
	
	public void rent(String title) {
		// 대여 기능
		this.stateCode = 0;
		System.out.println(this.title + "이(가) 대여 됐습니다.");
	}
	
	public void print() {
		System.out.println("책 제목:" + title + 
				", 작가:" + author + 
				", 대여 유무:" + (stateCode == 1 ? "재고있음" : "대여중"));
	}

	public int getNo() {
		return no;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setStateCode() {
		stateCode = 0;
	}
}
