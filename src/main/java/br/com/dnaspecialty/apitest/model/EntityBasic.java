package br.com.dnaspecialty.apitest.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;

@MappedSuperclass
public abstract class EntityBasic implements Serializable {

    private static final long serialVersionUID = -6102169402724075508L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    protected EntityBasic(final Long id) {
        this.id = id;
        this.createdDate = LocalDateTime.now();
    }

    protected EntityBasic() {
        this.createdDate = LocalDateTime.now();
    }

    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(final LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(final Object o) {
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        final EntityBasic that = (EntityBasic) o;
        return Objects.equals(this.id, that.id);
    }

}
