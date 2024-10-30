package com.example.spring2024.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void createMember(String loginId, String password) {
        memberRepository.save(new Member(loginId, password));
    }

    @Transactional(readOnly = true)
    public Member getMember(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId);
        if(member == null) {
            throw new Exception("존재하지 않는 멤버입니다.");
        }
        return member;
    }

    @Transactional
    public void updateMember(Long memberId,String loginId, String password) throws Exception {
        Member member = memberRepository.findById(memberId);
        if(member == null) {
            throw new Exception("존재하지 않는 멤버입니다.");
        }
        member.updateLogin(loginId, password);
    }

    @Transactional
    public void deleteMember(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId);
        if(member == null) {
            throw new Exception("존재하지 않는 멤버입니다.");
        }
        memberRepository.deleteById(memberId);
    }
}
