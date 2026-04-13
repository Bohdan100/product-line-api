package corp.product.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.time.LocalDate;
import corp.product.data.RecordEntity;

@Repository
public interface RecordRepository extends JpaRepository<RecordEntity, Long> {
    Page<RecordEntity> findAllByLineId(Long lineId, Pageable pageable);

    List<RecordEntity> findAllByDateBetween(LocalDate start, LocalDate end);

    @Query("SELECT r FROM RecordEntity r JOIN r.author u " +
            "WHERE r.line.id = :id " +
            "AND r.date BETWEEN :start AND :end " +
            "AND LOWER(r.nameOfOrganization) LIKE LOWER(CONCAT('%', :org, '%')) " +
            "AND LOWER(r.nameOfProduct) LIKE LOWER(CONCAT('%', :prod, '%')) " +
            "AND LOWER(r.variant) LIKE LOWER(CONCAT('%', :var, '%')) " +
            "AND LOWER(r.side) LIKE LOWER(CONCAT('%', :side, '%')) " +
            "AND LOWER(u.surname) LIKE LOWER(CONCAT('%', :surname, '%'))")
    Page<RecordEntity> filterWithParams(
            @Param("id") long id,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("org") String nameOfOrganization,
            @Param("prod") String nameOfProduct,
            @Param("var") String variant,
            @Param("side") String side,
            @Param("surname") String surname,
            Pageable pageable
    );

    @Query("SELECT COUNT(r) FROM RecordEntity r")
    long countAllRecords();

    @Query("SELECT COUNT(r) FROM RecordEntity r WHERE r.line.id = :lineId")
    long countRecordsByLineId(@Param("lineId") long lineId);

    @Query("SELECT SUM(r.quantity) FROM RecordEntity r")
    Long sumAllQuantity();

    @Query(value = "SELECT u.name || ' ' || u.surname, COUNT(r.id) as cnt " +
            "FROM record r JOIN \"user\" u ON r.author_id = u.id " +
            "GROUP BY u.id, u.name, u.surname " +
            "ORDER BY cnt DESC LIMIT 1", nativeQuery = true)
    List<Object[]> findTopAuthor();
}