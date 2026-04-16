package com.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LibraryServiceTest {
	
	private LibraryService service;
	private BookRepository bookRepository;
	private MemberRepository memberRepository;
	
	
	@BeforeEach
	void setUp() {
		bookRepository = new BookManager();
		memberRepository = new MemberManager();
		
		service = new LibraryService(bookRepository,memberRepository);
		
		bookRepository.save(new Book("001", "Java", "山田"));
		memberRepository.save(new Member("m001", "田中", "test@test.com"));
	}
	
	@Test
	void サービス経由で貸出できる() {
		service.borrowBook("m001", "001");
		
		Book book = bookRepository.findByIsbn("001").get();
		assertFalse(book.isAvailable());
	}
	
    @Test
    void 存在しない会員は例外() {
        assertThrows(MemberNotFoundException.class, () -> {
            service.borrowBook("xxx", "001");
        });
    }
    
    @Test
    void 返却できる() {
    	service.borrowBook("m001", "001");
    	
    	service.returnBook("m001", "001");
    	
    	Book book = bookRepository.findByIsbn("001").get();
    	assertTrue(book.isAvailable());
    }

}
