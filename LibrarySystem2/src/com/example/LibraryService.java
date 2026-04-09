package com.example;

import java.util.List;

/**
 * 図書館の主要な業務ロジックを提供するサービスクラス 書籍の貸出/返却、検索などの機能を統合的に管理する
 */
public class LibraryService {
	// 書籍管理用のマネージャー
	private final BookRepository bookRepository;
	// 会員管理用のマネージャー
	private final MemberRepository memberRepository;
	// 標準の貸出期間（日数）
	private static final int DEFAULT_BORROW_DAYS = 14;

	public LibraryService(BookRepository bookRepository, MemberRepository memberRepository,
			) {
		this.bookRepository = bookRepository;
		this.memberRepository = memberRepository;
	}

	public void borrowBook(String memberId, String isbn) {

		Book book = bookRepository.findByIsbn(isbn).orElseThrow();

		book.borrow(memberId, 14);

		bookRepository.save(book);
	}

	public void returnBook(String memberId, String isbn) {

		Book book = bookRepository.findByIsbn(isbn).orElseThrow();

		book.returnBook(memberId);

		bookRepository.save(book);
	}

	public List<Book> getAvailableBooks() {
		return bookRepository.findAll().stream().filter(Book::isAvailable).toList();
	}

	public List<Book> searchBooks(String keyword) {
		return bookRepository.search(keyword);
	}

	public List<Loan> findOverdueLoans() {
		return bookRepository.findAll().stream().flatMap(book -> book.getOverdueLoans().stream()).toList();
	}
	
	public void removeBook(String isbn) {

	    Book book = bookRepository.findByIsbn(isbn).orElseThrow();

	    if (!book.isAvailable()) {
	        throw new LibraryException("貸出中の本は削除できません: " + isbn);
	    }

	    bookRepository.remove(isbn);
	}



}
