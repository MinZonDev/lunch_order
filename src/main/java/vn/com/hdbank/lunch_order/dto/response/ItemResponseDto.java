package vn.com.hdbank.lunch_order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDto {
    private Long menuId;
    private Long itemId;
    private String itemName;
    private BigDecimal price;
    private String itemImage;
    private Integer isActive; // status
    private String itemDetails;
}
