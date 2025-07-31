package vn.com.hdbank.lunch_order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.hdbank.lunch_order.entity.OrderForm;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFormDetailResponseDto {

    private Long id;
    private String name;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private String status;
    private BigDecimal totalPrice;
    private String dividingMethod;
    private LocalDateTime createdAt;
    private List<ItemResponseDto> items;

    public static OrderFormDetailResponseDto fromEntity(OrderForm orderForm) {
        List<ItemResponseDto> itemDtos = orderForm.getMenu().getItems().stream()
                .map(item -> new ItemResponseDto(
                        item.getMenu().getId(),
                        item.getId(),
                        item.getItemName(),
                        item.getPrice(),
                        item.getItemImage(),
                        item.getIsActive(),
                        item.getItemDetails()
                ))
                .collect(Collectors.toList());

        return new OrderFormDetailResponseDto(
                orderForm.getId(),
                orderForm.getName(),
                orderForm.getBeginTime(),
                orderForm.getEndTime(),
                orderForm.getStatus(),
                orderForm.getTotalPrice(),
                orderForm.getDividingMethod(),
                orderForm.getCreatedAt(),
                itemDtos
        );
    }
}
