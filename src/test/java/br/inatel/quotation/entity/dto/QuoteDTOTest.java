package br.inatel.quotation.entity.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.inatel.quotation.entity.Quotation;
import br.inatel.quotation.entity.Quote;

class QuoteDTOTest {

	List<Quote> quotes = new ArrayList<>();
	Quote quote = new Quote(LocalDate.of(1989, 11, 2), "10", null);
	
	@BeforeEach
	void dataPrep() {
		Quotation q = new Quotation("Meu Teste");
		quotes.addAll(Arrays.asList(new Quote(LocalDate.of(1989, 11, 2), "10", q), 
									new Quote(LocalDate.of(1995, 05, 8), "20", q), 
									new Quote(LocalDate.of(2000, 9, 11), "30", q), 
									new Quote(LocalDate.of(2004, 04, 5), "40", q)));
	}
	
	@Test
	void deveriaConverterUmQuoteEmUmDTO() {
		QuoteDTO dto = QuoteDTO.convert(quote);
		Assertions.assertEquals("10", dto.getValue());
	}
	
	@Test
	void deveriaConverterUmaListaDeQuotesEmUmaListaDeDTOs() {
		Map<LocalDate, String> map1 = QuoteDTO.convertAll(quotes);
		Map<LocalDate, String> map2 = quotes.stream().collect(Collectors.toMap(p -> p.getDate(), p -> p.getValue()));

		Assertions.assertEquals(map2.size(), map1.size());
	}

}
