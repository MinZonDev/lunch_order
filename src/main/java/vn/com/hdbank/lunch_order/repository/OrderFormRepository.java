package vn.com.hdbank.lunch_order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.hdbank.lunch_order.entity.OrderForm;

import java.util.List;

public interface OrderFormRepository extends JpaRepository<OrderForm, Long> {
    List<OrderForm> findByStatus(String status);
}

