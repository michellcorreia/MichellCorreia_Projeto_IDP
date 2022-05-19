package br.inatel.quotation.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.inatel.quotation.entity.Quotation;
import br.inatel.quotation.entity.Quote;
import br.inatel.quotation.entity.form.FormQuote;
import br.inatel.quotation.repository.QuotationRepository;
import br.inatel.quotation.repository.QuoteRepository;
import br.inatel.quotation.web.WebClientApi;

class QuotationServiceTest {
	
	@Mock
	QuotationRepository quotationRepository;
	@Mock
	WebClientApi stockRepository;
	@Mock
	StockService stockService;
	@Mock
	QuoteRepository quoteRepository;
	QuoteService quoteService;
	QuotationService quotationService;
	
	
	@BeforeEach
	void beforeEach() {
		MockitoAnnotations.openMocks(this);
		quoteService = new QuoteService(quoteRepository);
		quotationService = new QuotationService(quotationRepository, stockService, quoteService);
	}
	
	@Test
	void deveriaListarTodosOsQuotations() {
		quotationService.listAllQuotations();
		Mockito.verify(quotationRepository).findAll();
	}
	
	@Test
	void deveriaProcurarUmaQuotationPorId() {
		UUID id = UUID.randomUUID();
		quotationService.findQuotationById(id);
		Mockito.verify(quotationRepository).findById(id);
	}
	
	@Test
	void deveriaInserirUmaQuotationNoBD() {
		Quotation quotation = new Quotation("Meu Teste");
		quotationService.insertQuotation(quotation);
		Mockito.verify(quotationRepository).save(quotation);
	}

	@Test
	void deveriaProcurarUmaQuotationPeloSeuIdEPorStockId() {
		UUID id = UUID.randomUUID();
		Quotation quotation = new Quotation("Meu Teste");
		quotation.setId(id);
		
		quotationService.findQuotationByQuotationIdAndStockId(quotation.getId(), quotation.getStockId());
		Mockito.verify(quotationRepository).findByQuotationIdAndStockId(quotation.getId().toString(), quotation.getStockId());
	}
	
	@Test
	void deveriaProcurarUmaQuotationPorStockId() {
		String stockId = "Teste";
		quotationService.findQuotationByStockId(stockId);
		Mockito.verify(quotationRepository).findByStockId(stockId.trim().toLowerCase());
	}
	
	@Test
	void verificaAExistenciaDeQuotationPeloStockId() {
		String stockId = "Teste";
		quotationService.existsByStockId(stockId);
		Mockito.verify(quotationRepository).findByStockId(stockId);
	}
	
	@Test
	void deveriaRetornarFalsePoisOsQuotationIdsSaoDiferentes() {
		Optional<Quotation> q1 = Optional.of(new Quotation("meu teste 1"));
		q1.get().setId(UUID.randomUUID());
		Quotation q2 = new Quotation("meu teste 1");
		q2.setId(UUID.randomUUID());
		
		Mockito.when(quotationRepository.findById(Mockito.any())).thenReturn(q1);
		Mockito.when(quotationRepository.findByStockId(Mockito.any())).thenReturn(q2);
		boolean valid = quotationService.isValidForCreation(new FormQuote("Stock teste", new HashMap<LocalDate, String>()));
		
		Assertions.assertFalse(valid);
	}
	
	@Test
	void deveriaRetornarFalsePoisOsStockIdsSaoDiferentes() {
		UUID id = UUID.randomUUID();
		Optional<Quotation> q1 = Optional.of(new Quotation("Meu Teste 1"));
		q1.get().setId(id);
		Quotation q2 = new Quotation("Meu Teste 2");
		q2.setId(id);
		
		Mockito.when(quotationRepository.findById(Mockito.any())).thenReturn(q1);
		Mockito.when(quotationRepository.findByStockId(Mockito.any())).thenReturn(q2);
		boolean valid = quotationService.isValidForCreation(new FormQuote("Stock teste", new HashMap<LocalDate, String>()));
		
		Assertions.assertFalse(valid);
	}
	
	@Test
	void deveriaRetornarFalsePoisQ2EhNull() {
		UUID id = UUID.randomUUID();
		Optional<Quotation> q1 = Optional.of(new Quotation("Meu Teste 1"));
		q1.get().setId(id);

		
		Mockito.when(quotationRepository.findById(Mockito.any())).thenReturn(q1);
		Mockito.when(quotationRepository.findByStockId(Mockito.any())).thenReturn(null);
		boolean valid = quotationService.isValidForCreation(new FormQuote("Stock teste", new HashMap<LocalDate, String>()));
		
		Assertions.assertFalse(valid);
	}
	
	@Test
	void deveriaRetornarTruePoisOStockAindaNaoExisteNemPorQuotationIdNemPorStockId() {
		FormQuote form = new FormQuote("Stock teste", new HashMap<LocalDate, String>());
		form.setId(UUID.randomUUID());
		Mockito.when(stockService.existsById(Mockito.any())).thenReturn(true);
		boolean valid = quotationService.isValidForCreation(form);
		
		Assertions.assertTrue(valid);
	}
	
	@Test
	void DeveriaGerarUmaQuotationPreenchida() {
		FormQuote form = new FormQuote("Stock teste", new HashMap<LocalDate, String>());
		form.setId(UUID.randomUUID());
		
		quotationService.persistQuotation(form);
		Mockito.verify(quotationRepository).save(Mockito.any());
	}
	
	@Test
	void devePersistirOsQuotesECriarUmQuotationDTOPreenchidoComTodosOsQuotes() {
		Map<LocalDate, String> quotes = new HashMap<>();
		Quote quote = new Quote(LocalDate.now(), "20", null);
		quotes.put(quote.getDate(), quote.getValue());
		FormQuote form = new FormQuote("Stock teste", quotes);
		Quotation quotation = new Quotation("Test");
		Mockito.when(quoteRepository.findByQuotation_stockId(Mockito.any())).thenReturn(Arrays.asList(quote));
		
		quotationService.persistQuotesAndCreateQuotationDTO(form, quotation);
		Mockito.verify(quoteRepository).save(Mockito.any());
		Mockito.verify(quoteRepository).findByQuotation_stockId(Mockito.any());
	}
	
	@Test
	void deveriaRetornarQueOFormNaoEhValidoPoisOStockIdEstaVazio() {
		FormQuote form = new FormQuote(" ", new HashMap<LocalDate, String>());
		form.setId(UUID.randomUUID());
		boolean valid = quotationService.isFormValid(form);
		Assertions.assertFalse(valid);
	}
	
	@Test
	void deveriaRetornarQueOFormNaoEhValidoPoisOQuotationIdEhNull() {
		FormQuote form = new FormQuote("Stock teste", new HashMap<LocalDate, String>());
		boolean valid = quotationService.isFormValid(form);
		Assertions.assertFalse(valid);
	}
	
	@Test
	void deveriaRetornarQueOFormEhValido() {
		FormQuote form = new FormQuote("Stock teste", new HashMap<LocalDate, String>());
		form.setId(UUID.randomUUID());
		boolean valid = quotationService.isFormValid(form);
		Assertions.assertTrue(valid);
	}
}
