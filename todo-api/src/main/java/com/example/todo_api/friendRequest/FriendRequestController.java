package com.example.todo_api.friendRequest;

import com.example.todo_api.common.exception.BadRequestException;
import com.example.todo_api.friend.FriendshipService;
import com.example.todo_api.friendRequest.dto.FriendRequestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friendRequests")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;
    private final FriendshipService friendshipService;

    // 친구 요청 생성
    @PostMapping("/send")
    public ResponseEntity<Long> sendFriendRequest(@RequestParam String requesterId, @RequestParam String receiverId) throws BadRequestException {
        Long requestId = friendRequestService.sendFriendRequest(requesterId, receiverId);
        return ResponseEntity.ok(requestId);
    }

    // 친구 요청 수락
    @PostMapping("/{loginId}/accept")
    public ResponseEntity<Void> acceptFriendRequest(@PathVariable String loginId) throws BadRequestException {
        friendRequestService.acceptFriendRequest(loginId);
        return ResponseEntity.noContent().build();
    }

    // 친구 요청 거절
    @DeleteMapping("/{loginId}/reject")
    public ResponseEntity<Void> rejectFriendRequest(@PathVariable String loginId) throws BadRequestException {
        friendRequestService.rejectFriendRequest(loginId);
        return ResponseEntity.noContent().build();
    }

    // 특정 사용자의 모든 친구 요청 조회
    @GetMapping("/{loginId}/requests")
    public ResponseEntity<List<FriendRequestResponse>> getFriendRequests(@PathVariable String loginId) throws BadRequestException {
        List<FriendRequestResponse> requests = friendRequestService.getRequestsForMember(loginId);
        List<FriendRequestResponse> responseList = requests.stream()
                .map(req -> new FriendRequestResponse(req.getId(), req.getRequesterId(), req.getReceiverId(), FriendRequest.Status.PENDING,
                        friendshipService.isFriends(req.getReceiverId(), req.getRequesterId())))
                .toList();
        return ResponseEntity.ok(responseList);
    }
}
