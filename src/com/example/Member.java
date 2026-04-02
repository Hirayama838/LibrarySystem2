package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * 図書館の会員を管理するクラス 会員情報と貸出中の書籍リストを管理する
 */

public class Member {
	// 会員を一意に識別するID
	private String memberId;
	// 会員の名前
	private String name;
	// 会員のメールアドレス
	private String email;
	// 貸出中の書籍のISBNリスト
	private List<String> borrowedBooks;
	
	private static final int MAX_BORROW_LIMIT = 5;

	public Member(String memberId, String name, String email) {
		this.memberId = memberId;
		this.name = name;
		this.email = email;
		this.borrowedBooks = new ArrayList<>(); // 空の貸出リストで初期化
	}

	// Getterメソッド
	public String getMemberId() {
		return memberId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public List<String> getBorrowedBooks() {
		return new ArrayList<>(borrowedBooks);
	}
	
	public void borrow(Book book) {
		if (borrowedBooks.size() >= MAX_BORROW_LIMIT) {
			throw new LibraryException("貸出上限(5冊)を超えています");
		}
		
		if (borrowedBooks.contains(book.getIsbn())) {
			throw new LibraryException("既に借りています: " + book.getIsbn());
		}
		
		borrowedBooks.add(book.getIsbn());
	}
	
	
	public void returnBook(Book book) {
		if (!borrowedBooks.contains(book.getIsbn())) {
			throw new LibraryException("この本は借りていません: " + book.getIsbn());
		}
		
		borrowedBooks.remove(book.getIsbn());
	}





	public int getBorrowedCount() {
		return borrowedBooks.size();
	}

}
