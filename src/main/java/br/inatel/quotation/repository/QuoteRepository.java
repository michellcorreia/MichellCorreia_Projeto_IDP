package br.inatel.quotation.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.inatel.quotation.entity.Quote;
@Repository
public interface QuoteRepository extends JpaRepository<Quote, UUID> {

	@Query(nativeQuery = true, value = "select * "
									+ "from quote "
									+ "inner join quotation on quote.quotation_id = quotation.id "
									+ "where quotation.stock_id = ?1")
	List<Quote> findByQuotation_stockId(String stockId);
	
	@Query(nativeQuery = true, value = "select * "
									+ "from quote "
									+ "inner join quotation on quote.quotation_id = quotation.id "
									+ "where quote.date = ?1 "
									+ "and quotation.stock_id = ?2")
	Quote findByStockAndDate(LocalDate quoteDate, String stockId);
}
