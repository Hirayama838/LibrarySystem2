package com.example;

import java.time.LocalDate;
import java.util.List;

/**
 * 図書館の主要な業務ロジックを提供するサービスクラス 書籍の貸出/返却、検索などの機能を統合的に管理する
 */
public class LibraryService {
	// 書籍管理用のマネージャー
	private final BookRepository bookRepository;
	// 会員管理用のマネージャー
	private final MemberManager memberManager;
	// 標準の貸出期間（日数）
	private static final int DEFAULT_BORROW_DAYS = 14;

	public LibraryService(BookRepository bookRepository, MemberManager memberManager) {
		this.bookRepository = bookRepository;
		this.memberManager = memberManager;
	}

	public void borrowBook(String memberId, String isbn) {
		// 会員と書籍の存在確認
		Member member = memberManager.findById(memberId).orElseThrow(() -> new IllegalArgumentException("会員が見つかりません"));

		Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new IllegalArgumentException("本が見つかりません"));

		if (member.getBorrowedCount() >= 5) {
			throw new IllegalStateException("貸出上限に達しています");
		}

		// 貸出可能かの確認
		if (!book.isAvailable()) {
			throw new IllegalStateException("この本は現在貸出できません");
		}

		// 貸出処理の実行
		book.borrowBook(memberId, DEFAULT_BORROW_DAYS);
		member.borrowBook(isbn);
	}

	public void returnBook(String memberId, String isbn) {
		// 会員と書籍の存在確認
		Member member = memberManager.findById(memberId).orElseThrow(() -> new IllegalArgumentException("会員が見つかりません"));

		Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new IllegalArgumentException("本が見つかりません"));

		// 返却権限の確認
		if (!memberId.equals(book.getBorrowedBy())) {
			throw new IllegalStateException("この本は別の会員が借りています");
		}

		// 返却処理の実行
		book.returnBook();
		member.returnBook(isbn);
	}

	public List<Book> searchBooks(String keyword) {
		return bookRepository.search(keyword);
	}
	
	public List<Book> getAvailableBooks() {
	    return bookRepository.findAll().stream()
	            .filter(Book::isAvailable)
	            .toList();
	}
	
	public List<Book> findOverdueBooks() {
	    LocalDate today = LocalDate.now();

	    return bookRepository.findAll().stream()
	            .filter(book -> !book.isAvailable())
	            .filter(book -> book.getDueDate() != null)
	            .filter(book -> book.getDueDate().isBefore(today))
	            .toList();
	}

}
