package com.haven.pojo;

import java.io.Serializable;

public class Address implements Serializable {
    private Integer id;
    private String address;
    private String lng;//经度
    private String lat;//纬度

    public Address() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Address(Integer id, String address, String lng, String lat) {
        this.id = id;
        this.address = address;
        this.lng = lng;
        this.lat = lat;
    }
}
