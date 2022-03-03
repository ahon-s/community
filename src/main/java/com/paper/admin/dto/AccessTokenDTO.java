package com.paper.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenDTO {
    public String client_id;
    public String client_secret;
    public String code;
    public String redirect_uri;
    public String state;

}
