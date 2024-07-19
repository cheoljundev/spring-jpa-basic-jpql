package jpql;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");

            em.persist(member);

            //TypedQuery,Query
            TypedQuery<Member> query1 = em.createQuery("select  m from Member  m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select  m.username from Member  m", String.class);
            Query query3 = em.createQuery("select  m.username, m.age from Member  m");


            // getResultList
            List<Member> resultList = em.createQuery("select  m from Member  m", Member.class).getResultList();
            for (Member m : resultList) {
                System.out.println("member = " + m);
            }

            // getSingleResult - 결과가 정확히 하나여야 한다.
            Member singleResult = em.createQuery("select  m from Member  m where m.id = 1", Member.class).getSingleResult();
            System.out.println("singleResult = " + singleResult);

            // setParameter
            Member singleResult1 = em.createQuery("select  m from Member  m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();
            System.out.println("singleResult1 = " + singleResult1);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
