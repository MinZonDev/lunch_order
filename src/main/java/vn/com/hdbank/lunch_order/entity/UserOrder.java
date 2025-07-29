package vn.com.hdbank.lunch_order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_orders")
@Data
@EqualsAndHashCode(exclude = "orderDetails")
@ToString(exclude = "orderDetails")
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal money;

    @Column(length = 50, nullable = false)
    private String status = "pending";

    @Column(name = "submit_time", nullable = false)
    private LocalDateTime submitTime = LocalDateTime.now();

    @Column(name = "is_payment", nullable = false)
    private Boolean isPayment = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_form_id", nullable = false)
    @JsonIgnore
    private OrderForm orderForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<UserOrderDetail> orderDetails = new ArrayList<>();
}
