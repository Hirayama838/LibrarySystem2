package com.example;

public class Book {
	// 書籍を一意に識別するISBNコード
	private String isbn;
	// 書籍のタイトル
	private String title;
	// 著者名
	private String author;
	
	private List<Loan> loans;

	public Book(String isbn, String title, String author) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
	}

	// Getterメソッド
	public String getIsbn() {
		return isbn;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}
	
	public void assertCanBorrow(List<Loan> loans) {

	    boolean alreadyBorrowed = loans.stream()
	        .anyMatch(l -> l.getIsbn().equals(this.isbn) && !l.isReturned());

	    if (alreadyBorrowed) {
	        throw new BookNotAvailableException(isbn);
	    }
	}
	
	public void borrow(String memberId, int days) {

	    if (isBorrowed()) {
	        throw new BookNotAvailableException(isbn);
	    }

	    loans.add(new Loan(isbn, memberId, days));
	}
	
	public void returnBook(String memberId) {

	    Loan loan = findActiveLoan(memberId);
	    loan.returnLoan();
	}

	public void extend(String memberId, int days) {

	    Loan loan = findActiveLoan(memberId);
	    loan.extend(days);
	}

	private Loan findActiveLoan(String memberId) {
	    return loans.stream()
	        .filter(l -> l.getMemberId().equals(memberId))
	        .filter(l -> !l.isReturned())
	        .findFirst()
	        .orElseThrow(() -> new LibraryException("貸出が見つかりません"));
	}
	


}
