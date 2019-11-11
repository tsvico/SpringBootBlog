package com.tsvico.blog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/13 12:21
 * 功能 博客实体类 从对象出发来建模，自定生成对应数据库
 *  //对应数据库直接生成的能力 JPA中注解
 *  //更改默认数据库生成的名字
 */
@Entity
@Table(name = "t_blog")
public class Blog {
    /**声明主键*/
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @Basic(fetch = FetchType.LAZY) //   懒加载
    /**
     * 声明长类型，因为默认给的是varchar(255)
     */
    @Lob
    private String content;
    /**
     *  声明长类型，因为默认给的是varchar(255)
     */
    @Lob
    private String firstPicture;
    /**转载、原创*/
    private String flag;
    /**浏览次数*/
    private Integer views;
    /**赞赏*/
    private boolean appreciation;
    /**转载开启*/
    private boolean shareStatment;
    private boolean commentable; //评论开启
    private boolean published; //是否发布 //1 发布 0 保存
    private boolean recommend;//是否推荐
    @Temporal(TemporalType.TIMESTAMP)  //指定数据库存储时间类型
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)  //指定数据库存储时间类型
    private Date updateTime;

    @ManyToOne  //关系维护端，多对一，维护与type之间的关系
    private Type type;

    @ManyToMany(cascade = {CascadeType.PERSIST}) //与标签是多对多关系    负责维护关系  添加级联新增(这边增加tag类中也增加)
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne  //多个Blog对应一个blog
    private User user;

    @OneToMany(mappedBy = "blog") //此时是被维护端
    private List<Comment> comments = new ArrayList<>();

    @Transient  //不和数据库绑定
    private String tagIds;

    public Blog() {
        //必须有构造
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatment() {
        return shareStatment;
    }

    public void setShareStatment(boolean shareStatment) {
        this.shareStatment = shareStatment;
    }

    public boolean isCommentable() {
        return commentable;
    }

    public void setCommentable(boolean commentable) {
        this.commentable = commentable;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public void init(){
        this.tagIds = tagsToIds(this.getTags());
    }

    private String tagsToIds(List<Tag> tags){
        if (!tags.isEmpty()){
            StringBuilder ids = new StringBuilder();
            boolean flag = false;
            for (Tag tag : tags){
                if (flag){
                    ids.append(",");
                }else{
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else {
            return tagIds;
        }
    }
    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatment=" + shareStatment +
                ", commentable=" + commentable +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
