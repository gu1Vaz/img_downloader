package com.example.skindownloader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.skindownloader.Fragments.AllFragment;
import com.example.skindownloader.Fragments.Skin.S_FindById;
import com.example.skindownloader.Fragments.User.P_FindComunidadeFragment;
import com.example.skindownloader.Fragments.User.P_FindPerfilFragment;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    ActionBar ac ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ac = getSupportActionBar();
        assert ac != null;
        ac.setDisplayHomeAsUpEnabled(true);
        ac.setHomeAsUpIndicator(R.drawable.ic_menu);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView n =  findViewById(R.id.nav_view);
        n.setNavigationItemSelectedListener(this);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AllFragment(this,"POPULAR")).commit();
            n.setCheckedItem(R.id.nav_popular);
            ac.setTitle(R.string.popular);
        }
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @SuppressLint({"WrongConstant", "NonConstantResourceId"})
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_popular:
                ac.setTitle(R.string.popular);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AllFragment(this,"POPULAR")).commit();
                break;
            case R.id.nav_new:
                ac.setTitle(R.string.novas);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AllFragment(this,"NEW")).commit();
                break;
            case R.id.nav_S_id:
                item.setCheckable(true);
                ac.setTitle(R.string.byid);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new S_FindById(this)).commit();
                break;
            case R.id.nav_P_idPerfil:
                item.setCheckable(true);
                ac.setTitle(R.string.byidPerfil);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new P_FindPerfilFragment(this)).commit();
                break;
            case R.id.nav_P_idComunidade:
                item.setCheckable(true);
                ac.setTitle(R.string.byidComunidade);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new P_FindComunidadeFragment(this)).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            drawer.openDrawer(GravityCompat.START);
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}