package br.inatel.quotation.controller;

import javax.annotation.PostConstruct;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inatel.quotation.web.WebClientApi;

@RestController
@RequestMapping("/")
public class SystemController {
	
	WebClientApi webClientApi = new WebClientApi();

	@PostConstruct
	public void registerIntoApi(){
		webClientApi.registerIntoApi();
		System.out.println("Registered into API!");
	}
	
	@DeleteMapping("/stockcache")
	@CacheEvict(cacheNames = "listOfStocks")
	public void CleanCache() {
		System.out.println("Cache Cleaned!");
	}
}
