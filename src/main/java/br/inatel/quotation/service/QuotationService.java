package br.inatel.quotation.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inatel.quotation.entity.Quotation;
import br.inatel.quotation.entity.dto.QuotationDTO;
import br.inatel.quotation.entity.form.FormQuote;
import br.inatel.quotation.repository.QuotationRepository;

@Service
public class QuotationService {

	private QuotationRepository quotationRepository;
	private StockService stockService;
	private QuoteService quoteService;
	
	@Autowired
	public QuotationService(QuotationRepository quotationRepository, StockService stockService, QuoteService quoteService) {
		this.quotationRepository = quotationRepository;
		this.stockService = stockService;
		this.quoteService = quoteService;
	}
	
	public List<Quotation> listAllQuotations() {
		return quotationRepository.findAll();
	}
	
	public Quotation findQuotationById(UUID id) {
		Optional<Quotation> quotation = quotationRepository.findById(id);
		if(quotation.isPresent())
			return quotation.get();
		return null;
	}
	
	public Quotation insertQuotation(Quotation quote) {
		return quotationRepository.save(quote);
	}

	public Quotation findQuotationByQuotationIdAndStockId(UUID quotationId, String stockId) {
		return quotationRepository.findByQuotationIdAndStockId(quotationId.toString(), stockId);
	}
	
	public Quotation findQuotationByQuotationIdAndStockId(FormQuote form) {
		return quotationRepository.findByQuotationIdAndStockId(form.getId().toString(), form.getStockId().trim().toLowerCase());
	}
	
	public Quotation findQuotationByStockId(String stockId) {
		return quotationRepository.findByStockId(stockId.trim().toLowerCase());
	}
	
	public boolean existsByStockId(String stockId) {
		Quotation quotation = quotationRepository.findByStockId(stockId);
		return (quotation != null);
	}
	
	public boolean isValidForCreation(FormQuote form) {
		if(stockService.existsById(form.getStockId())) {
			Quotation q1 = findQuotationById(form.getId());
			Quotation q2 = findQuotationByStockId(form.getStockId());
			
			if((q1 == null && q2 != null) || (q1 != null && q2 == null))
				return false;
			if(q1 != null && q2 != null && (!(q1.getStockId().equals(q2.getStockId())) || !(q1.getId().equals(q2.getId()))))
				return false;
			
			return true;
		}
		return false;
	}	
	
	public Quotation persistQuotation(FormQuote form) {
		Quotation quotation = new Quotation(form.getStockId().trim().toLowerCase());
		quotation.setId(form.getId());
		insertQuotation(quotation);
		return quotation;
	}
	
	public QuotationDTO persistQuotesAndCreateQuotationDTO(FormQuote form, Quotation quotation) {
		quoteService.persistQuotes(form, quotation);
		QuotationDTO quotationDTO = new QuotationDTO(quotation.getId(), quotation.getStockId());
		quoteService.loadQuotesFromDB(quotationDTO);
		return quotationDTO;
	}
	
	public boolean isFormValid(FormQuote form) {
		if(form != null) {
			if(form.getId() == null || form.getId().toString().trim().isEmpty())
				return false;
			else if(form.getStockId() == null || form.getStockId().trim().isEmpty())
				return false;
			else if(form.getQuotes() == null) 
				return false;
			return true;
		}
		return false;
	}
}
