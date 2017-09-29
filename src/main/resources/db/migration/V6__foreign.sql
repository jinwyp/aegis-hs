use hsdb;

alter table hs_team add foreign key(deptId) references hs_dept(id);

alter table hs_ying_order add foreign key(upstreamId)     references hs_party(id);
alter table hs_ying_order add foreign key(downstreamId)   references hs_party(id);
alter table hs_ying_order add foreign key(mainAccounting) references hs_party(id);

alter table hs_ying_order add foreign key(creatorId)      references hs_user(id);
alter table hs_ying_order add foreign key(ownerId)        references hs_user(id);

alter table hs_ying_order_party add foreign key(orderId)    references hs_ying_order(id);
alter table hs_ying_order_party add foreign key(customerId) references hs_party(id);
alter table hs_ying_order_party add foreign key(orderId) references hs_ying_order(id);

alter table hs_ying_fayun add foreign key(orderId) references hs_ying_order(id);
alter table hs_ying_fayun add foreign key(hsId)    references hs_ying_order_config(id);

alter table hs_ying_fukuan add foreign key(orderId)   references hs_ying_order(id);
alter table hs_ying_fukuan add foreign key(hsId)      references hs_ying_order_config(id);
alter table hs_ying_fukuan add foreign key(capitalId) references hs_party(id);
alter table hs_ying_fukuan add foreign key(receiveCompanyId) references hs_party(id);

alter table hs_ying_huikuan add foreign key(orderId) references hs_ying_order(id);
alter table hs_ying_huikuan add foreign key(hsId) references hs_ying_order_config(id);
alter table hs_ying_huikuan add foreign key(huikuanCompanyId) references hs_party(id);

alter table hs_ying_huikuan_map add foreign key(huikuanId) references hs_ying_huikuan(id);
alter table hs_ying_huikuan_map add foreign key(fukuanId) references hs_ying_fukuan(id);

alter table hs_ying_huankuan add foreign key(orderId) references hs_ying_order(id);
alter table hs_ying_huankuan add foreign key(hsId)    references hs_ying_order_config(id);
alter table hs_ying_huankuan add foreign key(skCompanyId) references hs_party(id);

alter table hs_ying_huankuan_map add foreign key(huankuanId) references hs_ying_huankuan(id);
alter table hs_ying_huankuan_map add foreign key(fukuanId)   references hs_ying_fukuan(id);

alter table hs_ying_settle_upstream add foreign key(orderId) references hs_ying_order(id);
alter table hs_ying_settle_upstream add foreign key(hsId)    references hs_ying_order_config(id);

alter table hs_ying_settle_downstream add foreign key(orderId) references hs_ying_order(id);
alter table hs_ying_settle_downstream add foreign key(hsId)    references hs_ying_order_config(id);

alter table hs_ying_settle_traffic add foreign key(orderId) references hs_ying_order(id);
alter table hs_ying_settle_traffic add foreign key(hsId)    references hs_ying_order_config(id);
alter table hs_ying_settle_traffic add foreign key(trafficCompanyId) references hs_party(id);

alter table hs_ying_fee add foreign key(orderId) references hs_ying_order(id);
alter table hs_ying_fee add foreign key(hsId)    references hs_ying_order_config(id);

alter table hs_ying_invoice add foreign key(orderId)       references hs_ying_order(id);
alter table hs_ying_invoice add foreign key(hsId)          references hs_ying_order_config(id);
alter table hs_ying_invoice add foreign key(openCompanyId) references hs_party(id);

alter table hs_ying_invoice_detail add foreign key(invoiceId) references hs_ying_invoice(id);

alter table hs_ying_transfer add foreign key(orderId) references hs_ying_order(id);

alter table hs_ying_log add foreign key(orderId) references hs_ying_order(id);
alter table hs_ying_log add foreign key(hsId) references hs_ying_order_config(id);

