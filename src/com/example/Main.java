package com.example;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BookRepository bookRepository = new BookManager();
		MemberRepository memberRepository = new MemberManager();
		LoanRepository loanRepository = new LoanManager();

		LibraryService service =
		    new LibraryService(bookRepository, memberRepository, loanRepository);


		// データ作成
		Book book1 = new Book("001", "Java入門", "山田");
		Book book2 = new Book("002", "Spring入門", "佐藤");

		bookRepository.save(book1);
		bookRepository.save(book2);

		Member member = new Member("m001", "田中", "tanaka@example.com");
		memberRepository.save(member);

		// 貸出
		service.borrowBook("m001", "001");
		
		//　貸出2回目(例外)
		try {
		    service.borrowBook("m001", "001");
		} catch (BookNotAvailableException e) {
		    System.out.println("OK: " + e.getMessage());
		}

		// 確認
		System.out.println(service.getAvailableBooks().contains(book1));

		// 返却
		service.returnBook("m001", "001");

		System.out.println(service.getAvailableBooks().contains(book1));

		try {
			service.borrowBook("m001", "001");
			System.out.println("貸出完了");
		} catch (BookNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (MemberNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (BookNotAvailableException e) {
			System.out.println(e.getMessage());
		}

	}
}
