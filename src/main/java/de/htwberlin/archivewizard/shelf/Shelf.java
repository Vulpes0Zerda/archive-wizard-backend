package de.htwberlin.archivewizard.shelf;

// ArchiveWizard
import de.htwberlin.archivewizard.category.group.CategoryGroup;
import de.htwberlin.archivewizard.user.User;

// JPA
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Represents a shelf within a user's archive structure.
 *
 * <p>
 * A shelf belongs to one user and one category group, and contains a collection of items. The
 * position field allows ordering within the category group layout.
 * </p>
 */
@Entity
@Table(name = "shelfs")
public class Shelf {

  // ──────────────────────────────────────────────────────────────
  // Attributes
  // ──────────────────────────────────────────────────────────────

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shelfs_seq")
  @SequenceGenerator(name = "shelfs_seq", sequenceName = "shelfs_sequence", allocationSize = 1)
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owned_by_user", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "has_category_group", nullable = false)
  private CategoryGroup categoryGroup;

  @Column(name = "name", nullable = false, length = 60)
  private String name;

  @Column(name = "position", nullable = false, columnDefinition = "SMALLINT")
  private Short position;

  // ──────────────────────────────────────────────────────────────
  // Constructors
  // ──────────────────────────────────────────────────────────────
  protected Shelf() {}

  public Shelf(User user, CategoryGroup categoryGroup, String name, short position) {
    this.user = user;
    this.categoryGroup = categoryGroup;
    this.name = name;
    this.position = position;
  }

  // ──────────────────────────────────────────────────────────────
  // Setters
  // ──────────────────────────────────────────────────────────────

  public void setId(final Long id) {
    this.id = id;
  }

  public void setUser(final User user) {
    this.user = user;
  }

  public void setCategoryGroup(final CategoryGroup categoryGroup) {
    this.categoryGroup = categoryGroup;
  }

  public void setName(final String name) {
    this.name = name;
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

  public User getUser() {
    return this.user;
  }

  public CategoryGroup getCategoryGroup() {
    return this.categoryGroup;
  }

  public String getName() {
    return this.name;
  }

  public Short getPosition() {
    return this.position;
  }

  // ──────────────────────────────────────────────────────────────
  // Object Methods
  // ──────────────────────────────────────────────────────────────

  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (!(otherObject instanceof Shelf)) {
      return false;
    }

    final Shelf otherShelf = (Shelf) otherObject;

    if (!otherShelf.getId().equals(this.getId())) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 91 * this.getUser().hashCode() + this.getId().hashCode();
  }

  @Override
  public String toString() {
    return String.format(
        "%s - @%d[\n  id=%d, name='%s', position=%d, categoryGroup={\n    %s\n  }, user={\n    %s\n  }\n]",
        this.getClass().getSimpleName(), System.identityHashCode(this), getId(), getName(),
        getPosition(), getCategoryGroup().toString(), getUser().toString());
  }
}
