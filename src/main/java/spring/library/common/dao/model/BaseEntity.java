package spring.library.common.dao.model;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public abstract class BaseEntity {
  @Column(name = "created_at",nullable = false,updatable = false)
  @CreatedDate
  private ZonedDateTime createdAt;

  @Column(name = "updated_at",nullable = false)
  @LastModifiedDate
  private ZonedDateTime updatedAt;

  @Column(name = "created_by", nullable = false)
  @CreatedBy
  private Long createdBy;

  @Column(name = "updated_by",nullable = false)
  @LastModifiedBy
  private Long updatedBy;

  @Column(name = "deleted",nullable = false)
  private Short deleted = 0;

  public void setAuditProperties(Long createdBy, ZonedDateTime createdAt, Long updatedBy,
      ZonedDateTime updatedAt) {
    this.createdBy = createdBy;
    this.createdAt = createdAt;
    this.updatedBy = updatedBy;
    this.updatedAt = updatedAt;
  }

  public abstract void setId(Long id);

  public abstract Long getId();
}
