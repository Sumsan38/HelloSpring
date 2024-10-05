package tobyspring.hellospring.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tobyspring.hellospring.exrate.ExTateDate;

import java.math.BigDecimal;

public class ErApiExRateExtractor implements ExRateExtractor {
    @Override
    public BigDecimal extract(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ExTateDate data = mapper.readValue(response, ExTateDate.class);
        return data.rates().get("KRW");
    }
}
