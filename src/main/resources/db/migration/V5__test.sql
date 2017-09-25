USE hsdb;


-- 造数据
-- 创建用户
INSERT INTO hs_user(id ,deptId,phone,password,passwordSalt,createDate,createBy,isAdmin,isActive)
	VALUES(1,2,'18321805757','1234567','12345','2017-01-01','ADMIN','1','1'),
	      (2,2,'18321806666','1234567','12345','2017-01-01','ADMIN','1','1');
-- 创建部门




  -- 应收月核算配置
INSERT INTO  hs_ying_order_config(
	id,
	orderId ,           
    hsMonth ,             
    maxPrepayRate ,
    unInvoicedRate ,
    contractBaseInterest ,
    expectHKDays ,             
    tradeAddPrice  , 
    weightedPrice
  )
VALUES(2000,3001,'201701',20.3,10.22,11.11,10,50.21,100.1),
      (2001,3001,'201702',20.3,10.22,11.11,10,50.21,100.1),
      (2002,3001,'201703',20.3,10.22,11.11,10,50.21,100.1),
      (2003,3001,'201704',20.3,10.22,11.11,10,50.21,100.1),
      (2004,3001,'201709',20.3,10.22,11.11,10,50.21,100.1);

      -- 应收业务
INSERT INTO hs_ying_order(
	id,
   deptId  ,
   teamId  ,
   creatorId ,
   ownerId ,
   mainAccounting ,
   line ,
   cargoType ,
   upstreamId ,
   upstreamSettleMode ,
   downstreamId  ,
   downstreamSettleMode ,
   STATUS
  ) 
  VALUES(3001,1001,2,1,1,1,'业务线','COAL',11,'ONE_PAPER_SETTLE',10,'ONE_PAPER_SETTLE','UNCOMPLETED');
  -- 参与方
  INSERT INTO hs_ying_order_party(
  	  id,
  	  orderId ,                 
      custType ,
      customerId 
    )
  VALUES(4001,3001,'UPSTREAM',6),
        (4002,3001,'DOWNSTREAM',5),
        (4003,3001,'TRAFFICKCER',4),
        (4004,3001,'FUNDER',3),
        (4005,3001,'FUNDER',8),
        (4006,3001,'ACCOUNTING_COMPANY',1);


-- 应收订单 - 发运 

INSERT INTO hs_ying_fayun(
     id ,         
    orderId ,     
    hsId ,        
   fyDate ,        
   fyAmount  ,
   arriveStatus ,
                           
   upstreamTrafficMode ,
    upstreamCars ,        
    upstreamJHH , 
    upstreamShip ,                       
    downstreamTrafficMode ,
    downstreamCars ,      
    downstreamJHH ,
    downstreamShip
)VALUES
    (5001,3001,2000,'20170408',10000010.89,'ARRIVE','SHIP',0,'','L12345','MOTOR',12,'',''),
    (5002,3001,2001,'20170508',10000010.89,'ARRIVE','SHIP',0,'','L12345','MOTOR',12,'',''),
     (5003,3001,2002,'20170608',10000010.89,'ARRIVE','SHIP',0,'','L12345','MOTOR',12,'',''),
     (5004,3001,2003,'20170708',10000010.89,'ARRIVE','SHIP',0,'','L12345','MOTOR',12,'',''),
     (5005,3001,2000,'20170218',10000010.89,'ARRIVE','SHIP',0,'','L12345','MOTOR',12,'','');

-- 应收订单 - 付款:(主账务公司付给上游)
INSERT INTO hs_ying_fukuan(
    id ,            
    orderId ,        
    hsId ,                             
    payDate ,         
    recieveCompanyId ,
    payUsage , 
    payAmount , 
    payMode ,
    capitalId ,     
    useInterest,
    useDays             
)VALUES
    (6001,3001,2000,'20170603',6,'PAYMENT_FOR_GOODS',800000,'ELEC_REMITTANCE',3,0,0),
    (6002,3001,2000,'20170703',6,'PAYMENT_FOR_GOODS',600000,'ELEC_REMITTANCE',8,7.6,60),
    (6003,3001,2000,'20170803',6,'PAYMENT_FOR_GOODS',300000,'ELEC_REMITTANCE',3,0,0),
    (6004,3001,2000,'20170903',6,'PAYMENT_FOR_GOODS',300000,'ELEC_REMITTANCE',8,8.4,30);





-- 回款
INSERT INTO hs_ying_huikuan(
      id ,
      orderId ,
hsId ,
huikuanCompanyId ,
huikuanDate ,
huikuanAmount ,
huikuanUsage ,
huikuanMode ,

huikuanBankPaper ,
huikuanBankPaperDate,
huikuanBankDiscount ,
huikuanBankDiscountRate ,
huikuanBankPaperExpire ,

huikuanBusinessPaper ,
huikuanBusinessPaperDate ,
huikuanBusinessDiscount ,
huikuanBusinessDiscountRate ,
huikuanBusinessPaperExpire
)VALUES
 (7001,3001,2000,5,'20170820',14000000,'PAYMENT_FOR_GOODS','ELEC_REMITTANCE',false,'20170101',false,0,'20170101',false,'20170101',false,0,'20170101'),
 (7002,3001,2000,5,'20170820',3000000,'PAYMENT_FOR_GOODS','ELEC_REMITTANCE',false,'20170101',false,0,'20170101',false,'20170101',false,0,'20170101'),
 (7003,3001,2000,5,'20170820',3000000,'PAYMENT_FOR_GOODS','ELEC_REMITTANCE',false,'20170101',false,0,'20170101',false,'20170101',false,0,'20170101');




INSERT  into hs_ying_huikuan_map(
id ,
huikuanId ,
fukuanId ,
amount
)VALUES
 (8001,7001,6001,800000),
 (8002,7001,6002,600000),
 (8003,7002,6003,300000),
 (8004,7003,6004,300000);








