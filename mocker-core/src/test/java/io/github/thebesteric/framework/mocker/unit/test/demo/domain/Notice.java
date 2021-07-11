package io.github.thebesteric.framework.mocker.unit.test.demo.domain;

import io.github.thebesteric.framework.mocker.annotation.MockIgnore;
import io.github.thebesteric.framework.mocker.annotation.MockProp;
import lombok.Data;

@Data
public class Notice {
    @MockProp({"15914", "15917", "15920", ""})
    private String id;
    @MockProp({"0", "1", "2", "3"})
    private Integer sequence;
    @MockIgnore
    private String linkUrl;
    @MockProp({"null", "测试", "2", "3"})
    private String linkText;
    @MockIgnore
    private String linkTitle;
    @MockProp({"<a href=\"https://www.starbucks.com.cn/help/legal/terms-of-starbucks-gift-card/\" target=\"_blank\">https://www.starbucks.com.cn/help/legal/terms-of-starbucks-gift-card/</a><br/>",
            "3333", "6562", "1234"})
    private String text;
    @MockProp({"GroupNotice", "GroupNotice", "GroupNotice", "link"})
    private String type;
    @MockProp(length = 4)
    private Title title;
}
