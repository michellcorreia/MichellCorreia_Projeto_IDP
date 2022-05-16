package br.inatel.quotation.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inatel.quotation.entity.FormQuote;
import br.inatel.quotation.entity.Quotation;
import br.inatel.quotation.repository.QuotationRepository;

@Service
public class QuotationService {

	private QuotationRepository quotationRepository;
	private StockService stockService;
	
	@Autowired
	public QuotationService(QuotationRepository quotationRepository, StockService stockService) {
		this.quotationRepository = quotationRepository;
		this.stockService = stockService;
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
		return quotationRepository.findByQuotationIdAndStockId(form.getId().toString(), form.getStockId());
	}
	
	public Quotation findQuotationByStockId(String stockId) {
		return quotationRepository.findByStockId(stockId);
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
	
	public Quotation generateQuotation(FormQuote form) {
		Quotation quotation = new Quotation(form.getStockId().trim().toLowerCase());
		quotation.setId(form.getId());
		insertQuotation(quotation);
		return quotation;
	}
}
