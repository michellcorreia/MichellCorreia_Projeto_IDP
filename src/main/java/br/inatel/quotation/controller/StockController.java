package br.inatel.quotation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inatel.quotation.entity.Stock;
import br.inatel.quotation.service.StockService;

@RestController
@RequestMapping("/stock")
public class StockController {

	private StockService stockService;
	@Autowired
	public StockController(StockService stockService) {
		this.stockService = stockService;
	}
	
	@GetMapping
	@Cacheable(value = "listOfStocks")
	public ResponseEntity<List<Stock>> showAllStocks() {		
		List<Stock> stocks = stockService.listAllStocks();
		System.out.println("Cached!");
		return ResponseEntity.status(HttpStatus.OK).body(stocks);
	}
	
	@PostMapping
	public ResponseEntity<Stock> insertStock(@RequestBody Stock stock) {
		if(!stockService.existsById(stock.getId())) {
			stock.setId(stock.getId().trim().toLowerCase());
			stockService.insertStock(stock);
			return ResponseEntity.status(HttpStatus.CREATED).body(stock); 
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).build(); 
	}
}
