package team.far.footing.model.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by luoyy on 2015/8/14 0014.
 */
public class MapBean extends BmobObject implements Serializable {

    private Userbean userbean;
    private String map_url;
    private String map_file_name;
    private String[] map_array;
    private String all_time;
    private String all_distance;
    private String start_time;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String city;
    private String address;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    private String street;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getMap_file_name() {
        return map_file_name;
    }

    public void setMap_file_name(String map_file_name) {
        this.map_file_name = map_file_name;
    }

    public String getAll_time() {
        return all_time;
    }

    public void setAll_time(String all_time) {
        this.all_time = all_time;
    }

    public String getAll_distance() {
        return all_distance;
    }

    public void setAll_distance(String all_distance) {
        this.all_distance = all_distance;
    }

    public Userbean getUserbean() {
        return userbean;
    }

    public void setUserbean(Userbean userbean) {
        this.userbean = userbean;
    }

    public String getMap_url() {
        return map_url;
    }

    public void setMap_url(String map_url) {
        this.map_url = map_url;
    }


    public String[] getMap_array() {
        return map_array;
    }

    public void setMap_array(String[] map_array) {
        this.map_array = map_array;
    }


}
