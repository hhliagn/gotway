package com.gotway.gotway.pojo.vo;

import com.gotway.gotway.pojo.CommentsLog;

import java.util.ArrayList;
import java.util.List;

public class CommentsLogVo extends CommentsLog {

    private Integer commenterId;//评论者id
    private String commenterHeadImg;//评论者头像
    private String commenterName;//评论者nickname
    private String commenterEmail;
    private Integer stickerId;//发帖者id
    private String stickerName;//发帖者nickname
    private String stickerHeadImg;//发帖者头像
    private String stickerEmail;

    private List<CommentsLogVo> replies = new ArrayList<CommentsLogVo>();//回复

    public List<CommentsLogVo> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentsLogVo> replies) {
        this.replies = replies;
    }

    public String getCommenterHeadImg() {
        return commenterHeadImg;
    }

    public void setCommenterHeadImg(String commenterHeadImg) {
        this.commenterHeadImg = commenterHeadImg;
    }

    public String getStickerHeadImg() {
        return stickerHeadImg;
    }

    public void setStickerHeadImg(String stickerHeadImg) {
        this.stickerHeadImg = stickerHeadImg;
    }

    public String getCommenterEmail() {
        return commenterEmail;
    }

    public void setCommenterEmail(String commenterEmail) {
        this.commenterEmail = commenterEmail;
    }

    public String getStickerEmail() {
        return stickerEmail;
    }

    public void setStickerEmail(String stickerEmail) {
        this.stickerEmail = stickerEmail;
    }

    public Integer getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(Integer commenterId) {
        this.commenterId = commenterId;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public Integer getStickerId() {
        return stickerId;
    }

    public void setStickerId(Integer stickerId) {
        this.stickerId = stickerId;
    }

    public String getStickerName() {
        return stickerName;
    }

    public void setStickerName(String stickerName) {
        this.stickerName = stickerName;
    }
}
