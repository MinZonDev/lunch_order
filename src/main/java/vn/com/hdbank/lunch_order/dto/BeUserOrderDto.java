package vn.com.hdbank.lunch_order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BeUserOrderDto {
    private Long orderFormId;
    private Long userId;
    private BigDecimal money;
}

