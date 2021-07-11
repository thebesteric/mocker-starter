package io.github.thebesteric.framework.mocker.unit.test.demo.domain;

import lombok.Data;

@Data
public class Category {
    /** 一级分类 code */
    private String code;
    /** 二级分类名 */
    private String name;
    /** 二级分类 */
    private String category;
}
