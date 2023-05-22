package com.app.projectfinal_master.activity;

import static com.app.projectfinal_master.utils.Constant.COLOR;
import static com.app.projectfinal_master.utils.Constant.FAVORITE;
import static com.app.projectfinal_master.utils.Constant.INSERT_CART;
import static com.app.projectfinal_master.utils.Constant.PRODUCT_DETAIL;
import static com.app.projectfinal_master.utils.Constant.SIZE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.adapter.ColorAdapter;
import com.app.projectfinal_master.adapter.ImageAdapter;
import com.app.projectfinal_master.adapter.SizeAdapter;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.model.Address;
import com.app.projectfinal_master.model.Cart;
import com.app.projectfinal_master.model.Color;
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.model.Size;
import com.app.projectfinal_master.utils.AnimationUtil;
import com.app.projectfinal_master.utils.ArrayUtil;
import com.app.projectfinal_master.utils.CheckStateNetwork;
import com.app.projectfinal_master.utils.ICallbackActivity;
import com.app.projectfinal_master.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class DetailProductActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private ProgressBar progressBar;
    private RatingBar ratingBar;

    private StringRequest mStringRequest;
    private Product detailProduct;

    private Cart cart;
    private Size size;
    private Color color;
    private List<Cart> carts = new ArrayList<>();
    private List<Product> products = new ArrayList<>();

    private ViewPager2 viewPager2;
    private RecyclerView rcvSize, rcvColor;
    private RecyclerView.Adapter sizeAdapter, colorAdapter;
    private RecyclerView.LayoutManager sizeLayout, colorLayout;

    private TextView tvName, tvPrice, tvDescribeContent;
    private Button btnAddCart;
    private EditText edtSearch;
    private ImageView imgAddFavorite, imgReturn, imgViewAnim, imgStartAnim, imgBack;
    private View imgEndAnim;
    private Handler setDelay = new Handler();

    List<Size> sizeList = new ArrayList<>();
    List<Color> colorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        initView();
        getProductDetail(this, getCodeProduct());
        setViewActionBar();
        setEventClickButtonBuy();
        setEventClickButtonFavorite();
        eventClickBackImg();
        onClickEdtSearch();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvName = (TextView) findViewById(R.id.tv_product_name);
        edtSearch = findViewById(R.id.edt_search);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        viewPager2 = (ViewPager2) findViewById(R.id.vp2_img_details);
//        imgAddFavorite = (ImageView) findViewById(R.id.img_add_favorite);
        imgStartAnim = (ImageView) findViewById(R.id.img_start_anim);
        imgViewAnim = (ImageView) findViewById(R.id.img_view_anim);
        imgBack = (ImageView) findViewById(R.id.img_back);
        btnAddCart = (Button) findViewById(R.id.btn_add_cart);
        progressBar = findViewById(R.id.progress);
        ratingBar = findViewById(R.id.ratingBar);
        rcvSize = findViewById(R.id.rcv_size);
        rcvColor = findViewById(R.id.rcv_color);
        tvDescribeContent = findViewById(R.id.tv_describe_content);
    }

    private void eventClickBackImg() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void onClickEdtSearch() {
        edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someActivityResultLauncher.launch(new Intent(DetailProductActivity.this, SearchActivity.class));
            }
        });
    }

    private List<Cart> getCartDataLocal() {
        return DataLocalManager.getCarts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_product, menu);
        imgEndAnim = findViewById(menu.getItem(0).getItemId());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.act_cart:
                startActivity(new Intent(DetailProductActivity.this, CartActivity.class));
                finish();
                break;
