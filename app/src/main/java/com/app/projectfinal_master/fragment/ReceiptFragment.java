package com.app.projectfinal_master.fragment;

import static com.app.projectfinal_master.utils.Constant.CATEGORIES;
import static com.app.projectfinal_master.utils.Constant.PRODUCTS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.activity.CartActivity;
import com.app.projectfinal_master.activity.SearchActivity;
import com.app.projectfinal_master.adapter.CategoryAdapter;
import com.app.projectfinal_master.adapter.ProductFragmentAdapter;
import com.app.projectfinal_master.adapter.ReceiptAdapter;
import com.app.projectfinal_master.model.Category;
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.model.Receipt;
import com.app.projectfinal_master.utils.ICallbackActivity;
import com.app.projectfinal_master.utils.VolleySingleton;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReceiptFragment extends Fragment {
    private View view;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private Context context;
    private AHBottomNavigation bottomNavigation;
    private EditText edtSearch;

    private Receipt receipt;
    public List<Receipt> receipts = new ArrayList<>();;

    private RecyclerView rcvReceipt;
    private ReceiptAdapter receiptAdapter;
    private RecyclerView.LayoutManager receiptLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receipt, container, false);
        context = getContext();
        initView();
        setRcvReceipt();
        return view;
    }

    private void initView() {
        rcvReceipt = view.findViewById(R.id.rcv_receipt);
    }

    public void setDataReceipt(@NonNull List<Receipt> receipts) {
        if (receipts != null && context != null) {
            this.receipts = receipts;
            setRcvReceipt();
            addItem(receipts);
        }
    }

    private ICallbackActivity iCallbackActivity = new ICallbackActivity() {
        @Override
        public void callback(Object object) {

        }
    };

    private void setRcvReceipt() {
        receiptAdapter = new ReceiptAdapter(Receipt.itemCallback, context, iCallbackActivity);
        receiptLayout = new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false);
        rcvReceipt.setLayoutManager(receiptLayout);
        rcvReceipt.setAdapter(receiptAdapter);
    }

    private void addItem(List<Receipt> receipts) {
//        if (product.getIdCategory() == category.getId() && product.getSex().equals(category.getSex())) {
        receiptAdapter.submitList(receipts);
//        }
    }


    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        Intent data = result.getData();

                    }
                }
            });
}