package com.example.fileopera.dto;

import lombok.Data;

import java.util.List;

@Data
public class ModuleFunction {


    private String _id;

    private String displayname;

    private String description;

    private List<Function> functions;

    private boolean hide;

    private Integer index;

    private String name;

    private String parentid;

    private Integer subsystem;


}
