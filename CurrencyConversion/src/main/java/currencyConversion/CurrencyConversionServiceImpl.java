package currencyConversion;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import api.dtos.CurrencyConversionDto;
import api.dtos.CurrencyExchangeDto;
import api.services.CurrencyConversionService;
import util.exceptions.InvalidQuantityException;

@RestController
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

	private RestTemplate template = new RestTemplate();
	
	@Override
	public ResponseEntity<?> getConversion(String from, String to, BigDecimal quantity) {
		if(quantity.compareTo(BigDecimal.valueOf(300.0)) == 1) {
			throw new InvalidQuantityException(String.format("Quantity of %s is too large", quantity));
		}
		
		String endPoint = "http://localhost:8000/currency-exchange?from=" + from + "&to=" + to;
		ResponseEntity<CurrencyExchangeDto> response;
		response = template.getForEntity(endPoint, CurrencyExchangeDto.class);
	
		
		return ResponseEntity.ok(new CurrencyConversionDto(response.getBody(), quantity));
	}

}
