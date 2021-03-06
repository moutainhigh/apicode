package com.ycandyz.master.service.miniprogram;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.model.miniprogram.MpTransferApplyModel;
import com.ycandyz.master.entities.miniprogram.MpTransferApply;

/**
 * <p>
 * @Description 小程序转交接申请 业务接口类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-16
 * @version 2.0
 */
public interface IMpTransferApplyService extends IService<MpTransferApply>{

    /**
     * 新增交接信息
     * @param model
     * @return
     */
    Boolean add(MpTransferApplyModel model);

    /**
     * 修改交接信息
     * @param entity
     * @return
     */
    Boolean updateTransferApply(MpTransferApply entity);
}
