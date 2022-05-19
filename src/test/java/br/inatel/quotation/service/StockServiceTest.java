package br.inatel.quotation.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.inatel.quotation.entity.Stock;
import br.inatel.quotation.web.WebClientApi;

class StockServiceTest {

	@Mock
	WebClientApi webClientApi;
	StockService stockService;
	
	@BeforeEach
	void beforeEach() {
		MockitoAnnotations.openMocks(this);
		stockService = new StockService(webClientApi);
	}
	
	@Test
	void deveriaRetornarTodosOsStocks() {
		stockService.listAllStocks();
		Mockito.verify(webClientApi).getAllStocksFromApi();
	}
	
	@Test
	void deveriaInserirUmStockNoDB() {
		Stock stock = new Stock("Teste", "Descrição teste");
		stockService.insertStock(stock);
		Mockito.verify(webClientApi).InsertStockIntoApi(stock);
	}

	@Test
	void verificaAExistenciaDeUmStockPeloSeuId() {
		List<Stock> stocksTeste = new ArrayList<>();
		stocksTeste.add(new Stock("Teste", "Description"));
		
		Mockito.when(webClientApi.getAllStocksFromApi()).thenReturn(stocksTeste);
		boolean existe = stockService.existsById("Teste");
		Assertions.assertTrue(existe);
	}
	
	@Test
	void deveriaRetornarFalsePoisOStockIdEhNull() {
		Stock stock = new Stock(null, "Descrição teste");
		boolean valid = stockService.isStockValid(stock);
		Assertions.assertFalse(valid);
	}
	
	@Test
	void deveriaRetornarFalsePoisOStockIdEhVazio() {
		Stock stock = new Stock(" ", "Descrição teste");
		boolean valid = stockService.isStockValid(stock);
		Assertions.assertFalse(valid);
	}
	
	@Test
	void deveriaRetornarFalsePoisADescricaoEhNull() {
		Stock stock = new Stock("test3", null);
		boolean valid = stockService.isStockValid(stock);
		Assertions.assertFalse(valid);
	}

	@Test
	void deveriaRetornarFalsePoisADescricaoEhVazia() {
		Stock stock = new Stock("test3", " ");
		boolean valid = stockService.isStockValid(stock);
		Assertions.assertFalse(valid);
	}
	
	@Test
	void deveriaRetornarTrue() {
		Stock stock = new Stock("test3", "Descrição teste");
		boolean valid = stockService.isStockValid(stock);
		Assertions.assertTrue(valid);
	}
}
