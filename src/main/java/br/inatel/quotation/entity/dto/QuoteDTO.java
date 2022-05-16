package br.inatel.quotation.entity.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.inatel.quotation.entity.Quote;

public class QuoteDTO {

	private LocalDate date;
	private String value;
	
	public QuoteDTO(LocalDate date, String value) {
		super();
		this.date = date;
		this.value = value;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static Map<LocalDate, String> convert (Quote quote) {
		Map<LocalDate, String> map = new HashMap<>();
		map.put(quote.getDate(), quote.getValue());
		return map;
	}
	public static Map<LocalDate, String> convertAll (List<Quote> quotes) {
		Map<LocalDate, String> map = new HashMap<>();
		quotes.stream().map(q -> map.put(q.getDate(), q.getValue())).collect(Collectors.toList());
		return map;
	}
}
