package corp.product.dto;

import lombok.Data;
import java.io.Serializable;
import corp.product.data.RecordEntity;

@Data
public class ReportDto implements Serializable {

    private String nameOrg;

    private String namePr;

    private String var;

    private int quantity;

    private String side;

    private Long lineId;

    private String lineName;

    public ReportDto(RecordEntity record) {
        this.nameOrg = record.getNameOfOrganization();
        this.namePr = record.getNameOfProduct();
        this.var = record.getVariant();
        this.quantity = record.getQuantity();
        this.side = record.getSide();
        this.lineId = record.getLine().getId();
        this.lineName = record.getLine().getName();
    }
}
