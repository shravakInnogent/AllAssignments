package com.libraryManagementSystem.service;

import com.libraryManagementSystem.Exception.ResourceNotFoundException;
import com.libraryManagementSystem.entity.Member;
import com.libraryManagementSystem.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MemberService {

    @Autowired
    MemberRepo memberRepo;

    public void add(Member member){
        memberRepo.save(member);
    }

    public Member getMemberById(long id){
            return memberRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                    "Member not found with id: " + id));

    }

    public void delete(long id){
        memberRepo.deleteById(id);
    }
}
