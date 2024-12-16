package bookmall.vo;

public class CartVo {
	private int quantity;
	private Long userNo;
	private Long bookNo;
	private String bookTitle;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}

	public Long getBookNo() {
		return bookNo;
	}

	public void setBookNo(Long bookNo) {
		this.bookNo = bookNo;
	}
	
	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String title) {
		this.bookTitle = title;
	}
}
