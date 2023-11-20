package com.ssafy.enjoytrip.plan.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.plan.model.dto.PlanDto;

@Mapper
public interface PlanDao {
	int writePlan(PlanDto planDto) throws SQLException;
	List<PlanDto> listPlan(Map<String, Object> param) throws SQLException;
	PlanDto getPlan(int plan_no) throws SQLException;
	void modifyPlan(PlanDto planDto) throws SQLException;
	void deletePlan(int plan_no) throws SQLException;
	int getTotalPlanCount(Map<String, String> param) throws SQLException;
}
