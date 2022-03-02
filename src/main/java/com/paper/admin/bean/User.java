package com.paper.admin.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    public int id;
    public String username;
    public String password;
    public String gender;
    public String rg_time;
    public String email;
    public String header;
}