//            case R.id.a:
//
//                break;
//            case R.id.b:
//
//                break;
//            case R.id.c:
//
//                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setViewActionBar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(null);
    }

    private String getCodeProduct() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return null;
        String codeProduct = bundle.getString("code_product");
        return codeProduct;
    }

    private void setEventClickButtonFavorite() {
//        imgAddFavorite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (DataLocalManager.getUser() != null)
//                    addFavorite();
//                else
//                    someActivityResultLauncher.launch(new Intent(DetailProductActivity.this, MainActivity.class));
//            }
//        });
    }


    private void setEventClickButtonBuy() {
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (size == null || color == null) {
                    Toast.makeText(DetailProductActivity.this, "Vui lòng chọn thông tin sản phẩm!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!CheckStateNetwork.isNetworkAvailable(DetailProductActivity.this)) {
                    Toast.makeText(DetailProductActivity.this, "Mất kết nối! Xin vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                    return;
                }
                btnAddCart.setEnabled(false);
                cart = new Cart(detailProduct, detailProduct.getIdColor(), detailProduct.getIdSize(), 1);
                addCart(DetailProductActivity.this, getCartDataLocal(), cart);
                AnimationUtil.translateAnimation(imgViewAnim, imgStartAnim, imgEndAnim, new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                Runnable startDelay = new Runnable() {
                    @Override
                    public void run() {
                        btnAddCart.setEnabled(true);
                    }
                };
                setDelay.postDelayed(startDelay, AnimationUtil.ANIMATION_DURATION + 100);
            }
        });
    }

    public void addCart(Context context, List<Cart> carts, Cart cart) {
        if (carts != null) {
            for (int i = 0; i < carts.size(); i++) {
                if (Objects.equals(cart.getProduct().getCodeProduct(), carts.get(i).getProduct().getCodeProduct())
                && Objects.equals(cart.getProduct().getColor(), carts.get(i).getProduct().getColor())
                && Objects.equals(cart.getProduct().getSize(), carts.get(i).getProduct().getSize())) {
                    carts.get(i).setQuantity(carts.get(i).getQuantity() + 1);
                    addCart(context, cart.getProduct().getCodeProduct(), cart.getProduct().getIdColor(), cart.getProduct().getIdSize(), carts.get(i).getQuantity());
                    DataLocalManager.changeDataCart(carts.get(i), i);
                    return;
                }
            }
            carts.add(cart);
            DataLocalManager.addCarts(carts);
            if (DataLocalManager.getUser() != null)
                addCart(context, cart.getProduct().getCodeProduct(), cart.getProduct().getIdColor(), cart.getProduct().getIdSize(), 1);
        }
    }

    public void addCart(Context context, String codeProduct, int idColor, int idSize, int quantity) {
        if (DataLocalManager.getUser() != null) {
            mStringRequest = new StringRequest(Request.Method.POST, INSERT_CART, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
//                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id_user", DataLocalManager.getUser().getIdUser());
                    params.put("code_product", codeProduct);
                    params.put("id_color", String.valueOf(idColor));
                    params.put("id_size", String.valueOf(idSize));
                    params.put("quantity", String.valueOf(quantity));
                    return params;
                }
            };

            mStringRequest.setShouldCache(false);
            VolleySingleton.getInstance(context).getRequestQueue().add(mStringRequest);
        }
    }

    public void getProductDetail(Context context, String codeProduct) {
        progressBar.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.POST, PRODUCT_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        int idColor = object.getInt("id_color");
                        int idSize = object.getInt("id_size");
                        String name = object.getString("name");
                        String sex = object.getString("sex");
                        String imageThumb = object.getString("image_thumb");
                        JSONArray imageLarge = object.getJSONArray("image_large");
                        String sellingPrice = object.getString("selling_price");
                        int quantity = object.getInt("quantity");
                        double rate = object.getDouble("rate");
                        String description = object.getString("description");
                        int discount = object.getInt("discount");
                        getSize(context, idSize);
                        getColor(context, idColor);
                        detailProduct = new Product(codeProduct, idColor, idSize, name, sex, imageThumb, imageLarge, sellingPrice, quantity, rate, description, discount);
                        products.add(detailProduct);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
                setViewProductDetail(detailProduct);
                setRattingBarCount((float) detailProduct.getRate());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("code_product", codeProduct);
                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(DetailProductActivity.this).getRequestQueue().add(mStringRequest);
    }

    public void getSize(Context context, int idSize) {
        progressBar.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.POST, SIZE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        int id = object.getInt("id_size");
                        String size = object.getString("size");
                        Size sizeModel = new Size(id, size);
                        for (Size sizeTmp :
                                sizeList) {
                            if (sizeTmp.getSize().equals(size)) {
                                return;
                            }
                        }
                        sizeList.add(sizeModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
                getSizeProductData(sizeList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_size", String.valueOf(idSize));
                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(DetailProductActivity.this).getRequestQueue().add(mStringRequest);
    }

    public void getColor(Context context, int idColor) {
        progressBar.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.POST, COLOR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        int id = object.getInt("id_color");
                        String color = object.getString("color");
                        String code = object.getString("code");
                        Color colorModel = new Color(id, color, code);
                        for (Color colorTmp :
                                colorList) {
                            if (colorTmp.getColor().equals(color)) {
                                return;
                            }
                        }
                        colorList.add(colorModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
                getColorProductData(colorList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_color", String.valueOf(idColor));
                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(DetailProductActivity.this).getRequestQueue().add(mStringRequest);
    }

    private void setViewProductDetail(Product product) {
        try {
            tvName.setText(product.getName());
            tvDescribeContent.setText(product.getDescription());
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            tvPrice.setText(String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(product.getSellingPrice())))));
            viewPager2.setAdapter(new ImageAdapter(DetailProductActivity.this, getListImageLarge(), viewPager2));
            viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        } catch (Exception e) {

        }

    }

    private final ICallbackActivity iCallbackActivitySize = new ICallbackActivity() {
        @Override
        public void callback(Object object) {
            size = (Size) object;
            detailProduct.setIdSize(size.getId());
            detailProduct.setSize(size.getSize());
        }
    };

    private final ICallbackActivity iCallbackActivityColor = new ICallbackActivity() {
        @Override
        public void callback(Object object) {
            color =  (Color)object;
            detailProduct.setIdColor(color.getId());
            detailProduct.setColor(color.getColor());
        }
    };

    private void setRattingBarCount(float rate) {
        ratingBar.setRating(rate);
    }

    private void getSizeProductData(List<Size> sizeList) {
        sizeAdapter = new SizeAdapter(DetailProductActivity.this, sizeList, iCallbackActivitySize);
        sizeLayout = new GridLayoutManager(DetailProductActivity.this, 1, GridLayoutManager.HORIZONTAL, false);
        rcvSize.setLayoutManager(sizeLayout);
        rcvSize.setAdapter(sizeAdapter);
    }

    private void getColorProductData(List<Color> colorList) {
        colorAdapter = new ColorAdapter(DetailProductActivity.this, colorList, iCallbackActivityColor);
        colorLayout = new GridLayoutManager(DetailProductActivity.this, 1, GridLayoutManager.HORIZONTAL, false);
        rcvColor.setLayoutManager(colorLayout);
        rcvColor.setAdapter(colorAdapter);
    }

    private void addFavorite() {
        progressBar.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.POST, FAVORITE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DetailProductActivity.this, "Vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("id_user", DataLocalManager.getUser().getIdUser());
                params.put("code_product", detailProduct.getCodeProduct());

                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(DetailProductActivity.this).getRequestQueue().add(mStringRequest);
    }

    private List<String> getListImageLarge() {
        List<Object> img = ArrayUtil.convert(detailProduct.getImageLarge());

        List<String> imageItems = new ArrayList<>();
        for (Object item : img) {
            imageItems.add(item.toString());
        }
        return imageItems;
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