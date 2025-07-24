package vn.com.hdbank.lunch_order.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_order_details")
@Data
public class UserOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_order_id", nullable = false)
    private UserOrder userOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
}
