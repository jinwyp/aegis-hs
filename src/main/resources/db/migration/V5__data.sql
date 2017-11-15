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
insert into hs_user(deptId, phone, password, passwordSalt, createDate, createBy, isAdmin) values
 (1, '13022117050', 'c9ca4cc1ce82a2c7c21feb41df67ebdd12a471f5', 'd379e428ee88629f', '2017-01-01', 'hary', 1);
insert into hs_user(deptId, phone, password, passwordSalt, createDate, createBy, isAdmin) values
                                                                                          (2, '18321805753', 'c9ca4cc1ce82a2c7c21feb41df67ebdd12a471f5', 'd379e428ee88629f', '2017-01-01', 'hary', 0);

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
  (2, '北京领先创融网络科技有限公司', '中瑞财富'),
  (2, '中平金融', '中平金融'),
  (3, '江西蒙晋能源贸易有限公司', '江西蒙晋'),
  (3, '江苏华电物流有限公司', '江苏华电'),
  (3, '创硕', '创硕'),
  (3, '泰州立翔电力燃料有限公司', '泰州立翔'),
  (3, '郴州博太超细石墨股份有限公司', '郴州博太'),
  (3, '湖南华润电力鲤鱼江有限公司', '华润鲤鱼江'),
  (3, '资兴和顺燃料有限公司', '资兴和顺'),
  (3, '漯河泰润', '漯河泰润'),
  (3, '广州鑫丰润能源科技有限公司', '广州鑫丰润'),
  (3, '宁夏福星石油化工销售有限公司', '宁夏福星'),
  (3, '广州莲荷能源科技有限责任公司', '广州莲荷'),
  (3, '林州市林通煤炭贸易有限公司', '林州林通'),
  (3, '国家电投宁夏能源铝业有限公司临河发电分公司', '国电投临河');
