package com.yimei.hs.boot.persistence;

import static org.junit.Assert.*;

/**
 * Created by hary on 2017/9/25.
 */
public class BaseFilterTest {
    @org.junit.Test
    public void getCountSqlForResultMap() throws Exception {

        BaseFilter<Integer> bf = new BaseFilter<>();

        String orginalSQL = "SELECT\n" +
                "            t1.id  t1Id,\n" +
                "            t2.id  t2Id,\n" +
                "            t1.orderId,\n" +
                "            t1.hsId,\n" +
                "            t1.invoiceDirection,\n" +
                "            t1.invoiceType,\n" +
                "            t1.openDate,\n" +
                "            t1.openCompanyId,\n" +
                "            t1.recieverId,\n" +
                "            t1.tsc,\n" +
                "            t2.invoiceId,\n" +
                "            t2.invoiceNumber,\n" +
                "            t2.cargoAmount,\n" +
                "            t2.taxRate,\n" +
                "            t2.priceAndTax,\n" +
                "            t2.tsc t2tsc\n" +
                "        FROM hs_ying_invoice t1 LEFT JOIN hs_ying_invoice_detail t2 ON t1.id = t2.invoiceId WHERE t1.id = ?";

        String getSql = bf.getCountSqlForResultMap(orginalSQL);

        System.out.println(getSql);
    }

}