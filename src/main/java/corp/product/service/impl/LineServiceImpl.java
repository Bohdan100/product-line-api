package corp.product.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Lookup;
import lombok.AllArgsConstructor;

import corp.product.service.LineService;
import corp.product.repository.LineRepository;
import corp.product.converter.LineConverter;
import corp.product.dto.LineDto;
import corp.product.data.Line;
import corp.product.common.tracker.ExecutionTracker;
import corp.product.common.validator.LineValidator;
import corp.product.exception.types.ValidationException;
import corp.product.exception.types.ResourceNotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class LineServiceImpl implements LineService {
    private final LineRepository lineRepository;

    private final LineConverter converter;

    @Lookup
    public ExecutionTracker getTracker() {
        return null;
    }

    @Lookup
    public LineValidator getValidator() {
        return null;
    }

    public List<LineDto> list() {
        List<Line> all = lineRepository.findAllByOrderByIdAsc();
        return converter.createFromEntities(all);
    }

    public LineDto getOne(long id) {
        return lineRepository.findById(id)
                .map(converter::convertFromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Line not found with id: " + id));
    }

    public void createOrUpdate(LineDto lineDto) {
        ExecutionTracker tracker = getTracker();
        tracker.logStep("Processing line: " + lineDto.getName());

        LineValidator validator = getValidator();
        if (!validator.validate(lineDto).isValid()) {
            throw new ValidationException("Validation failed: " + validator.getErrors());
        }

        Line line = converter.convertFromDto(lineDto);
        lineRepository.save(line);
        tracker.finish("createOrUpdateLine");
    }

    public void delete(long id) {
        ExecutionTracker tracker = getTracker();
        tracker.logStep("Deleting line id: " + id);

        if (id <= 0) throw new ValidationException("Invalid ID");
        if (!lineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete: Line not found with id: " + id);
        }
        lineRepository.deleteById(id);
        tracker.finish("deleteLine");
    }
}
