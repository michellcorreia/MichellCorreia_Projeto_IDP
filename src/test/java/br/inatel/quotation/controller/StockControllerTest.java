package br.inatel.quotation.controller;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.inatel.quotation.entity.Stock;
import br.inatel.quotation.service.StockService;
import br.inatel.quotation.web.WebClientApi;

class StockControllerTest {

	@Mock
	private WebClientApi webClientApi;
	private StockService stockService;
	private StockController stockController;
	
	@BeforeEach
	void beforeEach() {
		MockitoAnnotations.openMocks(this);
		stockService = new StockService(webClientApi);
		stockController = new StockController(stockService);
	}
	
	@Test
	void deveriaRetornarTodosOsStocksDaApi() {
		stockController.showAllStocks();
		Mockito.verify(webClientApi).getAllStocksFromApi();
	}
	
	@Test
	void deveriaInserirUmaStockNoDBDaApi() {
		Stock stock = new Stock("T3ST", "Description Test");
		stockController.insertStock(stock);
		
		Mockito.verify(webClientApi).InsertStockIntoApi(stock);
	}

	@Test
	void naoDeveriaInserirUmaStockNoDBDaApiPoisElaJaExiste() {
		Stock stock = new Stock("test", "description test");
		Mockito.when(webClientApi.getAllStocksFromApi()).thenReturn(Arrays.asList(stock));

		ResponseEntity<Stock> re = stockController.insertStock(stock);
		Assertions.assertEquals(HttpStatus.CONFLICT.value(), re.getStatusCodeValue());;
	}
}
