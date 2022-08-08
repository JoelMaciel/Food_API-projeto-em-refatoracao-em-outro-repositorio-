package com.joel.food.infrastructure.service.report;

import org.springframework.stereotype.Service;

import com.joel.food.domain.filter.VendaDiariaFilter;
import com.joel.food.domain.service.VendaReportService;

@Service
public class PdfVendaReportService  implements VendaReportService {

	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		
		return null;
	}

}
