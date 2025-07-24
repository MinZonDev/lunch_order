package vn.com.hdbank.lunch_order.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_users")
@Data
public class BookUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "display_name", length = 100)
    private String displayName;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal money;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_form_id", nullable = false)
    private OrderForm orderForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
