package com.ycandyz.master.enums;

/**
 * @author sangang
 */
public enum OperateEnum {
    //
    INSERT("新增"),
    UPDATE("更新"),
    DELETE("删除");
    //
    private String description;

    OperateEnum(String description) {
        this.description = description;
    }
}
