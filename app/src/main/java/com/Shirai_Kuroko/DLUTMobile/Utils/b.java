package com.Shirai_Kuroko.DLUTMobile.Utils;

import java.io.*;

public class b implements c
{
    public final byte[] a;
    public final byte[] b;

    public b() {
        super();
        this.a = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
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
        final int index2 = i - 4;
        int n = 0;
        int index3 = this.d(s, 0, index2);
        int n2;
        while (true) {
            n2 = 2;
            if (index3 >= index2) {
                break;
            }
            final byte b = this.b[s.charAt(index3)];
            final int d = this.d(s, index3 + 1, index2);
            final byte b2 = this.b[s.charAt(d)];
            final int d2 = this.d(s, d + 1, index2);
            final byte b3 = this.b[s.charAt(d2)];
            final int d3 = this.d(s, d2 + 1, index2);
            final byte b4 = this.b[s.charAt(d3)];
            outputStream.write(b << 2 | b2 >> 4);
            outputStream.write(b2 << 4 | b3 >> 2);
            outputStream.write(b3 << 6 | b4);
            n += 3;
            index3 = this.d(s, d3 + 1, index2);
        }
        final char char1 = s.charAt(index2);
        final char char2 = s.charAt(i - 3);
        final char char3 = s.charAt(i - 2);
        final char char4 = s.charAt(i - 1);
        int n3;
        if (char3 == '=') {
            final byte[] b5 = this.b;
            outputStream.write(b5[char2] >> 4 | b5[char1] << 2);
            n3 = 1;
        }
        else if (char4 == '=') {
            final byte[] b6 = this.b;
            final byte b7 = b6[char1];
            final byte b8 = b6[char2];
            final byte b9 = b6[char3];
            outputStream.write(b7 << 2 | b8 >> 4);
            outputStream.write(b9 >> 2 | b8 << 4);
            n3 = n2;
        }
        else {
            final byte[] b10 = this.b;
            final byte b11 = b10[char1];
            final byte b12 = b10[char2];
            final byte b13 = b10[char3];
            final byte b14 = b10[char4];
            outputStream.write(b11 << 2 | b12 >> 4);
            outputStream.write(b12 << 4 | b13 >> 2);
            outputStream.write(b14 | b13 << 6);
            n3 = 3;
        }
        return n + n3;
    }

    @Override
    public int b(final byte[] array, int n, int n2, final OutputStream outputStream) throws IOException {
        final int n3 = n2 % 3;
        final int n4 = n2 - n3;
        n2 = n;
        int n5;
        int n6;
        while (true) {
            n5 = n + n4;
            n6 = 4;
            if (n2 >= n5) {
                break;
            }
            final int n7 = array[n2] & 0xFF;
            final int n8 = array[n2 + 1] & 0xFF;
            final int n9 = array[n2 + 2] & 0xFF;
            outputStream.write(this.a[n7 >>> 2 & 0x3F]);
            outputStream.write(this.a[(n7 << 4 | n8 >>> 4) & 0x3F]);
            outputStream.write(this.a[(n8 << 2 | n9 >>> 6) & 0x3F]);
            outputStream.write(this.a[n9 & 0x3F]);
            n2 += 3;
        }
        Label_0287: {
            if (n3 != 1) {
                if (n3 != 2) {
                    break Label_0287;
                }
                n = (array[n5] & 0xFF);
                n2 = (array[n5 + 1] & 0xFF);
                outputStream.write(this.a[n >>> 2 & 0x3F]);
                outputStream.write(this.a[(n << 4 | n2 >>> 4) & 0x3F]);
                outputStream.write(this.a[n2 << 2 & 0x3F]);
            }
            else {
                n = (array[n5] & 0xFF);
                outputStream.write(this.a[n >>> 2 & 0x3F]);
                outputStream.write(this.a[n << 4 & 0x3F]);
                outputStream.write(61);
            }
            outputStream.write(61);
        }
        n2 = n4 / 3;
        n = n6;
        if (n3 == 0) {
            n = 0;
        }
        return n2 * 4 + n;
    }

    public final boolean c(final char c) {
        return c == '\n' || c == '\r' || c == '\t' || c == ' ';
    }

    public final int d(final String s, int index, final int n) {
        while (index < n && this.c(s.charAt(index))) {
            ++index;
        }
        return index;
    }
}
