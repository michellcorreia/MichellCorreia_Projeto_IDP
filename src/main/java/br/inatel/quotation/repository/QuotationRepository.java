package br.inatel.quotation.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.inatel.quotation.entity.Quotation;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, UUID> {

	
	@Query(nativeQuery = true, value = "select * "
									+ "from quotation q "
									+ "inner join stock s on q.stock_id = s.id "
									+ "where s.id = :stockId")
	public List<Quotation> buscarQuotesPorStockId(String stockId);
	
	@Query(nativeQuery = true, value = "select * "
									+ "from quotation q "
									+ "where q.id = :quotationId "
									+ "and q.stock_id = :stockId")
	public Quotation findByQuotationIdAndStockId(String quotationId, String stockId);
	
	@Query(nativeQuery = true, value = "select * "
									+ "from quotation q "
									+ "where q.stock_id = :stockId")
	public Quotation findByStockId(String stockId);
	
}
