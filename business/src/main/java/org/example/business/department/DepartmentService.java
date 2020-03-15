package org.example.business.department;

import org.example.business.BaseService;
import org.example.common.rest.common.Tree;
import org.example.data.model.department.Department;

import java.util.List;

public interface DepartmentService extends BaseService<Department> {
    List<Tree> tree();

}
