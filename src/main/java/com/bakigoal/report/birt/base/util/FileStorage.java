package com.bakigoal.report.birt.base.util;

import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.Validate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Scope(org.springframework.web.context.WebApplicationContext.SCOPE_SESSION)
public class FileStorage {
  private final Map<String, FileValue> fileMap = new ConcurrentHashMap<>();

  /**
   * @param extension
   * @return
   * @deprecated используй {@link #createTemporaryFile(String, String)}
   */
  @Deprecated
  public File createTemporaryFile(String extension) {
    return createTemporaryFile(extension, null);
  }

  /**
   *
   * @param extension
   * @param fileName имя файла, для хранения в дескрипторе
   * @return
   */
  public File createTemporaryFile(String extension, String fileName) {
    return createTemporaryFile(extension, fileName, false);
  }

  public File createTemporaryFile(String extension, String fileName, boolean isAttachment) {
    try {
      File tmp = File.createTempFile(UUID.randomUUID().toString(), extension);
      fileMap.put(tmp.getName(), new FileValue(fileName, tmp, isAttachment));
      return tmp;
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public File getTemporaryFile(String fileId) {
    return Validate.notNull(fileMap.get(fileId).getFile(), "%s is not found", fileId);
  }


  @PreDestroy
  private void destroy() {
    for (FileValue fileValue : fileMap.values()) {
      try {
        File file = fileValue.getFile();
        if (!file.delete()) {
        }
      } catch (RuntimeException e) {

      }
    }
  }

  public static OutputStreamWriter getOutputStreamWriter(
    File aFile)
  {
    try {
      return new OutputStreamWriter(new FileOutputStream(aFile), CharEncoding.UTF_8);
    } catch (UnsupportedEncodingException | FileNotFoundException e) {
      throw new IllegalStateException(e);
    }
  }

  public FileDescriptor getFileDescriptor(String aFileName) {
    FileDescriptor fd = Validate.notNull(fileMap.get(aFileName), "%s is not found", aFileName);
    return new FileDescriptor(fd.getName(), fd.isAttachmet());
  }

  /**
   * позволяет изменить дескриптор файла на сервере
   *
   * @param aFileName Ид файла
   * @param aOutputFileName имя файла для выгрузки на клиенте
   * @param aIsAttachment
   */
  public void updateFileDescriptor(String aFileName, String aOutputFileName, boolean aIsAttachment) {

    FileValue oldFileValue = Validate.notNull(fileMap.get(aFileName), "%s is not found", aFileName);
    fileMap.put(aFileName,new FileValue(aOutputFileName,oldFileValue.getFile(),aIsAttachment));
  }

  private static final class FileValue extends FileDescriptor {
    private final File file;

    public FileValue(String aName, File aFile) {
      this(aName, aFile, false);
    }

    public FileValue(String aName, File aFile, boolean aIsAttachmet) {
      super(aName, aIsAttachmet);
      file = aFile;
    }

    public File getFile() {
      return file;
    }

  }
}
