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

import br.inatel.quotation.entity.Quotation;
import br.inatel.quotation.entity.dto.QuotationDTO;
import br.inatel.quotation.entity.form.FormQuote;
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
	public ResponseEntity<List<QuotationDTO>> showAllQuotation() {
		List<QuotationDTO> quotations = QuotationDTO.convertAll(quotationService.listAllQuotations());
		quoteService.loadAllQuotesFromDB(quotations);
		
		return ResponseEntity.status(HttpStatus.OK).body(quotations);
	}
	
	@GetMapping("/{stockId}")
	public ResponseEntity<QuotationDTO> showQuotationByStockId(@PathVariable ("stockId") String stockId) {
		Quotation quotation = quotationService.findQuotationByStockId(stockId);
		if(quotation != null) {
			QuotationDTO quotationdto = QuotationDTO.convert(quotation);
			quoteService.loadQuotesFromDB(quotationdto);
			return ResponseEntity.status(HttpStatus.OK).body(quotationdto);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@PostMapping
	public ResponseEntity<QuotationDTO> insertQuotation(@RequestBody FormQuote form) {
		if(!quotationService.isFormValid(form))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
		Quotation quotation = quotationService.findQuotationByQuotationIdAndStockId(form);
		if(quotation != null) {
			QuotationDTO quotationDTO = quotationService.persistQuotesAndCreateQuotationDTO(form, quotation);
			return ResponseEntity.status(HttpStatus.OK).body(quotationDTO);
		}
		else if(quotationService.isValidForCreation(form)) {
			quotation = quotationService.persistQuotation(form); 
			QuotationDTO quotationDTO = quotationService.persistQuotesAndCreateQuotationDTO(form, quotation);
			return ResponseEntity.status(HttpStatus.CREATED).body(quotationDTO);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
