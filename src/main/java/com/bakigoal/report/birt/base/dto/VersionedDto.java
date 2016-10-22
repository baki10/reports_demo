package com.bakigoal.report.birt.base.dto;

public abstract class VersionedDto extends IdentifiedDto {
  private long version;

  public long getVersion() {
    return version;
  }

  public void setVersion(long version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    VersionedDto versionedDtoto = (VersionedDto) o;
    return getVersion() == versionedDtoto.getVersion();
  }

  @Override
  public int hashCode() {
    return 31 * super.hashCode() + (int) (getVersion() ^ (getVersion() >>> 32));
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
      "id=" + getId() +
      ", version=" + version +
      '}';
  }
}
