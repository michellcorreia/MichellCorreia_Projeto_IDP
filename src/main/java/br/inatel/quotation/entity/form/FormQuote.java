package br.inatel.quotation.entity.form;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class FormQuote {

	private UUID id;
	private String stockId;
	private Map<LocalDate, String> quotes;
	
	public FormQuote(String stockId, Map<LocalDate, String> quotes) {
		super();
		this.stockId = stockId;
		this.quotes = quotes;
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
		return this.quotes;
	}
	public void setQuotes(Map<LocalDate, String> quotes) {
		this.quotes = quotes;
	}
	
}
