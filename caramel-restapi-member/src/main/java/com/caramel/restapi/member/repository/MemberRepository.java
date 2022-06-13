package com.caramel.restapi.member.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.caramel.restapi.member.model.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class MemberRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private final MemberRowMapper memberRowMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public MemberRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.memberRowMapper = new MemberRowMapper();
	}
	
	public List<Member> findList() {
		log.debug("findList query : {}", MemberSql.SELECT);
		return namedParameterJdbcTemplate.query(MemberSql.SELECT
				, EmptySqlParameterSource.INSTANCE
				, this.memberRowMapper);
	}
	
	// Member Seq로 멤버 찾기
	public List<Member> findMbrByMbrSeq(int mbrSeq) {
		String qry = MemberSql.SELECT
						+ MemberSql.MBRSEQ_CONDITION;
		SqlParameterSource param = new MapSqlParameterSource("mbrSeq", mbrSeq);
		return namedParameterJdbcTemplate.query(qry, param, this.memberRowMapper);
	}
	
	// Member Join - 회원가입
	public Member insert(Member member) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		//String rawPassword = member.getMbrPwd();
		//String encodedPwd = passwordEncoder.encode(rawPassword);
		SqlParameterSource parameterSource = new MapSqlParameterSource("mbrId", member.getMbrId())
													.addValue("mbrPwd", member.getMbrPwd())
													.addValue("mbrNm", member.getMbrNm())
													.addValue("mbrTelNo", member.getMbrTelNo())
													.addValue("mbrMphonNo", member.getMbrMphonNo());
		int affectedRows = namedParameterJdbcTemplate.update(MemberSql.INSERT, parameterSource, keyHolder);
		log.debug("{} inserted, new id = {}", affectedRows, keyHolder.getKey());
		member.setMbrSeq(keyHolder.getKey().intValue());
		return member;
	}
	
	// Member Update - 회원 정보 수정
	public Integer updateByMbrId(Member member) {
		String qry = MemberSql.UPDATE + MemberSql.MBRID_CONDITION;
		//String rawPassword = member.getMbrPwd();
		//String encodedPwd = passwordEncoder.encode(rawPassword);
		SqlParameterSource parameterSource = new MapSqlParameterSource("mbrId", member.getMbrId())
													.addValue("mbrPwd", member.getMbrPwd())
													.addValue("mbrNm", member.getMbrNm())
													.addValue("mbrTelNo", member.getMbrTelNo())
													.addValue("mbrMphonNo", member.getMbrMphonNo());
		return namedParameterJdbcTemplate.update(qry, parameterSource);
	}
	
	// Member Delete - 회원 삭제
	public Integer deleteByMbrId(String mbrId) {
		SqlParameterSource parameterSource = new MapSqlParameterSource("mbrId", mbrId);
		return namedParameterJdbcTemplate.update(MemberSql.DELETE + MemberSql.MBRID_CONDITION, parameterSource);
	}
	
	// Member Login
	public List<Member> mbrLogin(String mbrId, String mbrPwd) {
		//String rawPassword = mbrPwd;
		//String encodedPwd = passwordEncoder.encode(rawPassword);
		String qry = MemberSql.SELECT
						+ MemberSql.MBRID_CONDITION
						+ MemberSql.MBRPWD_CONDITION;
		SqlParameterSource param = new MapSqlParameterSource("mbrId", mbrId)
										.addValue("mbrPwd", mbrPwd);
		return namedParameterJdbcTemplate.query(qry, param, this.memberRowMapper);
	}
	
}
