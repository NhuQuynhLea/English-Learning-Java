package com.example.englishlearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.englishlearning.databinding.ActivityMainBinding;
import com.example.englishlearning.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private int startDestinationId = 0;
    private int endDestinationId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomLayout.setVisibility(View.VISIBLE);
        BottomNavigationView bottomNavigationView = binding.bottomNavigation;

//        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment_container);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_container_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("startDestination", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("startDestination", startDestinationId);
        editor.apply();
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.homeFragment) {
                // Show the bottom navigation bar when on the home destination
                binding.bottomLayout.setVisibility(View.VISIBLE);
            }else if(destination.getId() == R.id.myListFragment){
                binding.bottomLayout.setVisibility(View.VISIBLE);
            }else if(destination.getId() == R.id.profileFragment ){
                binding.bottomLayout.setVisibility(View.VISIBLE);
            }else if(destination.getId() == R.id.splashFragment ){
                binding.bottomLayout.setVisibility(View.VISIBLE);
            }
            else {
                // Hide the bottom navigation bar for other destinations
                binding.bottomLayout.setVisibility(View.INVISIBLE);
            }
        });
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
                if(item.getItemId() == R.id.myList){
                    startDestinationId = endDestinationId;
                    endDestinationId = 1;
                    editor.putInt("startDestination", endDestinationId);
                    editor.apply();
                routeDestination(startDestinationId,endDestinationId);
                 }
                if(item.getItemId() == R.id.myhome){
                    startDestinationId = endDestinationId;
                    endDestinationId = 0;
                    editor.putInt("startDestination", endDestinationId);
                    editor.apply();
                    routeDestination(startDestinationId,endDestinationId);
                }
                if(item.getItemId() == R.id.profile){
                    startDestinationId = endDestinationId;
                    endDestinationId = 3;
                    editor.putInt("startDestination", endDestinationId);
                    editor.apply();
                    routeDestination(startDestinationId,endDestinationId);
                }
            if(item.getItemId() == R.id.chatbot){
                startDestinationId = endDestinationId;
                endDestinationId = 2;
                editor.putInt("startDestination", endDestinationId);
                editor.apply();
                routeDestination(startDestinationId,endDestinationId);
            }
            return true;
        });
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                binding.bottomLayout.setVisibility(View.INVISIBLE);
               if(endDestinationId == 0)
                Navigation.findNavController(MainActivity.this,R.id.nav_host_container_fragment).navigate(R.id.action_homeFragment_to_createModuleFragment);
                else if(endDestinationId == 1){
                   Navigation.findNavController(MainActivity.this,R.id.nav_host_container_fragment).navigate(R.id.action_myListFragment_to_createModuleFragment);
               }else if(endDestinationId == 3){
                   Navigation.findNavController(MainActivity.this,R.id.nav_host_container_fragment).navigate(R.id.action_profileFragment_to_createModuleFragment);
               }else{
                   Navigation.findNavController(MainActivity.this,R.id.nav_host_container_fragment).navigate(R.id.action_splashFragment_to_createModuleFragment);
               }

            }
        });




    }

    private void routeDestination(int startDestinationId,int endDestinationId){

        if(startDestinationId == 0 && endDestinationId == 1){
            Navigation.findNavController(this,R.id.nav_host_container_fragment).navigate(R.id.action_homeFragment_to_myListFragment);
        }
        else if(startDestinationId == 0 && endDestinationId == 2){
            Navigation.findNavController(this,R.id.nav_host_container_fragment).navigate(R.id.action_homeFragment_to_splashFragment);
        }
        else if(startDestinationId == 0 && endDestinationId == 3){
            Navigation.findNavController(this,R.id.nav_host_container_fragment).navigate(R.id.action_homeFragment_to_profileFragment);
        }
        else if(startDestinationId == 1 && endDestinationId == 0){
            Navigation.findNavController(this,R.id.nav_host_container_fragment).navigate(R.id.action_myListFragment_to_homeFragment);
        }
        else if(startDestinationId == 1 && endDestinationId == 2){
            Navigation.findNavController(this,R.id.nav_host_container_fragment).navigate(R.id.action_myListFragment_to_splashFragment);
        }
        else if(startDestinationId == 1 && endDestinationId == 3){
            Navigation.findNavController(this,R.id.nav_host_container_fragment).navigate(R.id.action_myListFragment_to_profileFragment);
        }
        else if(startDestinationId == 2 && endDestinationId == 0){
            Navigation.findNavController(this,R.id.nav_host_container_fragment).navigate(R.id.action_splashFragment_to_homeFragment);
        }
        else if(startDestinationId == 2 && endDestinationId == 1){
            Navigation.findNavController(this,R.id.nav_host_container_fragment).navigate(R.id.action_splashFragment_to_myListFragment);
        }
        else if(startDestinationId == 2 && endDestinationId == 3){
            Navigation.findNavController(this,R.id.nav_host_container_fragment).navigate(R.id.action_splashFragment_to_profileFragment);
        }
        else if(startDestinationId == 3 && endDestinationId == 0){
            Navigation.findNavController(this,R.id.nav_host_container_fragment).navigate(R.id.action_profileFragment_to_homeFragment);
        }
        else if(startDestinationId == 3 && endDestinationId == 1){
            Navigation.findNavController(this,R.id.nav_host_container_fragment).navigate(R.id.action_profileFragment_to_myListFragment);
        }
        else if(startDestinationId == 3 && endDestinationId == 2){
            Navigation.findNavController(this,R.id.nav_host_container_fragment).navigate(R.id.action_profileFragment_to_splashFragment);
        }


    }

}