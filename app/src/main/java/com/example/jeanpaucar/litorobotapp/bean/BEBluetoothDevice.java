package com.example.jeanpaucar.litorobotapp.bean;

/**
 * Created by Jencarlos Paucar on 03/07/2016.
 */
public class BEBluetoothDevice {

    private String title;
    private String info;
    private String conexionDate;
    private String disconexionDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getConexionDate() {
        return conexionDate;
    }

    public void setConexionDate(String conexionDate) {
        this.conexionDate = conexionDate;
    }

    public String getDisconexionDate() {
        return disconexionDate;
    }

    public void setDisconexionDate(String disconexionDate) {
        this.disconexionDate = disconexionDate;
    }
}
