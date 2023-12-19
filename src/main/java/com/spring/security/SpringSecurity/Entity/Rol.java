package com.spring.security.SpringSecurity.Entity;

public enum Rol {
    ADMIN(1),USER(99);

    private Integer rolId;

    private Rol(Integer rolId){
        this.rolId = rolId;
    }

    public Integer getRolId() {
        return rolId;
    }
}
