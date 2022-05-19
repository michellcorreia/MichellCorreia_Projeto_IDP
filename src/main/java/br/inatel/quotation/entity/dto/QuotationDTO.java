package br.inatel.quotation.entity.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

import br.inatel.quotation.entity.Quotation;

public class QuotationDTO {

	private UUID id;
	private String stockId;
	private Map<LocalDate, String> quotes = new TreeMap<>((q1,q2) -> -q1.compareTo(q2));
	
	
	public QuotationDTO() {
		super();
	}
	public QuotationDTO(UUID id, String stockId) {
		super();
		this.id = id;
		this.stockId = stockId;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public Map<LocalDate, String> getQuotes() {
		return quotes;
	}
	public void setQuotes(Map<LocalDate, String> quotes) {
		this.quotes = quotes;
	}	
	
	public static QuotationDTO convert(Quotation quotation) {
		return new QuotationDTO(quotation.getId(), quotation.getStockId());
	}
	
	public static List<QuotationDTO> convertAll(List<Quotation> quotations) {
		return quotations.stream().map(q -> new QuotationDTO(q.getId(), q.getStockId())).collect(Collectors.toList());
	}
}
