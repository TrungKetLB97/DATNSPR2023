package com.app.projectfinal_master.utils;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class SignInWithGoogle {
    public GoogleSignInOptions gso;
    public GoogleSignInClient gsc;

    public void signIn(Context context) {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(context, gso);
        Intent signInIntent = gsc.getSignInIntent();
    }


}
