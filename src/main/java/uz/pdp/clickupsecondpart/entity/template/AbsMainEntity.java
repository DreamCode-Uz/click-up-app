package uz.pdp.clickupsecondpart.entity.template;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.pdp.clickupsecondpart.entity.User;

import java.sql.Timestamp;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class AbsMainEntity {

    @Column(nullable = false, updatable = false, name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(nullable = false, name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

//    @Column(name = "created_by", updatable = false)
//    @CreatedBy
//    private UUID createdBy;
//
//    @Column(name = "updated_by")
//    @LastModifiedBy
//    private UUID updatedBy;

    @JoinColumn(updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    private User updatedBy;
}
