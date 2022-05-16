package br.inatel.quotation.web;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.inatel.quotation.entity.Stock;

@Service
public class WebClientApi {

	private static WebClient webClient = WebClient.create();
	
	public List<Stock> getAllStocksFromApi() {
		return webClient.get().uri("stock-manager:8080/stock")
			.retrieve()
			.bodyToFlux(Stock.class)
			.collectList()
			.block();
	}
	
	public List<Stock> getStockByIdFromApi() {
		return webClient.get().uri("stock-manager:8080/stock")
			.retrieve()
			.bodyToFlux(Stock.class)
			.collectList()
			.block();
	}
	
	public Stock InsertStockIntoApi(Stock stock) {	
		return webClient.post().uri("stock-manager:8080/stock")
			.bodyValue(stock)
			.retrieve()
			.bodyToFlux(Stock.class)
			.blockFirst();
	}
	
	public void registerIntoApi() {
		RegistrationDTO register = new RegistrationDTO("quotation-management-app", 8081);
		webClient.post().uri("stock-manager:8080/notification")
		.bodyValue(register)
		.retrieve()
		.bodyToFlux(RegistrationDTO.class)
		.blockFirst();
	}
}
