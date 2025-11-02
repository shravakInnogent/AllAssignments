package com.libraryManagementSystem.controller;

import com.libraryManagementSystem.entity.Member;
import com.libraryManagementSystem.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/member" )
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/add")
    public void add(@RequestBody Member member){
        memberService.add(member);
    }

    @GetMapping("/getMemberById/{id}")
    public Member getMemberById(@PathVariable long id){
        return memberService.getMemberById(id);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable long id){
        memberService.delete(id);
    }
}
