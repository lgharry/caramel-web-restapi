package com.caramel.restapi.member;

import java.util.List;

import org.springframework.stereotype.Service;

import com.caramel.restapi.member.model.Member;
import com.caramel.restapi.member.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	// Member List 출력
	public List<Member> getMbrList() {
		return this.memberRepository.findList();
	}
	
	// Member Seq에 해당하는 Member만 출력
	public List<Member> findMbrByMbrSeq(int mbrSeq) {
		log.debug("Service -> mbr_seq = {}", mbrSeq);
		return this.memberRepository.findMbrByMbrSeq(mbrSeq);
	}
	
	// Member 추가 (회원가입)
	public Member insert(Member member) {
		return this.memberRepository.insert(member);
	}
	
	// Member 정보 수정
	public Integer updateByMbrId(Member member) {
		log.debug("member Id = {}", member.getMbrId());
		return memberRepository.updateByMbrId(member);
	}
	
	// Member 삭제
	public Integer deleteByMbrId(String mbrId) {
		log.debug("member Id = {}", mbrId);
		return memberRepository.deleteByMbrId(mbrId);
	}
	
	// Member Login => id, pwd 맞는 Member만 출력
	public List<Member> mbrLogin(String mbrId, String mbrPwd) {
		log.debug("Service -> mbr_id = {}", mbrId);
		log.debug("Service -> mbr_pwd = {}", mbrPwd);
		return this.memberRepository.mbrLogin(mbrId, mbrPwd);
	}
}
