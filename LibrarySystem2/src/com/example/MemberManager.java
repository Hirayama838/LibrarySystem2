package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 図書館の会員を管理するクラス 会員の追加、検索、削除などの機能を提供する
 */
public class MemberManager implements MemberRepository{
	// 会員IDをキーとした会員のマップ
	private Map<String, Member> members;

	/**
	 * MemberManagerの初期化を行うコンストラクタ
	 */
	public MemberManager() {
		this.members = new HashMap<>();
	}
	@Override
	public void save(Member member) {
		if (members.containsKey(member.getMemberId())) {
			throw new IllegalArgumentException("この会員IDは既に使用されています: " + member.getMemberId());
		}
		members.put(member.getMemberId(), member);
	}
	
	@Override
	public Optional<Member> findById(String memberId) {
		return Optional.ofNullable(members.get(memberId));
	}

	public int getTotalMemberCount() {
		return members.size();
	}
}
