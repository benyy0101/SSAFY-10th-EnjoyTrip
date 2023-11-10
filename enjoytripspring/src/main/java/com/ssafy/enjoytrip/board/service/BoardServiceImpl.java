package com.ssafy.enjoytrip.board.service;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.MyException;
import com.ssafy.enjoytrip.board.model.dao.BoardDao;
import com.ssafy.enjoytrip.board.model.dto.BoardDto;
import com.ssafy.enjoytrip.util.PageNavigation;
import com.ssafy.enjoytrip.util.SizeConstant;

@Service("BoardService")
@Repository
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao boardDao;

	public BoardServiceImpl(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	@Override
	public void writeArticle(BoardDto boardDto) {
		try {
			boardDao.writeArticle(boardDto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<BoardDto> listArticle(Map<String, String> map) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			String key = map.get("key");
			if ("userid".equals(key))
				key = "b.user_id";
			param.put("key", key == null ? "" : key);
			param.put("word", map.get("word") == null ? "" : map.get("word"));
			int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
			int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
			param.put("start", start);
			param.put("listsize", SizeConstant.LIST_SIZE);
			return boardDao.listArticle(param);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MyException("listArticle 처리 중 오류 발생!");

		}
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) {
		try {
			PageNavigation pageNavigation = new PageNavigation();

			int naviSize = SizeConstant.NAVIGATION_SIZE;
			int sizePerPage = SizeConstant.LIST_SIZE;
			int currentPage = Integer.parseInt(map.get("pgno"));

			pageNavigation.setCurrentPage(currentPage);
			pageNavigation.setNaviSize(naviSize);
			int totalCount = boardDao.getTotalArticleCount(map);
			pageNavigation.setTotalCount(totalCount);
			int totalPageCount = (totalCount - 1) / sizePerPage + 1;
			pageNavigation.setTotalPageCount(totalPageCount);
			boolean startRange = currentPage <= naviSize;
			pageNavigation.setStartRange(startRange);
			boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
			pageNavigation.setEndRange(endRange);
			pageNavigation.makeNavigator();

			return pageNavigation;
			
		} catch (Exception e) {
			throw new MyException("makePageNavigation 처리 중 오류 발생!");
		}
		
	}

	@Override
	public BoardDto getArticle(int articleNo) {
		try {
			return boardDao.getArticle(articleNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException("getArticle 처리 중 오류 발생!");
		}
	}

	@Override
	public void updateHit(int articleNo) {
		try {
			boardDao.updateHit(articleNo);
		} catch (Exception e) {
			throw new MyException("updateHit 처리 중 오류 발생!");
		}
	}

	@Override
	public void modifyArticle(BoardDto boardDto) {
		try {
			boardDao.modifyArticle(boardDto);
		} catch (Exception e) {
			throw new MyException("modifyArticle 처리 중 오류 발생!");
		}
	}

	@Override
	public void deleteArticle(int articleNo) {
		try {
			boardDao.deleteArticle(articleNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException("deleteArticle 처리 중 오류 발생!");
		}
	}
}
