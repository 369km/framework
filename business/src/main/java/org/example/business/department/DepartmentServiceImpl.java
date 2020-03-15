package org.example.business.department;

import org.example.common.rest.common.Tree;
import org.example.common.service.CommonService;
import org.example.data.model.department.Department;
import org.example.data.service.department.DepartmentDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentDataService departmentDataService;
    @Autowired
    private CommonService commonService;

    @Override
    public Department save(Department department) {
        return departmentDataService.save(department);
    }

    @Override
    public Department findById(Long id) {
        return departmentDataService.findById(id).orElseGet(Department::new);
    }

    @Override
    public Page<Department> findAll(Department department, Pageable pageable) {
        return departmentDataService.findAll(Example.of(department), pageable);
    }

    @Override
    public List<Department> findAll() {
        return departmentDataService.findAll();
    }

    public List<Tree> tree() {
        return commonService.buildTree(
                departmentDataService.findAll().stream()
                        .map(department -> {
                            Tree tree = new Tree();
                            BeanUtils.copyProperties(department, tree);
                            return tree;
                        }).collect(Collectors.toList()));
    }

}
