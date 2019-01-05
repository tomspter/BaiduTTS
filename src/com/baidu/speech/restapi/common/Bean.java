package com.baidu.speech.restapi.common;

public class Bean {
    private int id;

    private String name;

    private String name_mp3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_mp3() {
        return name_mp3;
    }

    public void setName_mp3(String name_mp3) {
        this.name_mp3 = name_mp3;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", name_mp3='" + name_mp3 + '\'' +
                '}';
    }

    public Bean(int id, String name, String name_mp3) {
        this.id = id;
        this.name = name;
        this.name_mp3 = name_mp3;
    }

    public Bean() {
    }
}
