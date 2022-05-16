package br.inatel.quotation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.inatel.quotation.entity.Stock;
import br.inatel.quotation.web.WebClientApi;

@Service
public class StockService {
	
	WebClientApi webClientApi;
	public StockService(WebClientApi webClientApi) {
		this.webClientApi = webClientApi;
	}
	
	public List<Stock> listAllStocks() {
		return webClientApi.getAllStocksFromApi();
	}
	
	public Stock insertStock(Stock stock) {
		return webClientApi.InsertStockIntoApi(stock);
	}

	public boolean existsById(String stockId) {
		List<Stock> stocks = listAllStocks();
		Optional<Stock> stock = stocks.stream().filter(s -> s.getId().trim().toLowerCase().equals(stockId.trim().toLowerCase())).findFirst();
		
		return stock.isPresent();
	}
}
