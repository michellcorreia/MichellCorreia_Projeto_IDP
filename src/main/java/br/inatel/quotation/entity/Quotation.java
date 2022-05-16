package br.inatel.quotation.entity;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "quotation")
public class Quotation {

	@Id
	@Type(type="uuid-char")
	private UUID id;
	@Column(unique = true, name = "stock_id", nullable = false)
	private String stockId;
	@Transient
	private Map<LocalDate, String> quotes = new TreeMap<>((q1,q2) -> q1.compareTo(q2));
	
	public Quotation() {
		
	}
	public Quotation(String stockId) {
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
}
