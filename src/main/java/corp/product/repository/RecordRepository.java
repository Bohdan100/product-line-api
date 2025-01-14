package corp.product.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.time.LocalDate;
import corp.product.data.RecordEntity;

@Repository
public interface RecordRepository extends JpaRepository<RecordEntity, Long> {

    RecordEntity getRecordById(long id);

    @Query(value = "select record.* from record where line_id = ?1", nativeQuery = true)
    Page<RecordEntity> getRecordsPageable(Long id, Pageable pageable);

    @Query(value = "select record.* from record where " +
            "record.date between  ?1 and ?2", nativeQuery = true)
    List<RecordEntity> getRecordsBetweenDate(LocalDate start,
                                             LocalDate end);

    @Query(value = "select record.* from record" +
            " left join usr on record.author_id = usr.id" +
            " where record.line_id = ?1" +
            " and record.date between ?2 and ?3" +
            " and record.name_of_organization like %?4%" +
            " and record.name_of_product like %?5%" +
            " and record.variant like %?6%" +
            " and record.side like %?7%" +
            " and usr.surname like %?8%" +
            " \n-- #pageable\n ", nativeQuery = true)
    Page<RecordEntity> filter(
            long id,
            LocalDate start,
            LocalDate end,
            String nameOfOrganization,
            String nameOfProduct,
            String variant,
            String side,
            String surname,
            Pageable pageable
    );
}
