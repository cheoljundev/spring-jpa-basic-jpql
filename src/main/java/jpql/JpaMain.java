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

            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setAge(10);
            member1.setTeam(teamA);
            member1.setType(MemberType.ADMIN);
            em.persist(member1);


            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setAge(10);
            member2.setTeam(teamB);
            member2.setType(MemberType.ADMIN);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setAge(10);
            member3.setTeam(teamB);
            member3.setType(MemberType.ADMIN);
            em.persist(member3);


            em.flush();
            em.clear();

            // fetch 조인

            // ManyToOne fetch 조인
//            TypedQuery<Member> query = em.createQuery("select m from Member  m", Member.class); // n+1 문제 발생
            // TypedQuery<Member> query = em.createQuery("select m from Member  m join fetch m.team", Member.class); // fetch join으로 해결

//            List<Member> result = query.getResultList();
//
//            for (Member m : result) {
//                System.out.println("member = " + m.getUsername() + ", " + m.getTeam().getName());
//            }

            // 컬렉션 fetch 조인, OneToMany

            TypedQuery<Team> query = em.createQuery("select t from Team  t join fetch t.members", Team.class);

            List<Team> result = query.getResultList();

            for (Team t : result) {
                System.out.println("team = " + t.getName() + "|members = " + t.getMembers().size());
                for (Member member : t.getMembers()) {
                    System.out.println("member = " + member);
                }
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
