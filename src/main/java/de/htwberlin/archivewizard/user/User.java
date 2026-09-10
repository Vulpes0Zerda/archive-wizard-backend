package de.htwberlin.archivewizard.user;

// ArchiveWizard
import de.htwberlin.archivewizard.category.group.CategoryGroup;
import de.htwberlin.archivewizard.shelf.Shelf;

// Java
import java.util.List;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

// JPA
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Represents a single application user and the account state used for authentication.
 *
 * <p>
 * A user owns shelves and category groups, and stores a password hash rather than a plain-text
 * password. The entity is persisted by JPA into the users table and is used as the core identity
 * for the archive management domain.
 * </p>
 */
@Entity
@Table(name = "users")
public class User {

  // ──────────────────────────────────────────────────────────────
  // Attributes
  // ──────────────────────────────────────────────────────────────

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
  @SequenceGenerator(name = "users_seq", sequenceName = "users_sequence", allocationSize = 1)
  @Column(name = "id", nullable = false, unique = true)
  private Integer id;

  @Column(name = "name", nullable = false, length = 120)
  private String name;

  @Column(name = "email", unique = true, nullable = false, length = 320)
  private String email;

  @Column(name = "passwordHash", nullable = false, columnDefinition = "TEXT")
  private String passwordHash;

  @Column(name = "authority", nullable = false)
  @Enumerated(EnumType.STRING)
  @JdbcType(value = PostgreSQLEnumJdbcType.class)
  private Authority authority = Authority.USER;

  @OneToMany(mappedBy = "user")
  private List<Shelf> shelfs;

  @OneToMany(mappedBy = "user")
  private List<CategoryGroup> categoryGroups;

  // ──────────────────────────────────────────────────────────────
  // Constructors
  // ──────────────────────────────────────────────────────────────

  protected User() {}

  public User(String name, String email, String passwordHash) {
    this.name = name;
    this.email = email;
    this.passwordHash = passwordHash;
  }

  // ──────────────────────────────────────────────────────────────
  // Setters
  // ──────────────────────────────────────────────────────────────

  public void setId(final Integer id) {
    this.id = id;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public void setPasswordHash(final String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public void setAuthority(final Authority authority) {
    this.authority = authority;
  }

  // ──────────────────────────────────────────────────────────────
  // Getters
  // ──────────────────────────────────────────────────────────────

  public Integer getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPasswordHash() {
    return this.passwordHash;
  }

  public Authority getAuthority() {
    return this.authority;
  }

  // ──────────────────────────────────────────────────────────────
  // Object Methods
  // ──────────────────────────────────────────────────────────────

  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (!(otherObject instanceof User)) {
      return false;
    }

    final User otherUser = (User) otherObject;

    if (!otherUser.getId().equals(this.getId())) {
      return false;
    }
    if (!otherUser.getEmail().equals(this.getEmail())) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 91 * this.getEmail().hashCode() + this.getId().hashCode();
  }

  @Override
  public String toString() {
    return String.format("%s - @%d[\n  id=%d, name='%s', email='%s', authority='%s'\n]",
        this.getClass().getSimpleName(), System.identityHashCode(this), getId(), getName(),
        getEmail(), getAuthority().toString());
  }

}
