package com.ssafy.enjoytrip.plan.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.plan.model.dao.PlanDao;
import com.ssafy.enjoytrip.plan.model.dto.PlanDto;
import com.ssafy.enjoytrip.plan.model.dto.PlanException;
import com.ssafy.enjoytrip.util.PageNavigation;

@Service
@Repository
public class PlanServiceImpl implements PlanService {
	@Autowired
	private PlanDao planDao;
	
	public PlanServiceImpl(PlanDao planDao) {
		this.planDao = planDao;
	}

	@Override
	public int writePlan(PlanDto planDto) {
		try {
			return planDao.writePlan(planDto);
		} catch (SQLException e) {
			throw new PlanException("계획 등록 중 오류 발생");
		}
	}

	@Override
	public List<PlanDto> listPlan(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanDto getPlan(int plan_no) {
		try {
			PlanDto plan = planDao.getPlan(plan_no);
			if(plan == null) {
				throw new PlanException("요청한 계획은 등록되지 않은 계획 정보입니다.");
			}
			return plan;
		} catch (SQLException e) {
			throw new PlanException("계획 정보 조회 중 오류 발생");
		}
	}

	@Override
	public void modifyPlan(PlanDto planDto) {
		try {
			planDao.modifyPlan(planDto);
		} catch (Exception e) {
			throw new PlanException("계획 정보 수정 중 오류 발생");
		}
	}

	@Override
	public void deletePlan(int plan_no) {
		try {
			planDao.deletePlan(plan_no);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PlanException("계획 정보 삭제 중 오류 발생");
		}
	}
}
