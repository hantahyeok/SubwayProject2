package com.hdk.subway;

public class MySingleton {
    private static MySingleton instance;
    private String data;

    private MySingleton() {
        // private 생성자로 외부에서 인스턴스 생성을 막음
    }

    public static MySingleton getInstance() {
        if (instance == null) {
            instance = new MySingleton();
        }
        return instance;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
