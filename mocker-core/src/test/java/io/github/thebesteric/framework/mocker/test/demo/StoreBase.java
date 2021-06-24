package io.github.thebesteric.framework.mocker.test.demo;

import lombok.Data;

/**
 * StoreBase
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-22 13:00
 * @since 1.0
 */
@Data
public abstract class StoreBase {

    /** 门店 id */
    private String id;

    /** 门店名称 */
    private String name;

    /** 直径 */
    private Integer distance;

    /** 经度 */
    private Double longitude;

    /** 纬度 */
    private Double latitude;

    /** 商户号 */
    private String partnerId;

    /** 是否营业，1：是 2：否 */
    private Integer businessStatus = 1;

    /** 是否有库存售卖：0：否，1：是，2：库存紧张 */
    private Integer sell = 1;

    /** 下一天的营业时间段 */
    private String[] afterDayBusinessTime;

    /** 当天的关店时间 */
    private String todayCloseBusinessTime;

    /** 明天的开店时间 */
    private String nextDayOpenBusinessTime;

    private String designType;
    private String formatType;
    private String portfolioType;
    private String programType;
    private String operationType;
    private String deliveryType;
    private String opsTradeAreaType;

    /** 店铺形态：0：实体店，1：手推车 （默认0） */
    private Integer storeFrom = 0;

    /** 店铺楼层 */
    private String floor;

}
