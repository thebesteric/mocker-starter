package io.github.thebesteric.framework.mocker.unit.test.demo.domain;

import io.github.thebesteric.framework.mocker.annotation.MockIgnore;
import io.github.thebesteric.framework.mocker.annotation.MockProp;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDetailResponse {

    /** 商品名称 */
    @MockProp("QA测试-H可爱马克杯")
    private String name;
    /** 默认价格对应的图片 */
    @MockProp("https://www-static.chinacdn.starbucks.com.cn/prod/images/pages/apps-mobile-screens-1.png")
    private String imgUrl;
    /** 默认价格对应的sku */
    @MockProp("menusku0353010")
    private String menuSkuId;
    /** 默认价格对应的spu */
    @MockProp("menuspu0353")
    private String menuSpuId;
    /** 图片或视频 */
    @MockProp(length = 1)
    private List<ImageOrVideo> imageSupportVideo;
    /** 原价（元） */
    @MockProp("109.00")
    private String price;
    /** 划线价格（元） */
    @MockIgnore
    private String underlinePrice;
    /** 原价（分） */
    @MockProp("10900")
    private Integer priceInt;
    /** 划线价格（分） */
    @MockIgnore
    private Integer underlinePriceInt;
    /** 商品是否已下架，1：下架，2：上架 */
    @MockProp("2")
    private Integer status;
    /** 库存状态：0：售罄，1：库存紧张，2：库存充足 */
    @MockProp("2")
    private Integer stockStatus;
    /** PCM 的 SKU 的属性 */
    @MockProp("1200012")
    private String poskey;
    /** 预计获得星星数 */
    @MockProp("2.1")
    private String starEarned;
    /** 预计获取星星数文案 */
    @MockProp("此处为单件商品预估可累计的星星数量，以最终下单后到帐的情况为准。")
    private String starEarnedNotice;
    /** 标签信息 */
    @MockProp(length = 3)
    private List<LabelList> labelList;
    /** 购物须知等文案集合 */
    @MockProp(length = 4)
    private List<Notice> notice = new ArrayList<>();
    /** 商品主图 */
    @MockProp(length = 1)
    private List<ImageText> imageText = new ArrayList<>();
    /** 商品详情h5链接 */
    @MockProp("https://artwork-assets-staging-sbux.starbucks.com.cn/mobile/h5-e-commerce/index.html#/customized/pages/onlineShop/mopProduct")
    private String imageTextLink;
    /** 商品分享链接 */
    @MockProp("https://artwork-assets-staging-sbux.starbucks.com.cn/mobile/h5-e-commerce/index.html#/customized/pages/onlineShop/productShare?_n=0.4355210088897482&menuSpuId=menuspu0353")
    private String shareLink;
    /** sku列表 */
    @MockProp(length = 2)
    private List<SkuList> skuList = new ArrayList<>();
    /** 规格列表 */
    @MockProp(length = 2)
    private List<SpecList> specList = new ArrayList<>();
    /** 商品分类 */
    @MockIgnore
    private Category category;
}
