package corp.product.converter;

import org.springframework.stereotype.Component;
import corp.product.data.Line;
import corp.product.dto.LineDto;

@Component
public class LineConverter extends Converter<LineDto, Line> {

    public LineConverter() {
        super(LineConverter::convertToEntity, LineConverter::convertToDto);
    }

    private static LineDto convertToDto(Line line) {
        return new LineDto(line.getId(), line.getName(), line.getRecords());
    }

    private static Line convertToEntity(LineDto lineDto) {
        Line line = new Line();
        line.setId(lineDto.getId());
        line.setName(lineDto.getName());
        line.setRecords(lineDto.getRecords());
        return line;
    }
}
