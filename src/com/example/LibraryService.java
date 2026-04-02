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

	private final LoanRepository loanRepository;

	public LibraryService(BookRepository bookRepository, MemberRepository memberRepository,
			LoanRepository loanRepository) {
		this.bookRepository = bookRepository;
		this.memberRepository = memberRepository;
		this.loanRepository = loanRepository;
	}

	public void borrowBook(String memberId, String isbn) {

		// 存在チェックのみ
		bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));

		memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));

		// ★ Loanベースで貸出可否を判断
		boolean alreadyBorrowed = loanRepository.findAll().stream()
				.anyMatch(l -> l.getIsbn().equals(isbn) && !l.isReturned());

		if (alreadyBorrowed) {
			throw new BookNotAvailableException(isbn);
		}

		// ★ Loanのみで完結
		Loan loan = new Loan(isbn, memberId, DEFAULT_BORROW_DAYS);
		loanRepository.save(loan);
	}

	public void returnBook(String memberId, String isbn) {

		Loan loan = loanRepository.findAll().stream().filter(l -> l.getIsbn().equals(isbn))
				.filter(l -> l.getMemberId().equals(memberId)).filter(l -> !l.isReturned()).findFirst()
				.orElseThrow(() -> new LibraryException("貸出が見つかりません"));

		loan.returnLoan();
	}

	public List<Book> getAvailableBooks() {

		List<String> borrowedIsbns = loanRepository.findAll().stream().filter(l -> !l.isReturned()).map(Loan::getIsbn)
				.toList();

		return bookRepository.findAll().stream().filter(book -> !borrowedIsbns.contains(book.getIsbn())).toList();
	}

	public List<Book> searchBooks(String keyword) {
		return bookRepository.search(keyword);
	}

	public List<Loan> findOverdueLoans() {
		return loanRepository.findAll().stream().filter(Loan::isOverdue).toList();
	}

	public void removeBook(String isbn) {

		boolean borrowed = loanRepository.findAll().stream().anyMatch(l -> l.getIsbn().equals(isbn) && !l.isReturned());

		if (borrowed) {
			throw new LibraryException("貸出中の本は削除できません: " + isbn);
		}

		bookRepository.remove(isbn);
	}

}
