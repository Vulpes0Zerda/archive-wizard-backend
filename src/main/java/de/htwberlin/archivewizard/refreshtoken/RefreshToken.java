package de.htwberlin.archivewizard.refreshtoken;

import java.time.Instant;
import de.htwberlin.archivewizard.user.User;
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
 * Represents a refresh token created by a user using the authentication process.
 *
 * <p>
 * An item belongs to a single shelf, has a name, and stores a binary picture. Additional metadata
 * values for the item are modelled as category values linked to its category keys.
 * </p>
 */
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

  // ──────────────────────────────────────────────────────────────
  // Attributes
  // ──────────────────────────────────────────────────────────────

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_tokens_seq")
  @SequenceGenerator(name = "refresh_tokens_seq", sequenceName = "refresh_tokens_sequence",
      allocationSize = 1)
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "hash", nullable = false, unique = true, columnDefinition = "TEXT")
  private String hash;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "belongs_to")
  private User user;

  @Column(name = "expires_at", nullable = false)
  private Instant expiresAt;

  @Column(name = "is_revoked", nullable = false)
  private Boolean isRevoked = false;

  @Column(name = "revoked_at", nullable = true)
  private Instant revokedAt = null;

  // ──────────────────────────────────────────────────────────────
  // Constructors
  // ──────────────────────────────────────────────────────────────

  protected RefreshToken() {}

  public RefreshToken(String hash, User user, Instant expiresAt) {
    this.hash = hash;
    this.user = user;
    this.expiresAt = expiresAt;
  }

  // ──────────────────────────────────────────────────────────────
  // Setters
  // ──────────────────────────────────────────────────────────────

  public void setId(final Long id) {
    this.id = id;
  }

  public void setHash(final String hash) {
    this.hash = hash;
  }

  public void setUser(final User user) {
    this.user = user;
  }

  public void setExpiresAt(final Instant expiresAt) {
    this.expiresAt = expiresAt;
  }

  public void setIsRevoked(final Boolean isRevoked) {
    this.isRevoked = isRevoked;
  }

  public void setRevokedAt(final Instant revokedAt) {
    this.revokedAt = revokedAt;
  }

  // ──────────────────────────────────────────────────────────────
  // Getters
  // ──────────────────────────────────────────────────────────────

  public Long getId() {
    return this.id;
  }

  public String getHash() {
    return this.hash;
  }

  public User getUser() {
    return this.user;
  }

  public Instant getExpiresAt() {
    return this.expiresAt;
  }

  public Boolean getIsRevoked() {
    return this.isRevoked;
  }

  public Instant getRevokedAt() {
    return this.revokedAt;
  }

  // ──────────────────────────────────────────────────────────────
  // Object Methods
  // ──────────────────────────────────────────────────────────────

  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (!(otherObject instanceof RefreshToken)) {
      return false;
    }

    final RefreshToken otherRefreshToken = (RefreshToken) otherObject;

    if (!otherRefreshToken.getId().equals(this.getId())) {
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
    return String.format(
        "%s - @%d[\n  id=%d, hash='%s', user={\\n    %s\\n  }, expiresAt='%s', isRevoked=%b, revokedAt='%s'\n]",
        this.getClass().getSimpleName(), System.identityHashCode(this), getId(), getHash(),
        getUser().toString(), getExpiresAt(), getIsRevoked(), getRevokedAt().toString());
  }

}
