package com.example.todo_api.friend;

import com.example.todo_api.common.exception.BadRequestException;
import com.example.todo_api.common.message.ErrorMessage;
import com.example.todo_api.friend.dto.FriendshipResponse;
import com.example.todo_api.member.Member;
import com.example.todo_api.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createFriend(Long memberId, Long friendId) throws BadRequestException{
        Member member = memberRepository.findById(memberId);
        Member friend = memberRepository.findById(friendId);
        if(member==null || friend==null) {
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
        }
        if (friendshipRepository.findAllByMember(member).stream()
                .anyMatch(f -> (f.getUser().equals(member) && f.getFriend().equals(friend)) ||
                        (f.getUser().equals(friend) && f.getFriend().equals(member)))) {
            throw new BadRequestException(ErrorMessage.ALREADY_FRIEND.getMessage());
        }
        Friendship friendship = new Friendship(member, friend);
        friendshipRepository.save(friendship);
        return friendship.getId();
    }

    @Transactional(readOnly = true)
    public List<FriendshipResponse> getFriends(Long memberId) throws BadRequestException {
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
        }

        List<Friendship> friendships = friendshipRepository.findAllByMember(member);
        if (friendships.isEmpty()) {
            throw new BadRequestException(ErrorMessage.ZERO_FRIEND.getMessage());
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
    public void deleteFriend(Long memberId, Long friendId) throws BadRequestException {
        Member member = memberRepository.findById(memberId);
        Member friend = memberRepository.findById(friendId);

        if (member == null || friend == null) {
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
        }

        // 특정 멤버의 모든 친구 관계 조회
        List<Friendship> friendships = friendshipRepository.findAllByMember(member);

        // 해당 관계가 존재하는지 확인하고 삭제
        Friendship friendshipToDelete = friendships.stream()
                .filter(f -> (f.getUser().equals(member) && f.getFriend().equals(friend)) ||
                        (f.getUser().equals(friend) && f.getFriend().equals(member)))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(ErrorMessage.NOT_FRIEND.getMessage()));

        friendshipRepository.deleteById(friendshipToDelete.getId());
    }

    public boolean isFriends(Long memberId, Long friendId) throws BadRequestException {
        Member member = memberRepository.findById(memberId);
        Member friend = memberRepository.findById(friendId);
        return friendshipRepository.existsByMemberAndFriend(member, friend);
    }
}
