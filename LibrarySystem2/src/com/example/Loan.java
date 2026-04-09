package com.example;

import java.time.LocalDate;

public class Loan {
	private final String isbn;
	private final String memberId;
	private  LocalDate dueDate;
	private boolean returned;
	
	public Loan(String isbn, String memberId, int days) {
		this.isbn = isbn;
		this.memberId = memberId;
		this.dueDate = LocalDate.now().plusDays(days);
		this.returned = false;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public String getMemberId() {
		return memberId;
	}
	
	public LocalDate getDueDate() {
		return dueDate;
	}
	
	public boolean isReturned() {
		return returned;
	}
	
	public void returnLoan() {
		if (returned) {
			throw new LibraryException("すでに返却済みです");
		}
		this.returned = true;
	}
	
	public boolean isOverdue() {
		return !returned && dueDate.isBefore(LocalDate.now());
	}
	
	public void extend(int days) {
	    if (returned) {
	        throw new LibraryException("返却済みの貸出は延長できません");
	    }
	    if (days <= 0) {
	        throw new IllegalArgumentException("延長日数は正の値である必要があります");
	    }

	    // dueDate を延長
	    // ※ dueDate が final だとエラーになるので注意
	    this.dueDate = this.dueDate.plusDays(days);
	}

}
