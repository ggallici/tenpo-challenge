package com.ggallici.tenpo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class TransactionLog {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = STRING)
    private TransactionStatus status;

    private String uri;

    @Embedded
    private Add result;

    public TransactionLog(TransactionStatus status, String uri, Add result) {
        this.status = status;
        this.uri = uri;
        this.result = result;
    }
}
