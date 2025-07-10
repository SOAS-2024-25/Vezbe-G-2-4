package currencyExchange;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import api.dtos.CurrencyExchangeDto;
import api.services.CurrencyExchangeService;
import util.exceptions.CurrencyDoesntExistException;
import util.exceptions.NoDataFoundException;

@RestController
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService{

	@Autowired
	private CurrencyExchangeRepository repo;
	
	@Override
	public ResponseEntity<?> getCurrencyExchange(String from, String to) {
		String missingCurrency = null;
		List<String> validCurrencies = repo.findAllDistinctCurrencis();
//		proveriti da li from parametar odgovara nekoj valuti
		if(!isValidCurrency(from)) missingCurrency = from;
//		proveriti da li to parametar odgovara nekoj valuti
		else if(!isValidCurrency(to)) missingCurrency = to;
//		provera da li je missingCurrency razlicit od null i ako jeste bacanje domain exception-a
		if(missingCurrency != null) throw new 
		CurrencyDoesntExistException(String.format("Currency %s does not exist in the database", missingCurrency),
				validCurrencies);
		
		CurrencyExchangeModel dbResponse  = repo.findByFromAndTo(from, to);
		
		if(dbResponse == null) {
			throw new NoDataFoundException(String.format("Requested exchange rate [%s to %s] does not exist", from,to),
					validCurrencies);
		}

		CurrencyExchangeDto dto = new CurrencyExchangeDto(dbResponse.getFrom(), dbResponse.getTo(), dbResponse.getExchangeRate());
		return ResponseEntity.ok(dto);
	}
	
	public boolean isValidCurrency(String currency) {
		List<String> currencies = repo.findAllDistinctCurrencis();
		for(String s: currencies) {
			if(s.equalsIgnoreCase(currency))
				return true;
		}
		return false;
	}

}
