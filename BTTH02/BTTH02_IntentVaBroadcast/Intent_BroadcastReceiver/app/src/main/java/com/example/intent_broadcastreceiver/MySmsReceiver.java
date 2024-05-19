package com.example.intent_broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MySmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        processReceive(context,intent);
    }

    public void processReceive(Context context, Intent intent){
        // Lấy tất cả các thông tin đính kèm từ Intent.
        Bundle extras = intent.getExtras();
        String message = "";
        String body = "";
        String address = "";
        if(extras != null){
            // Lấy mảng các đối tượng PDU từ extras. PDU là định dạng tin nhắn SMS.
            Object[] smsExtra = (Object[]) extras.get("pdus");
            // Duyệt qua từng PDU trong mảng.
            for(int i = 0; i < smsExtra.length; i++){
                // Tạo đối tượng SmsMessage từ PDU hiện tại.
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsExtra[i]);
                // Lấy nội dung tin nhắn.
                body = sms.getMessageBody();
                // Lấy địa chỉ gửi tin nhắn.
                address = sms.getOriginatingAddress();
                message += body;
            }
            message = "Có 1 tin nhắn từ " + address + "\n" + message + "\ngửi đến";
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }
}
//Một tin nhắn SMS đơn lẻ sử dụng bảng mã GSM 7-bit có thể chứa tối đa 160 ký tự.
//Nếu sử dụng bảng mã UCS-2 (Unicode), một tin nhắn SMS có thể chứa tối đa 70 ký tự.