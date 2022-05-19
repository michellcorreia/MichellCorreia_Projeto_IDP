package br.inatel.quotation.entity.dto;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.inatel.quotation.entity.Quotation;

class QuotationDTOTest {

	List<Quotation> quotations;
	Quotation quotation;
	
	@BeforeEach
	void dataPrep() {
		quotation = new Quotation("Meu Teste");
		quotations = Arrays.asList(quotation);
		
	}
	
	@Test
	void DeveriaConverterUmQuotationEmUmDTO() {
		QuotationDTO dto = QuotationDTO.convert(quotation);
		Assertions.assertEquals("Meu Teste", dto.getStockId());
	}
	
	@Test
	void DeveriaConverterAListaDeQuotationsEmUmaListaDeDTOs() {
		List<QuotationDTO> dtos = QuotationDTO.convertAll(quotations);
		Assertions.assertEquals("Meu Teste", dtos.get(0).getStockId());
	}

}