alter table hs_cang_order add foreign key(upstreamId)     references hs_party(id);
alter table hs_cang_order add foreign key(downstreamId)   references hs_party(id);
alter table hs_cang_order add foreign key(mainAccounting) references hs_party(id);
alter table hs_cang_order add foreign key(creatorId)      references hs_user(id);
alter table hs_cang_order add foreign key(ownerId)        references hs_user(id);

alter table hs_cang_order_party add foreign key(orderId)    references hs_cang_order(id);
alter table hs_cang_order_party add foreign key(customerId) references hs_party(id);
alter table hs_cang_order_party add foreign key(orderId) references hs_cang_order(id);

alter table hs_cang_ruku add foreign key(orderId) references hs_cang_order(id);
alter table hs_cang_ruku add foreign key(hsId)    references hs_cang_order_config(id);
alter table hs_cang_chuku add foreign key(orderId) references hs_cang_order(id);
alter table hs_cang_chuku add foreign key(hsId)    references hs_cang_order_config(id);
alter table hs_cang_fukuan add foreign key(orderId)   references hs_cang_order(id);
alter table hs_cang_fukuan add foreign key(hsId)      references hs_cang_order_config(id);
alter table hs_cang_fukuan add foreign key(capitalId) references hs_party(id);
alter table hs_cang_fukuan add foreign key(recieveCompanyId) references hs_party(id);
alter table hs_cang_huikuan add foreign key(orderId) references hs_cang_order(id);
alter table hs_cang_huikuan add foreign key(hsId) references hs_cang_order_config(id);
alter table hs_cang_huikuan add foreign key(huikuanCompanyId) references hs_party(id);
alter table hs_cang_huikuan_map add foreign key(huikuanId) references hs_cang_huikuan(id);
alter table hs_cang_huikuan_map add foreign key(fukuanId) references hs_cang_fukuan(id);
alter table hs_cang_huankuan add foreign key(orderId) references hs_cang_order(id);
alter table hs_cang_huankuan add foreign key(hsId)    references hs_cang_order_config(id);
alter table hs_cang_huankuan add foreign key(skCompanyId) references hs_party(id);
alter table hs_cang_huankuan_map add foreign key(huankuanId) references hs_cang_huankuan(id);
alter table hs_cang_huankuan_map add foreign key(fukuanId)   references hs_cang_fukuan(id);
alter table hs_cang_settle_upstream add foreign key(orderId) references hs_cang_order(id);
alter table hs_cang_settle_upstream add foreign key(hsId)    references hs_cang_order_config(id);
alter table hs_cang_settle_downstream add foreign key(orderId) references hs_cang_order(id);
alter table hs_cang_settle_downstream add foreign key(hsId)    references hs_cang_order_config(id);
alter table hs_cang_settle_upstream_map add foreign key(settleId) references hs_cang_settle_upstream(id);
alter table hs_cang_settle_upstream_map add foreign key(rukuId) references hs_cang_ruku(id);
alter table hs_cang_settle_traffic add foreign key(orderId) references hs_cang_order(id);
alter table hs_cang_settle_traffic add foreign key(hsId)    references hs_cang_order_config(id);
alter table hs_cang_settle_traffic add foreign key(trafficCompanyId) references hs_party(id);
alter table hs_cang_fee add foreign key(orderId) references hs_cang_order(id);
alter table hs_cang_fee add foreign key(hsId)    references hs_cang_order_config(id);
alter table hs_cang_invoice add foreign key(orderId)       references hs_cang_order(id);
alter table hs_cang_invoice add foreign key(hsId)          references hs_cang_order_config(id);
alter table hs_cang_invoice add foreign key(openCompanyId) references hs_party(id);
alter table hs_cang_invoice_detail add foreign key(invoiceId) references hs_cang_invoice(id);
alter table hs_cang_transfer add foreign key(orderId) references hs_cang_order(id);
alter table hs_cang_log add foreign key(orderId) references hs_cang_order(id);
alter table hs_cang_log add foreign key(hsId) references hs_cang_order_config(id);
