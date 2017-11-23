package com.yimei.hs.same.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.FeeClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class PageJiekuanDTO extends BaseFilter<PageJiekuanDTO> {
    private Long fukuanId;

    private Long orderId;

    private Long hsId;

    private BigDecimal amount;

    private Long capitalId;

    private BigDecimal useInterest;

    private Integer useDays;

    private LocalDateTime jiekuanDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate jiekuanDateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate jiekuanDateEnd;




}

