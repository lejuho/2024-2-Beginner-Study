package com.example.todo_api.member;

import com.example.todo_api.common.exception.BadRequestException;
import com.example.todo_api.member.dto.MemberCreateRequest;
import com.example.todo_api.member.dto.MemberLoginRequest;
import com.example.todo_api.member.dto.MemberResponse;
import com.example.todo_api.member.dto.MemberUpdateRequest;
import jakarta.validation.Valid;
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
    public ResponseEntity<Void> createMember(@RequestBody @Valid MemberCreateRequest request) throws BadRequestException {
        Long memberId = memberService.createMember(request.getLoginId(),request.getPassword());
        return ResponseEntity.created(URI.create("/user/"+memberId)).build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long memberId) throws BadRequestException {
        MemberResponse member = memberService.getMember(memberId);
        return ResponseEntity.ok().body(member);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) throws BadRequestException{
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<Void> updateMember(@PathVariable Long memberId, @RequestBody @Valid MemberUpdateRequest request) throws BadRequestException {
        memberService.updateMember(memberId,request.getLoginId(),request.getPassword());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody @Valid MemberLoginRequest request) throws BadRequestException {
        Long memberId = memberService.loginMember(request.getLoginId(), request.getPassword());
        return ResponseEntity.ok(memberId);  // Return the primary key on successful login
    }
}
