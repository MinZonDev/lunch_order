package vn.com.hdbank.lunch_order.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "items")
@Data
@EqualsAndHashCode(exclude = "orderDetails")
@ToString(exclude = "orderDetails")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", nullable = false, length = 255)
    private String itemName;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "old_price", precision = 10, scale = 2)
    private BigDecimal oldPrice;

    @Column(name = "item_image", length = 500)
    private String itemImage;

    @Column(name = "item_details", columnDefinition = "TEXT")
    private String itemDetails;

    @Column(name = "is_active", nullable = false)
    private Integer isActive = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserOrderDetail> orderDetails = new ArrayList<>();
}
