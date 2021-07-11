package io.github.thebesteric.framework.mocker.unit.test.demo.domain;

import io.github.thebesteric.framework.mocker.annotation.MockProp;
import lombok.Data;

@Data
public class Title {
    @MockProp({"235", "236", "237", "null"})
    private Integer iconId;
    @MockProp({"1", "2", "3", "null"})
    private Integer mainTitleShow;
    @MockProp({"须知", "测试", "须知1", "null"})
    private String mainTitle;
    @MockProp("https://ml-storage.stg.starbucks.com.cn/app/sbuxappeclayout/f62430a4-3684-4b21-b718-edc681aa2de7")
    private String icon;
    @MockProp("1")
    private Integer iconShow;
    @MockProp("null")
    private String mainTitleBgColor;
    @MockProp("1")
    private String topMargin;
    @MockProp("1")
    private String bottomMargin;
    @MockProp("1")
    private Integer radius;
}
