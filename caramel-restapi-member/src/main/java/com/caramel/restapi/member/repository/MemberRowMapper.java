package com.caramel.restapi.member.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.caramel.restapi.member.model.Member;

public class MemberRowMapper implements RowMapper<Member>{

	@Override
	public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
		Member member = new Member();
		member.setMbrSeq(rs.getInt("mbr_seq"));
		member.setMbrId(rs.getString("mbr_id"));
		member.setMbrPwd(rs.getString("mbr_pwd"));
		member.setMbrNm(rs.getString("mbr_nm"));
		member.setMbrTelNo(rs.getString("mbr_tel_no"));
		member.setMbrMphonNo(rs.getString("mbr_mphon_no"));
		return member;
	}
}
