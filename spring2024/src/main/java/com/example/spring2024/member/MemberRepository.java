package com.example.spring2024.member;

import com.example.spring2024.todo.Todo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findById(Long MemberId) {
        return em.find(Member.class,MemberId);
    }

    public void updateById(Long MemberId,String newLoginId,String newPassword) {
        Member member = em.find(Member.class,MemberId);
        member.updateLogin(newLoginId,newPassword);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member as m", Member.class).getResultList();
    }


    public void deleteById(Long MemberId) {
        Member member = em.find(Member.class,MemberId);
        em.remove(member);
    }

    public void flushAndClear(){
        em.flush();
        em.clear();
    }

    public Member findByLoginId(String loginId) {
        return em.createQuery("SELECT m FROM Member m WHERE m.loginId = :loginId", Member.class)
                .setParameter("loginId", loginId)
                .getSingleResult();
    }
}
