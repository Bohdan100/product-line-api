package corp.product.service;

import corp.product.dto.LineDto;
import java.util.List;

public interface LineService {
    List<LineDto> list();
    LineDto getOne(long id);
    void createOrUpdate(LineDto lineDto);
    void delete(long id);
}