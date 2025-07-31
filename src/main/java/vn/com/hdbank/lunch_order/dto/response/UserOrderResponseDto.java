package vn.com.hdbank.lunch_order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.hdbank.lunch_order.entity.UserOrder;
import vn.com.hdbank.lunch_order.entity.UserOrderDetail;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderResponseDto {

    private Long id;
    private String note;
    private BigDecimal money;
    private String status;
    private LocalDateTime submitTime;
    private Long orderFormId;
    private String username;
    private List<UserOrderDetailDto> orderDetails;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserOrderDetailDto {
        private Long itemId;
        private String itemName;
        private Integer quantity;
        private BigDecimal price;
    }

    public static UserOrderResponseDto fromEntity(UserOrder userOrder) {
        List<UserOrderDetailDto> detailDtos = userOrder.getOrderDetails().stream()
                .map(detail -> new UserOrderDetailDto(
                        detail.getItem().getId(),
                        detail.getItem().getItemName(),
                        detail.getQuantity(),
                        detail.getItem().getPrice()
                ))
                .collect(Collectors.toList());

        return new UserOrderResponseDto(
                userOrder.getId(),
                userOrder.getNote(),
                userOrder.getMoney(),
                userOrder.getStatus(),
                userOrder.getSubmitTime(),
                userOrder.getOrderForm().getId(),
                userOrder.getUser().getUsername(),
                detailDtos
        );
    }
}
