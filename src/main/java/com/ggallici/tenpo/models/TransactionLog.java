package com.ggallici.tenpo.models;

import com.ggallici.tenpo.models.converters.TransactionResultConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class TransactionLog {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus status;

    private String uri;

    @Convert(converter = TransactionResultConverter.class)
    private Object result;

    public TransactionLog(TransactionStatus status, String uri, Object result) {
        this.status = status;
        this.uri = uri;
        this.result = result;
    }
}
