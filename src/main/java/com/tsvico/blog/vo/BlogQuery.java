package com.tsvico.blog.vo;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/17 11:51
 * 功能 查询功能
 */
public class BlogQuery {

    private String title;
    private Long typeId;
    private Long tagId;
    private boolean recommend;

    public BlogQuery() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
