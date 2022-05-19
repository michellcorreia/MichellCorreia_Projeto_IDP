package br.inatel.quotation.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.inatel.quotation.entity.Quotation;
import br.inatel.quotation.entity.dto.QuotationDTO;
import br.inatel.quotation.entity.form.FormQuote;
import br.inatel.quotation.repository.QuotationRepository;
import br.inatel.quotation.repository.QuoteRepository;
import br.inatel.quotation.service.QuotationService;
import br.inatel.quotation.service.QuoteService;
import br.inatel.quotation.service.StockService;

class QuotationControllerTest {

	@Mock
	private QuotationRepository quotationRepository;
	@Mock
	private QuoteRepository quoteRepository;
	@Mock
	private StockService stockService;
	private QuoteService quoteService;
	private QuotationService quotationService;
	private QuotationController quotationController;
	
	@BeforeEach
	void beforeEach() {
		MockitoAnnotations.openMocks(this);
		quoteService = new QuoteService(quoteRepository);
		quotationService = new QuotationService(quotationRepository, stockService, quoteService);
		quotationController = new QuotationController(quotationService, new QuoteService(quoteRepository));
	}
	
	@Test
	void deveriaRetornarTodasAsQuotations() {
		Mockito.when(quotationRepository.findAll()).thenReturn(Arrays.asList(new Quotation("teste")));
		quotationController.showAllQuotation();
		Mockito.verify(quotationRepository).findAll();
		Mockito.verify(quoteRepository).findByQuotation_stockId(Mockito.any());
	}
	
	@Test
	void deveriaRetornarUmaQuotationPeloStockId() {
		UUID id = UUID.randomUUID();
		Quotation quotation = new Quotation("teste");
		quotation.setId(id);
		Mockito.when(quotationRepository.findByStockId(quotation.getStockId())).thenReturn(quotation);
		
		ResponseEntity<QuotationDTO> re = quotationController.showQuotationByStockId(quotation.getStockId());
		Assertions.assertEquals(HttpStatus.OK.value(), re.getStatusCodeValue());
	}
	
	@Test
	void deveriaRetornaNotFoundPoisNaoExistemQuotationsComEsseStockIdNoBD() {
		UUID id = UUID.randomUUID();
		Quotation quotation = new Quotation("teste");
		quotation.setId(id);
		
		ResponseEntity<QuotationDTO> re = quotationController.showQuotationByStockId(quotation.getStockId());
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), re.getStatusCodeValue());
	}
	
	@Test
	void deveriaRetornarOkParaAInsercaoDeUmQuoteEmUmQuotationJaExistenteNoBD() {
		UUID id = UUID.randomUUID();
		Quotation quotation = new Quotation("quotation teste");
		quotation.setId(id);
		Mockito.when(quotationRepository.findByQuotationIdAndStockId(Mockito.any(), Mockito.any()))
				.thenReturn(quotation);	
		FormQuote form = new FormQuote(quotation.getStockId(), new HashMap<LocalDate, String>());
		form.setId(id);
		
		ResponseEntity<QuotationDTO> re = quotationController.insertQuotation(form);
		Assertions.assertEquals(HttpStatus.OK.value(), re.getStatusCodeValue());
	}
	
	@Test
	void deveriaRetornarCreatedParaACriacaoDoQuotationEDeSeusQuotes() {
		UUID id = UUID.randomUUID();
		FormQuote form = new FormQuote("quotation teste", new HashMap<LocalDate, String>());
		form.setId(id);
		Mockito.when(stockService.existsById(Mockito.any())).thenReturn(true);
		ResponseEntity<QuotationDTO> re = quotationController.insertQuotation(form);
		Assertions.assertEquals(HttpStatus.CREATED.value(), re.getStatusCodeValue());
	}
	
	@Test
	void deveriaRetornarNotFoundPoisOIdJaExisteNoDBEONomeEhDiferente() {
		UUID id = UUID.randomUUID();
		Optional<Quotation> quotation = Optional.of(new Quotation("teste1"));
		quotation.get().setId(id);
		Mockito.when(quotationRepository.findById(id)).thenReturn(quotation);
		FormQuote form = new FormQuote("teste2", new HashMap<LocalDate, String>());
		form.setId(id);
		
		ResponseEntity<QuotationDTO> re = quotationController.insertQuotation(form);
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), re.getStatusCodeValue());
	}
	
	

}
