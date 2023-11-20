package com.ssafy.enjoytrip.member.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.exception.MyException;
import com.ssafy.enjoytrip.member.model.dao.MemberDao;
import com.ssafy.enjoytrip.member.model.dto.MemberDto;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao;
	private Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	public MemberServiceImpl(MemberDao memberDao) {
		super();
		this.memberDao = memberDao;
	}
	
	@Override
	public int idCheck(String userId){
		try {
			logger.debug("idCheck..................................:{}", userId);
			MemberDto mDto = memberDao.userInfo(userId);
			if(mDto == null) throw new MyException("해당 아이디는 사용하실 수 있습니다.");
			return memberDao.idCheck(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public void joinMember(MemberDto memberDto){
		try {
			logger.debug("joinMember..................................:{}", memberDto);
			//이미지 관련
//			if(profileImg != null){
//            	logger.info("image : {}",profileImg.getOriginalFilename());
//
////                //1.서버에 이미지파일을 저장, 이미지를 서버에 업로드
////                //1-a.파일 저장 위치를 지정하여 파일 객체에 포장
////                String originalFilename = profileImg.getOriginalFilename();
////                //1-a-1.파일명이 중복되지 않도록 변경
////                String uploadFileName = UUID.randomUUID() + "_" + originalFilename;
////                //1-a-2.업로드, 폴더를 날짜별로 생성
////                String newUploadPath = FileUploadUtil.makeUploadDirectory(uploadRootPath);
////                File uploadFile = new File(newUploadPath + File.separator + uploadFileName);
//                //1-b. 파일을 해당 경로에 업로드
////                profileImg.transferTo(uploadFile);
////
////                String savePath = newUploadPath.substring(uploadRootPath.length());
////
////                // 이미지 처리 해주고 나서 엔티티에 setImage로 저장
////                memberDto.setProfileImg(savePath + File.separator + uploadFileName);
//                memberDao.joinMember(memberDto, profileImg);
//            }
			memberDao.joinMember(memberDto);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MyException("회원가입 중 오류 발생!");
		}
	}

	@Override
	public void updateMember(MemberDto memberDto){
		try {
			logger.debug("updateMember..................................:{}", memberDto);
			memberDao.updateMember(memberDto);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MyException("회원 정보 수정 중 오류 발생!");
		}
	}
	
	@Override
	public void deleteMember(String userId){
		try {
			logger.debug("deleteMember..................................:{}", userId);
			memberDao.deleteMember(userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MyException("회원 정보 삭제 중 오류 발생!");
		}
	}

	// JWT 로그인을 위해 추가
	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		return memberDao.login(memberDto);
	}
	
	@Override
	public MemberDto userInfo(String userId) throws Exception {
		return memberDao.userInfo(userId);
	}

	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		memberDao.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return memberDao.getRefreshToken(userId);
	}

	@Override
	public void deleRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		memberDao.deleteRefreshToken(map);
	}
}
