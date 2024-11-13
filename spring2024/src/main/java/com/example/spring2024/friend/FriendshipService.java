package com.example.spring2024.friend;

import com.example.spring2024.friend.dto.FriendshipResponse;
import com.example.spring2024.member.Member;
import com.example.spring2024.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createFriend(Long memberId, Long friendId) throws Exception{
        Member member = memberRepository.findById(memberId);
        Member friend = memberRepository.findById(friendId);
        if(member==null || friend==null) {
            throw new Exception("존재하지 않는 멤버입니다.");
        }
        if (friendshipRepository.findAllByMember(member).stream()
                .anyMatch(f -> (f.getUser().equals(member) && f.getFriend().equals(friend)) ||
                        (f.getUser().equals(friend) && f.getFriend().equals(member)))) {
            throw new Exception("이미 친구입니다.");
        }
        Friendship friendship = new Friendship(member, friend);
        friendshipRepository.save(friendship);
        return friendship.getId();
    }

    @Transactional(readOnly = true)
    public List<FriendshipResponse> getFriends(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new Exception("존재하지 않는 멤버입니다.");
        }

        List<Friendship> friendships = friendshipRepository.findAllByMember(member);
        if (friendships.isEmpty()) {
            throw new Exception("친구가 아직 없습니다.");
        }

        // FriendshipResponse DTO로 매핑
        return friendships.stream()
                .map(friendship -> new FriendshipResponse(
                        friendship.getId(),
                        friendship.getUser().getId(),
                        friendship.getFriend().getId()))
                .toList();
    }

    @Transactional
    public void deleteFriend(Long memberId, Long friendId) throws Exception {
        Member member = memberRepository.findById(memberId);
        Member friend = memberRepository.findById(friendId);

        if (member == null || friend == null) {
            throw new Exception("존재하지 않는 멤버입니다.");
        }

        // 특정 멤버의 모든 친구 관계 조회
        List<Friendship> friendships = friendshipRepository.findAllByMember(member);

        // 해당 관계가 존재하는지 확인하고 삭제
        Friendship friendshipToDelete = friendships.stream()
                .filter(f -> (f.getUser().equals(member) && f.getFriend().equals(friend)) ||
                        (f.getUser().equals(friend) && f.getFriend().equals(member)))
                .findFirst()
                .orElseThrow(() -> new Exception("친구가 아닙니다"));

        friendshipRepository.deleteById(friendshipToDelete.getId());
    }
}
