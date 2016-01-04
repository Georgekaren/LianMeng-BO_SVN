package com.lianmeng.core.framework.bo.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public final class FileUtil
{
  private static final Logger logger = Logger.getLogger(FileUtil.class);
  public static final int FILE_TYPE = 1;
  public static final int FOLDER_TYPE = 2;
  public static final int NULL_TYPE = -1;

  public static String cloneFile(String srcFile, String suffix)
    throws FileNotFoundException, IOException
  {
    return cloneFile(srcFile, suffix, false);
  }

  public static String cloneFile(String srcFile, String suffix, boolean deleteOnExit)
    throws FileNotFoundException, IOException
  {
    if (StringUtil.isEmpty(suffix)) {
      suffix = ".clone";
    }

    String clonePath = srcFile + suffix;
    File colneFile = new File(clonePath);
    if (colneFile.exists()) {
      boolean deletde = colneFile.delete();
      if (!(deletde)) {
        throw new IllegalStateException("file:" + clonePath + " can  not be deleted");
      }
    }
    boolean created = colneFile.createNewFile();
    if (!(created)) {
      throw new IllegalStateException("file:" + clonePath + " can  not be created");
    }
    byte[] cfgContent = loadFile(srcFile);
    writeFile(clonePath, cfgContent);
    if (deleteOnExit) {
      colneFile.deleteOnExit();
    }
    return clonePath;
  }

  public static File getFile(String filename) {
    File file = new File(filename);
    if ((file == null) || (!(file.exists())))
    {
      try
      {
       // file = ResourceUtil.getResourceAsFile(getTCL(), filename);
      }
      catch (Exception ie) {
        file = null;
      }
    }
    if ((file == null) || (!(file.exists())))
    {
      try
      {
       // file = ResourceUtil.getResourceAsFile(FileUtil.class.getClassLoader(), filename);
      }
      catch (Exception ie) {
        file = null;
      }
    }
    return file;
  }

  private static ClassLoader getTCL() throws IllegalAccessException, InvocationTargetException
  {
    Method method = null;
    try {
      method = Thread.class.getMethod("getContextClassLoader", null);
    }
    catch (NoSuchMethodException e) {
      return null;
    }

    return ((ClassLoader)method.invoke(Thread.currentThread(), null));
  }

  public static byte[] loadFile(String fileName)
    throws FileNotFoundException, IOException
  {
    byte[] retBytes = new byte[0];
    File file = new File(fileName);

    FileInputStream fs = new FileInputStream(fileName);
    int fileLen = (int)file.length();
    retBytes = new byte[fileLen];
    fs.read(retBytes);
    fs.close();

    return retBytes;
  }

  public static void writeFile(String fileName, byte[] data)
    throws FileNotFoundException, IOException
  {
    File file = new File(fileName);
    FileOutputStream fs = new FileOutputStream(file);
    fs.write(data);
    fs.close();
  }

  public static String loadTxtFile(String fileName)
    throws FileNotFoundException, IOException
  {
    byte[] bfs = loadFile(fileName);
    String ret = new String(bfs, "UTF-8");
    return ret;
  }

  public static void writeTxtFile(String fileName, String data)
    throws FileNotFoundException, IOException
  {
    byte[] ofs = data.getBytes("UTF-8");
    writeFile(fileName, ofs);
  }

  public static void writeTxtFile(String filename, String data, boolean mode)
    throws FileNotFoundException, IOException
  {
    if ((null == filename) || (0 == filename.length())) {
      return;
    }

    if ((null == data) || (0 == data.length())) {
      return;
    }

    File file = new File(filename);

    DataOutputStream out = null;
    try {
      if (!(file.getParentFile().exists())) {
        file.getParentFile().mkdirs();
      }

      out = new DataOutputStream(new FileOutputStream(file, mode));

      out.writeBytes(data);
      try
      {
        if (null != out)
          out.close();
      }
      catch (Exception e)
      {
        return;
      }
    }
    finally
    {
      try
      {
        if (null != out)
          out.close();
      }
      catch (Exception e)
      {
        return;
      }
    }
  }

  public static int getFileType(String fileName)
  {
    File file = new File(fileName);
    if (file.exists())
    {
      if (file.isDirectory()) {
        return 2;
      }
      return 1;
    }
    /*try
    {
     // file = ResourceUtil.getResourceAsFile(fileName);
    }
    catch (IOException e)
    {
      file = null;
    }*/
    if ((file != null) && (file.exists())) {
      if (file.isDirectory()) {
        return 2;
      }
      return 1;
    }

    return -1;
  }

  public static byte[] getBytesFromFile(File file)
  {
    byte[] ret = null;
    try {
      if (file == null)
      {
        return null;
      }
      FileInputStream in = new FileInputStream(file);
      ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
      byte[] b = new byte[4096];

      int n=0;
      while ((n = in.read(b)) != -1)
      {
        out.write(b, 0, n);
      }
      in.close();
      out.close();
      ret = out.toByteArray();
    }
    catch (IOException e) {
      logger.error("helper:get bytes from file process error!");
    }

    return ret;
  }

  public static File getFileFromBytes(byte[] b, String outputFile)
  {
    File ret = null;
    BufferedOutputStream stream = null;
    try {
      ret = new File(outputFile);
      FileOutputStream fstream = new FileOutputStream(ret);
      stream = new BufferedOutputStream(fstream);
      stream.write(b);
    }
    catch (Exception e) {
      logger.error("helper:get file from byte process error!", e);
    }
    finally
    {
      if (stream != null) {
        try {
          stream.close();
        }
        catch (IOException e) {
          logger.error("helper:get file from byte process error!", e);
        }
      }
    }

    return ret;
  }

  public static void writeZip(String[] strs, String finalZipFile)
    throws IOException
  {
    String[] files = strs;
    OutputStream os = new BufferedOutputStream(new FileOutputStream(finalZipFile));
    ZipOutputStream zos = new ZipOutputStream(os);
    byte[] buf = new byte[8192];

    for (int i = 0; i < files.length; ++i) {
      File file = new File(files[i]);
      if (!(file.isFile())) {
        continue;
      }
      ZipEntry ze = new ZipEntry(file.getName());
      zos.putNextEntry(ze);
      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
      int len=0;
      while ((len = bis.read(buf)) > 0)
      {
        zos.write(buf, 0, len);
      }
      bis.close();
      zos.closeEntry();
    }
    zos.setEncoding("utf-8");
    zos.closeEntry();
    zos.close();
  }

  public static void unZip(String sourceFileName, String desDir)
    throws IOException
  {
    ZipFile zf = new ZipFile(new File(sourceFileName));

    Enumeration en = zf.getEntries();
    int length = 0;
    byte[] b = new byte[8192];

    while (en.hasMoreElements()) {
      ZipEntry ze = (ZipEntry)en.nextElement();

      File f = new File(desDir + ze.getName());

      if (ze.isDirectory()) {
        f.mkdirs();
      }
      else
      {
        if (!(f.getParentFile().exists())) {
          f.getParentFile().mkdirs();
        }

        OutputStream outputStream = new FileOutputStream(f);
        InputStream inputStream = zf.getInputStream(ze);
        while ((length = inputStream.read(b)) > 0) {
          outputStream.write(b, 0, length);
        }

        inputStream.close();
        outputStream.close();
      }
    }
    zf.close();
  }

  public static boolean copyDir(File src, File dest, boolean withFile, boolean overwrite)
    throws IOException
  {
    if ((!(src.isDirectory())) || (!(dest.isDirectory()))) {
      return false;
    }
    if (!(src.canRead())) {
      return false;
    }
    if (!(dest.canWrite())) {
      return false;
    }
    File[] subs = src.listFiles();
    if (subs == null) {
      return true;
    }
    for (File sub : subs) {
      String subName = sub.getName();
      File subDest = new File(dest, subName);
      if (sub.isDirectory()) {
        if (!(subDest.exists())) {
          subDest.mkdir();
        }
        copyDir(sub, subDest, withFile, overwrite);
      }
      else if (sub.isFile()) {
        if (!(withFile)) {
          continue;
        }
        if (sub.canRead()) {
          copyFile(sub, subDest, overwrite);
        }
      }
    }
    return true;
  }

  public static boolean copyFile(File src, File dest, boolean overwrite)
    throws IOException
  {
    if (dest.exists()) {
      if (overwrite) {
        boolean deleted = dest.delete();
        if (!(deleted))
        {
          return false;
        }
      }
      else {
        return true;
      }
    }

    FileInputStream in = null;
    FileOutputStream out = null;
    try {
      in = new FileInputStream(src);
      out = new FileOutputStream(dest);
      byte[] buffer = new byte[2097152];
      int pos = 0;
      while (true) {
        int len = in.read(buffer);
        if (len == -1) {
          break;
        }
        out.write(buffer, pos, len);
        pos += len;
      }
      if (pos > 0)
        out.flush();
    }
    finally
    {
      if (in != null) {
        in.close();
      }
      if (out != null) {
        out.close();
      }
    }
    return true;
  }
}