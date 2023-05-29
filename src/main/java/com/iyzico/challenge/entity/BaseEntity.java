package com.iyzico.challenge.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreatedDate
  @Column(name = "CREATED_DATE", nullable = false, updatable = false)
  protected LocalDateTime createdDate;

  @LastModifiedDate
  @Column(name = "MODIFIED_DATE")
  protected LocalDateTime modifiedDate;

  @Version
  private Integer version;
}
