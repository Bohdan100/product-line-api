package corp.product.common.validator;

import corp.product.dto.LineDto;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class LineValidator {
    private final List<String> errors = new ArrayList<>();

    public LineValidator validate(LineDto dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            errors.add("Line name cannot be empty");
        }
        return this;
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public String getErrors() {
        return String.join("; ", errors);
    }
}