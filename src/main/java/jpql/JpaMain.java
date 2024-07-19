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
            Team team = new Team();
            Order order = new Order();
            member.setUsername("member1");
            member.setTeam(team);

            Address address = new Address();
            address.setCity("Seoul");
            address.setStreet("street");
            address.setZipcode("12345");
            order.setAddress(address);

            em.persist(member);
            em.persist(team);
            em.persist(order);

            // 프로젝션(SELECT)
            // 엔티티 프로젝션, 임베디드 프로젝션, 스칼라 프로젝션

            em.flush();
            em.clear();

            // 엔티티 프로젝션
            // 찾은 결과는 영속성 컨텍스트에서 관리됨

            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            Member findMember = result.get(0);
            findMember.setAge(20);
            System.out.println("findMember = " + findMember);

            // 엔티티 프로젝션

            // List<Team> resultList1 = em.createQuery("select m.team from Member m", Team.class)
            //        .getResultList(); -> 좋지 않음. 예측불가

            List<Team> result2 = em.createQuery("select t from Member m join m.team t", Team.class)
                    .getResultList(); // 예측 가능

            Team resultTeam = result2.get(0);

            // 임베디드 타입 프로젝션
            List<Address> result3 = em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();
            Address findAddress = result3.get(0);

            // 스칼라 프로젝션
//            List resultList = em.createQuery("select distinct m.username, m.age from Member m")
//                    .getResultList();
//
//            Object[] sResult = (Object[]) resultList.get(0);
//            System.out.println("username = " + sResult[0]);
//            System.out.println("age = " + sResult[1]);

            // 스칼라 + 제네릭

//            List<Object[]> resultList = em.createQuery("select distinct m.username, m.age from Member m")
//                    .getResultList();
//
//            Object[] sResult = resultList.get(0);
//            System.out.println("username = " + sResult[0]);
//            System.out.println("age = " + sResult[1]);

            // 스칼라 + DTO

            List<MemberDto> resultList = em.createQuery("select distinct new jpql.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                    .getResultList();

            MemberDto memberDto = resultList.get(0);
            System.out.println("username = " + memberDto.getUsername());
            System.out.println("age = " + memberDto.getAge());


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
