package com.example;

public class Book {
	// 書籍を一意に識別するISBNコード
	private String isbn;
	// 書籍のタイトル
	private String title;
	// 著者名
	private String author;

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

}
