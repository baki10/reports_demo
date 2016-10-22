package com.bakigoal.report.birt.base.dto;

import java.io.Serializable;

public abstract class IdentifiedDto implements Serializable {

  private Serializable id;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || (getClass() != o.getClass())) {
      return false;
    }

    IdentifiedDto that = (IdentifiedDto) o;

    if (getId() == null) {
      return this == that;
    } else {
      return getId().equals(that.getId());
    }

  }

  @Override
  public int hashCode() {
    return getId() != null ? getId().hashCode() : 0;
  }


  public Serializable getId() {
    return id;
  }
}
