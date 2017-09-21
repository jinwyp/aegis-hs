package AutoTest;

use strict;
use warnings FATAL => 'all';
use Mojo::JSON qw/encode_json decode_json/;
# use utf8 ':all';
# use Encode;
use Data::Dumper;
use Getopt::Long;

use constant debug => 0;

# binmode STDERR, ':utf8';
# binmode STDOUT, ':utf8';

sub new {
  my $pkg = shift;
  my $r = { @_ };
  unless ($r->{host}) {
    die "must provide host";
  }
  unless ($r->{name}) {
    die "cookie name provide host";
  }
  bless $r, $pkg;
}

# 发送post
# 如果成功返回data部分, 如果错误, 打印error并退出
# $agent->pos($name, $url, { jwt => "jwt", data => json })
sub pos {
  my $self = shift;
  my $name = shift;
  my $url = shift;
  my $args = shift;

  my $json = encode_json($args->{data});
  warn "send json[$json]";

  my $cookie_option = "";
  if (exists $self->{c} && $self->{c}) {
    $cookie_option = "--cookie \"$self->{c}\"";
  }

  my $cmd;
  if ($args->{jwt}) {
    $cmd = "curl -s -H \"Authorization: Bearer $self->{jwt}\"  $cookie_option -XPOST -H \"Content-Type: application/json\" \"http://$self->{host}$url\" -d \'$json\'";
  } else {
    $cmd = "curl -s $cookie_option -XPOST -H \"Content-Type: application/json\" \"http://$self->{host}$url\" -d \'$json\'";
  }
  my $response = `$cmd`;
  if ($? != 0) {
    warn sprintf("%s - 系统失败[$cmd]\n", $name);
    exit 0;
  } else {
    warn "response = $response" if debug;
    my $res = decode_json($response);
    if ($res->{success}) {
      warn sprintf("%s - 成功\n", $name);
      return $res->{data};
    } else {
      warn sprintf("%s - 失败[%s]\n", $name, $res->{error}->{message});
      exit 0;
    }
  }
}

# 发送get
# 如果成功返回data部分, 如果错误, 打印error并退出
# $agent->get($name, $url, {jwt => kkk)
sub get {
  my $self = shift;
  my $name = shift;
  my $url = shift;
  my $args = shift;

  my $cookie_option = "";
  if (exists $self->{c} && $self->{c}) {
    $cookie_option = "--cookie \"$self->{c}\"";
  }

  my $cmd;
  if ($args->{jwt}) {
    $cmd = "curl -s -H \"Authorization: Bearer $self->{jwt}\" --cookie-jar target/cookie  $cookie_option -XGET \"http://$self->{host}$url\"";
  } else {
    $cmd = "curl -s --cookie-jar target/cookie $cookie_option -XGET \"http://$self->{host}$url\"";
  }

  my $response = `$cmd`;

  #    if ( $url =~ /captcha\/find/) {
  #       warn "captch find response = $response";
  #    }

  if ($? != 0) {
    die sprintf("%s - 系统失败\n", $name);
  } else {
    # 如果是触发生成图形验证码, 则不返回
    if ($name =~ "触发服务器生成图形验证码") {
      warn sprintf("%s - 成功\n", $name);
      return;
    }
    if ($url =~ /\/web\//) {
      warn sprintf("%s - 成功\n", $name);
      return;
    }

    my $res;
    eval {
      $res = decode_json($response);
    };
    if ($@) {
      die sprintf("%s - 失败, response=[s%]\n", $name, $response);
    }

    if ($res->{success}) {
      warn sprintf("%s - 成功\n", $name);
      return $res->{data};
    } else {
      die sprintf("%s - 失败[%s]\n", $name, $res->{error}->{message});
    }

  }
}

# 清空系统
sub clear {
  my $self = shift;
  # 1. 清空所有数据库
  warn "-----------------------------------------------\n";
  warn "清空系统";
  warn "-----------------------------------------------\n";
  my $cmd = <<EOF;
echo "db.user.drop(); db.company.drop(); db.userinfo.drop();" |  mongo
EOF
  system($cmd);
}

## 注册所有用户
#sub register {
#    my $self = shift;
#    my ($phone, $userName, $loginName, $password) = @_;
#    my $system = $self->{system};
#    warn "$system 注册...\n";
#    my $u000 = $self->get("0. 打开注册页面", "/web/$system/register");
#    $self->cookie(); # 设置cookie
#    my $u001 = $self->get("1. 触发服务器生成图形验证码", "/api/$system/captcha/get");
#    my $u002 = $self->get("2. 获取图形验证码", "/api/$system/captcha/find");
#    my $u003 = $self->get("3. 触发服务器发送短信验证码", "/api/$system/register/sms?mobilePhone=$phone&imgCode=$u002");
#    my $u004 = $self->get("4. 获取短信验证码", "/api/$system/register/findSms?authCodeType=REGISTER");
#    my $u005 = $self->pos("5. 发起注册", "/api/$system/register/register", {
#            data => {
#                "userName"  => $userName,
#                "loginName" => $loginName,
#                "password"  => $password,
#                "phone"     => $phone,
#                "smsCode"   => $u004,   # 短信验证码
#            }
#        });
#}

sub login {
  my $self = shift;
  my ($username, $password) = @_;
  warn "-----------------------------------------------\n";
  warn "登录...\n";
  warn "-----------------------------------------------\n";
  my $u000 = $self->get("0. 打开登录页面", "/web/login");
  $self->cookie(); # 设置cookie
  my $u001 = $self->pos("1. 登录", "/api/login", {
      jwt  => undef, # 不需要jwt
      data => {
        username => $username,
        password => $password,
      }
    });
  $self->jwt($u001); # 保存jwt到agent中
}


sub dump {
  my $self = shift;
  print Dumper(@_);
}

# 从保存的cookie中读取cookie
sub cookie {
  my $self = shift;
  my $cookie = `cat target/cookie | grep $self->{name} | awk '{ print \$7;}'`;
  chomp $cookie;
  $self->{c} = "$self->{name}=$cookie";
}

sub jwt {
  my $self = shift;
  my $jwt = shift;
  $self->{jwt} = $jwt;
}

1;

__END__
