package corp.product.dto;

import lombok.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;
import corp.product.data.RecordEntity;

@Getter
@Setter
@AllArgsConstructor
@ToString(exclude = "records")
@EqualsAndHashCode
public class LineDto implements Serializable {

    private Long id;

    @NotBlank(message = "Fill in the field!")
    private String name;

    List<RecordEntity> records;
}
