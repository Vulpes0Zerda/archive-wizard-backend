package de.htwberlin.archivewizard.item;

// ArchiveWizard
import de.htwberlin.archivewizard.category.value.CategoryValue;
import de.htwberlin.archivewizard.shelf.Shelf;

// Java
import java.util.List;

// JPA
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "items")
public class Item {

  // ──────────────────────────────────────────────────────────────
  // Attributes
  // ──────────────────────────────────────────────────────────────

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
  @SequenceGenerator(name = "item_seq", sequenceName = "item_sequence", allocationSize = 1)
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "contained_within_shelf", nullable = false)
  private Shelf shelf;

  @Column(name = "name", nullable = false, length = 120)
  private String name;

  @Column(name = "picture", nullable = false, columnDefinition = "BYTEA")
  @Lob
  @Basic(fetch = FetchType.EAGER)
  private byte[] picture;

  @OneToMany(mappedBy = "item")
  private List<CategoryValue> categoryValues;

  // ──────────────────────────────────────────────────────────────
  // Constructors
  // ──────────────────────────────────────────────────────────────
  protected Item() {}

  public Item(Shelf shelf, String name, byte[] picture) {
    this.shelf = shelf;
    this.name = name;
    this.picture = picture;
  }

  // ──────────────────────────────────────────────────────────────
  // Setters
  // ──────────────────────────────────────────────────────────────

  public void setId(final Long id) {
    this.id = id;
  }

  public void setShelf(final Shelf shelf) {
    this.shelf = shelf;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setPicture(final byte[] picture) {
    this.picture = picture;
  }

  // ──────────────────────────────────────────────────────────────
  // Getters
  // ──────────────────────────────────────────────────────────────

  public Long getId() {
    return this.id;
  }

  public Shelf getShelf() {
    return this.shelf;
  }

  public String getName() {
    return this.name;
  }

  public byte[] getPicture() {
    return this.picture;
  }

  // ──────────────────────────────────────────────────────────────
  // Object Methods
  // ──────────────────────────────────────────────────────────────

  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (!(otherObject instanceof Item)) {
      return false;
    }

    final Item otherItem = (Item) otherObject;

    if (!((Long) otherItem.getId()).equals((Long) this.getId())) {
      return false;
    }
    if (!otherItem.getShelf().equals(this.getShelf())) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 91 * this.getShelf().hashCode() + this.getId().hashCode();
  }

  @Override
  public String toString() {
    return String.format("%s - @%d[\n  id=%d, name='%s', picture=%d, shelf={\n    %s\n  }\n]",
        this.getClass().getSimpleName(), System.identityHashCode(this), getId(), getName(),
        getPicture().toString(), getShelf().toString());
  }
}
