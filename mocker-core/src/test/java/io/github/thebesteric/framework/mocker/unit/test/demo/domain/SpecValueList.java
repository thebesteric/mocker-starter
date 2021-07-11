package io.github.thebesteric.framework.mocker.unit.test.demo.domain;

import io.github.thebesteric.framework.mocker.annotation.MockIgnore;
import io.github.thebesteric.framework.mocker.annotation.MockProp;
import io.github.thebesteric.framework.mocker.annotation.MockPropGroup;
import lombok.Data;

@Data
public class SpecValueList {
    /** 明细 code */
    @MockProp(group = {
            @MockPropGroup({"pcms01_01", "pcms01_02"}),
            @MockPropGroup({"pcms02_04", "pcms02_11"})
    })
    private String specCodeChild;
    /** 明细名 */
    @MockProp(group = {
            @MockPropGroup({"黑色", "白色"}),
            @MockPropGroup({"500ml", "370ml"})
    })
    private String specName;
    /** 排序 */
    @MockProp(group = {
            @MockPropGroup({"1", "2"}),
            @MockPropGroup({"3", "4"})
    })
    private Integer sequence;
    /** 明细图片 */
    private String defaultPictureUrl;
    /** 是否默认选项 */
    @MockProp("1")
    private Integer available;
    /** 明细单位 */
    @MockIgnore
    private String volume;
    /** 明细描述 */
    @MockIgnore
    private String description;
}
