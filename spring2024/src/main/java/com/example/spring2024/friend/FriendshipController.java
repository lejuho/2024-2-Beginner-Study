package com.example.spring2024.friend;


import com.example.spring2024.friend.dto.FriendshipCreateRequest;
import com.example.spring2024.friend.dto.FriendshipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friendship")
public class FriendshipController {
    private final FriendshipService friendshipService;

    @PostMapping
    public ResponseEntity<Void> createFriendship(@RequestBody FriendshipCreateRequest request) throws Exception {
        Long friendshipId = friendshipService.createFriend(request.getUserId(),request.getFriendId());
        return ResponseEntity.created(URI.create("/friendship/"+friendshipId)).build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<FriendshipResponse>> getFriendship(@PathVariable Long memberId) throws Exception {
        List<FriendshipResponse> friends = friendshipService.getFriends(memberId);
        return ResponseEntity.ok().body(friends);
    }

    @DeleteMapping("/{memberId}/{friendId}")
    public ResponseEntity<Void> deleteFriendship(@PathVariable Long memberId,@PathVariable Long friendId) throws Exception{
        friendshipService.deleteFriend(memberId,friendId);
        return ResponseEntity.noContent().build();
    }
}
