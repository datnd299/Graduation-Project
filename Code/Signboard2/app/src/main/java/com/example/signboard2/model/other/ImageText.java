package com.example.signboard2.model.other;

public class ImageText {
    private String src;
    private String text;
    public ImageText() {

    }
    public ImageText(String src, String text) {
        this.src = src;
        this.text = text;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
