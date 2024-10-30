package com.example.spring2024.friend;

import com.example.spring2024.member.Member;
import com.example.spring2024.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createFriend(Long memberId, Long friendId) throws Exception{
        Member member = memberRepository.findById(memberId);
        Member friend = memberRepository.findById(friendId);
        if(member==null || friend==null) {
            throw new Exception("존재하지 않는 멤버입니다.");
        }
        if (friendRepository.findAllByMember(member).stream()
                .anyMatch(f -> (f.getUser().equals(member) && f.getFriend().equals(friend)) ||
                        (f.getUser().equals(friend) && f.getFriend().equals(member)))) {
            throw new Exception("이미 친구입니다.");
        }
        friendRepository.save(new Friend(member, friend));
    }

    @Transactional(readOnly = true)
    public List<Friend> getFriends(Long memberId) throws Exception{
        Member member = memberRepository.findById(memberId);
        if(member==null) {
            throw new Exception("존재하지 않는 멤버입니다.");
        }
        List<Friend> friends = friendRepository.findAllByMember(member);
        if(friends.equals(Collections.emptyList())||friends.isEmpty()) {
            throw new Exception("친구가 아직 없습니다.");
        }
        return friends;
    }

    @Transactional
    public void deleteFriend(Long memberId, Long friendId) throws Exception {
        Member member = memberRepository.findById(memberId);
        Member friend = memberRepository.findById(friendId);

        if (member == null || friend == null) {
            throw new Exception("존재하지 않는 멤버입니다.");
        }

        // 특정 멤버의 모든 친구 관계 조회
        List<Friend> friends = friendRepository.findAllByMember(member);

        // 해당 관계가 존재하는지 확인하고 삭제
        Friend friendshipToDelete = friends.stream()
                .filter(f -> (f.getUser().equals(member) && f.getFriend().equals(friend)) ||
                        (f.getUser().equals(friend) && f.getFriend().equals(member)))
                .findFirst()
                .orElseThrow(() -> new Exception("친구가 아닙니다"));

        friendRepository.deleteById(friendshipToDelete.getId());
    }
}
