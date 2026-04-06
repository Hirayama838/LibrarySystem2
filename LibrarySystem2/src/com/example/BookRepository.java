package com.example;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

	void save(Book book);

	Optional<Book> findByIsbn(String isbn);

	List<Book> findAll();

	void remove(String isbn);
	
	List<Book> search(String keyword);

}
