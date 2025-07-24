package vn.com.hdbank.lunch_order.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "deposit_users")
@Data
public class DepositUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "update_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal updateValue;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "current_balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal currentBalance;

    @Column(name = "after_balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal afterBalance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updater_id", nullable = false)
    private User updater;
}
