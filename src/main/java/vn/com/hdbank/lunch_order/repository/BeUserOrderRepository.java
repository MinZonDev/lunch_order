package vn.com.hdbank.lunch_order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.hdbank.lunch_order.entity.BeUserOrder;

import java.util.List;
import java.util.Optional;

public interface BeUserOrderRepository extends JpaRepository<BeUserOrder, Long> {
    List<BeUserOrder> findByOrderFormId(Long orderFormId);
    Optional<BeUserOrder> findByOrderFormIdAndUserId(Long orderFormId, Long userId);
}

