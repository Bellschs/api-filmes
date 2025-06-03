package org.acme.config;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "idempotency_records")
public class IdempotencyRecord extends PanacheEntity {

    @Column(unique = true, nullable = false)
    public String keyRecord;
}
