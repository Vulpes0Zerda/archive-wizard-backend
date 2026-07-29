package de.htwberlin.archivewizard.category.key;

// ArchiveWizard
import de.htwberlin.archivewizard.category.group.CategoryGroup;
import de.htwberlin.archivewizard.category.value.CategoryValue;

// Java
import java.util.List;

// JPA
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "category_keys")
public class CategoryKey {

  // ──────────────────────────────────────────────────────────────
  // Attributes
  // ──────────────────────────────────────────────────────────────

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_key_seq")
  @SequenceGenerator(name = "category_key_seq", sequenceName = "category_key_sequence",
      allocationSize = 1)
  @Column(name = "id", nullable = false, unique = true)
  private long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "belongs_to_category_group", nullable = false)
  private CategoryGroup categoryGroup;

  @Column(name = "key", nullable = false, length = 80)
  private String key;

  @Column(name = "position", nullable = false, columnDefinition = "SMALLINT")
  private Short position;

  @OneToMany(mappedBy = "categoryKey", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CategoryValue> categoryValues;

  // ──────────────────────────────────────────────────────────────
  // Constructors
  // ──────────────────────────────────────────────────────────────

  protected CategoryKey() {}

  public CategoryKey(CategoryGroup categoryGroup, String key, Short position) {
    this.categoryGroup = categoryGroup;
    this.key = key;
    this.position = position;
  }

  // ──────────────────────────────────────────────────────────────
  // Setters
  // ──────────────────────────────────────────────────────────────
  public void setId(final Long id) {
    this.id = id;
  }

  public void setCategoryGroup(final CategoryGroup categoryGroup) {
    this.categoryGroup = categoryGroup;
  }

  public void setKey(final String key) {
    this.key = key;
  }

  public void setPosition(final Short position) {
    this.position = position;
  }

  // ──────────────────────────────────────────────────────────────
  // Getters
  // ──────────────────────────────────────────────────────────────

  public Long getId() {
    return this.id;
  }

  public CategoryGroup getCategoryGroup() {
    return this.categoryGroup;
  }

  public String getKey() {
    return this.key;
  }

  public Short getPosition() {
    return this.position;
  }

  public List<CategoryValue> getCategoryValues() {
    return this.categoryValues;
  }

  // ──────────────────────────────────────────────────────────────
  // Object Methods
  // ──────────────────────────────────────────────────────────────

  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (!(otherObject instanceof CategoryKey)) {
      return false;
    }

    final CategoryKey otherCategoryKey = (CategoryKey) otherObject;

    if (!otherCategoryKey.getId().equals(this.getId())) {
      return false;
    }
    if (!otherCategoryKey.getCategoryGroup().equals(this.getCategoryGroup())) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 91 * this.getCategoryGroup().hashCode() + this.getId().hashCode();
  }

  @Override
  public String toString() {
    return String.format("%s - @%d[\n  id=%d, key='%s', categoryGroup={\n    %s\n  }\n]",
        this.getClass().getSimpleName(), System.identityHashCode(this), getId(), getKey(),
        getCategoryGroup().toString());
  }

}
