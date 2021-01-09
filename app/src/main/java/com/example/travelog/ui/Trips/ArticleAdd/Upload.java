package com.example.travelog.ui.Trips.ArticleAdd;

public class Upload {
    private String imageId;
    private String imageName;
    private String imageUrl;
    public Upload() {
        //empty constructor needed
    }
    public Upload(String imageId, String imageName, String imageUrl) {
        this.imageId = imageId;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }
    public String getName() {
        return imageId;
    }
    public void setName(String imageId) {
        this.imageId = imageId;
    }
    public String getImageName() {
        return imageName;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
