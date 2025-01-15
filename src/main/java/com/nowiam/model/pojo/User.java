package com.nowiam.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("user")
public class User {
    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer userId;
    @TableField("user_name")
    private String userName;
    @TableField("user_password")
    private String userPassword;
    @TableField("user_avatar")
    private String userAvatar;
    @TableField("user_used")
    private long userUsed;
    @TableField("user_space")
    private long userSpace;
    @TableField("user_count")
    private int userCount;
    @TableField("user_max_count")
    private int userMaxCount;

    //

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", userUsed=" + userUsed +
                ", userSpace=" + userSpace +
                ", userCount=" + userCount +
                ", userMaxCount=" + userMaxCount +
                '}';
    }


    //get&set

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public long getUserUsed() {
        return userUsed;
    }

    public void setUserUsed(long userUsed) {
        this.userUsed = userUsed;
    }

    public long getUserSpace() {
        return userSpace;
    }

    public void setUserSpace(long userSpace) {
        this.userSpace = userSpace;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getUserMaxCount() {
        return userMaxCount;
    }

    public void setUserMaxCount(int userMaxCount) {
        this.userMaxCount = userMaxCount;
    }
}
