package vn.com.hdbank.lunch_order.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.hdbank.lunch_order.dto.BeUserOrderDto;
import vn.com.hdbank.lunch_order.dto.CreateOrderFormDto;
import vn.com.hdbank.lunch_order.dto.UpdateOrderFormDto;
import vn.com.hdbank.lunch_order.entity.BeUserOrder;
import vn.com.hdbank.lunch_order.entity.Menu;
import vn.com.hdbank.lunch_order.entity.OrderForm;
import vn.com.hdbank.lunch_order.entity.User;
import vn.com.hdbank.lunch_order.repository.BeUserOrderRepository;
import vn.com.hdbank.lunch_order.repository.MenuRepository;
import vn.com.hdbank.lunch_order.repository.OrderFormRepository;
import vn.com.hdbank.lunch_order.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderFormService {

    private final OrderFormRepository orderFormRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final BeUserOrderRepository beUserOrderRepository;

    public OrderForm create(CreateOrderFormDto dto) {
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

        return orderFormRepository.save(orderForm);
    }

    public OrderForm update(Long id, UpdateOrderFormDto dto) {
        OrderForm form = orderFormRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderForm not found"));

        form.setName(dto.getName());
        form.setBeginTime(dto.getBeginTime());
        form.setEndTime(dto.getEndTime());
        form.setTotalPrice(dto.getTotalPrice());
        form.setDividingMethod(dto.getDividingMethod());
        form.setStatus(dto.getStatus());
        form.setUpdatedAt(LocalDateTime.now());

        return orderFormRepository.save(form);
    }

    public void delete(Long id) {
        orderFormRepository.deleteById(id);
    }

    public OrderForm get(Long id) {
        return orderFormRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderForm not found"));
    }

    public List<OrderForm> getAll() {
        return orderFormRepository.findAll();
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

        // Tính lại totalPrice dựa trên tất cả BeUserOrders của đơn
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

        // Update totalPrice của orderForm
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

