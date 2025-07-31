package vn.com.hdbank.lunch_order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.com.hdbank.lunch_order.dto.BeUserOrderDto;
import vn.com.hdbank.lunch_order.dto.CreateOrderFormDto;
import vn.com.hdbank.lunch_order.dto.CreateUserOrderDto;
import vn.com.hdbank.lunch_order.dto.UpdateOrderFormDto;
import vn.com.hdbank.lunch_order.dto.response.OrderFormDetailResponseDto;
import vn.com.hdbank.lunch_order.dto.response.UserOrderResponseDto;
import vn.com.hdbank.lunch_order.entity.BeUserOrder;
import vn.com.hdbank.lunch_order.service.OrderFormService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/order-forms")
@RequiredArgsConstructor
public class OrderFormController {

    private final OrderFormService orderFormService;

    @PostMapping
    public ResponseEntity<OrderFormDetailResponseDto> create(@RequestBody CreateOrderFormDto dto) {
        return ResponseEntity.ok(orderFormService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderFormDetailResponseDto> update(@PathVariable Long id, @RequestBody UpdateOrderFormDto dto) {
        return ResponseEntity.ok(orderFormService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderFormDetailResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(orderFormService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderFormDetailResponseDto>> getAll() {
        return ResponseEntity.ok(orderFormService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderFormService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<UserOrderResponseDto> placeOrder(@PathVariable Long id, @RequestBody CreateUserOrderDto dto, Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(orderFormService.placeOrder(id, dto, username));
    }

    @PostMapping("/be-users")
    public ResponseEntity<BeUserOrder> addBeUser(@RequestBody BeUserOrderDto dto) {
        return ResponseEntity.ok(orderFormService.addBeUserToOrderForm(dto));
    }

    @PutMapping("/be-users/{beUserOrderId}")
    public ResponseEntity<BeUserOrder> updateBeUserMoney(
            @PathVariable Long beUserOrderId,
            @RequestParam BigDecimal money
    ) {
        return ResponseEntity.ok(orderFormService.updateBeUserInOrderForm(beUserOrderId, money));
    }
}
