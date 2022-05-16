package br.inatel.quotation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inatel.quotation.entity.FormQuote;
import br.inatel.quotation.entity.Quotation;
import br.inatel.quotation.service.QuotationService;
import br.inatel.quotation.service.QuoteService;

@RestController
@RequestMapping("/quotation")
public class QuotationController {
	
	private QuotationService quotationService;
	private QuoteService quoteService;
	@Autowired
	public QuotationController(QuotationService quotationService, QuoteService quoteService) {
		this.quotationService = quotationService;
		this.quoteService = quoteService;
	}

	@GetMapping
	public ResponseEntity<List<Quotation>> showAllQuotation() {
		List<Quotation> quotes = quotationService.listAllQuotations();
		quoteService.loadAllQuotesFromDB(quotes);
		
		return ResponseEntity.status(HttpStatus.OK).body(quotes);
	}
	
	@GetMapping("/{stockId}")
	public ResponseEntity<Quotation> showQuotationByStockId(@PathVariable ("stockId") String stockId) {
		Quotation quotation = quotationService.findQuotationByStockId(stockId.trim().toLowerCase());
		if(quotation != null) {
			quoteService.loadQuotesFromDB(quotation);
			return ResponseEntity.status(HttpStatus.OK).body(quotation);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@PostMapping
	public ResponseEntity<Quotation> insertQuotation(@RequestBody FormQuote form) {
		Quotation quotation = quotationService.findQuotationByQuotationIdAndStockId(form);
		if(quotation != null) {
			quoteService.createQuotes(form, quotation);
			return ResponseEntity.status(HttpStatus.OK).body(quotation);
		}
		else if(quotationService.isValidForCreation(form)) {
			quotation = quotationService.generateQuotation(form);
			quoteService.createQuotes(form, quotation);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(quotation);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

}
