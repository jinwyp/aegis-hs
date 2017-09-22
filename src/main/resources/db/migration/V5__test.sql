use hsdb;


-- 造数据
-- 创建用户
insert into hs_user(id ,deptId,phone,password,passwordSalt,createDate,createBy,isAdmin,isActive)
	values(1002,2,'18321805757','1234567','12345','2017-01-01','ADMIN','1','1'),
	      (1003,2,'18321806666','1234567','12345','2017-01-01','ADMIN','1','1');
-- 创建部门




  -- 应收月核算配置
insert into  hs_ying_order_config(
	id,
	orderId ,           
    hsMonth ,             
    maxPrepayRate ,
    unInvoicedRate ,
    contractBaseInterest ,
    expectHKDays ,             
    tradeAddPrice  , 
    wPrice         
  )
values(2000,3001,'201701',20.3,10.22,11.11,10,50.21,100.1),
      (2002,3001,'201702',20.3,10.22,11.11,10,50.21,100.1),
      (2003,3001,'201703',20.3,10.22,11.11,10,50.21,100.1),
      (2004,3001,'201704',20.3,10.22,11.11,10,50.21,100.1),
      (2005,3001,'201709',20.3,10.22,11.11,10,50.21,100.1);

      -- 应收业务
insert into hs_ying_order(
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
   status 
  ) 
  values(3001,1001,2,1,1,1,'业务线','COAL',11,'ONE_PAPER_SETTLE',10,'ONE_PAPER_SETTLE','UNCOMPLETED');
  -- 参与方
  insert into hs_ying_order_party(
  	  id,
  	  orderId ,                 
      custType ,
      customerId 
    )
  values(4001,3001,'UPSTREAM',6),
        (4002,3001,'DOWNSTREAM',5),
        (4003,3001,'TRAFFICKCER',4),
        (4004,3001,'FUNDER',3),
        (4004,3001,'FUNDER',8),
        (4005,3001,'ACCOUNTING_COMPANY',1)；


-- 应收订单 - 发运 

insert into hs_ying_fayun(
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
)values
    (5001,3001,2000,'20170408',10000010.89,'ARRIVE','SHIP',0,'','L12345','MOTOR',12,'',''),
    (5002,3001,2100,'20170508',10000010.89,'ARRIVE','SHIP',0,'','L12345','MOTOR',12,'',''),
     (5003,3001,2200,'20170608',10000010.89,'ARRIVE','SHIP',0,'','L12345','MOTOR',12,'',''),
     (5004,3001,2300,'20170708',10000010.89,'ARRIVE','SHIP',0,'','L12345','MOTOR',12,'',''), 
     (5005,3001,200,'20170218',10000010.89,'ARRIVE','SHIP',0,'','L12345','MOTOR',12,'','');

-- 应收订单 - 付款:(主账务公司付给上游)
insert into hs_ying_fukuan(
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
)values
    (6001,3001,2000,'20170603',6,'PAYMENT_FOR_GOODS',1000000.10,'ELEC_REMITTANCE',3,0,0),
    (6002,3001,2000,'20170703',6,'PAYMENT_FOR_GOODS',1000000.10,'ELEC_REMITTANCE',8,7.6,60),
    (6003,3001,2000,'20170803',6,'PAYMENT_FOR_GOODS',1000000.10,'ELEC_REMITTANCE',3,0,0),
    (6004,3001,2000,'20170903',6,'PAYMENT_FOR_GOODS',1000000.10,'ELEC_REMITTANCE',8,8.4,30);













