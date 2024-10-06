package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import tobyspring.hellospring.api.ApiTemplate;
import tobyspring.hellospring.exrate.RestTemplateExRateProvider;
import tobyspring.hellospring.exrate.WebApiExRateProvider;
import tobyspring.hellospring.payment.ExRateProvider;
import tobyspring.hellospring.payment.PaymentService;

import java.time.Clock;

@Configuration // 빈끼리의 연관관계를 알려준다
public class PaymentConfig {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider(), clock());
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public ExRateProvider exRateProvider() {
//        return new WebApiExRateProvider(apiTemplate());
        return new RestTemplateExRateProvider(restTemplate());
    }


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(new JdkClientHttpRequestFactory());
    }

    @Bean
    public ApiTemplate apiTemplate(){
        return new ApiTemplate();
    }
}
