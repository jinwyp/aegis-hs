use hsdb;


drop view  IF EXISTS  v_1019;
create view v_1019 as
select
base.orderId,
base.hsId,
map.amount as mapAmount,
fukuan.payDate,
huikuan.huikuanDate,
fukuan.hsId as fukuanHsId,
case when DATEDIFF(huikuan.huikuanDate,fukuan.payDate) >0 then DATEDIFF(huikuan.huikuanDate,fukuan.payDate)-IFNULL(seller.discountDays,0.00)else 0 end as time,

CASE WHEN IFNULL(case when DATEDIFF(huikuan.huikuanDate,fukuan.payDate) >0 then DATEDIFF(huikuan.huikuanDate,fukuan.payDate)-IFNULL(seller.discountDays,0.00)else 0 end,0.00)>0
          AND IFNULL(case when DATEDIFF(huikuan.huikuanDate,fukuan.payDate) >0 then DATEDIFF(huikuan.huikuanDate,fukuan.payDate)-IFNULL(seller.discountDays,0.00)else 0 end,0.00)<60
THEN IFNULL(map.amount,0.00)*IFNULL(case when DATEDIFF(huikuan.huikuanDate,fukuan.payDate) >0 then DATEDIFF(huikuan.huikuanDate,fukuan.payDate)-IFNULL(seller.discountDays,0.00)else 0 end,0.00)
     *IFNULL(v_1018.actualUtilizationRate,0.00)/360

WHEN IFNULL(case when DATEDIFF(huikuan.huikuanDate,fukuan.payDate) >0 then DATEDIFF(huikuan.huikuanDate,fukuan.payDate)-IFNULL(seller.discountDays,0.00)else 0 end,0.00)>=60
     AND IFNULL(case when DATEDIFF(huikuan.huikuanDate,fukuan.payDate) >0 then DATEDIFF(huikuan.huikuanDate,fukuan.payDate)-IFNULL(seller.discountDays,0.00)else 0 end,0.00)<90
THEN IFNULL(map.amount,0.00)*((case when DATEDIFF(huikuan.huikuanDate,fukuan.payDate) >0 then DATEDIFF(huikuan.huikuanDate,fukuan.payDate)-IFNULL(seller.discountDays,0.00)else 0 end-60.00)*
                                                                                                                                                                          (v_1018.actualUtilizationRate+0.05)+60*v_1018.actualUtilizationRate)/360
WHEN IFNULL(case when DATEDIFF(huikuan.huikuanDate,fukuan.payDate) >0 then DATEDIFF(huikuan.huikuanDate,fukuan.payDate)-IFNULL(seller.discountDays,0.00)else 0 end,0.00)>=90
THEN IFNULL(map.amount,0.00)*
                         ((case when DATEDIFF(huikuan.huikuanDate,fukuan.payDate) >0 then DATEDIFF(huikuan.huikuanDate,fukuan.payDate)-IFNULL(seller.discountDays,0.00)else 0 end-90)
                          *(v_1018.actualUtilizationRate+0.1)+30*(v_1018.actualUtilizationRate+0.05)+60*v_1018.actualUtilizationRate)/360
ELSE 0 END as rate

from base 
     left join hs_same_huikuan_map map on base.orderId=map.orderId and  map.deleted=0
     left join hs_same_huikuan huikuan on huikuan.id=map.huikuanId  and huikuan.hsId=base.hsId and base.orderId=huikuan.orderId
     left join hs_same_fukuan fukuan on fukuan.id=map.fukuanId  and fukuan.hsId=base.hsId and base.orderId=fukuan.orderId and fukuan.deleted=0
     left join v_1018 on v_1018.hsId=huikuan.hsId  and   v_1018.orderId=base.orderId
     left join hs_same_settle_seller seller on fukuan.hsId=seller.hsId  and  seller.deleted=0;
