package com.ssafy.enjoytrip.plan.controller;

import com.ssafy.enjoytrip.map.controller.MapController;
import com.ssafy.enjoytrip.map.model.dto.GugunDto;
import com.ssafy.enjoytrip.plan.model.dto.PlanDto;
import com.ssafy.enjoytrip.plan.model.dto.PlanTimeDetailDto;
import com.ssafy.enjoytrip.plan.model.service.PlanService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Api(value = "핫플찾기 API", description = "/hotPlace API")
@CrossOrigin(origins = {"*"}) // 다른 서버에서 Ajax 요청이 와도 서비스 되도록 설정
@RestController
@RequestMapping("/plan")
public class PlanController {
    private Logger logger = LoggerFactory.getLogger(MapController.class);
    private PlanService planService;

    private static final String SUCCESS = "success";

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping
    public ResponseEntity<?> insertPlan(@RequestBody Map<String, Object> response){
//        String startDate = plan.get("0").getDate();
//        int temp = plan.size() - 1;
//        String endDate = plan.get(temp.toString()).getDate();
        //logger.debug("PlanController.................map:{}", plan.get("4").get(0).toString());
        //logger.debug("PlanController.................map:{}", plan.toString());
        //logger.debug("PlanController............................reached");
        //logger.debug("PlanController............................response:{}", response);
        //logger.debug("PlanController............................response:{}", response.get("title"));
        //플랜 dto만들기
        PlanDto plan = new PlanDto();
        plan.setTitle(response.get("title").toString());
        plan.setEndDate(response.get("endDate").toString());
        plan.setStartDate(response.get("startDate").toString());
        plan.setUserId(response.get("userId").toString());
        int planIndex = planService.writePlan(plan);
        //logger.debug("PLAN.result....................:{}", result);
        //2.시작날짜, 끝날짜 별로 날짜 dto 만들기
        logger.debug("planController......................response:{}", response.get("totalInfo").getClass().getSimpleName());
        LinkedHashMap<String, Object> planByDate = (LinkedHashMap<String, Object>) response.get("totalInfo");
        logger.debug("planByDate:{}", planByDate.toString());
        logger.debug("planByDateSize:{}", planByDate.keySet().size());
//        for(int i = 0; i < response){
//
//        }
        //장소 dto만들기



        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
