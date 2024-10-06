package tobyspring.hellospring.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import tobyspring.hellospring.order.Order;

public class OrderRepository {

    private final EntityManagerFactory emf;

    public OrderRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(Order order) {
        EntityManager em = emf.createEntityManager();

        // transaction 생성
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        // em.persist (영속화 해달라)
        try {
            em.persist(order);
            em.flush();

            // transaction 종료
            transaction.commit();
        }
        catch (RuntimeException e) {
            if(transaction.isActive()) transaction.rollback();
            throw e;
        }
        finally {
            if(em.isOpen())  em.close();
        }
    }
}
