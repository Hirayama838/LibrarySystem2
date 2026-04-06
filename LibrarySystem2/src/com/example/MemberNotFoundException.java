package com.example;

public class MemberNotFoundException extends LibraryException{
    public MemberNotFoundException(String memberId) {
        super("会員が見つかりません: " + memberId);
    }
}