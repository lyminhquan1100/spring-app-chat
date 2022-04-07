package spring.boot.core.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.ZonedDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public abstract class CoreEntity {

    @Column(name = "create_at",nullable = false,updatable = false)
    @CreatedDate
    private ZonedDateTime createdAt;

    @Column(name = "update_at",nullable = false)
    @LastModifiedDate
    private ZonedDateTime updatedAt;

    @Column(name = "created_by")
    @CreatedBy
    private Long createdBy;

    @Column(name = "updated_by")
    @LastModifiedBy
    private Long updatedBy;

    @Column(name = "deleted", nullable = false)
    private Short deleted = 0;

    @Transient
    private Boolean mapAllProperties = false;

    public abstract void setId(Long id);

    public abstract Long getId();
}
