package com.epam.polygor.webstore.model;

import java.sql.Timestamp;
import java.util.Arrays;

public class Image extends BaseEntity {
    public static final int STANDARD_WIDTH = 120;
    public static final int STANDARD_HEIGHT = 100;
    private String name;
    private String contentType;
    private byte[] content;
    private Timestamp lastModified;

    public Image() {
        lastModified = new Timestamp(System.currentTimeMillis());
    }

    public Image(String name, String contentType, byte[] content) {
        this();
        this.name = name;
        this.contentType = contentType;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public Image setName(String name) {
        this.name = name; return this;
    }

    public String getContentType() {
        return contentType;
    }

    public Image setContentType(String contentType) {
        this.contentType = contentType; return this;
    }

    public byte[] getContent() {
        return content;
    }

    public Image setContent(byte[] content) {
        this.content = content; return this;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public Image setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified; return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Image image = (Image) o;

        if (!Arrays.equals(content, image.content)) return false;
        if (contentType != null ? !contentType.equals(image.contentType) : image.contentType != null) return false;
        if (name != null ? !name.equals(image.name) : image.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
        result = 31 * result + (content != null ? Arrays.hashCode(content) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Image{" +
                "name='" + name + '\'' +
                ", contentType='" + contentType + '\'' +
                ", content=" + Arrays.toString(content) +
                "} " + super.toString();
    }
}
