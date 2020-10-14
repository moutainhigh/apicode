package com.ycandyz.master.validation;

public class RegexCons {

    public static final String MOBILE = "^[1][3,4,5,6,7,8,9][0-9]{9}$";

    public static final String PHONE = "(^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{1,}))?$)|(^[1][3,4,5,6,7,8,9][0-9]{9}$)";

    public static final String EMAIL = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
}
