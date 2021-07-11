package io.github.thebesteric.framework.mocker.unit.test.demo.domain;

import io.github.thebesteric.framework.mocker.annotation.MockProp;
import lombok.Data;

@Data
public class LabelList {
    @MockProp({"Label01025", "Label06", "Label073"})
    private String code;
    @MockProp({"业务类型标签", "杯类", "测试标签1"})
    private String name;
    @MockProp({"3", "1", "1"})
    private Integer type;
    @MockProp({"0", "0", "2"})
    private Integer sequence;
}
