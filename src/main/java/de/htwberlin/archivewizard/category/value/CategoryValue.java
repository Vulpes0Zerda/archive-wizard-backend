package de.htwberlin.archivewizard.category.value;

// ArchiveWizard
import de.htwberlin.archivewizard.category.key.CategoryKey;
import de.htwberlin.archivewizard.item.Item;

// JPA
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Stores the actual value for a category key on a specific item.
 *
 * <p>
 * A category value links a particular item to a category key and holds the user-defined content for
 * that metadata field, such as the value for a title, date, or description.
 * </p>
 */
@Entity
@Table(name = "category_values")
public class CategoryValue {

  // ──────────────────────────────────────────────────────────────
  // Attributes
  // ──────────────────────────────────────────────────────────────

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_value_seq")
  @SequenceGenerator(name = "category_value_seq", sequenceName = "category_value_sequence",
      allocationSize = 1)
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "value", nullable = true, columnDefinition = "TEXT")
  @Lob
  private String value;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "belongs_to_category_key", nullable = false)
  private CategoryKey categoryKey;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tags_item", nullable = false)
  private Item item;

  // ──────────────────────────────────────────────────────────────
  // Constructors
  // ──────────────────────────────────────────────────────────────

  public CategoryValue() {}

  public CategoryValue(String value, CategoryKey categoryKey, Item item) {
    this.value = value;
    this.categoryKey = categoryKey;
    this.item = item;
  }

  // ──────────────────────────────────────────────────────────────
  // Setters
  // ──────────────────────────────────────────────────────────────

  public void setId(final Long id) {
    this.id = id;
  }

  public void setValue(final String value) {
    this.value = value;
  }

  public void setCategoryKey(final CategoryKey categoryKey) {
    this.categoryKey = categoryKey;
  }

  public void setItem(final Item item) {
    this.item = item;
  }

  // ──────────────────────────────────────────────────────────────
  // Getters
  // ──────────────────────────────────────────────────────────────

  public Long getId() {
    return this.id;
  }

  public String getValue() {
    return this.value;
  }

  public CategoryKey getCategoryKey() {
    return this.categoryKey;
  }

  public Item getItem() {
    return this.item;
  }

  // ──────────────────────────────────────────────────────────────
  // Object Methods
  // ──────────────────────────────────────────────────────────────

  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (!(otherObject instanceof CategoryValue)) {
      return false;
    }

    final CategoryValue otherCategoryValue = (CategoryValue) otherObject;

    if (!otherCategoryValue.getId().equals(this.getId())) {
      return false;
    }
    if (!otherCategoryValue.getCategoryKey().equals(this.getCategoryKey())) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 91 * this.getCategoryKey().hashCode() + this.getId().hashCode();
  }

  @Override
  public String toString() {
    return String.format(
        "%s - @%d[\n  id=%d, value='%s', categoryKey={\n    %s\n  }, item={\n    %s\n  }\n]",
        this.getClass().getSimpleName(), System.identityHashCode(this), getId(), getValue(),
        getCategoryKey().toString(), getItem().toString());
  }

}
