package com.example.member;

import com.example.member.dto.MemberDTO;
import com.example.member.repository.MemberRepository;
import com.example.member.service.MemberService;

//Assertion의 속한 모든 메서드를 사용할 떄 메서드 이름만 작성 해서 사용가능
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.stream.IntStream;


@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    private MemberDTO newMember(int i){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("test" + i);
        memberDTO.setMemberPassword("1234" + i);
        memberDTO.setMemberName("test_name"+ i);
        memberDTO.setMemberBirth("2023-09-27"+ i);
        memberDTO.setMemberMobile("010-1111-1111"+ i);
        return memberDTO;
    }

    @Test
    @DisplayName("회원 데이터 붓기")
    public void dataInsert(){
//        for (int i=0; i<=20; i++){
//            MemberDTO memberDTO = newMember(i);
//            memberService.save(memberDTO);
//        }
        IntStream.rangeClosed(1, 20).forEach(i -> {
            MemberDTO memberDTO = newMember(i);
            memberService.save(memberDTO);
        });
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원가입 테스트")
    public void memberSaveTest(){
        MemberDTO newMember = newMember(0);
        Long savedId = memberService.save(newMember);
        MemberDTO findMember = memberService.findById(savedId);
        //assertj.core
        assertThat(newMember.getMemberEmail()).isEqualTo(findMember.getMemberEmail());
    }

    @Test
    @DisplayName("로그인 테스트")
    @Transactional
    @Rollback(value = true)
    public void loginTest(){
        MemberDTO newMember = newMember(999);
        memberService.save(newMember);
        MemberDTO loginMember = new MemberDTO();
        loginMember.setMemberEmail(newMember.getMemberEmail());
        loginMember.setMemberPassword(newMember.getMemberPassword());
        boolean result = memberService.login(loginMember);
        assertThat(true).isEqualTo(result);
    }

    @Test
    @DisplayName("삭제테스트")
    @Transactional
    @Rollback(value = true)
    public void deleteTest(){
        MemberDTO newMember = newMember(0);
        Long savedId = memberService.save(newMember);
        memberService.delete(savedId);
        assertThatThrownBy(()-> memberService.findById(savedId)).isInstanceOf(NoSuchElementException.class);
    }

}
