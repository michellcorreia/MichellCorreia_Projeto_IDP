package br.inatel.quotation.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inatel.quotation.entity.FormQuote;
import br.inatel.quotation.entity.Quotation;
import br.inatel.quotation.entity.Quote;
import br.inatel.quotation.entity.dto.QuoteDTO;
import br.inatel.quotation.repository.QuoteRepository;

@Service
public class QuoteService {

	private QuoteRepository quoteRepository;
	@Autowired
	public QuoteService(QuoteRepository quoteRepository) {
		this.quoteRepository = quoteRepository;
	}
	
	public List<Quote> listAllQuote() {
		return quoteRepository.findAll();
	}
	
	public Quote findQuoteById(UUID id) {
		return quoteRepository.getById(id);
	}
	
	public Quote insertQuote(Quote quote) {
		return quoteRepository.save(quote);
	}
	
	public Quote findByStockAndDate(LocalDate quoteDate, String stockId) {
		return quoteRepository.findByStockAndDate(quoteDate, stockId);
	}
	
	public List<Quote> findByQuotationStockId(String stockId) {
		return quoteRepository.findByQuotation_stockId(stockId);
	}

	public void createQuotes(FormQuote form, Quotation quotation) {
		form.getQuotes().forEach((k,v) -> {
			Quote quote = findByStockAndDate(k, quotation.getStockId());
			if(quote != null) {
				quote.setValue(v);
				insertQuote(quote);
			}
			else {
				insertQuote(new Quote(k,v,quotation));
			}
		});
		quotation.setQuotes(QuoteDTO.convertAll(findByQuotationStockId(form.getStockId().trim().toLowerCase())));
	}
	
	public void loadQuotesFromDB(Quotation quotation) {
		quotation.setQuotes(QuoteDTO.convertAll(findByQuotationStockId(quotation.getStockId())));
	}

	public void loadAllQuotesFromDB(List<Quotation> quotations) {
		quotations.forEach(q -> q.setQuotes(QuoteDTO.convertAll(findByQuotationStockId(q.getStockId()))));
	}
	
}
