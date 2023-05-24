package com.maykoll.integracionv5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

public class Formulario extends AppCompatActivity {

    TabLayout tablayout1;
    ViewPager2 viewpager1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        tablayout1 = findViewById(R.id.tablayout1);
        viewpager1 = findViewById(R.id.viewpager1);

        viewpager1.setAdapter(new AdaptadorFragment(getSupportFragmentManager(), getLifecycle()));
        //para seleccionar el fragment deslizando
        viewpager1.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tablayout1.selectTab(tablayout1.getTabAt(position));
            }
        });


        //para seleccionar el fragment clickando en la pestaña
        tablayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager1.setCurrentItem(tab.getPosition());

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
            // Vuelve a la clase de inicio (Activity Main)
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
    }

    static class AdaptadorFragment extends FragmentStateAdapter {

        public AdaptadorFragment(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new Fragmento1();
                case 1:
                    return new Fragmento2();
                default:
                    return new Fragmento3();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}