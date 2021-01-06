package com.ycandyz.master.domain.enums.organize;

import com.ycandyz.master.domain.enums.IEnum;

/**
 * 企业 实体枚举
 * @author SanGang
 */
public class OrganizeEnum {

    public enum IsGroup implements IEnum<Integer> {
        Type_0(0, "非集团"),
        Type_1(1, "集团");

        private Integer code;
        private String text;

        IsGroup(Integer code, String text) {
            this.code = code;
            this.text = text;
        }

        @Override
        public Integer getCode() {
            return code;
        }

        @Override
        public String getText() {
            return text;
        }

        public static OrganizeEnum.IsGroup parseCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (OrganizeEnum.IsGroup value : values()) {
                if (code.equals(value.code)) {
                    return value;
                }
            }
            return null;
        }
    }


}
