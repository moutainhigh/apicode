package com.ycandyz.master.validation;

/**
 * @author wef
 * @Null(groups = OnCreate.class)  // 标记为OnCreate时才验证
 * @NotNull(groups = OnUpdate.class)
 * @create 2019-01-26
 */
public interface ValidatorContract {

    interface OnCreate {
    }

    interface OnUpdate {
    }
}
