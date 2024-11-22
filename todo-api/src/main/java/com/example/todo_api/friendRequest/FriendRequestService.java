package com.example.todo_api.friendRequest;

import com.example.todo_api.common.exception.BadRequestException;
import com.example.todo_api.common.message.ErrorMessage;
import com.example.todo_api.friend.Friendship;
import com.example.todo_api.friend.FriendshipRepository;
import com.example.todo_api.friendRequest.dto.FriendRequestResponse;
import com.example.todo_api.member.Member;
import com.example.todo_api.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final MemberRepository memberRepository;
    private final FriendshipRepository friendshipRepository;

    @Transactional
    public Long sendFriendRequest(String requesterId, String receiverId) throws BadRequestException {
        Member requester = memberRepository.findByLoginId(requesterId);
        Member receiver = memberRepository.findByLoginId(receiverId);
        if (requester == null || receiver == null) {
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
        }
        if(!requester.isSigned()) throw new BadRequestException(ErrorMessage.NOT_SIGNED.getMessage());
        boolean alreadyFriends = friendshipRepository.existsByMemberAndFriend(requester, receiver);
        if (alreadyFriends) {
            throw new BadRequestException(ErrorMessage.ALREADY_FRIEND.getMessage());
        }
        if (friendRequestRepository.existsByRequesterAndReceiver(requester, receiver)) {
            throw new BadRequestException(ErrorMessage.ALREADY_REQUESTED.getMessage());
        }
        FriendRequest friendRequest = new FriendRequest(requester, receiver);
        friendRequestRepository.save(friendRequest);
        return friendRequest.getId();
    }

    @Transactional
    public void acceptFriendRequest(String requestId) throws BadRequestException {
        Member requester = memberRepository.findByLoginId(requestId);
        if(requester==null) throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
        if(!requester.isSigned()) throw new BadRequestException(ErrorMessage.NOT_SIGNED.getMessage());
        FriendRequest request = friendRequestRepository.findById(requester.getId())
                .orElseThrow(() -> new BadRequestException(ErrorMessage.REQUEST_NOT_EXIST.getMessage()));
        Friendship friendship = new Friendship(request.getSender(), request.getReceiver());
        friendshipRepository.save(friendship);
        friendRequestRepository.deleteById(requester.getId());
    }

    @Transactional
    public void rejectFriendRequest(String loginId) throws BadRequestException {
        Member requester = memberRepository.findByLoginId(loginId);
        if(requester==null) throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
        if(!requester.isSigned()) throw new BadRequestException(ErrorMessage.NOT_SIGNED.getMessage());
        if (friendRequestRepository.findById(requester.getId()).isEmpty()) {
            throw new BadRequestException(ErrorMessage.REQUEST_NOT_EXIST.getMessage());
        }
        friendRequestRepository.deleteById(requester.getId());
    }

    @Transactional(readOnly = true)
    public List<FriendRequestResponse> getRequestsForMember(String loginId) throws BadRequestException {
        Member member = memberRepository.findByLoginId(loginId);
        if (member == null) {
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_EXIST.getMessage());
        }
        if(!member.isSigned()) throw new BadRequestException(ErrorMessage.NOT_SIGNED.getMessage());
        List<FriendRequest> requests = friendRequestRepository.findReceivedRequests(member);
        return requests.stream()
                .map(req -> new FriendRequestResponse(
                        req.getId(),
                        req.getSender().getId(),
                        req.getReceiver().getId(),
                        req.getStatus(),
                        friendshipRepository.existsByMemberAndFriend(req.getSender(), req.getReceiver())
                ))
                .toList();
    }
}

