package coffee_manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "shift")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shift_id", unique = true, nullable = false)
    private Integer shiftId;

    @Column(name = "time_start")
    private Date timeStart;

    @Column(name = "time_end")
    private Date timeEnd;

    @Column(name = "text_note")
    private String textNote;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User users;
}
