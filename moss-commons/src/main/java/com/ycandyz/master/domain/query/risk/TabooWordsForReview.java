package com.ycandyz.master.domain.query.risk;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 基础商品表 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Getter
@Setter
@ApiModel(value="base_taboo_words对象", description="敏感词表")
public class TabooWordsForReview extends Model {

   @ApiModelProperty(value = "词组名称")
   private String phraseName;

   @ApiModelProperty(value = "敏感词")
   private String tabooWords;

   @ApiModelProperty(value = "处理方式")
   private Integer treatmentMethod;

}
