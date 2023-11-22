package com.ssafy.enjoytrip.plan.controller;

import com.ssafy.enjoytrip.map.controller.MapController;
import com.ssafy.enjoytrip.map.model.dto.GugunDto;
import com.ssafy.enjoytrip.plan.model.dto.PlanDateDetailDto;
import com.ssafy.enjoytrip.plan.model.dto.PlanDto;
import com.ssafy.enjoytrip.plan.model.dto.PlanTimeDetailDto;
import com.ssafy.enjoytrip.plan.model.service.PlanDateService;
import com.ssafy.enjoytrip.plan.model.service.PlanService;
import com.ssafy.enjoytrip.plan.model.service.PlanTimeDetailService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.*;

@Api(value = "핫플찾기 API", description = "/hotPlace API")
@CrossOrigin(origins = {"*"}) // 다른 서버에서 Ajax 요청이 와도 서비스 되도록 설정
@RestController
@RequestMapping("/plan")
public class PlanController {
    private Logger logger = LoggerFactory.getLogger(MapController.class);
    private PlanService planService;
    private PlanDateService planDateService;
    private PlanTimeDetailService planTimeService;
    private static final String SUCCESS = "success";
    public PlanController(
            PlanService planService,
            PlanDateService planDateService,
            PlanTimeDetailService planTimeService) {
        this.planDateService = planDateService;
        this.planService = planService;
        this.planTimeService = planTimeService;
    }

    @PostMapping
    public ResponseEntity<?> insertPlan(@RequestBody Map<String, Object> response){
        PlanDto plan = new PlanDto();
        plan.setTitle(response.get("title").toString());
        plan.setEndDate(response.get("endDate").toString());
        plan.setStartDate(response.get("startDate").toString());
        plan.setUserId(response.get("userId").toString());
        planService.writePlan(plan);

        logger.debug("planController......................response:{}", response.get("totalInfo").getClass().getSimpleName());
        LinkedHashMap<String, Object> planByDate = (LinkedHashMap<String, Object>) response.get("totalInfo");

        logger.debug("planByDate:{}", planByDate.toString());
        logger.debug("planByDateSize:{}", planByDate.keySet().size());

        for(String i: planByDate.keySet()){
            PlanDateDetailDto curDate = new PlanDateDetailDto();
            curDate.setPlanNo(plan.getPlanNo());
            curDate.setDate(i);
            planDateService.insertPlanDate(curDate);
            ArrayList datePlan = (ArrayList) planByDate.get(i);
            int dateIndex = curDate.getDateNo();
            for(int j = 0, end = datePlan.size();j<end;j++){
                PlanTimeDetailDto timeDto = new PlanTimeDetailDto();
                LinkedHashMap timePlan = (LinkedHashMap) datePlan.get(j);
                timeDto.setDateNo(dateIndex);
                timeDto.setLocation(timePlan.get("contentId").toString());
                planTimeService.insertTime(timeDto);
            }
        }
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getPlanList(@RequestParam String userId){
        //logger.debug("EXECUTED..............................");
        List<PlanDto> planList = planService.getPlanList(userId);
        List<List<List<PlanTimeDetailDto>>> result = new ArrayList<>();
        //logger.debug("planList..............................................:{}", planList);
        for(int i = 0, end = planList.size(); i< end;i++){
            List<PlanDateDetailDto> dateList = planDateService.getDateList(planList.get(i).getPlanNo());
            //logger.debug("dateList..............................................:{}", dateList);
            List<List<PlanTimeDetailDto>> temp = new ArrayList<>();
            for(int j = 0, end2 = dateList.size(); j < end2;j++){
                List<PlanTimeDetailDto> timeList = planTimeService.getTimeList(dateList.get(j).getDateNo());
                for(int k = 0, end3 = timeList.size(); k < end3;k++){
                    int contentId = Integer.parseInt(timeList.get(i).getLocation());

                }
                //logger.debug("timeList..............................................:{}", timeList);
                temp.add(timeList);
            }
            result.add(temp);
        }
        return new ResponseEntity<List<List<List<PlanTimeDetailDto>>>>(result, HttpStatus.OK);
        //return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
