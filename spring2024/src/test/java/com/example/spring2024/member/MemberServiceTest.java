package com.example.spring2024.member;

import com.example.spring2024.member.dto.MemberResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberService memberService;

    @Test
    public void createMemberTest() {
        // Test that createMember calls save on the repository
        memberService.createMember("testLogin", "testPassword");
        Mockito.verify(memberRepository, Mockito.times(1)).save(any(Member.class));
    }

    @Test
    public void getMemberTest() throws Exception {
        // Given
        Member member = new Member("testLogin", "testPassword");
        BDDMockito.given(memberRepository.findById(1L)).willReturn(member);

        // When
        MemberResponse foundMember = memberService.getMember(1L);

        // Then
        assertEquals(member, foundMember);
    }

    @Test
    public void getMemberTest_When_MemberDoesNotExist() {
        // Given
        BDDMockito.given(memberRepository.findById(1L)).willReturn(null);

        // When & Then
        Exception exception = assertThrows(Exception.class, () -> {
            memberService.getMember(1L);
        });
        assertTrue(exception.getMessage().contains("존재하지 않는 멤버입니다."));
    }

    @Test
    public void updateMemberTest() throws Exception {
        // Given
        Member member = new Member("testLogin", "testPassword");
        BDDMockito.given(memberRepository.findById(1L)).willReturn(member);

        // When
        memberService.updateMember(1L, "newLogin", "newPassword");

        // Then
        Mockito.verify(memberRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(memberRepository, Mockito.never()).deleteById(1L);
    }

    @Test
    public void updateMemberTest_When_MemberDoesNotExist() {
        // Given
        BDDMockito.given(memberRepository.findById(1L)).willReturn(null);

        // When & Then
        Exception exception = assertThrows(Exception.class, () -> {
            memberService.updateMember(1L, "newLogin", "newPassword");
        });
        assertTrue(exception.getMessage().contains("존재하지 않는 멤버입니다."));
    }

    @Test
    public void deleteMemberTest() throws Exception {
        // Given
        Member member = new Member("testLogin", "testPassword");
        BDDMockito.given(memberRepository.findById(1L)).willReturn(member);

        // When
        memberService.deleteMember(1L);

        // Then
        Mockito.verify(memberRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void deleteMemberTest_When_MemberDoesNotExist() {
        // Given
        BDDMockito.given(memberRepository.findById(1L)).willReturn(null);

        // When & Then
        Exception exception = assertThrows(Exception.class, () -> {
            memberService.deleteMember(1L);
        });
        assertTrue(exception.getMessage().contains("존재하지 않는 멤버입니다."));
    }
}
