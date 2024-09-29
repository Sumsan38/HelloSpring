package tobyspring.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
class PaymentServiceTest {

    Clock clock;

    @BeforeEach
    void beforeEach() {
        this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 잘 충족했는지 검증")
    void convertedAmount() throws IOException {
        testAmount(valueOf(500), valueOf(5_000), this.clock);
        testAmount(valueOf(1000), valueOf(10_000), this.clock);
        testAmount(valueOf(3000), valueOf(30_000), this.clock);
    }

    @Test
    void validUntil() throws IOException {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(valueOf(1_000)), clock);

        Payment payment = paymentService.prepare(1L, "USD", TEN);

        // valid until이 prepare() 30분 뒤로 설정됐는가?
        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);

        Assertions.assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
    }

    @NonNull
    private static void testAmount(BigDecimal exRate, BigDecimal convertedAmount,
                                   Clock clock) throws IOException {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate), clock);

        // 기능 실행
        Payment payment = paymentService.prepare(1L, "USD", TEN);

        // 검증
        // 1. 환율 정보를 가져온다
        assertThat(payment.getExRate()).isEqualByComparingTo(exRate);

        // 2. 원화 환산 금액 계산
        assertThat(payment.getConvertedAmount()).isEqualTo(convertedAmount);
    }
}