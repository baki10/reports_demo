package com.bakigoal.report.birt.base.util;

/**
 * Дескриптор, для хранения файла в FileStorage
 *
 * @author Evgeniy Artemjev
 */
public class FileDescriptor {
  private final String name;
  private final boolean isAttachmet;

  public FileDescriptor(String aName,
                        boolean aIsAttachmet)
  {
    name = aName;
    isAttachmet = aIsAttachmet;
  }

  public String getName() {
    return name;
  }

  public boolean isAttachmet() {
    return isAttachmet;
  }
}
