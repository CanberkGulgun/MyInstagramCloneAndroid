package com.canberkgulgun.instagramcloneandroid.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.canberkgulgun.instagramcloneandroid.R;
import com.canberkgulgun.instagramcloneandroid.adapter.PostAdapter;
import com.canberkgulgun.instagramcloneandroid.databinding.ActivityFeedBinding;
import com.canberkgulgun.instagramcloneandroid.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth auth ;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Post> postArrayList;
    private ActivityFeedBinding binding;
    PostAdapter postAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        postArrayList = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();


        binding.recyclerViewFeed.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(postArrayList);
        binding.recyclerViewFeed.setAdapter(postAdapter);
        getdata();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


         if (item.getItemId() == R.id.addPost){
            Intent intentToAddPost = new Intent(FeedActivity.this, AddPostActivity.class);
            startActivity(intentToAddPost);

        }else if (item.getItemId() == R.id.signOut){
            auth.signOut();

            Intent intentTologin = new Intent(FeedActivity.this,LoginActivity.class);
            startActivity(intentTologin);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }






    private void getdata(){

        firebaseFirestore.collection("posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){
                    Toast.makeText(FeedActivity.this,error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }

                if (value != null) {

                    for (DocumentSnapshot snapshot : value.getDocuments()){

                        Map<String,Object> data = snapshot.getData();

                        String userEmail = (String) data.get("email");
                        String comment = (String) data.get("comment");
                        String downloadUrl = (String) data.get("downloadUrl");

                        Post post = new Post(userEmail,comment,downloadUrl);
                        postArrayList.add(post);
                    }
                    postAdapter.notifyDataSetChanged();

                }
            }
        });

    }

}