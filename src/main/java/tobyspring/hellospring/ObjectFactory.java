package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 빈끼리의 연관관계를 알려준다
public class ObjectFactory {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider());
    }

    @Bean
    public OrderService orderService(){
        return new OrderService(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new SimpleExRateProvider();
    }

    class OrderService {
        ExRateProvider exRateProvider;

        public OrderService(ExRateProvider exRateProvider) {
            this.exRateProvider = exRateProvider;
        }
    }
}
