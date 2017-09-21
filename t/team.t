#!/usr/bin/perl
use strict;
use warnings;
use Test::More;

use lib './t/lib';
use AutoTest;

my $t = AutoTest->new( name => "hs", host => '127.0.0.1:8080');

my $r001 = $t->pos("创建部门", "/api/departments", { data => { name => '我的部门' }});
my $r002 = $t->get("查询部门", "/api/departments/$r001->{id}");
my $r003 = $t->get("分页部门", "/api/departments?pageNum=1&PageSize=10");

my $r004 = $t->pos("创建团队", "/api/teams", { data => { name => '我的团队', deptId => $r001->{id}, }});
my $r005 = $t->get("查询团队", "/api/teams/$r004->{id}");
my $r006 = $t->get("分页团队", "/api/teams?pageNum=1&pageSize=10");

