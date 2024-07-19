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

            Member member1 = new Member();
//            member.setUsername("member1");
//            member.setUsername("teamA");
//            member.setUsername(null);
            member1.setUsername("관리자1");
            member1.setAge(10);
            member1.setTeam(team);
            member1.setType(MemberType.ADMIN);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setAge(10);
            member2.setTeam(team);
            member2.setType(MemberType.ADMIN);

            em.persist(member1);
            em.persist(member2);

            em.flush();
            em.clear();

            // jpql 함수

            // concat
//            TypedQuery<String> query = em.createQuery("select concat('a', 'b') from Member  m", String.class);
//            TypedQuery<String> query = em.createQuery("select 'a' || 'b' from Member  m", String.class);

//            TypedQuery<String> query = em.createQuery("select concat('a', 'b') from Member  m", String.class);

            // substring
//            TypedQuery<String> query = em.createQuery("select substring(m.username, 2, 3) from Member  m", String.class);

            // trim
//            TypedQuery<String> query = em.createQuery("select trim(m.username) from Member  m", String.class);

            // length
//            TypedQuery<Integer> query = em.createQuery("select length(m.username) from Member  m", Integer.class);

            // locate
//            TypedQuery<Integer> query = em.createQuery("select locate('de', 'abcdefg') from Member  m", Integer.class);

            // size
//            TypedQuery<Integer> query = em.createQuery("select size(t.members) from Team  t", Integer.class);

            // 사용자정의 함수
//            TypedQuery<String> query = em.createQuery("select function('group_concat', m.username) from Member  m", String.class);
            TypedQuery<String> query = em.createQuery("select group_concat(m.username) from Member  m", String.class);

            List<String> result = query.getResultList();

            for (String s : result) {
                System.out.println("s = " + s);
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
