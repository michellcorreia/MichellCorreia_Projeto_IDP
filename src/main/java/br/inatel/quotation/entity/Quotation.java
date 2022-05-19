package br.inatel.quotation.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "quotation")
public class Quotation {

	@Id
	@Type(type="uuid-char")
	private UUID id;
	@Column(unique = true, name = "stock_id", nullable = false)
	private String stockId;
	@OneToMany (mappedBy = "quotation", fetch = FetchType.LAZY)
	private List<Quote> quotes; 
	
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
	public List<Quote> getQuotes() {
		return quotes;
	}
	public void setQuotes(List<Quote> quotes) {
		this.quotes = quotes;
	}
	
}
