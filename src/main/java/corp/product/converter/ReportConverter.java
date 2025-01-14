package corp.product.converter;

import org.springframework.stereotype.Component;
import corp.product.data.RecordEntity;
import corp.product.dto.ReportDto;

@Component
public class ReportConverter extends Converter<ReportDto, RecordEntity> {

    public ReportConverter() {
        super(ReportConverter::convertToDto, ReportConverter::convertToEntity);
    }

    private static ReportDto convertToEntity(RecordEntity record) {
        return new ReportDto(record);
    }

    private static RecordEntity convertToDto(ReportDto reportDto) {
        throw new UnsupportedOperationException();
    }

}
