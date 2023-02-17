package uz.pdp.clickupsecondpart.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
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

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private Attachment attachment;
}
