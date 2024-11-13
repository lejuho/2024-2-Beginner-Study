package com.example.spring2024.member;

import com.example.spring2024.member.dto.MemberCreateRequest;
import com.example.spring2024.member.dto.MemberLoginRequest;
import com.example.spring2024.member.dto.MemberResponse;
import com.example.spring2024.member.dto.MemberUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody MemberCreateRequest request) throws Exception {
        Long memberId = memberService.createMember(request.getLoginId(),request.getPassword());
        return ResponseEntity.created(URI.create("/user/"+memberId)).build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long memberId) throws Exception {
        MemberResponse member = memberService.getMember(memberId);
        return ResponseEntity.ok().body(member);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) throws Exception{
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<Void> updateMember(@PathVariable Long memberId, @RequestBody MemberUpdateRequest request) throws Exception {
        memberService.updateMember(memberId,request.getLoginId(),request.getPassword());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody MemberLoginRequest request) throws Exception {
        Long memberId = memberService.loginMember(request.getLoginId(), request.getPassword());
        return ResponseEntity.ok(memberId);  // Return the primary key on successful login
    }

}
