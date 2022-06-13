package com.caramel.restapi.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Member {
	
	public int mbrSeq;
	public String mbrId;
	public String mbrPwd;
	public String mbrNm;
	public String mbrTelNo;
	public String mbrMphonNo;
	
}
