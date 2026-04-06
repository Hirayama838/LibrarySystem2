package com.example;

import java.util.Optional;

public interface MemberRepository {
	Optional<Member> findById(String memberId);

	void save(Member member);

}
