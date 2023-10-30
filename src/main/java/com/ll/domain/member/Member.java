package com.ll.domain.member;

import java.util.HashMap;
import java.util.Map;

public class Member {
    String member_id,member_pw;
    Map<String,String>members=new HashMap<>();
    public Member(String member_id,String member_pw){
        this.member_id=member_id;
        this.member_pw=member_pw;

    }

}