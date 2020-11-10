package com.ycandyz.master.domain.response.mall;

import com.ycandyz.master.entities.mall.MallItemVideoComplaint;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 商品视频投诉 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-11-10
 * @version 2.0
 */
@Getter
@Setter
@TableName("mall_item_video_complaint")
public class MallItemVideoComplaintResp extends MallItemVideoComplaint {

}
