package com.ssafy.enjoytrip.member.service;

import java.sql.SQLException;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.member.model.dto.MemberDto;
import com.ssafy.enjoytrip.member.model.dto.ProfileImgDto;

public interface MemberService {

	int idCheck(String userId);
//	void joinMember(MemberDto memberDto, MultipartFile profileImg);
	void joinMember(MemberDto memberDto);
	void updateMember(MemberDto memberDto);
	void deleteMember(String userId);
	//void profileImgUpload(ProfileImgDto profileImgDto);
	
	MemberDto login(MemberDto memberDto) throws Exception;
	MemberDto userInfo(String userId) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleRefreshToken(String userId) throws Exception;
}