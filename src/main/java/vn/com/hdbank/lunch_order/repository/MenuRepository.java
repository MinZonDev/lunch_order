package vn.com.hdbank.lunch_order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.lunch_order.entity.Menu;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long > {
}
