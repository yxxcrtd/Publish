package com.chinaedustar.publish.model;

import java.util.Date;

/**
 * 评论对象。
 * 
 */
public class Comment extends AbstractModelBase {
     // Fields    
     /** 所属项目对应模块的标识。 */
     private int itemId;
     
     /** 用户类型。>0：会员，且表示其会员的标识；0：游客。 */
     private int userType;
     
     /** 用户名。 */
     private String userName;
     
     /** 性别。0：女；1：男。*/
     private int sex;
     
     /** QQ号码。 */
     private String qq;
     
     /** MSN号码。 */
     private String msn;
     
     /**  电子邮件地址。 */
     private String email;
     
     /** 主页地址。 */
     private String homepage;
     
     /** IP地址。 */
     private String ip;
     
     /** 评论发表的时间。 */
     private Date writeTime;
     
     /** 对于项目的评分等级。分为1-5五个级别。 */
     private int score;
     
     /** 评论内容。 */
     private String content;
     
     /** 回复评论的管理员用户名。 */
     private String replyName;
     
     /** 管理员回复评论的内容。 */
     private String replyContent;
     
     /** 管理员回复评论的时间。 */
     private Date replyTime;
     
     /** 是否验证通过。*/ 
     private boolean passed;


    // Constructors

    /** 默认构造函数 */
    public Comment() {
    }
   
    // Property accessors
    /** 所属项目的标识。 */
    public Integer getItemId() {
        return this.itemId;
    }
    
    /** 所属项目的标识。 */
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
    
    /** 用户类型。1：会员；0：游客。 */
    public Integer getUserType() {
        return this.userType;
    }
    
    /** 用户类型。1：会员；0：游客。 */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }
    
    /** 用户类型。>0：会员，且表示其会员的标识；0：游客。 */
    public String getUserName() {
        return this.userName;
    }
    
    /** 用户类型。>0：会员，且表示其会员的标识；0：游客。 */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    /** 性别。0：女；1：男。*/
    public int getSex() {
        return this.sex;
    }
    
    /** 性别。0：女；1：男。*/
    public void setSex(int sex) {
        this.sex = sex;
    }
    
    /** QQ号码。 */
    public String getQq() {
        return this.qq;
    }
    
    /** QQ号码。 */
    public void setQq(String qq) {
        this.qq = qq;
    }
    
    /** MSN号码。 */
    public String getMsn() {
        return this.msn;
    }
    
    /** MSN号码。 */
    public void setMsn(String msn) {
        this.msn = msn;
    }
    
    /**  电子邮件地址。 */
    public String getEmail() {
        return this.email;
    }
    
    /**  电子邮件地址。 */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /** 主页地址。 */
    public String getHomepage() {
        return this.homepage;
    }
    
    /*** 主页地址。 */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
    
    /** IP地址。 */
    public String getIp() {
        return this.ip;
    }
    
    /** IP地址。 */
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    /** 评论发表的时间。 */
    public Date getWriteTime() {
        return this.writeTime;
    }
    
    /** 评论发表的时间。 */
    public void setWriteTime(Date writeTime) {
        this.writeTime = writeTime;
    }
    
    /** 对于项目的评分等级。分为1-5五个级别。 */
    public int getScore() {
        return this.score;
    }
    
    /** 对于项目的评分等级。分为1-5五个级别。 */
    public void setScore(int score) {
        this.score = score;
    }
    
    /** 评论内容。 */
    public String getContent() {
        return this.content;
    }
    
    /** 评论内容。 */
    public void setContent(String content) {
        this.content = content;
    }
    
    /** 回复评论的管理员用户名。 */
    public String getReplyName() {
        return this.replyName;
    }
    
    /** 回复评论的管理员用户名。 */    
    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }
    
    /** 管理员回复评论的内容。 */
    public String getReplyContent() {
        return this.replyContent;
    }
    
    /** 管理员回复评论的内容。 */
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
    
    /** 管理员回复评论的时间。 */
    public Date getReplyTime() {
        return this.replyTime;
    }
    
    /** 管理员回复评论的时间。 */
    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }
    
    /** 是否验证通过。*/
    public boolean getPassed() {
        return this.passed;
    }
    
    /** 是否验证通过。*/
    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
