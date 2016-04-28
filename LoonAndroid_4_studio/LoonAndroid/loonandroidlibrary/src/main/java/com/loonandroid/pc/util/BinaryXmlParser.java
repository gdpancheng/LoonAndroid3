package com.loonandroid.pc.util;

import android.util.Log;

import org.apache.http.util.ByteArrayBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class BinaryXmlParser {

    private static final int END_DOC_TAG = 0x00100101;
    private static final int START_TAG = 0x00100102;
    private static final int END_TAG = 0x00100103;

    private byte[] mData = null;

    public BinaryXmlParser(byte[] data) {
        mData = data;
    }

    public BinaryXmlParser(InputStream input) throws IOException {
        ByteArrayBuffer content = new ByteArrayBuffer(1024);
        int size;
        byte[] buffer = new byte[1024];
        while (-1 != (size = input.read(buffer))) {
            content.append(buffer, 0, size);
        }
        mData = content.toByteArray();
        input.close();
    }

    public void parse(EventHandler handler) {
        if (null == mData) {
            throw new IllegalStateException("Data null.");
        }
        int numbStrings = getLittleEndian32(mData, 4 * 4);
        int sitOff = 0x24;
        int stOff = sitOff + numbStrings * 4;
        int xmlTagOff = getLittleEndian32(mData, 3 * 4);
        for (int ii = xmlTagOff; ii < mData.length - 4; ii += 4) {
            if (getLittleEndian32(mData, ii) == START_TAG) {
                xmlTagOff = ii;
                break;
            }
        }
        int off = xmlTagOff;
        while (off < mData.length) {
            int tag0 = getLittleEndian32(mData, off);
            // int nameNsSi = getLittleEndian32(xml, off + 4 * 4);
            int nameSi = getLittleEndian32(mData, off + 5 * 4);
            if (tag0 == START_TAG) {
                // int tag6 = getLittleEndian32(xml, off + 6 * 4);
                int numbAttrs = getLittleEndian32(mData, off + 7 * 4);
                off += 9 * 4;
                String name = compXmlString(mData, sitOff, stOff, nameSi);
                Map<String, String> attrs = new HashMap<String, String>();
                for (int ii = 0; ii < numbAttrs; ii++) {
                    // int attrNameNsSi = getLittleEndian32(xml, off);
                    int attrNameSi = getLittleEndian32(mData, off + 1 * 4);
                    int attrValueSi = getLittleEndian32(mData, off + 2 * 4);
                    // int attrFlags = getLittleEndian32(xml, off + 3 * 4);
                    int attrResId = getLittleEndian32(mData, off + 4 * 4);
                    off += 5 * 4;
                    String attrName = compXmlString(mData, sitOff, stOff, attrNameSi);
                    String attrValue = attrValueSi != -1 ? compXmlString(mData, sitOff, stOff,
                            attrValueSi) :
                            "resourceID 0x" + Integer.toHexString(attrResId);
                    attrs.put(attrName, attrValue);
                }
                handler.onStartTag(name, attrs);
            } else if (tag0 == END_TAG) {
                off += 6 * 4;
                String name = compXmlString(mData, sitOff, stOff, nameSi);
                handler.onEndTag(name);
            } else if (tag0 == END_DOC_TAG) {
                break;
            } else {
                Log.v(this.getClass().getSimpleName(),
                        "Unrecognized tag code '" + Integer.toHexString(tag0) + "' at offset "
                                + off);
                break;
            }
        }
        Log.v(this.getClass().getSimpleName(), "end at offset " + off);
    }

    public interface EventHandler {
        public void onStartTag(String tag, Map<String, String> attrs);

        public void onEndTag(String tag);
    }

    private static String compXmlString(byte[] xml, int sitOff, int stOff, int strInd) {
        if (strInd < 0)
            return null;
        int strOff = stOff + getLittleEndian32(xml, sitOff + strInd * 4);
        return compXmlStringAt(xml, strOff);
    }

    private static String compXmlStringAt(byte[] arr, int strOff) {
        int strLen = arr[strOff + 1] << 8 & 0xff00 | arr[strOff] & 0xff;
        byte[] chars = new byte[strLen];
        for (int ii = 0; ii < strLen; ii++) {
            chars[ii] = arr[strOff + 2 + ii * 2];
        }
        return new String(chars);
    }

    private static int getLittleEndian32(byte[] arr, int off) {
        return arr[off + 3] << 24 & 0xff000000 | arr[off + 2] << 16 & 0xff0000
                | arr[off + 1] << 8 & 0xff00 | arr[off] & 0xFF;
    }
}
