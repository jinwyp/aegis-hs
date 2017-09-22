use hsdb;

-- 部门
insert into hs_dept(name) values
  ('管理部'),
  ('金融产品事业部');

-- 团队
insert into hs_team(name, deptId) values
  ('赵善文团队', 2),
  ('张培栓团队', 2),
  ('魏靖团队',2),
  ('卢昆团队', 2),
  ('赵悝团队', 2),
  ('余东升团队', 2),
  ('孔光明团队', 2),
  ('张超超团队', 2),
  ('宁夏自营分公司', 2),
  ('钢材金融分公司',2),
  ('赵孟晓团队', 2),
  ('陈璐团队', 2),
  ('杨邓团队', 2),
  ('田雪冬团队', 2),
  ('冷链团队', 2);

-- 用户
# insert into hs_user(deptId, phone, password, passwordSalt, createDate, createBy, isAdmin) values
#  (1, '13022117050', '12345678', 'salt', '2017-01-01', 'hary', 2);


-- 参与方
insert into hs_party(partyType, name, shortName) values
  (1, '江苏晋和电力燃料有限公司', '晋和'),
  (1, '那曲瑞昌煤炭运销有限公司','那曲'),
  (1, '郑州嘉瑞供应链管理有限公司', '嘉瑞'),
  (1, '山西瑞茂通供应链有限公司', '山瑞'),
  (1, '宁夏瑞茂通供应链管理有限公司', '宁夏瑞茂通'),
  (1, '宁夏华运昌煤炭运销有限公司', '华运昌'),
  (1, '宁夏腾瑞达电力燃料有限公司', '腾瑞达'),
  (1, '陕西秦瑞丰煤炭运销有限公司', '秦瑞丰'),
  (1, '陕西吕通贸易有限公司', '吕通'),
  (1, '新疆瑞茂通煤炭供应链管理有限公司', '新疆瑞茂通'),
  (1, '浙江瑞茂通供应链管理有限公司', '浙江瑞茂通'),
  (1, '浙江和辉电力燃料有限公司', '和辉'),
  (1, '宣威瑞茂通商贸有限公司', '宣威瑞茂通'),
  (1, '江西瑞茂通供应链管理有限公司', '江西瑞茂通'),
  (1, '上海瑞易供应链管理有限公司', '上海瑞易'),
  (1, '深圳前海瑞茂通供应链平台服务有限公司', '前海瑞茂通'),
  (2, '中瑞财富', ''),
  (2, '中平金融', '');