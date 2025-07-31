package vn.com.hdbank.lunch_order.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateUserOrderDto {
    private String note;
    private List<OrderItemDto> items;

    @Data
    public static class OrderItemDto {
        private Long itemId;
        private Integer quantity;
    }
}
