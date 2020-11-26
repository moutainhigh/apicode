package com.ycandyz.master.controller.risk;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.query.risk.ContentReviewQuery;
import com.ycandyz.master.domain.query.risk.ReviewBatchExamineParam;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.domain.response.risk.ContentReviewRep;
import com.ycandyz.master.dto.risk.ContentReviewDTO;
import com.ycandyz.master.service.risk.ContentReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contentReview")
@Api(value="内容审核",tags={"内容审核-动态接口"})
@Slf4j
public class ContentReviewController {


    @Autowired
    private ContentReviewService contentReviewService;

    /**
     * @Description: 内容审核展示
     * @Author li Zhuo
     * @Date 2020/10/28
     * @Version: V1.0
     */
    @ApiOperation(value = "内容审核展示",notes = "内容审核",httpMethod = "POST")
    @PostMapping("/content/list")
    public ReturnResponse<Page<ContentReviewRep>> list(@RequestBody RequestParams<ContentReviewQuery> requestParams) {
        log.info("内容审核展示查询请求入参:{}", JSON.toJSONString(requestParams));
        ReturnResponse<Page<ContentReviewRep>> contentReviewReps = contentReviewService.list(requestParams);
        log.info("内容审核展示查询请求响应:{}", JSON.toJSONString(contentReviewReps));
        return contentReviewReps;
    }


    /**
     * @Description: 商品详情/企业动态/商友圈审核  0通过/1屏蔽
     * @Author li Zhuo
     * @Date 2020/10/28
     * @Version: V1.0
     */
    @ApiOperation(value = "商品详情/企业动态/商友圈审核",notes = "0通过/1屏蔽",httpMethod = "PUT")
    @PutMapping("/content/examine")
    public ReturnResponse updateStatus(@RequestBody ReviewParam reviewParam) {
        log.info("内容审核通过屏蔽请求入参:{}", JSON.toJSONString(reviewParam));
        ReturnResponse response = contentReviewService.examine(reviewParam);
        log.info("内容审核通过屏蔽请求响应:{}", JSON.toJSONString(response));
        return response;
    }


    /**
     * @Description: 批量通过/屏蔽
     * @Author li Zhuo
     * @Date 2020/10/28
     * @Version: V1.0
     */
    @ApiOperation(value = "商品详情/企业动态/商友圈批量审核",notes = "0通过/1屏蔽",httpMethod = "POST")
    @PostMapping("/content/batchExamine")
    public ReturnResponse updateMallItem(@RequestBody ReviewBatchExamineParam reviewParams) {
        log.info("内容审核批量通过屏蔽入参:{}", JSON.toJSONString(reviewParams));
        ReturnResponse response = contentReviewService.batchExamine(reviewParams);
        log.info("内容审核批量通过屏蔽请求响应:{}", JSON.toJSONString(response));
        return response;
    }

}
