package tobyspring.hellospring.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tobyspring.hellospring.exrate.WebApiExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
class PaymentServiceTest {

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 잘 충족했는지 검증")
    void prepare() throws IOException {
        // 준비
        PaymentService paymentService = new PaymentService(new WebApiExRateProvider());

        // 기능 실행
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // 검증
        // 1. 환율 정보를 가져온다
        assertThat(payment.getExRate()).isNotNull();

        // 2. 원화 환산 금액 계산
        assertThat(payment.getConvertedAmount())
                .isEqualTo(payment.getExRate().multiply(payment.getForeignCurrencyAmount()));

        // 3. 원화 환산 금액의 유효시간 계산
        assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
        assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }
}