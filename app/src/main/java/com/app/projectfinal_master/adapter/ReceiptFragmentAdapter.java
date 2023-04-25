package com.app.projectfinal_master.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.app.projectfinal_master.fragment.ReceiptFragment;
import com.app.projectfinal_master.model.Receipt;
import com.app.projectfinal_master.utils.ICallbackActivity;

import java.util.ArrayList;
import java.util.List;

public class ReceiptFragmentAdapter extends MyFragmentAdapter {

    private ReceiptFragment allReceiptFragment = new ReceiptFragment();
    private ReceiptFragment processingReceiptFragment = new ReceiptFragment();
    private ReceiptFragment deliveringReceiptFragment = new ReceiptFragment();
    private ReceiptFragment deliveredReceiptFragment = new ReceiptFragment();
    private ReceiptFragment canceledReceiptFragment = new ReceiptFragment();
    private ReceiptFragment returnsReceiptFragment = new ReceiptFragment();
    private ICallbackActivity iCallbackActivity;
    private List<Receipt> receipts = new ArrayList<>();

    public ReceiptFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void setDataReceipt(List<Receipt> receipts) {
        this.receipts = receipts;
        allReceiptFragment.setDataReceipt(receipts);
        for (Receipt receipt :
                receipts) {
            if (receipt.getReceiptStatus().equalsIgnoreCase("Đang xử lý")) {
                processingReceiptFragment.setDataReceipt(receipts);
            } else if (receipt.getReceiptStatus().equalsIgnoreCase("Đang giao hàng")) {
                deliveringReceiptFragment.setDataReceipt(receipts);
            } else if (receipt.getReceiptStatus().equalsIgnoreCase("Đã giao hàng")) {
                deliveredReceiptFragment.setDataReceipt(receipts);
            } else if (receipt.getReceiptStatus().equalsIgnoreCase("Đã huỷ")) {
                canceledReceiptFragment.setDataReceipt(receipts);
            } else {
                returnsReceiptFragment.setDataReceipt(receipts);
            }
        }
    }

    public void setCallbackActivity(ICallbackActivity callbackActivity) {
        this.iCallbackActivity = callbackActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
//        allReceiptFragment = new ReceiptFragment();
//        processingReceiptFragment = new ReceiptFragment();
//        deliveringReceiptFragment = new ReceiptFragment();
//        deliveredReceiptFragment = new ReceiptFragment();
//        canceledReceiptFragment = new ReceiptFragment();
//        returnsReceiptFragment = new ReceiptFragment();
//        if (position == 1) {
//            return processingReceiptFragment;
//        } if (position == 2) {
//            return deliveringReceiptFragment;
//        }  if (position == 3) {
//            return deliveredReceiptFragment;
//        }  if (position == 4) {
//            return canceledReceiptFragment;
//        }  if (position == 5) {
//            return returnsReceiptFragment;
//        }
//            allReceiptFragment.setDataReceipt(receipts);
            return allReceiptFragment;

    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
