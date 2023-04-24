package com.app.projectfinal_master.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.adapter.AddressAdapter;
import com.app.projectfinal_master.model.Address;
import com.app.projectfinal_master.utils.ICallbackActivity;
import com.app.projectfinal_master.utils.ReadJSONExample;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddressActivity extends AppCompatActivity {

    private AddressAdapter addressAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView rcvAddress;


    private Address address = new Address();

    private TextView tvReset;
    private TextView tvAddress;

    private TextView tvCity;
    private TextView tvDistrict;
    private TextView tvCommune;

    private LinearLayoutCompat layoutAddress;
    private LinearLayoutCompat layoutCity;
    private LinearLayoutCompat layoutDistrict;
    private LinearLayoutCompat layoutCommune;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initView();
        setViewRcv();
        hideLayoutAddress();
        getCityList();
        setOnClickReset();
        eventClickCityLayout();
        eventClickDistrictLayout();
        eventClickCommuneLayout();
    }

    private void initView() {
        rcvAddress = findViewById(R.id.rcv_address);

        layoutAddress = findViewById(R.id.layout_address);
        layoutCity = findViewById(R.id.layout_city);
        layoutDistrict = findViewById(R.id.layout_district);
        layoutCommune = findViewById(R.id.layout_commune);

        tvReset = findViewById(R.id.tv_reset);
        tvAddress = findViewById(R.id.tv_update_address);

        tvCity = findViewById(R.id.tv_city);
        tvDistrict = findViewById(R.id.tv_district);
        tvCommune = findViewById(R.id.tv_commune);
    }

    private void hideLayoutAddress() {
        layoutAddress.setVisibility(View.GONE);
    }

    private void setViewRcv() {
        rcvAddress.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        layoutManager = new GridLayoutManager(this, 1);
        addressAdapter = new AddressAdapter(Address.itemCallback, this, iCallbackActivity);
        rcvAddress.setLayoutManager(layoutManager);
        rcvAddress.setAdapter(addressAdapter);
    }

    private void eventClickCityLayout() {
        layoutCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                address.setDistrict(null);
//                address.setCommune(null);
                getCityList();
                updateView(address);
                changeLayoutTextColor("city");
            }
        });
    }

    private void eventClickDistrictLayout() {
        layoutDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                address.setCommune(null);
                getDistrictList(address.getCityCode());
                updateView(address);
                changeLayoutTextColor("district");
            }
        });
    }

    private void eventClickCommuneLayout() {
        layoutCommune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                address.setCommune(null);
                getCommuneList(address.getDistrictCode());
                updateView(address);
                changeLayoutTextColor("commune");
            }
        });
    }

    private void changeLayoutTextColor(String layout) {
        if (layout.equalsIgnoreCase("city")) {
            tvCity.setTextColor(getColor(R.color.red));
            tvDistrict.setTextColor(getColor(R.color.black));
            tvCommune.setTextColor(getColor(R.color.black));
        }
        if (layout.equalsIgnoreCase("district")) {
            tvCity.setTextColor(getColor(R.color.black));
            tvDistrict.setTextColor(getColor(R.color.red));
            tvCommune.setTextColor(getColor(R.color.black));
        }
        if (layout.equalsIgnoreCase("commune")) {
            tvCity.setTextColor(getColor(R.color.black));
            tvDistrict.setTextColor(getColor(R.color.black));
            tvCommune.setTextColor(getColor(R.color.red));
        }
    }

    private final ICallbackActivity iCallbackActivity = new ICallbackActivity() {
        @Override
        public void callback(Object object) {
            Address addressTmp = (Address) object;
            updateAddress(addressTmp);
            updateView(addressTmp);
        }
    };

    private void updateAddress(Address addressTmp) {
        if (addressTmp.getCity() != null) {
            address.setCity(addressTmp.getCity());
            address.setCityCode(addressTmp.getCityCode());
            getDistrictList(addressTmp.getCityCode());
        }
        if (addressTmp.getDistrict() != null) {
            address.setDistrict(addressTmp.getDistrict());
            address.setDistrictCode(addressTmp.getDistrictCode());
            getCommuneList(addressTmp.getDistrictCode());
        }
        if (addressTmp.getCommune() != null) {
            address.setCommune(addressTmp.getCommune());
        }
    }

    private void updateView(Address addressTmp) {
        if (Objects.equals(address, new Address())) {
            layoutAddress.setVisibility(View.GONE);
            return;
        }
        layoutAddress.setVisibility(View.VISIBLE);
        updateViewLayoutCity(addressTmp);
        updateViewLayoutDistrict(addressTmp);
        updateViewLayoutCommune(addressTmp);
    }

    private void updateViewLayoutCity(Address addressTmp) {
        if (address.getCity() != null || addressTmp.getCity() != null) {
            layoutCity.setVisibility(View.VISIBLE);
            tvCity.setText(address.getCity());
        } else {
            layoutCity.setVisibility(View.GONE);
        }
    }

    private void updateViewLayoutDistrict(Address addressTmp) {
        if (addressTmp.getCity() != null && addressTmp.getDistrict() == null) {
            layoutDistrict.setVisibility(View.VISIBLE);
            tvDistrict.setText("Chọn Quận/Huyện");
            tvDistrict.setTextColor(getColor(R.color.red));
        } else if (address.getDistrict() != null) {
            layoutDistrict.setVisibility(View.VISIBLE);
            tvDistrict.setText(address.getDistrict());
            tvDistrict.setTextColor(getColor(R.color.black));
        } else {
            layoutDistrict.setVisibility(View.GONE);
        }
    }

    private void updateViewLayoutCommune(Address addressTmp) {
        if (addressTmp.getDistrict() != null && addressTmp.getCommune() == null) {
            layoutCommune.setVisibility(View.VISIBLE);
            tvCommune.setText("Chọn Xã/Phường");
            tvCommune.setTextColor(getColor(R.color.red));
        } else if (address.getCommune() != null) {
            layoutCommune.setVisibility(View.VISIBLE);
            tvCommune.setText(address.getCommune());
            tvCommune.setTextColor(getColor(R.color.black));
            setCallbackActivity();
        } else {
            layoutCommune.setVisibility(View.GONE);
        }
    }

    private void setCallbackActivity() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", address);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    private void setOnClickReset() {
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address = new Address();
                getCityList();
                updateView(address);
            }
        });
    }

    private void getCityList() {
        List<Address> addressList = new ArrayList<>();
        try {
            List<String> cityList = ReadJSONExample.getCity();
            int i = 0;
            for (String city :
                    cityList) {
                Address address = new Address();
                address.setCity(city);
                address.setCityCode(ReadJSONExample.cityCoded.get(i));
                addressList.add(address);
                i++;
            }
            addressAdapter.submitList(addressList);
            tvAddress.setText("Tỉnh/Thành phố");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDistrictList(String cityCoded) {
        List<Address> addressList = new ArrayList<>();
        try {
            List<String> districtList = ReadJSONExample.getDistrictWithCode(cityCoded);
            int i = 0;
            for (String district :
                    districtList) {
                Address address = new Address();
                address.setDistrict(district);
                address.setDistrictCode(ReadJSONExample.districtCoded.get(i));
                addressList.add(address);
                i++;
            }
            addressAdapter.submitList(addressList);
            tvAddress.setText("Quận/Huyện");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCommuneList(String districtCoded) {
        List<Address> addressList = new ArrayList<>();
        try {
            List<String> communeList = ReadJSONExample.getCommuneWithCode(districtCoded);
            for (String commune :
                    communeList) {
                Address address = new Address();
                address.setCommune(commune);
                addressList.add(address);
            }
            addressAdapter.submitList(addressList);
            tvAddress.setText("Xã/Phường");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}