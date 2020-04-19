package com.ck19.infodroid;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipBoardUtil {
    /**
     * 获取剪切板内容
     */
    public static String paste(Context context){
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            if (manager.hasPrimaryClip() && manager.getPrimaryClip().getItemCount() > 0) {
                int item_count = manager.getPrimaryClip().getItemCount();
                 String addedTextString= "";
                for (int i = 0; i < item_count; i++) {
                    CharSequence addedText = manager.getPrimaryClip().getItemAt(i).getText();
                    addedTextString += String.valueOf(addedText);
                }
                return addedTextString;
            }
        }
        return "";
    }

    /**
     * 设置剪切板内容
     */
    public static void set(Context context, String new_string){
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("label", new_string);
        manager.setPrimaryClip(clipData);
    }

    /**
     * 剪切板监听器
     */
    public static void listen(final Context context){
        final ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

         final ClipboardManager.OnPrimaryClipChangedListener listener = new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                System.out.println("PrimaryClipChanged");
                set(context, "woshihuaidan");
                manager.removePrimaryClipChangedListener(this);
            }
        };

        manager.addPrimaryClipChangedListener(listener);
    }
}
