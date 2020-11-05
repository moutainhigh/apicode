package com.ycandyz.master.controller.risk;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.query.risk.ContentReviewQuery;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.domain.response.risk.ContentReviewRep;
import com.ycandyz.master.dto.risk.ContentReviewDTO;
import com.ycandyz.master.service.risk.ContentReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contentReview")
@Api(value="内容审核",tags={"内容审核-动态接口"})
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
        ReturnResponse<Page<ContentReviewRep>> contentReviewReps = contentReviewService.list(requestParams);
        return contentReviewReps;
    }

    /**
     * @Description: 商品详情内容审核展示
     * @Author li Zhuo
     * @Date 2020/10/28
     * @Version: V1.0
    */
    @ApiOperation(value = "商品详情内容审核展示",notes = "内容审核",httpMethod = "POST")
    @PostMapping("/content/listMallItem")
    public ReturnResponse<Page<List<ContentReviewDTO>>> listMallItem(@RequestBody RequestParams<ContentReviewQuery> requestParams) {
        Page<List<ContentReviewDTO>> contentReviewReps = contentReviewService.listMallItem(requestParams);
        return ReturnResponse.success(contentReviewReps);
    }

    /**
     * @Description: 商友圈内容审核展示
     * @Author li Zhuo
     * @Date 2020/10/28
     * @Version: V1.0©
     */
    @ApiOperation(value = "商友圈内容审核展示",notes = "内容审核",httpMethod = "POST")
    @PostMapping("/content/listFootprint")
    public ReturnResponse<Page<List<ContentReviewDTO>>> listFootpring(@RequestBody RequestParams<ContentReviewQuery> requestParams) {
        Page<List<ContentReviewDTO>> contentReviewReps = contentReviewService.listFootprint(requestParams);
        return ReturnResponse.success(contentReviewReps);
    }

    /**
     * @Description: 商品详情/企业动态/商友圈审核  通过/屏蔽
     * @Author li Zhuo
     * @Date 2020/10/28
     * @Version: V1.0
     */
    @ApiOperation(value = "商品详情/企业动态/商友圈审核",notes = "通过/屏蔽",httpMethod = "PUT")
    @PutMapping("/content/updateStatus")
    public ReturnResponse updateMallItem(@RequestBody ReviewParam reviewParam) {
        ReturnResponse response = contentReviewService.updateStatus(reviewParam);
        return ReturnResponse.success(response);
    }



}
