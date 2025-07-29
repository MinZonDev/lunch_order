package vn.com.hdbank.lunch_order.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "be_users_orders") // tên rõ ràng hơn
@Data
public class BeUserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 100)
    private String userName; // có thể giữ lại nếu muốn lưu tên hiển thị tại thời điểm đặt

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "display_name", length = 100)
    private String displayName;

    @Column(name = "contribution_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal money; // rename cho rõ là tiền người đó góp

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createTime = LocalDateTime.now();

    // Một đơn có thể có nhiều người đặt từ app Be
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_form_id", nullable = false)
    private OrderForm orderForm;

    // Người dùng (có thể trùng nhau qua nhiều đơn)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

