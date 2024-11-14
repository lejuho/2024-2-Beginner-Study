package com.example.spring2024.friendRequest;

import com.example.spring2024.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FriendRequestRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(FriendRequest friendRequest) {
        em.persist(friendRequest);
    }

    public Optional<FriendRequest> findById(Long requestId) {
        return Optional.ofNullable(em.find(FriendRequest.class, requestId));
    }

    public boolean existsByRequesterAndReceiver(Member requester, Member receiver) {
        Long count = em.createQuery(
                        "SELECT COUNT(fr) FROM FriendRequest fr WHERE fr.sender = :requester AND fr.receiver = :receiver", Long.class)
                .setParameter("requester", requester)
                .setParameter("receiver", receiver)
                .getSingleResult();
        return count > 0;
    }
    // 특정 사용자로부터 받은 요청 조회
    public List<FriendRequest> findReceivedRequests(Member receiver) {
        return em.createQuery(
                        "SELECT fr FROM FriendRequest fr WHERE fr.receiver = :receiver AND fr.status = :status", FriendRequest.class)
                .setParameter("receiver", receiver)
                .setParameter("status", FriendRequest.Status.PENDING)
                .getResultList();
    }

    // 특정 사용자로부터 보낸 요청 조회
    public List<FriendRequest> findSentRequests(Member sender) {
        return em.createQuery(
                        "SELECT fr FROM FriendRequest fr WHERE fr.sender = :sender AND fr.status = :status", FriendRequest.class)
                .setParameter("sender", sender)
                .setParameter("status", FriendRequest.Status.PENDING)
                .getResultList();
    }

    // 요청 상태 업데이트
    public void updateRequestStatus(Long requestId, FriendRequest.Status newStatus) {
        FriendRequest request = em.find(FriendRequest.class, requestId);
        if (request != null) {
            request.updateStatus(newStatus);
        }
    }

    // 특정 ID로 요청 삭제
    public void deleteById(Long requestId) {
        FriendRequest request = em.find(FriendRequest.class, requestId);
        if (request != null) {
            em.remove(request);
        }
    }

    // 영속성 컨텍스트 초기화 (flush & clear)
    public void flushAndClear() {
        em.flush();
        em.clear();
    }
}

