package com.ssafy.enjoytrip.board.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.board.model.dto.BoardDto;
import com.ssafy.enjoytrip.board.service.BoardService;
import com.ssafy.enjoytrip.member.model.dto.MemberDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import springfox.documentation.annotations.ApiIgnore;

@RestController // Controller 내에서 작성하는 모든 메서드에 기본적으로 @ResponseBody로 출력됨.
@RequestMapping("/board") // 요청하는 자원(Domain)명을 붙인다. ==> /book이 이미 있어서 /rest로 함
@Api(value="Board REST-ful위한 API", description = "정보 공유 게시판")
@CrossOrigin({"*"})   // 다른 서버에서 AJax 
public class BoardRestController {
	private Logger logger = LoggerFactory.getLogger(BoardRestController.class);
	private BoardService boardService;

	private static final String SUCCESS = "success";

	public BoardRestController(BoardService boardService) {
		this.boardService = boardService;
	}

	/**
	 * ResponseEntity 응답 코드 + 응답 데이터를 전송하기 위한 객체
	 */
	
	// ${root}/board/insertReview
    @ApiOperation(value="정보 공유 등록", notes = "여행 정보 리뷰를 등록한다.")
	@ApiResponse(code = 200, message="success")
    @PostMapping("/insertReview")
    public ResponseEntity<String> insertReviewBoard(@ModelAttribute BoardDto bDto, @ApiIgnore HttpSession session) {

        MemberDto mDto = (MemberDto) session.getAttribute("loginUser");
        System.out.println(mDto);
        // userId, subject, content 입력
        bDto.setUserId("ssafy");
        System.out.println(bDto);
        boardService.writeArticle(bDto);
        
        // return "redirect:/board/boardMain";
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
	
}
