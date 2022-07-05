package com.Shirai_Kuroko.DLUTMobile.Utils;

import java.io.*;

public class e implements c
{
    public final byte[] a;
    public final byte[] b;

    public e() {
        super();
        this.a = new byte[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
        this.b = new byte[128];
        int n = 0;
        while (true) {
            final byte[] a = this.a;
            if (n >= a.length) {
                break;
            }
            this.b[a[n]] = (byte)n;
            ++n;
        }
        final byte[] b = this.b;
        b[65] = b[97];
        b[66] = b[98];
        b[67] = b[99];
        b[68] = b[100];
        b[69] = b[101];
        b[70] = b[102];
    }

    @Override
    public int a(final String s, final OutputStream outputStream) throws IOException {
        int i;
        int index;
        for (i = s.length(); i > 0; i = index) {
            index = i - 1;
            if (!this.c(s.charAt(index))) {
                break;
            }
        }
        int j = 0;
        int n = 0;
        while (j < i) {
            while (j < i && this.c(s.charAt(j))) {
                ++j;
            }
            final byte[] b = this.b;
            final int n2 = j + 1;
            final byte b2 = b[s.charAt(j)];
            for (j = n2; j < i && this.c(s.charAt(j)); ++j) {}
            outputStream.write(b2 << 4 | this.b[s.charAt(j)]);
            ++n;
            ++j;
        }
        return n;
    }

    @Override
    public int b(final byte[] array, final int n, final int n2, final OutputStream outputStream) throws IOException {
        for (int i = n; i < n + n2; ++i) {
            final int n3 = array[i] & 0xFF;
            outputStream.write(this.a[n3 >>> 4]);
            outputStream.write(this.a[n3 & 0xF]);
        }
        return n2 * 2;
    }

    public final boolean c(final char c) {
        return c == '\n' || c == '\r' || c == '\t' || c == ' ';
    }
}

