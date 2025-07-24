package vn.com.hdbank.lunch_order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UpdateOrderFormDto {
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;
    private BigDecimal totalPrice;
    private String dividingMethod;
    private String status;
}

