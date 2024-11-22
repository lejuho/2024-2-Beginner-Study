package com.example.todo_api.friend;

import com.example.todo_api.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FriendshipRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Friendship friendship) {
        em.persist(friendship);
    }

    public Friendship findById(Long FriendId) {
        return em.find(Friendship.class,FriendId);
    }

    public List<Friendship> findAllByMember(Member member) {
        return em.createQuery(
                        "SELECT f FROM Friendship f WHERE f.user = :member OR f.friend = :member", Friendship.class)
                .setParameter("member", member)
                .getResultList();
    }

    public void updateById(Long FriendshipId,Long UserId,Long FriendId) {
        Friendship friendship = em.find(Friendship.class,FriendshipId);
        Member user = em.find(Member.class,UserId);
        Member friend = em.find(Member.class,FriendId);
        if(user!=null&&friend!=null) {
            friendship.updateFriendship(user,friend);
        }
    }

    public boolean existsByMemberAndFriend(Member member,Member friend) {
        return findAllByMember(member).contains(new Friendship(member, friend)) || findAllByMember(friend).contains(new Friendship(friend, member));
    }

    public void deleteById(Long FriendId) {
        Friendship friendship = em.find(Friendship.class, FriendId);
        em.remove(friendship);
    }

    public void flushAndClear(){
        em.flush();
        em.clear();
    }
}
