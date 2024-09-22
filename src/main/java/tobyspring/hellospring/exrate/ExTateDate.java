package tobyspring.hellospring.exrate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)     // 생성자에 없는 데이터는 무시한다.
public record ExTateDate(String result, Map<String, BigDecimal> rates) {
}
