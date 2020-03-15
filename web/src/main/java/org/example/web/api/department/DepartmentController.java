package org.example.web.api.department;

import org.example.business.department.DepartmentService;
import org.example.common.Constant;
import org.example.common.rest.common.Tree;
import org.example.web.annotation.DepartmentSwagger;
import org.example.web.response.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@DepartmentSwagger
@RestController
@RequestMapping(Constant.API+"/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @GetMapping("/tree")
    public RestResponse<List<Tree>> tree(){
        return new RestResponse<>(departmentService.tree());
    }
}
