package com.lianmeng.core.framework.bo.utils;

import java.io.PrintStream;

public final class ByteUtil
{
  public static byte[] memset(byte[] src, byte c, int pos, int count)
  {
    if ((pos < 0) || (pos > src.length) || (count <= 0)) {
      return src;
    }

    if (src == null) {
      src = new byte[pos + count];
    }

    for (int i = 0; (i < count) && (pos + i < src.length); ++i) {
      src[(pos + i)] = c;
    }

    return src;
  }

  public static byte[] memcpy(byte[] dest, int pos, byte[] src, int size)
  {
    if (size >= 0) {
      size = (size > dest.length) ? dest.length : size;
      System.arraycopy(src, 0, dest, pos, size);
    }
    return dest;
  }

  public static byte[] fillHead(byte[] src, int length, byte fill)
  {
    if (src.length >= length) {
      return src;
    }

    byte[] dest = new byte[length];
    memset(dest, fill, 0, length);
    memcpy(dest, (length > src.length) ? length - src.length : 0, src, src.length);
    return dest;
  }

  public static byte[] fillTail(byte[] src, int length, byte fill)
  {
    if (src.length >= length) {
      return src;
    }

    byte[] dest = new byte[length];
    memset(dest, fill, 0, length);
    memcpy(dest, 0, src, src.length);
    return dest;
  }

  public static byte[] insert(byte[] dest, byte[] src, int pos, int length)
  {
    if (length > dest.length) {
      length = dest.length;
    }

    for (int i = 0; i < pos + length; ++i) {
      if ((i >= pos) && (i < pos + length)) {
        if (i + length < dest.length) {
          dest[(i + length)] = dest[i];
        }
        if (i - pos < src.length) {
          dest[i] = src[(i - pos)];
        }
      }
    }

    return dest;
  }

  public static byte[] copyto(byte[] dest, byte[] src, int pos, int length)
  {
    if (length > src.length) {
      length = src.length;
    }

    if ((dest == null) || (dest.length < pos + length)) {
      byte[] buf = new byte[pos + length];
      memcpy(buf, 0, dest, pos);
      dest = buf;
    }

    for (int i = 0; i < length; ++i) {
      dest[(pos + i)] = src[i];
    }

    return dest;
  }

  public static byte[] copyfrom(byte[] dest, byte[] src, int pos, int length)
  {
    if (length > src.length) {
      length = src.length;
    }

    if ((dest == null) || (dest.length < length)) {
      dest = new byte[length];
    }

    for (int i = 0; i < length; ++i) {
      dest[i] = src[(pos + i)];
    }

    return copy(dest, 0, length);
  }

  public static byte[] delete(byte[] src, int pos, int length)
  {
    if (src == null) {
      return null;
    }

    if ((pos > src.length) || (pos < 0) || (length < 0)) {
      return src;
    }

    if (pos + length > src.length) {
      length = src.length - pos;
    }

    byte[] dest = new byte[src.length - length];
    for (int i = 0; i < src.length; ++i) {
      if ((i - pos >= 0) && (i - pos < length)) {
        continue;
      }
      dest[i] = src[i];
    }
    return dest;
  }

  public static byte[] copy(byte[] src, int pos, int size)
  {
    if ((pos < 0) || (pos > src.length) || (size <= 0)) {
      return null;
    }

    byte[] dest = new byte[(src.length - pos < size) ? src.length - pos : size];
    for (int i = 0; (i < size) && (pos + i < src.length); ++i) {
      dest[i] = src[(pos + i)];
    }

    return dest;
  }

  public static byte[] toHexBytes(int length, int hexlen, byte fillhead)
  {
    if (hexlen <= 0) {
      return null;
    }

    byte[] dest = new byte[hexlen];
    memset(dest, fillhead, 0, hexlen);
    byte[] src = Integer.toHexString(length).getBytes();
    System.arraycopy(src, 0, dest, (hexlen - src.length > 0) ? hexlen - src.length : 0, src.length);

    return dest;
  }

  public static int bytes2int(byte[] b)
  {
    int mask = 255;
    int temp = 0;
    int res = 0;
    for (int i = 0; i < 4; ++i) {
      res <<= 8;
      temp = b[i] & mask;
      res |= temp;
    }
    return res;
  }

  public static String byte2hex(byte b)
  {
    String hex = Integer.toHexString(b & 0xFF);
    if (hex.length() == 1) {
      hex = "0" + hex;
    }
    return hex;
  }

  public static byte hex2byte(String hex)
  {
    return (byte)(Integer.parseInt(hex, 16) & 0xFF);
  }

  public static byte[] hex2bytes(String hex)
  {
    if ((hex == null) || (hex.trim().length() == 0)) {
      return null;
    }

    byte[] dest = new byte[hex.length() / 2];
    for (int i = 0; i < hex.length() / 2; ++i) {
      String h = hex.substring(2 * i, 2 * i + 2);
      dest[i] = hex2byte(h);
    }

    return dest;
  }

  public static String bytes2hex(byte[] bytes)
  {
    StringBuffer sb = null;
    if ((bytes == null) || (bytes.length == 0)) {
      return null;
    }

    sb = new StringBuffer();
    for (int i = 0; i < bytes.length; ++i) {
      sb.append(byte2hex(bytes[i]));
    }

    return sb.toString();
  }

