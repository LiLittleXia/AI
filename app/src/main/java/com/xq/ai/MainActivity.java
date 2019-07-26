package com.xq.ai;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xq.ai.usb_driver.UsbSerialDriver;
import com.xq.ai.usb_driver.UsbSerialPort;
import com.xq.ai.usb_driver.UsbSerialProber;
import com.xq.zxing.CaptureActivity;
import com.xq.zxing.Intents;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> allDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager);
        UsbSerialDriver usbSerialDriver = allDrivers.get(0);
        UsbDevice usbDevice = usbSerialDriver.getDevice();
        UsbSerialPort port = usbSerialDriver.getPorts().get(0);
        UsbDeviceConnection usbDeviceConnection1 = usbManager.openDevice(usbDevice);
        try {
            port.open(usbDeviceConnection1);
            Log.e("lz","打开OK");
            byte[] a = getByteArray("01050000FF008C3A");
            port.write(a,1000);
        } catch (IOException e) {
            Log.e("lz","打开失败"+e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 把十六进制字符串还原成，字符串中每两个字符表示一个字节
     *
     * @param hexString
     * @return
     */
    public static byte[] getByteArray(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        for (int i = 0; i < CHARS.length; i++) {
            if (c == CHARS[i]) {
                return (byte) i;
            }
        }
        return Byte.MIN_VALUE;
    }

    private static final char[] CHARS = new char[]{'0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
}
