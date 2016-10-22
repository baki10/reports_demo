package com.bakigoal.report.birt.base.dto;

public abstract class BaseDto extends VersionedDto {
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


}
