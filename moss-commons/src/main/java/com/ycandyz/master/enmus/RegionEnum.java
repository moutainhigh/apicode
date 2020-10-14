package com.ycandyz.master.enmus;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RegionEnum {
    DONGBEI("东北", new String[]{"黑龙江省", "吉林省", "辽宁省"}),
    HUADONG("华东", new String[]{"上海市", "江苏省", "浙江省", "安徽省", "福建省", "江西省", "山东省", "台湾省"}),
    HUABEI("华北", new String[]{"北京市", "天津市", "山西省", "河北省", "内蒙古自治区"}),
    HUAZHONG("华中", new String[]{"河南省", "湖北省", "湖南省"}),
    HUANAN("华南", new String[]{"广东省", "广西壮族自治区", "海南省", "香港特别行政区", "澳门特别行政区"}),
    XINAN("西南", new String[]{"四川省", "贵州省", "云南省", "重庆市", "西藏自治区"}),
    XIBEI("西北", new String[]{"陕西省", "甘肃省", "青海省", "宁夏回族自治区", "新疆维吾尔自治区"});

    private String region;

    private String[] province;

    RegionEnum(String region, String[] province) {
        this.region = region;
        this.province = province;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String[] getProvince() {
        return province;
    }

    public void setProvince(String[] province) {
        this.province = province;
    }
}
