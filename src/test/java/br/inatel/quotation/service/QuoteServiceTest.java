package br.inatel.quotation.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.inatel.quotation.entity.Quotation;
import br.inatel.quotation.entity.Quote;
import br.inatel.quotation.entity.dto.QuotationDTO;
import br.inatel.quotation.repository.QuoteRepository;

class QuoteServiceTest {
	
	@Mock
	private QuoteRepository quoteRepository;
	private QuoteService quoteService;
	
	@BeforeEach
	void beforEach() {
		MockitoAnnotations.openMocks(this);
		quoteService = new QuoteService(quoteRepository);
	}
	
	@Test
	void deveriaRetornarTodosOsQuotes() {
		quoteService.listAllQuote();
		Mockito.verify(quoteRepository).findAll();
		Mockito.when(quoteRepository.findAll()).thenReturn(mockListAllQuotes());
	}
	
	@Test
	void deveriaRetornarUmQuotePorId() {
		UUID id =UUID.randomUUID();
		quoteService.findQuoteById(id);
		Mockito.verify(quoteRepository).getById(id);
	}
	
	@Test
	void deveriaInserirUmQuoteNoBd() {
		Quotation quotationTeste = new Quotation("Meu Teste");
		Quote quote = new Quote(LocalDate.of(2022, 5, 12), "10", quotationTeste);
		quoteService.insertQuote(quote);
		Mockito.verify(quoteRepository).save(quote);
	}
	
	@Test
	void deveriarAcharUmQuotePorStockEDate() {
		LocalDate data = LocalDate.now();
		String stockId =  "stockId";
		quoteService.findQuoteByStockAndDate(data, stockId);
		Mockito.verify(quoteRepository).findByStockAndDate(data, stockId);
	}
	
	@Test
	void deveriarAcharUmaListaDeQuotesPeloStockId() {
		String stockId =  "stockId";
		quoteService.findQuotesByStockId(stockId);
		Mockito.verify(quoteRepository).findByQuotation_stockId(stockId);
	}
	
	@Test
	void deveriaCarregarTodasAsQuotesDaQuotation() {
		QuotationDTO quotationTeste = new QuotationDTO(UUID.randomUUID(), "Meu teste");
		quoteService.loadQuotesFromDB(quotationTeste);
		Mockito.verify(quoteRepository).findByQuotation_stockId(Mockito.any());
	}
	
	@Test
	void deveriaCarregarTodasAsQuotesDaListaDeQuotations() {
		QuotationDTO quotationTeste = new QuotationDTO(UUID.randomUUID(), "Meu teste");
		quoteService.loadAllQuotesFromDB(Arrays.asList(quotationTeste));
		Mockito.verify(quoteRepository).findByQuotation_stockId(Mockito.any());
	}
	
	
	List<Quote> mockListAllQuotes() {
		List<Quote> quotes = new ArrayList<>();
		quotes.addAll(Arrays.asList(new Quote(LocalDate.of(2022, 5, 12), "10", null),
									new Quote(LocalDate.of(2021, 6, 2), "20", null),
									new Quote(LocalDate.of(2020, 2, 3), "30", null)));
		return quotes;
	}

}
