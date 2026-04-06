package com.example;

/**
 * 図書館の会員を管理するクラス 会員情報と貸出中の書籍リストを管理する
 */

public class Member {
	// 会員を一意に識別するID
	private String memberId;
	// 会員の名前
	private String name;
	// 会員のメールアドレス
	private String email;

	private static final int MAX_BORROW_LIMIT = 5;

	public Member(String memberId, String name, String email) {
		this.memberId = memberId;
		this.name = name;
		this.email = email;
	}

	// Getterメソッド
	public String getMemberId() {
		return memberId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

}
