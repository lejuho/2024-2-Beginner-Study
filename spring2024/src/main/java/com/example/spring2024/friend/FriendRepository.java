package com.example.spring2024.friend;

import com.example.spring2024.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FriendRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Friend friend) {
        em.persist(friend);
    }

    public Friend findById(Long FriendId) {
        return em.find(Friend.class,FriendId);
    }

    public List<Friend> findAllByMember(Member member) {
        return em.createQuery(
                        "SELECT f FROM Friend f WHERE f.user = :member OR f.friend = :member", Friend.class)
                .setParameter("member", member)
                .getResultList();
    }

    public void updateById(Long FriendshipId,Long UserId,Long FriendId) {
        Friend friendship = em.find(Friend.class,FriendshipId);
        Member user = em.find(Member.class,UserId);
        Member friend = em.find(Member.class,FriendId);
        if(user!=null&&friend!=null) {
            friendship.updateFriendship(user,friend);
        }
    }

    public void deleteById(Long FriendId) {
        Friend friend = em.find(Friend.class, FriendId);
        em.remove(friend);
    }

    public void flushAndClear(){
        em.flush();
        em.clear();
    }
}
