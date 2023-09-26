package com.example.member.controller;

import com.example.member.dto.MemberDTO;
import com.example.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save")
    public String save(){
        return "/memberPages/memberSave";
    }


    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        memberService.save(memberDTO);
        return "/memberPages/memberSave";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "redirectURI", defaultValue = "/member/mypage") String redirectURI, Model model){
        model.addAttribute("redirectURI", redirectURI);
        return "/memberPages/memberLogin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginEmail");
        return "/memberPages/memberLogin";
    }

    @GetMapping("/members")
    public String members(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "/memberPages/memberList";
    }

    @GetMapping("/{id}")
    public String members(Model model, @PathVariable("id") Long id){
        try{
            MemberDTO memberDTO = memberService.findById(id);
            model.addAttribute("member", memberDTO);
            return "/memberPages/memberDetail";
        }catch (NoSuchElementException e){
            return "NotFound";
        }
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable("id") Long id){
        try{
            MemberDTO memberDTO = memberService.findById(id);
            model.addAttribute("member", memberDTO);
            return "/memberPages/memberUpdate";
        }catch (NoSuchElementException e){
            return "NotFound";
        }
    }

    @PutMapping("/")
    public ResponseEntity update(@RequestBody MemberDTO memberDTO){
        System.out.println("memberDTO = " + memberDTO);
        boolean loginResult = memberService.login(memberDTO);
        try{
            memberService.update(memberDTO);
            return new ResponseEntity<>("수정완료", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>( HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity members(@ModelAttribute MemberDTO memberDTO){
        System.out.println("memberDTO = " + memberDTO);
        boolean loginResult = memberService.login(memberDTO);
        if(loginResult){
            memberService.delete(memberDTO.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session, @RequestParam("redirectURI") String redirectURI){
        boolean loginResult = memberService.login(memberDTO);
        if(loginResult){
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
//            return "/memberPages/memberMain";
            return "redirect:" + redirectURI;
        }else{
            return "/memberPages/memberLogin";
        }
    }

    @GetMapping("/mypage")
    public String myPage(){
        return "memberPages/meberMain";
    }

    @PostMapping("/dup-check")
    public ResponseEntity login(@RequestBody MemberDTO memberDTO){
        boolean result = memberService.findByEmail(memberDTO.getMemberEmail());
        if(result){
            return new ResponseEntity<>("사용가능",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("사용불가능", HttpStatus.CONFLICT);
        }
    }
    @GetMapping("/detail")
    public ResponseEntity detail(@RequestParam("id") Long id){
        MemberDTO memberDTO = memberService.findById(id);
        if(memberDTO!=null){
            return new ResponseEntity<>(memberDTO,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
