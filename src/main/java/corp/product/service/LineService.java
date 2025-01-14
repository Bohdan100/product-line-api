package corp.product.service;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import corp.product.repository.LineRepository;
import corp.product.converter.LineConverter;
import corp.product.dto.LineDto;
import corp.product.data.Line;

import java.util.List;

@Service
@AllArgsConstructor
public class LineService {
    private final LineRepository lineRepository;

    private final LineConverter converter;


    public List<LineDto> list() {
        List<Line> all = lineRepository.findAll();
        return converter.createFromEntities(all);
    }

    public LineDto getOne(long id) {
        Line line = lineRepository.getOne(id);
        return converter.convertFromEntity(line);
    }

    public void createOrUpdate(LineDto lineDto) {
        Line line = converter.convertFromDto(lineDto);
        lineRepository.save(line);
    }

    public void delete(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID: ID must be greater than 0");
        }
        lineRepository.deleteById(id);
    }
}
