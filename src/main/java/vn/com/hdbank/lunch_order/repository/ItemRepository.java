package vn.com.hdbank.lunch_order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.lunch_order.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
