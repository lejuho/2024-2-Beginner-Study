package com.example.spring2024.friend;

import com.example.spring2024.friend.dto.FriendshipResponse;
import com.example.spring2024.member.Member;
import com.example.spring2024.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class FriendshipServiceTest {

    @Mock
    FriendshipRepository friendshipRepository;

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    FriendshipService friendshipService;

    @Test
    public void createFriendTest() throws Exception {
        // Given
        Member member = new Member("testUser", "testPassword");
        Member friend = new Member("testFriend", "testPassword");
        BDDMockito.given(memberRepository.findById(1L)).willReturn(member);
        BDDMockito.given(memberRepository.findById(2L)).willReturn(friend);
        BDDMockito.given(friendshipRepository.findAllByMember(member)).willReturn(Collections.emptyList());

        // When
        friendshipService.createFriend(1L, 2L);

        // Then
        Mockito.verify(friendshipRepository, Mockito.times(1)).save(any(Friendship.class));
    }

    @Test
    public void createFriendTest_When_MemberDoesNotExist() {
        // Given
        BDDMockito.given(memberRepository.findById(1L)).willReturn(null);

        // When & Then
        Exception exception = assertThrows(Exception.class, () -> {
            friendshipService.createFriend(1L, 2L);
        });
        assertTrue(exception.getMessage().contains("존재하지 않는 멤버입니다."));
    }

    @Test
    public void createFriendTest_When_FriendAlreadyExists() throws Exception {
        // Given
        Member member = new Member("testUser", "testPassword");
        Member friend = new Member("testFriend", "testPassword");
        BDDMockito.given(memberRepository.findById(1L)).willReturn(member);
        BDDMockito.given(memberRepository.findById(2L)).willReturn(friend);
        BDDMockito.given(friendshipRepository.findAllByMember(member))
                .willReturn(List.of(new Friendship(member, friend)));

        // When & Then
        Exception exception = assertThrows(Exception.class, () -> {
            friendshipService.createFriend(1L, 2L);
        });
        assertTrue(exception.getMessage().contains("이미 친구입니다."));
    }

    @Test
    public void getFriendsTest() throws Exception {
        // Given
        Member member = new Member("testUser", "testPassword");
        List<Friendship> friendships = List.of(new Friendship(member, new Member("testFriend", "testPassword")));
        BDDMockito.given(memberRepository.findById(1L)).willReturn(member);
        BDDMockito.given(friendshipRepository.findAllByMember(member)).willReturn(friendships);

        // When
        List<FriendshipResponse> result = friendshipService.getFriends(1L);

        // Then
        assertEquals(friendships, result);
    }

    @Test
    public void getFriendsTest_When_MemberDoesNotExist() {
        // Given
        BDDMockito.given(memberRepository.findById(1L)).willReturn(null);

        // When & Then
        Exception exception = assertThrows(Exception.class, () -> {
            friendshipService.getFriends(1L);
        });
        assertTrue(exception.getMessage().contains("존재하지 않는 멤버입니다."));
    }

    @Test
    public void getFriendsTest_When_NoFriendsExist() throws Exception {
        // Given
        Member member = new Member("testUser", "testPassword");
        BDDMockito.given(memberRepository.findById(1L)).willReturn(member);
        BDDMockito.given(friendshipRepository.findAllByMember(member)).willReturn(Collections.emptyList());

        // When & Thena
        Exception exception = assertThrows(Exception.class, () -> {
            friendshipService.getFriends(1L);
        });
        assertTrue(exception.getMessage().contains("친구가 아직 없습니다."));
    }

    @Test
    public void deleteFriendTest() throws Exception {
        // Given
        Member member = new Member("testUser", "testPassword");
        Member friend = new Member("testFriend", "testPassword");
        Friendship friendship = new Friendship(member, friend);
        BDDMockito.given(memberRepository.findById(1L)).willReturn(member);
        BDDMockito.given(memberRepository.findById(2L)).willReturn(friend);
        BDDMockito.given(friendshipRepository.findAllByMember(member)).willReturn(List.of(friendship));

        // When
        friendshipService.deleteFriend(1L, 2L);

        // Then
        Mockito.verify(friendshipRepository, Mockito.times(1)).deleteById(friendship.getId());
    }

    @Test
    public void deleteFriendTest_When_MemberDoesNotExist() {
        // Given
        BDDMockito.given(memberRepository.findById(1L)).willReturn(null);

        // When & Then
        Exception exception = assertThrows(Exception.class, () -> {
            friendshipService.deleteFriend(1L, 2L);
        });
        assertTrue(exception.getMessage().contains("존재하지 않는 멤버입니다."));
    }

    @Test
    public void deleteFriendTest_When_FriendshipDoesNotExist() throws Exception {
        // Given
        Member member = new Member("testUser", "testPassword");
        Member friend = new Member("testFriend", "testPassword");
        BDDMockito.given(memberRepository.findById(1L)).willReturn(member);
        BDDMockito.given(memberRepository.findById(2L)).willReturn(friend);
        BDDMockito.given(friendshipRepository.findAllByMember(member)).willReturn(Collections.emptyList());

        // When & Then
        Exception exception = assertThrows(Exception.class, () -> {
            friendshipService.deleteFriend(1L, 2L);
        });
        assertTrue(exception.getMessage().contains("친구가 아닙니다"));
    }
}
