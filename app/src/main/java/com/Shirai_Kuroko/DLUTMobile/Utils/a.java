package com.Shirai_Kuroko.DLUTMobile.Utils;

import java.io.*;

public class a
{
    public static final c a;

    static {
        a = new b();
    }

    public static byte[] a(final String s) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            a.a(s, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        catch (IOException obj) {
            final StringBuilder sb = new StringBuilder();
            sb.append("exception decoding base64 string: ");
            sb.append(obj);
            throw new RuntimeException(sb.toString());
        }
    }

    public static byte[] b(final byte[] array) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            a.b(array, 0, array.length, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        catch (IOException obj) {
            final StringBuilder sb = new StringBuilder();
            sb.append("exception encoding base64 string: ");
            sb.append(obj);
            throw new RuntimeException(sb.toString());
        }
    }
}
