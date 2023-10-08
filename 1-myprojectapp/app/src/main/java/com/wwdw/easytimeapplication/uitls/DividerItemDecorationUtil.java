package com.wwdw.easytimeapplication.uitls;

import android.content.Context;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

public class DividerItemDecorationUtil extends Y_DividerItemDecoration {
    private boolean isPackage;
    private int width, height;

    public DividerItemDecorationUtil(Context context, boolean pac) {
        super(context);
        isPackage = pac;
        if (isPackage) {
            width = 5;
            height = 10;
        } else {
            width=0;
            height = 10;
        }
    }



    @Override
    public Y_Divider getDivider(int itemPosition) {
        Y_Divider divider = null;
        switch (itemPosition % 2) {

            case 0:
                //每一行第一个显示rignt和bottom
                divider = new Y_DividerBuilder()
                        .setRightSideLine(true, 0x00F6F6F6, width, 0, 0)
                        .setBottomSideLine(true, 0x00F6F6F6, height, 0, 0)
                        .create();
                break;
            case 1:
                //第二个显示Left和bottom
                divider = new Y_DividerBuilder()
                        .setLeftSideLine(true, 0x00F6F6F6, width, 0, 0)
                        .setBottomSideLine(true, 0x00F6F6F6, height, 0, 0)
                        .create();
                break;
            default:
                break;
        }
        return divider;
    }
}


