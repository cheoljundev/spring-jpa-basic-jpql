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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
//            member.setUsername("member1");
            member.setUsername("teamA");
            member.setAge(10);
            member.setTeam(team);

            em.persist(member);

            // 조인

            // inner

//            List<Member> result = em.createQuery("select m from Member m inner join m.team t", Member.class)
//                    .getResultList();

            // outer

//            List<Member> result = em.createQuery("select m from Member m left join m.team t", Member.class)
//                    .getResultList();

            // outer on 절(조인 대상 필터링, 연관관계 있을때)
//            List<Member> result = em.createQuery("select m from Member m left join m.team t on t.name = 'teamA'", Member.class)
//                    .getResultList();

            // outer on 절(조인 대상 필터링, 연관관계 없을때)

            List<Member> result = em.createQuery("select m from Member m left join m.team t on m.username = t.name", Member.class)
                    .getResultList();

            // 세타 조인 (막조인, 연관관계가 없을때)

//            List<Member> result = em.createQuery("select m from Member m, Team t where m.username = t.name", Member.class)
//                    .getResultList();


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
