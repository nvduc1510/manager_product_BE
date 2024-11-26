package coffee_manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pay_statement")
public class PayStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_statement_id", unique = true, nullable = false)
    private Integer payStatementID;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "shift_id", nullable = false)
    private Shift shift;

    @Column(name = "total_salary")
    private Integer totalSalary;

    @Column(name = "date_created")
    private Date createDate;
}