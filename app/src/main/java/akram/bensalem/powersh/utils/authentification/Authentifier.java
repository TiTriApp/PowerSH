package akram.bensalem.powersh.utils.authentification;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.compose.runtime.MutableState;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import akram.bensalem.powersh.data.model.FireBaseUser;


public class Authentifier {
    private FirebaseAuth mAuth;
    private Activity activity;
    private FireBaseUser user;



    public Authentifier(Activity activity) {
        this.mAuth = FirebaseAuth.getInstance();
        this.activity = activity;
        this.user = getFireBaseUser();
    }


    public FirebaseUser getUser(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            return currentUser;
        } else return null;
    }

    public FireBaseUser getFireBaseUser(){
        FirebaseUser user =this.mAuth.getCurrentUser();

        FireBaseUser utilisateur = null;
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                String providerId = profile.getProviderId();
                String uid = profile.getUid();
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
                utilisateur = new FireBaseUser(providerId,uid,name,email,photoUrl);
            }
        }

        return utilisateur;
    }


    public String getUserName() {
        return this.user.getName();
    }

    public String getUserEmail() {
        return this.user.getEmail();
    }

    public void  creatNewUser(
            @Nullable String email,
            @Nullable String password,
            @Nullable String Name,
            @NotNull MutableState<Boolean> isOnProgress,
            @NotNull MutableState<Boolean> isConfirmationEmailSent){
        Context  context  = this.activity.getBaseContext();

        isOnProgress.setValue(true);
        this.mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this.activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("SlimaneTag", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(Name)
                                    .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("SlimaneTag", "User profile updated.");
                                                user.sendEmailVerification()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    isOnProgress.setValue(false);
                                                                    isConfirmationEmailSent.setValue(true);
                                                                    Log.d("SlimaneTag", "Email sent.");
                                                                } else {
                                                                    isOnProgress.setValue(false);
                                                                    Log.d("SlimaneTag", "Email verification not sent.");
                                                                }
                                                            }
                                                        });


                                            } else {
                                                // update not sucsessfull
                                                isOnProgress.setValue(false);
                                                Log.d("SlimaneTag", "update not sucsessfull");
                                            }
                                        }
                                    });


                        } else {
                            // If sign in fails, display a message to the user.
                            isOnProgress.setValue(false);
                            Log.w("SlimaneTag", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed. \n " + task.getException(), Toast.LENGTH_SHORT).show();


                        }
                    }
                });
    }


    public void signIn(
            @Nullable String email,
            @Nullable String password,
            @NotNull MutableState<Boolean> isOnProgress,
            @NotNull MutableState<Boolean> isLogged){


        Context  context  = this.activity.getBaseContext();
        isOnProgress.setValue(true);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( this.activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SlimaneTag", "signInWithEmail:success");
                          //  FirebaseUser user = mAuth.getCurrentUser();

                            isOnProgress.setValue(false);
                            isLogged.setValue(true);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SlimaneTag", "signInWithEmail:failure", task.getException());
                            isOnProgress.setValue(false);
                            Toast.makeText(context, "Authentication failed." +  task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void signOut(){
        FirebaseAuth.getInstance().signOut();
    }


 public void deleteUser(
         String email ,
         String password
 ){
     final FirebaseUser user = this.mAuth.getCurrentUser();

     AuthCredential credential = EmailAuthProvider
             .getCredential(email, password);

     // Prompt the user to re-provide their sign-in credentials
     user.reauthenticate(credential)
             .addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     user.delete()
                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     if (task.isSuccessful()) {
                                         Log.d("SlimaneTag", "User account deleted.");
                                     }
                                 }
                             });

                 }
             });
 }



 public void reinstalisationDeMotDePass(String email){

     Context  context  = this.activity.getBaseContext();
     this.mAuth.sendPasswordResetEmail(email)

             .addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     if (task.isSuccessful()) {
                         Toast.makeText(context, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                     } else {
                         Toast.makeText(context, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                     }
                 }
             });
 }


}
