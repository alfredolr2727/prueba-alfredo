package com.jember.alfredo.prueba.repository.query;

import com.jember.alfredo.prueba.dto.PoolSearchRequest;
import com.jember.alfredo.prueba.repository.entity.PoolEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class PoolSpecification {

  PoolSpecification() {}

  public static Specification<PoolEntity> withFilters(PoolSearchRequest request) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      // Filter dcsPoolIds
      if (request.dcsPoolIds() != null && !request.dcsPoolIds().isEmpty()) {
        predicates.add(root.get("dcsPoolId").in(request.dcsPoolIds()));
      }

      // Filter by authentication method
      if (request.filterCriteria() != null
          && request.filterCriteria().authenticationMethods() != null
          && !request.filterCriteria().authenticationMethods().isEmpty()) {

        // JOIN: pool -> chargingStations -> authMethods
        Join<Object, Object> chargingStations = root.join("chargingStations");
        Join<Object, Object> authMethods = chargingStations.join("authMethods");

        List<String> authMethodStrings =
            request.filterCriteria().authenticationMethods().stream().map(Enum::name).toList();

        predicates.add(authMethods.get("authMethod").in(authMethodStrings));

        assert query != null;
        query.distinct(true);
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
