package com.caramel.restapi.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.caramel.restapi.member.model.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("members")
public class MemberController {
	
	private MemberService memberService;
	
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("mbrList")
	public Object mbrList() {
		log.debug("/memberList start");
		List<Member> mbrList = memberService.getMbrList();
		return mbrList;
	}
	
	@GetMapping("mbrBySeq/{mbrSeq}")
	public Object mbrListByMbrSeq(@PathVariable("mbrSeq") int mbrSeq) {
		log.debug("mbrSeq = {}", mbrSeq);
		List<Member> member = memberService.findMbrByMbrSeq(mbrSeq);
		return member;
	}
	
	// Member Join
	@PostMapping(value = "mbrJoin")
	public ResponseEntity<Member> mbrJoin(@RequestBody Member member) {
		try {
			log.debug("member = {}", member.toString());
			return new ResponseEntity<>(memberService.insert(member), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Member Edit
	@PostMapping(value = "mbrEdit")
	public ResponseEntity<String> mbrEdit(@RequestBody Member member) {
		try {
			log.debug("member = {}", member.toString());
			Integer updatedCnt = memberService.updateByMbrId(member);
			return new ResponseEntity<>(String.format("%d updated", updatedCnt), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Id만 받아오기 때문에 @RequestParam 사용
	// 위의 Insert, Update처럼 Member 받아와서 Id만 빼서 사용해도 상관없음
	@ResponseBody
	@PostMapping(value = "mbrDelete")
	public ResponseEntity<String> mbrDelete(@RequestParam(value = "mbrId") String mbrId) {
		try {
			log.debug("member Id = {}", mbrId);
			Integer deletedCnt = memberService.deleteByMbrId(mbrId);
			return new ResponseEntity<>(String.format("%d deleted", deletedCnt), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "mbrLogin")
	public Object mbrLogin(@RequestParam(value = "mbrId") String mbrId, @RequestParam(value = "mbrPwd") String mbrPwd) {
		//String rawPassword = mbrPwd;
		//String encodedPwd = passwordEncoder.encode(rawPassword);
		log.debug("controller -> member Id = {}", mbrId);
		log.debug("controller -> member Pwd = {}", mbrPwd);
		List<Member> member = memberService.mbrLogin(mbrId, mbrPwd);
		return member;
	}
}
