package com.allane.leaseadmin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class LeasingContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Contract number is required")
    @Column(name = "contract_number", unique = true, nullable = false)
    private String contractNumber;

    @NotNull(message = "Monthly rate is required")
    @Column(name = "monthly_rate")
    private BigDecimal monthlyRate;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @NotNull(message = "Customer is required")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    @NotNull(message = "Vehicle is required")
    private Vehicle vehicle;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        LeasingContract that = (LeasingContract) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @PrePersist
    protected void onCreate() {
        this.contractNumber = generateUniqueContractNumber();
    }

    private String generateUniqueContractNumber() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());

        Random random = new Random();
        int minDigits = 100;
        int maxDigits = 999_999;
        int randomPart = random.nextInt(maxDigits - minDigits + 1) + minDigits;

        return timestamp + String.format("%09d", randomPart);
    }
}
