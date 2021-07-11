package io.github.thebesteric.framework.mocker.unit.test.demo.domain;

import io.github.thebesteric.framework.mocker.annotation.MockIgnore;
import io.github.thebesteric.framework.mocker.annotation.MockProp;
import lombok.Data;

@Data
public class ImageText {
    @MockProp("8528")
    private String id;
    @MockProp("1")
    private Integer sequence;
    @MockIgnore
    private Integer bgCoverId;
    @MockIgnore
    private String bgCover;
    @MockIgnore
    private String dpLink;
}
