package com.example;

import java.util.List;

public class LibraryService {

	private final BookRepository bookRepository;
	private final MemberRepository memberRepository;

	private static final int DEFAULT_DAYS = 14;

	public LibraryService(BookRepository bookRepository, MemberRepository memberRepository) {
		this.bookRepository = bookRepository;
		this.memberRepository = memberRepository;
	}

	public void borrowBook(String memberId, String isbn) {

		Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));

		Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));

		member.borrow(book, DEFAULT_DAYS);

		memberRepository.save(member);
		bookRepository.save(book);
	}

	public void returnBook(String memberId, String isbn) {

		Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));

		Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));

		member.returnBook(book);

		memberRepository.save(member);
		bookRepository.save(book);
	}

	public List<Book> getAvailableBooks() {
		return bookRepository.findAll().stream().filter(Book::isAvailable).toList();
	}
}