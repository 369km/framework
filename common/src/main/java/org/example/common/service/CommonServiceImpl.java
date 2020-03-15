package org.example.common.service;

import org.example.common.rest.common.Tree;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommonServiceImpl implements CommonService {
    @Override
    public List<Tree> buildTree(List<Tree> list) {
        Long rootId = list.stream()
                .map(Tree::getParentId)
                .min(Long::compareTo)
                .orElse(0L);
        List<Tree> children = list.stream()
                .filter(x -> x.getParentId().equals(rootId))
                .collect(Collectors.toList());
        List<Tree> successor = list.stream()
                .filter(x -> !x.getParentId().equals(rootId))
                .collect(Collectors.toList());
        children.forEach(x -> buildTree(successor)
                .forEach(y -> {
                    if (CollectionUtils.isEmpty(x.getChildren())) {
                        List<Tree> nodes = new ArrayList<>();
                        x.setChildren(nodes);
                    }
                    x.getChildren().add(y);
                })
        );
        return children;
    }
}
