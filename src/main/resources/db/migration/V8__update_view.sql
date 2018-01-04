use hsdb;

drop view  IF EXISTS  v_3013;
create View  v_3013(orderId,hsId,totalInstorageRemainPrice) as
select
base.orderId,
base.hsId,
ROUND(IFNULL(v_3012.totalInstorageRemainNum,0.00)*IFNULL(v_3001.instorageUnitPrice,0.00) /1.17
      +case when IFNULL(v_3001.totalInstorageNum,0.00) !=0
       then IFNULL(v_3012.totalInstorageRemainNum,0.00)*v_3014.withoutTaxFee/IFNULL(v_3001.totalInstorageNum,0.00)
       else 0.00
       end,2)
as totalInstorageRemainPrice
from base
     left join v_3012 on base.hsId=v_3012.hsId
     left join v_3001 on base.hsId=v_3001.hsId
     left join v_3014 on base.hsId=v_3014.hsId;
