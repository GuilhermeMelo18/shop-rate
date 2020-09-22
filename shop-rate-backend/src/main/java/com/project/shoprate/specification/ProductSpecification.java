package com.project.shoprate.specification;

import com.project.shoprate.domain.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> carriersInIds(List<Long> ids) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (!CollectionUtils.isEmpty(ids)) {
                predicates.add(root.get("id").in(ids));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
