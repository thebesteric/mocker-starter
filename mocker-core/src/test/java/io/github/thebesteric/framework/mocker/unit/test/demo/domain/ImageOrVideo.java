package io.github.thebesteric.framework.mocker.unit.test.demo.domain;

import io.github.thebesteric.framework.mocker.annotation.MockIgnore;
import io.github.thebesteric.framework.mocker.annotation.MockProp;
import lombok.Data;

@Data
public class ImageOrVideo {
    /** 0：图片，1：视频，2：gif */
    @MockProp("0")
    private Integer type;
    /** 视频链接 */
    @MockIgnore
    private String videoUrl = null;
    /** 图片链接 */
    @MockProp("https://pcmd.stg.starbucks.com.cn/test/ecmenu/menuspu0353-1616488060478584")
    private String imgUrl;
}
