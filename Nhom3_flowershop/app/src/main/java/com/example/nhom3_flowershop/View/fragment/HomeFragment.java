package com.example.nhom3_flowershop.View.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom3_flowershop.R;
import com.example.nhom3_flowershop.View.DanhMucActivity;
import com.example.nhom3_flowershop.View.GioHangActivity;
import com.example.nhom3_flowershop.View.ChiTietSPActivity;
import com.example.nhom3_flowershop.View.GioHangActivity;
import com.example.nhom3_flowershop.View.MainActivity;
import com.example.nhom3_flowershop.View.TimKiemActivity;
import com.example.nhom3_flowershop.adapter.DanhGiaAdapter;
import com.example.nhom3_flowershop.adapter.SanPhamAdapter;
import com.example.nhom3_flowershop.adapter.SanPhamDanhMucAdapter;
import com.example.nhom3_flowershop.model.SanPham;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements SanPhamAdapter.ItemClickListener{

    private ArrayList<SanPham> listSanPham;
    private ArrayList<SanPham> listSanPhamBanChay;
    List<String> listSP;
    SanPhamAdapter sanPhamBanChayAdapter ;
    SanPhamDanhMucAdapter sanPhamAdapter;
    RecyclerView rcTopBanChay,rcGoiY;
    ViewFlipper anhquangcao;
    View view;
    public static TextView tvSoLuongGioHang_home;
    ImageView img_GioHang, img_TimKiem;
    public static AutoCompleteTextView tv_TimKiem;
    LinearLayout hoamuaxuan,hoamuahe,hoamuathu,hoamuadong,hoadaingay,hoanganngay,hoathango,hoathanco;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_home, container, false);
        init();
        getSanPham();
        getSanPhamBanChay();
        ChuyenDenGioHang();
        DanhMuc();
        TimKiem();
        getSoLuongGiohang();
        //Toast.makeText(getContext(),listSanPham.size()+"",Toast.LENGTH_SHORT).show();
        return view;

    }
    private void init(){
        rcTopBanChay = view.findViewById(R.id.rcTopBanChay);
        rcGoiY= view.findViewById(R.id.rcGoiY);
        img_GioHang= view.findViewById(R.id.img_gioHang);
        hoamuaxuan=view.findViewById(R.id.hoamuaxuan);
        hoamuahe = view.findViewById(R.id.hoamuahe);
        hoamuathu=view.findViewById(R.id.hoamuathu);
        hoamuadong = view.findViewById(R.id.hoamuadong);
        hoadaingay = view.findViewById(R.id.hoadaingay);
        hoanganngay = view.findViewById(R.id.hoanganngay);
        hoathango = view.findViewById(R.id.hoathango);
        hoathanco = view.findViewById(R.id.hoathanco);
        tv_TimKiem = view.findViewById(R.id.tv_TimKiem);
        img_TimKiem = view.findViewById(R.id.img_TimKiem);
        tvSoLuongGioHang_home = view.findViewById(R.id.tvSoLuongGioHang_home);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        rcTopBanChay.setLayoutManager(linearLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        rcGoiY.setLayoutManager(gridLayoutManager);

        listSanPham = new ArrayList<>();
        listSanPhamBanChay= new ArrayList<>();

        sanPhamAdapter = new SanPhamDanhMucAdapter(listSanPham, this::onItemClick,getActivity());
        sanPhamBanChayAdapter = new SanPhamAdapter(listSanPhamBanChay,this,getActivity());

        rcTopBanChay.setAdapter(sanPhamBanChayAdapter);
        rcGoiY.setAdapter(sanPhamAdapter);
        rcGoiY.setHasFixedSize(true);

        listSP = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item, listSP);
        tv_TimKiem.setAdapter(adapter);

        int img[] = {R.drawable.poster1, R.drawable.poster2, R.drawable.poster3, R.drawable.poster4,R.drawable.poster5,R.drawable.poster6};
        for (int i = 0; i < img.length; i++) {
            flipperImage(img[i]);
        }
    }

    private void getSanPham( ){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("sanpham");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SanPham sanPham = dataSnapshot.getValue(SanPham.class);
                    listSanPham.add(sanPham);
                    listSP.add(sanPham.getTenSP());
                }
                sanPhamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Get Book Fail!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getSanPhamBanChay( ){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("sanpham");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SanPham sanPham = dataSnapshot.getValue(SanPham.class);
                    if(sanPham.getSaoDanhGia()>4.0){
                        listSanPhamBanChay.add(sanPham);
                    }
                }
                sanPhamBanChayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Get Book Fail!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void flipperImage(int image) {
        anhquangcao=view.findViewById(R.id.anhquangcao);
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);
        anhquangcao.addView(imageView);
        anhquangcao.setFlipInterval(3000);
        anhquangcao.setAutoStart(true);
        Animation animation_slide_in= AnimationUtils.loadAnimation(getContext().getApplicationContext(),android.R.anim.slide_in_left);
        Animation animation_slide_out= AnimationUtils.loadAnimation(getContext().getApplicationContext(),android.R.anim.slide_out_right);
        anhquangcao.setInAnimation(animation_slide_in);
        anhquangcao.setOutAnimation(animation_slide_out);
    }
    private void ChuyenDenGioHang(){
        img_GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                if(user == null){
                    Toast.makeText(getContext(),"Vui lòng đăng nhập để xem giỏ hàng",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void DanhMuc(){
        hoamuaxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhMucActivity.class);
                intent.putExtra("maDanhMuc", "dm01");
                startActivity(intent);
            }
        });
        hoamuahe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhMucActivity.class);
                intent.putExtra("maDanhMuc", "dm02");
                startActivity(intent);
            }
        });
        hoamuathu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhMucActivity.class);
                intent.putExtra("maDanhMuc", "dm03");
                startActivity(intent);
            }
        });
        hoamuadong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhMucActivity.class);
                intent.putExtra("maDanhMuc", "dm04");
                startActivity(intent);
            }
        });
        hoadaingay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhMucActivity.class);
                intent.putExtra("maDanhMuc", "dm05");
                startActivity(intent);
            }
        });
        hoanganngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhMucActivity.class);
                intent.putExtra("maDanhMuc", "dm06");
                startActivity(intent);
            }
        });
        hoathango.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhMucActivity.class);
                intent.putExtra("maDanhMuc", "dm07");
                startActivity(intent);
            }
        });
        hoathanco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhMucActivity.class);
                intent.putExtra("maDanhMuc", "dm08");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(SanPham sanPham) {
        Intent intent = new Intent(getActivity(), ChiTietSPActivity.class);
        intent.putExtra("maSP", sanPham.getIdSp()+"");
        startActivity(intent);
    }
    private void TimKiem(){
        img_TimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TimKiemActivity.class);
                startActivity(intent);
            }
        });
    }
    public static String tv_TimKiem(){
        return tv_TimKiem.getText().toString().trim();
    }

    public static void getSoLuongGiohang(){
        int SoLuong = 0;
        for(int i = 0; i< MainActivity.listGioHang.size(); i++){
            SoLuong += MainActivity.listGioHang.get(i).getSoluong();
        }
        tvSoLuongGioHang_home.setText(SoLuong+"");
    }
}