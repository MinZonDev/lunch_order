package vn.com.hdbank.lunch_order.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.hdbank.lunch_order.dto.CreateOrderFormDto;
import vn.com.hdbank.lunch_order.dto.UpdateOrderFormDto;
import vn.com.hdbank.lunch_order.entity.Menu;
import vn.com.hdbank.lunch_order.entity.OrderForm;
import vn.com.hdbank.lunch_order.entity.User;
import vn.com.hdbank.lunch_order.repository.MenuRepository;
import vn.com.hdbank.lunch_order.repository.OrderFormRepository;
import vn.com.hdbank.lunch_order.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderFormService {

    private final OrderFormRepository orderFormRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

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
}

