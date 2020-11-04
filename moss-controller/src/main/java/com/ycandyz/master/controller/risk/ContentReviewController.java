package com.ycandyz.master.controller.risk;


import com.ycandyz.master.api.RequestParams;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.query.risk.ContentReviewQuery;
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
     * @Description: 商品详情内容审核展示
     * @Author li Zhuo
     * @Date 2020/10/28
     * @Version: V1.0
    */
    @ApiOperation(value = "内容审核展示",notes = "内容审核",httpMethod = "POST")
    @PostMapping("/content/list")
    public ReturnResponse listMallItem(@RequestBody RequestParams<ContentReviewQuery> requestParams) {
        List<ContentReviewDTO> contentReviewReps = contentReviewService.listMallItem(requestParams);
        return ReturnResponse.success(contentReviewReps);
    }

    /**
     * @Description: 商品详情审核  屏蔽
     * @Author li Zhuo
     * @Date 2020/10/28
     * @Version: V1.0
     */
    @ApiOperation(value = "商品详情审核",notes = "商品详情审核",httpMethod = "PUT")
    @PutMapping("/content/updateMallItem")
    public ReturnResponse updateMallItem(@PathVariable Long  contentId) {
        int i = contentReviewService.updateOneMallItem(contentId);
        return ReturnResponse.success("屏蔽");
    }

    /**
     * @Description: 商友圈审核  屏蔽
     * @Author li Zhuo
     * @Date 2020/10/28
     * @Version: V1.0
     */
    @ApiOperation(value = "商友圈审核",notes = "商友圈审核",httpMethod = "PUT")
    @PutMapping("/content/updateFootprint")
    public ReturnResponse updateFootprint(@PathVariable Long  contentId) {
        contentReviewService.updateOneFootprint(contentId);
        return ReturnResponse.success("屏蔽");
    }

    /**
     * @Description: 企业动态审核  屏蔽
     * @Author li Zhuo
     * @Date 2020/10/28
     * @Version: V1.0
     */
    @ApiOperation(value = "企业动态审核",notes = "企业动态审核",httpMethod = "PUT")
    @PutMapping("/content/updateOrganizeNews")
    public ReturnResponse updateOrganizeNews(@PathVariable Long  contentId) {
        contentReviewService.updateOneOrganizeNews(contentId);
        return ReturnResponse.success("屏蔽");
    }
}
