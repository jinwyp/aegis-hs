package com.yimei.hs.boot.persistence;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangyang on 16/8/25.
 * 说明: Page类型不要当做mapper方法参数传递,因为当前项目客户化了ObjectWrapper的行为.
 * 会造成mybatis取值错误.可以阅读MetaObject的构造函数
 */
@Data
public class Page<T> {

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private Integer totalRecord;

    private Integer totalPage;

    private List<T> results;

//     private Map<String, Object> params = new HashMap<String, Object>();


    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
        int totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : totalRecord / pageSize + 1;
        this.setTotalPage(totalPage);
    }

    private Page() {
    }

    public static Page createInstance() {
        return new Page();
    }

    public static String getQueryString(HttpServletRequest request) {
        Enumeration em = request.getParameterNames();
        StringBuffer queryString = new StringBuffer();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = request.getParameter(name);
            if (name.equalsIgnoreCase("pageNo") || name.equalsIgnoreCase("pageSize")) {
                continue;
            }
            queryString.append(name).append("=").append(value).append("&");
        }
        if (!StringUtils.isBlank(queryString)) {
            return  "?"+queryString.substring(0, queryString.length() - 1);
        }
        return StringUtils.EMPTY;

    }


}
