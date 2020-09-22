package com.project.shoprate.queryParams;

import javax.ws.rs.QueryParam;
import java.util.List;

public class ProductQueryParams {

    @QueryParam("ids")
    private List <Long> ids;

    @QueryParam("name")
    private String name;

    public List<Long> getIds() { return ids; }

    public void setIds(List<Long> ids) { this.ids = ids; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
