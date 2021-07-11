package io.github.thebesteric.framework.mocker.unit.test.demo.domain;

import io.github.thebesteric.framework.mocker.annotation.MockProp;
import io.github.thebesteric.framework.mocker.annotation.MockPropGroup;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SkuList {
    /** 价格（元） */
    @MockProp({"109.00", "139.00"})
    private String price;
    /** 划线价（元） */
    @MockProp({"null", "199.00"})
    private String underlinePrice;
    /** 包含的规格code */
    @MockProp(group = {
            @MockPropGroup({"pcms01_01", "pcms01_02"}),
            @MockPropGroup({"pcms02_01", "pcms02_02"})
    })
    private List<String> specValueCodeList = new ArrayList<>();
    /** 是否可以被选中（即是否有库存） 0 不可 1 可 */
    @MockProp({"1", "1"})
    private Integer available;
    /** poskey */
    @MockProp({"1200012", "1200013"})
    private String poskey;
    /** menuSkuId */
    @MockProp({"menusku0353010", "menusku0353011"})
    private String menuSkuId;
    /** 商品图片 */
    @MockProp({"https://pcmd.stg.starbucks.com.cn/test/ecmenu/202103/-EC-1615386599205-800x800-zh.png",
            "https://pcmd.stg.starbucks.com.cn/test/ecmenu/202103/-EC-1615386727626-800x800-zh.png"})
    private String imgUrl;
    /** 商品状态：1：下架，2：上架 */
    @MockProp({"2", "2"})
    private Integer status;
    /** 库存状态：0：售罄，1：库存紧张，2：库存充足 */
    @MockProp({"2", "2"})
    private Integer stockStatus;
}
