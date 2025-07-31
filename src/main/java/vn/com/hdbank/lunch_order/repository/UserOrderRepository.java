package vn.com.hdbank.lunch_order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.lunch_order.entity.UserOrder;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
}
