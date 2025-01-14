package corp.product.data;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"line", "author"})
@EqualsAndHashCode(exclude = {"line", "author"})
public class RecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @Column(name = "start_time", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime;

    @Column(name = "name_of_organization", nullable = false, length = 255)
    private String nameOfOrganization;

    @Column(name = "name_of_product", nullable = false, length = 255)
    private String nameOfProduct;

    @Column(name = "variant", nullable = false, length = 255)
    private String variant;

    @Column(name = "side", nullable = false, length = 255)
    private String side;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id", nullable = false)
    private Line line;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
}
