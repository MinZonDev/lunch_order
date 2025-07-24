package vn.com.hdbank.lunch_order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BeItem {
    private String item_name;
    private BigDecimal price;
    private BigDecimal old_price;
    private String item_image;
    private String item_details;
    private Integer is_active;
}
