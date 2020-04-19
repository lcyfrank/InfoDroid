package com.ck19.infodroid;

public class InformationInput {

    private volatile static InformationInput input;
    private InformationInput() {};

    public static InformationInput instance() {
        if (input == null) {
            synchronized (InformationInput.class) {
                if (input == null) {
                    input = new InformationInput();
                }
            }
        }
        return input;
    }

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int length() {
        if (this.content != null) {
            return this.content.length();
        }
        return 0;
    }

    public void append(String c) {
        if (this.content == null) this.content = "";
        this.content += c;
    }

    public void delete() {
        if (this.content == null) this.content = "";
        if (this.content.length() > 1) this.content = this.content.substring(0, this.content.length() - 1);
    }
}
