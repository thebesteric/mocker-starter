package io.github.thebesteric.framework.mocker.unit.test.demo.domain;

import io.github.thebesteric.framework.mocker.annotation.MockProp;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SpecList {

    /** 明细 code */
    @MockProp({"pcms01", "pcms02"})
    private String specCode;
    /** 明细名 */
    @MockProp({"颜色1", "容量"})
    private String specName;
    /** 排序 */
    @MockProp({"2", "2"})
    private Integer sequence;
    /** 规格类型，一行展示几个 2：二个，3：三个 */
    @MockProp({"2", "2"})
    private Integer specType;
    /** 规格值 */
    @MockProp(length = 2)
    private List<SpecValueList> specValueList = new ArrayList<>();

}
