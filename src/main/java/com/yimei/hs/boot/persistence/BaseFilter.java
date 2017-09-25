package com.yimei.hs.boot.persistence;

import lombok.Data;

import java.util.List;

/**
 * Created by xiangyang on 16/8/30.
 */
@Data
public class BaseFilter<T> {
    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private Integer totalRecord;

    private Integer totalPage;

    private List<T> results;

    public String getCountSql(String sql) {
        return "select count(1) from (" + sql + ") jm_t";
    }


    /**
     * todo: 陆彪
     * 将sql中的 left join 到 where部分的东西删除  select asdfasf  left join  asdfasfadfa      where jsfasdfa
     * @param sql
     * @return
     */
    public String getCountSqlForResultMap(String sql) {

        int begin = sql.indexOf("LEFT JOIN");
        int end = sql.lastIndexOf("WHERE");
        if (end == -1) {
            end = sql.length();
        }
        //  System.out.println("end = " + end);

        String frag1 = sql.substring(0, begin);
        String frag2 = sql.substring(end);

        return "select count(1) (" + frag1 + frag2 + ") jm_t";
    }


    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo <= 0 ? 1 : pageNo;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageNo <= 0 ? 10 : pageSize;
    }


    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
        int totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : totalRecord / pageSize + 1;
        if(totalPage>0) {
            this.pageNo = this.getPageNo() > totalPage ? totalPage : this.getPageNo();
        }
        this.setTotalPage(totalPage);
    }

}
