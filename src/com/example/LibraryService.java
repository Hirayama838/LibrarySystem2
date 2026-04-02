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
	private final MemberRepository memberRepository;
	// 標準の貸出期間（日数）
	private static final int DEFAULT_BORROW_DAYS = 14;

	public LibraryService(BookRepository bookRepository, MemberRepository memberRepository) {
		this.bookRepository = bookRepository;
		this.memberRepository = memberRepository;
	}

	public void borrowBook(String memberId, String isbn) {
		Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));

		Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));

		book.borrow(member, DEFAULT_BORROW_DAYS);
		member.borrow(book);
		
	}
	
	public void returnBook(String memberId, String isbn) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(memberId));
		
		Book book = bookRepository.findByIsbn(isbn)
				.orElseThrow(() -> new BookNotFoundException(isbn));
		
		book.returnBy(member);
		member.returnBook(book);
	}



	public List<Book> searchBooks(String keyword) {
		return bookRepository.search(keyword);
	}

	public List<Book> getAvailableBooks() {
		return bookRepository.findAll().stream().filter(Book::isAvailable).toList();
	}

	public List<Book> findOverdueBooks() {
		LocalDate today = LocalDate.now();

		return bookRepository.findAll().stream().filter(book -> !book.isAvailable())
				.filter(book -> book.getDueDate() != null).filter(book -> book.getDueDate().isBefore(today)).toList();
	}

	public void removeBook(String isbn) {
		Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));

		if (!book.isAvailable()) {
			throw new LibraryException("貸出中の本は削除できません: " + isbn);
		}

		bookRepository.remove(isbn);
	}

}
