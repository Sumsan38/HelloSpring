package tobyspring.hellospring;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tobyspring.hellospring.order.Order;

import java.math.BigDecimal;

public class DataClient {

    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        EntityManagerFactory emf = beanFactory.getBean(EntityManagerFactory.class);

        // em 생성
        EntityManager em = emf.createEntityManager();

        // transaction 생성
        em.getTransaction().begin();

        // em.persist (영속화 해달라)
        Order order = new Order("100", BigDecimal.TEN);
        System.out.println(order);
        em.persist(order);

        System.out.println(order);

        // transaction 종료
        em.getTransaction().commit();
        em.close();
    }
}
