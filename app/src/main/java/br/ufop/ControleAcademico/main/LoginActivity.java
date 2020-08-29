package br.ufop.ControleAcademico.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import br.ufop.ControleAcademico.R;

public class LoginActivity extends AppCompatActivity {

    private SignInButton signInButton;
    private Button standardButton;
    private Button logoutButton;
    private GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signInButton = findViewById(R.id.sign_in_button);
        standardButton = findViewById(R.id.standard_button);
        logoutButton = findViewById(R.id.logout_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    public void updateUI(GoogleSignInAccount account){
        if(account == null) {
            signInButton.setVisibility(View.VISIBLE);
            signInButton.setSize(SignInButton.SIZE_STANDARD);
            // Find the TextView that is inside of the SignInButton and set its text
            for (int i = 0; i < signInButton.getChildCount(); i++) {
                View v = signInButton.getChildAt(i);

                if (v instanceof TextView) {
                    TextView tv = (TextView) v;
                    tv.setText("Fazer Login com conta Google");
                    return;
                }
            }
        }else{
            signInButton.setVisibility(View.GONE);
            standardButton.setText("Continuar com a conta Google");
            logoutButton.setVisibility(View.VISIBLE);
            //Intent it = new Intent(this, MainActivity.class);
            //startActivity(it);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Login Error", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    public void continuar(View view) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    public void deslogar(View view) {
        mGoogleSignInClient.signOut();
        finish();
        startActivity(getIntent());
    }
}
