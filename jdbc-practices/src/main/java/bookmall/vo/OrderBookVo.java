package bookmall.vo;

public class OrderBookVo {
	private int quantity;
	private int price;
	private Long bookNo;
	private Long orderNo;
	private String bookTitle;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Long getBookNo() {
		return bookNo;
	}

	public void setBookNo(Long bookNo) {
		this.bookNo = bookNo;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	@Override
	public String toString() {
		return "OrderBookVo [quantity=" + quantity + ", price=" + price + ", bookNo=" + bookNo + ", orderNo=" + orderNo
				+ ", bookTitle=" + bookTitle + "]";
	}

}
