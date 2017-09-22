#!/usr/bin/perl
use strict;
use warnings;
use Test::More;

use lib './t/lib';
use AutoTest;

my $t = AutoTest->new( name => "hs", host => "http://127.0.01:8080");


$k001 = $t->pos("1. 注册用户",  "/register", {
    data => {
      deptId => 2,
      isAdmin =>
    }
  })

my $r000 = $t->get("0. 打开登录页面", "/web/login"); $t->cookie(); # 设置cookie
my $r001 = $t->pos("1. 登录", "/login", {
    jwt  => undef, # 不需要jwt
    data => {
      phone => $username,
      password => $password,
    }
  });
$t->jwt($u001); # 保存jwt到agent中
