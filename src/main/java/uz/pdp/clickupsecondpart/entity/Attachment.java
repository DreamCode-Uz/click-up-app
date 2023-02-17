package uz.pdp.clickupsecondpart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.pdp.clickupsecondpart.entity.template.AbsUUIDEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Attachment extends AbsUUIDEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false, name = "original_name")
    private String originalName;

    @Column(nullable = false, name = "content_type")
    private String contentType;

    private Long size;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(mappedBy = "attachment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AttachmentContent content;
}
