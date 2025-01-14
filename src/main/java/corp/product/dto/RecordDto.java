package corp.product.dto;

import lombok.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString(exclude = {"line", "author"})
public class RecordDto implements Serializable {
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Enter the date!")
    private LocalDate date;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @NotNull(message = "Enter the time!")
    private LocalTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @NotNull(message = "Enter the time!")
    private LocalTime endTime;

    @NotBlank(message = "Fill in the field!")
    private String nameOfOrganization;

    @NotBlank(message = "Fill in the field!")
    private String nameOfProduct;

    @NotBlank(message = "Fill in the field!")
    private String variant;

    private String side;

    @Min(value = 1, message = "Fill in the field!")
    private int quantity;

    private LineDto line;

    private UserDto author;
}
