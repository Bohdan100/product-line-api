package corp.product.data;

import jakarta.persistence.*;

import java.util.List;

import lombok.*;

@Table(name = "line")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "records")
@EqualsAndHashCode(exclude = "records")
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "line_name", nullable = false, unique = true, length = 255)
    private String name;

    @OneToMany(mappedBy = "line", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<RecordEntity> records;
}