  public static byte[] int2bytes(int num)
  {
    byte[] b = new byte[4];

    for (int i = 0; i < 4; ++i) {
      b[i] = (byte)(num >>> 24 - (i * 8));
    }

    return b;
  }

  public static byte[] short2bytes(int num)
  {
    num &= 65535;
    byte[] b = new byte[2];

    for (int i = 0; i < 2; ++i) {
      b[i] = (byte)(num >>> 8 - (i * 8));
    }

    return b;
  }

  public static int byte2short(byte[] b)
  {
    int mask = 255;
    int temp = 0;
    int res = 0;
    for (int i = 0; i < 2; ++i) {
      res <<= 8;
      temp = b[i] & mask;
      res |= temp;
    }
    return res;
  }

  public static int htonl(int h)
  {
    int nl = 0;
    int b = 0;

    for (int i = 0; i < 4; ++i) {
      b = h << 24 - (i * 8) & 0xFF000000;

      nl += (b >> i * 8);
    }

    return nl;
  }

  public static byte[] htonlbytes(int h)
  {
    return int2bytes(h);
  }

  public static int htons(int h)
  {
    int ns = 0;
    int b = 0;
    for (int i = 0; i < 2; ++i) {
      b = h << 8 - (i * 8) & 0xFF00;

      ns += (b >> i * 8);
    }

    return ns;
  }

  public static byte[] htonsbytes(int h)
  {
    return short2bytes(h);
  }

  public static int ntohl(int n)
  {
    return htonl(n);
  }

  public static int nbytestohl(byte[] n)
  {
    return bytes2int(n);
  }

  public static int ntohs(int n) {
    return htons(n);
  }

  public static int nbytestohs(byte[] n)
  {
    return byte2short(n);
  }

  public static void showGrid()
  {
    for (int i = 0; i < 10; ++i) {
      System.out.print("0123456789");
    }
    System.out.println();
  }

  public static void showBytes(byte[] buf)
  {
    if (buf == null) {
      buf = new byte[0];
    }
    String hex = "";
    byte[] s = new byte[16];
    System.out.println("[BYTES INFO] : LENGTH = " + buf.length + "(0x" + Integer.toHexString(buf.length) + ")");
    for (int i = 0; i < buf.length; ++i) {
      if (i % 16 == 0) {
        hex = Integer.toHexString((i & 0xFF) / 16);
        if (hex.length() == 1) {
          hex = "0" + hex;
        }
        System.out.print("[" + hex + "]  ");
      }

      hex = Integer.toHexString(buf[i] & 0xFF);
      if (hex.length() == 1) {
        hex = "0" + hex;
      }
      System.out.print(hex + " ");
      s[(i % 16)] = buf[i];

      if ((i != 0) && (i % 8 == 7) && (i % 16 != 15)) {
        System.out.print("  ");
      }

      if ((i != 0) && (((i % 16 == 15) || (i == buf.length - 1)))) {
        if (i % 16 != 15) {
          for (int k = i % 16; k < 16; ++k) {
            if (k == 7) {
              System.out.print("  ");
            }
            else {
              System.out.print("   ");
            }
          }
        }

        System.out.print("  ");
        for (int j = 0; j < i % 16 + 1; ++j) {
          if ((s[j] == 0) || (s[j] <= 32) || (s[j] > 127)) {
            s[j] = 46;
          }
          System.out.print((char)s[j]);
        }

        System.out.print("\n");
      }
    }

    System.out.println("");
  }

  public static void main(String[] args)
  {
    String dd = "SCSC003C1.00JS123456UASUAS  00000000DLGCON    00000001TXBEG     HBHB";
    showBytes(dd.getBytes());

    byte[] length = new byte[20];
    byte[] to = new byte[10];
   // memset(length, 48, 0, 20);
    memcpy(to, 0, length, 20);
   // System.out.println(new String(toHexBytes(255, 2, 48)));
   // System.out.println(new String(fillHead(to, 20, 49)));
  //  System.out.println(new String(fillTail(to, 15, 57)));
    System.out.println(new String(copyto(to, "hello!".getBytes(), 0, 10)));
    System.out.println(new String(copyto(to, "Hello world!".getBytes(), 2, 15)));

    System.out.println(new String(copy(to, 5, 6)));
    System.out.println(new String(delete("Hello world".getBytes(), 5, 6)));
    System.out.println(new String(insert(to, "Gogogo!".getBytes(), 5, 5)));
  //  memset(length, 53, 10, 10);
    System.out.println(new String(length));
    System.out.println(new String(insert(length, "Gogogo!".getBytes(), 8, 8)));

    showGrid();

    System.out.println(new String(int2bytes(471670303)));

    int n = htonl(305419896);
    System.out.println(Integer.toHexString(n));
    n = ntohl(n);
    System.out.println(Integer.toHexString(n));

    n = htons(4660);
    System.out.println(Integer.toHexString(n));
    n = htons(n);
    System.out.println(Integer.toHexString(n));

    byte[] z = htonlbytes(305419896);
    System.out.println(new String(z));
    z = htonsbytes(4660);
    System.out.println(new String(z));
  }
}