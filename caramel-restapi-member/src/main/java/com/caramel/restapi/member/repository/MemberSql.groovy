package com.caramel.restapi.member.repository;

class MemberSql {
	
	public static final String SELECT = """
		SELECT mbr_seq, mbr_id, mbr_pwd, mbr_nm, mbr_tel_no, mbr_mphon_no FROM tb_diary_mbr WHERE 1=1
	""";

	public static final String MBRSEQ_CONDITION = """
		AND mbr_seq = :mbrSeq
	""";
	
	public static final String INSERT = """
		INSERT INTO tb_diary_mbr (mbr_id, mbr_pwd, mbr_nm, mbr_tel_no, mbr_mphon_no)
		VALUES (:mbrId, :mbrPwd, :mbrNm, :mbrTelNo, :mbrMphonNo)
	""";
	
	public static final String UPDATE = """
		UPDATE tb_diary_mbr SET mbr_pwd = :mbrPwd, mbr_nm = :mbrNm,
								mbr_tel_no = :mbrTelNo, mbr_mphon_no = :mbrMphonNo
		WHERE 1=1
	""";
	
	public static final String MBRID_CONDITION = """
		AND mbr_id = :mbrId
	""";
	
	public static final String DELETE = """
		DELETE FROM tb_diary_mbr
		WHERE 1=1
	""";
	
	public static final String MBRPWD_CONDITION = """
		AND mbr_pwd = :mbrPwd
	""";
}