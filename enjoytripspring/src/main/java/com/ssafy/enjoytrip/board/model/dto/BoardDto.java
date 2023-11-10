package com.ssafy.enjoytrip.board.model.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="Board Dto" ,description="게시판을 구성하는 DTO")
@Data
public class BoardDto {
	@ApiModelProperty(value="게시글 번호", example="0")
	private int articleNo;
	@ApiModelProperty(value="사용자 ID", example="Taesoo")
	private String userId;
	@ApiModelProperty(value="제목",example="테스트 제목")
	private String subject;
	@ApiModelProperty(value="글 내용", example="테스트 내용")
	private String content;
	@ApiModelProperty(value="조회수", example="0")
	private int hit;
	@ApiModelProperty(value="작성 시각", example="1999-01-01")
	private String registerTime;
}