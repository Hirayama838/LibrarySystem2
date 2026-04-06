package com.example;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 図書館の蔵書全体を管理するクラス 書籍の追加、検索、在庫確認などの機能を提供する
 */

public class BookManager implements BookRepository {
	// ISBNをキーとした書籍のマップ
	private Map<String, Book> books;

	/**
	 * BookManagerの初期化を行うコンストラクタ
	 */
	public BookManager() {
		this.books = new HashMap<>();
	}

	@Override
	public void save(Book book) {
		if (books.containsKey(book.getIsbn())) {
			throw new IllegalArgumentException("この本は既に登録されています:" + book.getIsbn());
		}
		books.put(book.getIsbn(), book);
	}

	@Override
	public Optional<Book> findByIsbn(String isbn) {
		return Optional.ofNullable(books.get(isbn));
	}

	@Override
	public List<Book> findAll() {
		return new ArrayList<>(books.values());
	}

	public int getTotalBookCount() {
		return books.size();
	}

	@Override
	public void remove(String isbn) {
		books.remove(isbn);

	}

	@Override
	public List<Book> search(String keyword) {
		return books.values().stream()
				.filter(book -> book.getTitle().contains(keyword) || book.getAuthor().contains(keyword))
				.collect(Collectors.toList());
	}

}
