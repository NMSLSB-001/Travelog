package com.example.travelog.ui.DiscoverFragment.View;

public class RatingComment {
    private String RCId;
    private String articleId;
    private float rating;
    private String comment;
    private String userName;

    public RatingComment() {

    }
    public RatingComment(String RCId, String articleId, float rating, String comment, String userName) {

        this.RCId = RCId;
        this.articleId = articleId;
        this.rating = rating;
        this.comment = comment;
        this.userName = userName;
    }

    public float getRating() {
        return rating;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getComment() {
        return comment;
    }

    public String getRCId() {
        return RCId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setRCId(String RCId) {
        this.RCId = RCId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
