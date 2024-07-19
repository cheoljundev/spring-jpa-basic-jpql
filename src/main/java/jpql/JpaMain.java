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
//            member.setUsername("teamA");
//            member.setUsername(null);
            member.setUsername("관리자");
            member.setAge(10);
            member.setTeam(team);
            member.setType(MemberType.ADMIN);

            em.persist(member);

            em.flush();
            em.clear();

            // case 식
//            TypedQuery<String> query = em.createQuery(
//                    "select " +
//                            "case when m.age <= 10 then '학생요금' " +
//                            "         when m.age >= 60 then '경로요금' " +
//                            "         else '일반요금' " +
//                            "end " +
//                            "from Member m", String.class);

            // coalesce: 하나씩 조회해서 null이 아니면 반환
//            TypedQuery<String> query = em.createQuery("select coalesce(m.username, '이름 없는 회원') as username from Member  m", String.class);

            // nullif: 두 값이 같으면 null 반환, 다르면 첫 번째 값 반환
            TypedQuery<String> query = em.createQuery("select nullif(m.username, '관리자') as username from Member  m", String.class);

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
