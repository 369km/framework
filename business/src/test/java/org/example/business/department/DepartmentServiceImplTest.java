package org.example.business.department;

import org.example.business.BaseJunit;
import org.example.data.model.department.Department;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class DepartmentServiceImplTest extends BaseJunit {
    @Autowired
    private DepartmentService departmentService;

    @Test
    public void save() {
        Department department = new Department();
        department.setParentId(0L);
        department.setName("阿里科技公司");
        BaseJunit.toPrintJson(departmentService.save(department));
    }
}