package uz.pdp.clickupsecondpart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.clickupsecondpart.entity.template.AbsLongEntity;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class AttachmentContent extends AbsLongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private Attachment attachment;

    @Lob
    private byte[] content;

    public AttachmentContent(Attachment attachment, byte[] content) {
        this.attachment = attachment;
        this.content = content;
    }
}
