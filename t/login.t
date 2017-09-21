#!/usr/bin/perl
use strict;
use warnings;
use Test::More;

use lib './t/lib';
use AutoTest;

my $r000 = $self->get("0. 打开登录页面", "/web/login"); $self->cookie(); # 设置cookie
my $r001 = $self->pos("1. 登录", "/login", {
    jwt  => undef, # 不需要jwt
    data => {
      username => $username,
      password => $password,
    }
  });
$self->jwt($u001); # 保存jwt到agent中
