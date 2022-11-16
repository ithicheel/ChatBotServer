package com.example.chatbot.Classes;

public class Users {
    private String _user_id;
    private String _username;
    private String _desc;
    private String _phone;
    private String _email;
    private String _status;
    private String _token;
    public Users(String id ,String name, String decs, String phone ,String email, String _status, String token){
        this._user_id = id == null ? "" : id;
        this._username = name == null ? "" : name;
        this._desc = decs == null ? "" : decs;
        this._phone = phone == null ? "" : phone;
        this._email = email == null ? "" : email;
        this._status = _status  == null ? "" : _status;
        this._token = token == null ? "" : token;
    }

    /**
     * getter
     * @return
     */
    public String get_desc() {
        return _desc;
    }

    public String get_email() {
        return _email;
    }

    public String get_phone() {
        return _phone;
    }

    public String get_status() {
        return _status;
    }

    public String get_user_id() {
        return _user_id;
    }

    public String get_username() {
        return _username;
    }

    public String get_token() {
        return _token;
    }

    /**
     * setter
     * @param _desc
     */
    public void set_desc(String _desc) {
        this._desc = _desc;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public void set_phone(String _phone) {
        this._phone = _phone;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public void set_user_id(String _user_id) {
        this._user_id = _user_id;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public void set_token(String _token) {
        this._token = _token;
    }
}
