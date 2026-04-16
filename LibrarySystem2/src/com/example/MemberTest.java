package com.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberTest {

	private Member member;
	private Book book;

	@BeforeEach
	void setUp() {
		member = new Member("m001", "田中", "test@test.com");
		book = new Book("001", "Java", "山田");
	}

	@Test
	void 貸出すると本は利用不可になる() {
		member.borrow(book, 14);
		assertFalse(book.isAvailable());
	}

	@Test
	void 貸出中の本は借りられない() {
		member.borrow(book, 14);

		assertThrows(BookNotAvailableException.class, () -> {
			member.borrow(book, 14);
		});
	}

	@Test
	void 返却すると利用可能になる() {
		member.borrow(book, 14);

		member.returnBook(book);

		assertTrue(book.isAvailable());
	}
	
	
    @Test
    void 延長できる() {
        member.borrow(book, 14);

        assertDoesNotThrow(() -> {
            member.extendLoan(book.getIsbn(), 7);
        });
    }
    
    @Test
    void 貸出上限を超えると例外() {
    	for (int i = 0; i < 5; i++) {
    		member.borrow(new Book("00" + i, "本", "著者"), 14);
    	}
    	
    	Book extra = new Book("999", "NG", "著者");
    	
    	assertThrows(LibraryException.class, () ->{
    		member.borrow(extra, 14);
    	});
    	
    }
}
