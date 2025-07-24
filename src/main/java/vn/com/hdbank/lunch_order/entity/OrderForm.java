package vn.com.hdbank.lunch_order.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_forms")
@Data
@EqualsAndHashCode(exclude = {"userOrders", "bookUsers"})
@ToString(exclude = {"userOrders", "bookUsers"})
public class OrderForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "begin_time", nullable = false)
    private LocalDateTime beginTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(length = 50, nullable = false)
    private String status = "active";

    @Column(name = "total_price", precision = 15, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "dividing_method", length = 50)
    private String dividingMethod;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @OneToMany(mappedBy = "orderForm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserOrder> userOrders = new ArrayList<>();

    @OneToMany(mappedBy = "orderForm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookUser> bookUsers = new ArrayList<>();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return "active".equals(status) &&
                now.isAfter(beginTime) &&
                now.isBefore(endTime);
    }
}
