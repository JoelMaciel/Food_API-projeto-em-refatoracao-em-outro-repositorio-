package com.joel.food.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joel.food.api.v1.FoodLinks;
import com.joel.food.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import com.joel.food.core.security.CheckSecurity;
import com.joel.food.domain.filter.VendaDiariaFilter;
import com.joel.food.domain.model.dto.VendaDiaria;
import com.joel.food.domain.service.VendaQueryService;
import com.joel.food.domain.service.VendaReportService;

@RequestMapping(path = "/v1/estatisticas")
@RestController
public class EstatisticasController  implements EstatisticasControllerOpenApi{
	
	@Autowired
	private FoodLinks foodLinks;
	
	@Autowired
	private VendaReportService vendaReportService;
	
	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public EstatisticasModel estatisticas() {
	    var estatisticasModel = new EstatisticasModel();
	    
	    estatisticasModel.add(foodLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));
	    
	    return estatisticasModel;
	}     
	
	@CheckSecurity.Estatisticas.PodeConsultar
	@GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro ,
		@RequestParam(required = false, defaultValue = "+00:00")	String timeOffset) {
		return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
	}
	
	@CheckSecurity.Estatisticas.PodeConsultar
	@GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro ,
			@RequestParam(required = false, defaultValue = "+00:00")	String timeOffset) {
		
		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffset);
		
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}
	public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {
	}

}














