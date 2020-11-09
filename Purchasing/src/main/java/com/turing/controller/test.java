package com.turing.controller;

import org.apache.shiro.crypto.hash.Md5Hash;

public class test {
    public static void main(String[] args) {
        //密码加密
        String newPwd=new Md5Hash("123","zl",3).toString();
        System.out.println(newPwd);
    }
}
