package ngo.sapne.intents.sapne;

/**
 * Created by dell pc on 05/10/2017.
 */
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
        import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;
CircleImageView profpic;
    //view objects,
    private TextView textViewUserEmail,name;
    private Button buttonLogout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profileuser, container, false);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            getActivity().getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.content_frame, new LoginFragment(), "LoginFragment")
                    .commit();
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) view.findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) view.findViewById(R.id.buttonLogout);
        profpic= view.findViewById(R.id.iv8);
        name= view.findViewById(R.id.name1);
        //displaying logged in user name
        textViewUserEmail.setText("Welcome "+user.getEmail());
        loadUserInfo();




        //adding listener to button
        buttonLogout.setOnClickListener(this);

        return view;
    }

    private void loadUserInfo() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null){
            if(user.getPhotoUrl()!=null){
                Glide.with(this).load(user.getPhotoUrl().toString()).into(profpic);
            }
            if(user.getDisplayName()!=null){
                name.setText(user.getDisplayName());
            }
        }
    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
        if(view == buttonLogout){
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            getActivity().getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.content_frame, new LoginFragment(), "LoginFragment")
                    .commit();
        }
    }
}