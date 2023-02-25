package uz.pdp.clickupsecondpart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.pdp.clickupsecondpart.entity.template.AbsUUIDEntity;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Attachment extends AbsUUIDEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false, name = "original_name")
    private String originalName;

    @Column(nullable = false, name = "content_type")
    private String contentType;

    private Long size;

    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(mappedBy = "attachment", cascade = CascadeType.ALL)
    private AttachmentContent content;

    public Attachment(String name, String originalName, String contentType, Long size) {
        this.name = name;
        this.originalName = originalName;
        this.contentType = contentType;
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Attachment that = (Attachment) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
