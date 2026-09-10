package de.htwberlin.archivewizard.category.group;

// ArchiveWizard
import de.htwberlin.archivewizard.shelf.Shelf;
import de.htwberlin.archivewizard.user.User;
import de.htwberlin.archivewizard.category.key.CategoryKey;

// Java
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
// JPA
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

/**
 * Represents a named group of archive categories belonging to a user.
 *
 * <p>
 * A category group acts as a container for multiple category keys and shelves, making it the
 * organizing unit for how archive metadata is grouped in the application.
 * </p>
 */
@Entity
@Table(name = "category_groups")
public class CategoryGroup {

  // ──────────────────────────────────────────────────────────────
  // Attributes
  // ──────────────────────────────────────────────────────────────

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_groups_seq")
  @SequenceGenerator(name = "category_groups_seq", sequenceName = "category_groups_sequence",
      allocationSize = 1)
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owned_by_user", nullable = true)
  private User user;

  @Column(name = "name", nullable = false, length = 80)
  private String name;

  @OneToMany(mappedBy = "categoryGroup")
  @JsonIgnore
  private List<Shelf> shelfs;

  @OneToMany(mappedBy = "categoryGroup")
  private List<CategoryKey> categoryKeys;

  // ──────────────────────────────────────────────────────────────
  // Constructors
  // ──────────────────────────────────────────────────────────────

  protected CategoryGroup() {}

  public CategoryGroup(String name) {
    this.name = name;
  }

  // ──────────────────────────────────────────────────────────────
  // Setters
  // ──────────────────────────────────────────────────────────────
  public void setId(final Long id) {
    this.id = id;
  }

  public void setName(final String name) {
    this.name = name;
  }

  // ──────────────────────────────────────────────────────────────
  // Getters
  // ──────────────────────────────────────────────────────────────

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public List<Shelf> getShelfs() {
    return this.shelfs;
  }

  public List<CategoryKey> getCategoryKeys() {
    return this.categoryKeys;
  }

  // ──────────────────────────────────────────────────────────────
  // Object Methods
  // ──────────────────────────────────────────────────────────────

  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (!(otherObject instanceof CategoryGroup)) {
      return false;
    }

    final CategoryGroup otherCategoryGroup = (CategoryGroup) otherObject;

    if (!otherCategoryGroup.getId().equals(this.getId())) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 91 * this.getId().hashCode();
  }

  @Override
  public String toString() {
    return String.format("%s - @%d[\n  id=%d, name='%s'\n]", this.getClass().getSimpleName(),
        System.identityHashCode(this), getId(), getName());
  }

}
