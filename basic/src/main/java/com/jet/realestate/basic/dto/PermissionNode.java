package com.jet.realestate.basic.dto;

import com.jet.realestate.basic.model.Permission;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class PermissionNode extends Permission {
    @Getter
    @Setter
    private List<PermissionNode> children;
}
