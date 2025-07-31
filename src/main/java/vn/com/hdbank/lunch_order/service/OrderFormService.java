package vn.com.hdbank.lunch_order.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.hdbank.lunch_order.dto.BeUserOrderDto;
import vn.com.hdbank.lunch_order.dto.CreateOrderFormDto;
import vn.com.hdbank.lunch_order.dto.CreateUserOrderDto;
import vn.com.hdbank.lunch_order.dto.UpdateOrderFormDto;
import vn.com.hdbank.lunch_order.dto.response.OrderFormDetailResponseDto;
import vn.com.hdbank.lunch_order.dto.response.UserOrderResponseDto;
import vn.com.hdbank.lunch_order.entity.*;
import vn.com.hdbank.lunch_order.exception.BusinessException;
import vn.com.hdbank.lunch_order.repository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderFormService {

    private final OrderFormRepository orderFormRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final BeUserOrderRepository beUserOrderRepository;
    private final ItemRepository itemRepository;
    private final UserOrderRepository userOrderRepository;

    public OrderFormDetailResponseDto create(CreateOrderFormDto dto) {
        Menu menu = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new EntityNotFoundException("Menu not found"));
        User creator = userRepository.findById(dto.getCreatorId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        OrderForm orderForm = new OrderForm();
        orderForm.setName(dto.getName());
        orderForm.setBeginTime(dto.getBeginTime());
        orderForm.setEndTime(dto.getEndTime());
        orderForm.setTotalPrice(dto.getTotalPrice());
        orderForm.setDividingMethod(dto.getDividingMethod());
        orderForm.setStatus("active");
        orderForm.setCreatedAt(LocalDateTime.now());
        orderForm.setMenu(menu);
        orderForm.setCreator(creator);

        OrderForm savedForm = orderFormRepository.save(orderForm);
        return OrderFormDetailResponseDto.fromEntity(savedForm);
    }

    public OrderFormDetailResponseDto update(Long id, UpdateOrderFormDto dto) {
        OrderForm form = orderFormRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderForm not found"));

        form.setName(dto.getName());
        form.setBeginTime(dto.getBeginTime());
        form.setEndTime(dto.getEndTime());
        form.setTotalPrice(dto.getTotalPrice());
        form.setDividingMethod(dto.getDividingMethod());
        form.setStatus(dto.getStatus());
        form.setUpdatedAt(LocalDateTime.now());

        OrderForm updatedForm = orderFormRepository.save(form);
        return OrderFormDetailResponseDto.fromEntity(updatedForm);
    }

    public void delete(Long id) {
        orderFormRepository.deleteById(id);
    }

    public OrderFormDetailResponseDto get(Long id) {
        OrderForm orderForm = orderFormRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderForm not found"));
        return OrderFormDetailResponseDto.fromEntity(orderForm);
    }

    public List<OrderFormDetailResponseDto> getAll() {
        return orderFormRepository.findAll().stream()
                .map(OrderFormDetailResponseDto::fromEntity)
                .toList();
    }

    @Transactional
    public UserOrderResponseDto placeOrder(Long orderFormId, CreateUserOrderDto dto, String username) {
        OrderForm orderForm = orderFormRepository.findById(orderFormId)
                .orElseThrow(() -> BusinessException.notFound("Order Form không tồn tại"));

        if (!orderForm.isActive()) {
            throw BusinessException.badRequest("Order Form đã đóng hoặc không hợp lệ");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> BusinessException.notFound("Người dùng không tồn tại"));

        UserOrder userOrder = new UserOrder();
        userOrder.setOrderForm(orderForm);
        userOrder.setUser(user);
        userOrder.setNote(dto.getNote());
        userOrder.setStatus("pending");

        BigDecimal totalMoney = BigDecimal.ZERO;
        List<UserOrderDetail> details = new ArrayList<>();

        for (CreateUserOrderDto.OrderItemDto itemDto : dto.getItems()) {
            Item item = itemRepository.findById(itemDto.getItemId())
                    .orElseThrow(() -> BusinessException.notFound("Món ăn với ID " + itemDto.getItemId() + " không tồn tại"));

            // Check if the item belongs to the correct menu
            if (!item.getMenu().getId().equals(orderForm.getMenu().getId())) {
                throw BusinessException.badRequest("Món ăn " + item.getItemName() + " không thuộc về menu của order form này.");
            }

            UserOrderDetail detail = new UserOrderDetail();
            detail.setItem(item);
            detail.setQuantity(itemDto.getQuantity());
            detail.setUserOrder(userOrder);
            details.add(detail);

            totalMoney = totalMoney.add(item.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())));
        }

        userOrder.setMoney(totalMoney);
        userOrder.setOrderDetails(details);

        UserOrder savedOrder = userOrderRepository.save(userOrder);
        return UserOrderResponseDto.fromEntity(savedOrder);
    }


    @Transactional
    public BeUserOrder addBeUserToOrderForm(BeUserOrderDto dto) {
        OrderForm orderForm = orderFormRepository.findById(dto.getOrderFormId())
                .orElseThrow(() -> new EntityNotFoundException("OrderForm not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        BeUserOrder beUserOrder = new BeUserOrder();
        beUserOrder.setUser(user);
        beUserOrder.setOrderForm(orderForm);
        beUserOrder.setUserName(user.getUsername());
        beUserOrder.setFullName(user.getName());
        beUserOrder.setDisplayName(user.getDisplayName());
        beUserOrder.setMoney(dto.getMoney());
        beUserOrder.setCreateTime(LocalDateTime.now());

        beUserOrderRepository.save(beUserOrder);

        BigDecimal total = beUserOrderRepository.findByOrderFormId(dto.getOrderFormId()).stream()
                .map(BeUserOrder::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        orderForm.setTotalPrice(total);
        orderFormRepository.save(orderForm);

        return beUserOrder;
    }

    @Transactional
    public BeUserOrder updateBeUserInOrderForm(Long beUserOrderId, BigDecimal newMoney) {
        BeUserOrder beUserOrder = beUserOrderRepository.findById(beUserOrderId)
                .orElseThrow(() -> new EntityNotFoundException("BeUserOrder not found"));

        beUserOrder.setMoney(newMoney);
        beUserOrderRepository.save(beUserOrder);

        Long orderFormId = beUserOrder.getOrderForm().getId();
        BigDecimal total = beUserOrderRepository.findByOrderFormId(orderFormId).stream()
                .map(BeUserOrder::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderForm orderForm = beUserOrder.getOrderForm();
        orderForm.setTotalPrice(total);
        orderFormRepository.save(orderForm);

        return beUserOrder;
    }
}
