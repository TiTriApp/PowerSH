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
import akram.bensalem.powersh.utils.localization.Strings;
import timber.log.Timber;


public class Authenticate {
    private final FirebaseAuth mAuth;
    private final Activity activity;
    private final FireBaseUser user;


    public Authenticate(Activity activity) {
        this.mAuth = FirebaseAuth.getInstance();
        this.activity = activity;
        this.user = getFireBaseUser();
    }


    public FirebaseUser getUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser;
    }

    public FireBaseUser getFireBaseUser() {
        FirebaseUser user = this.mAuth.getCurrentUser();

        FireBaseUser utilisateur = null;
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                String providerId = profile.getProviderId();
                String uid = profile.getUid();
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
                utilisateur = new FireBaseUser(providerId, uid, name, email, photoUrl);
            }
        }

        return utilisateur;
    }


    public void creatNewUser(
            @Nullable String email,
            @Nullable String password,
            @Nullable String Name,
            @NotNull MutableState<Boolean> isOnProgress,
            @NotNull MutableState<Boolean> isConfirmationEmailSent,
            Strings localStrings
    ) {
        Context context = this.activity.getBaseContext();

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
                                                Timber.i("User profile updated.");
                                                user.sendEmailVerification()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    isOnProgress.setValue(false);
                                                                    isConfirmationEmailSent.setValue(true);
                                                                    Timber.i("Email sent.");
                                                                } else {
                                                                    isOnProgress.setValue(false);
                                                                    Timber.i("Email verification not sent.");
                                                                }
                                                            }
                                                        });


                                            } else {
                                                // update not sucsessfull
                                                isOnProgress.setValue(false);
                                                Timber.i("update not sucsessfull");
                                            }
                                        }
                                    });


                        } else {
                            // If sign in fails, display a message to the user.
                            isOnProgress.setValue(false);
                            Timber.i( "createUserWithEmail:failure%s", task.getException());
                            Toast.makeText(context, localStrings.getAuthenticationFailed() + "\n" + task.getException(), Toast.LENGTH_SHORT).show();


                        }
                    }
                });
    }


    public void signIn(
            @Nullable String email,
            @Nullable String password,
            @NotNull MutableState<Boolean> isOnProgress,
            @NotNull MutableState<Boolean> isLogged,
            Strings localStrings
    ) {


        Context context = this.activity.getBaseContext();
        isOnProgress.setValue(true);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this.activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Timber.i( "signInWithEmail:success");
                            isOnProgress.setValue(false);
                            isLogged.setValue(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Timber.i(  "signInWithEmail:failure%s", task.getException());
                            isOnProgress.setValue(false);
                            Toast.makeText(context, localStrings.getAuthenticationFailed() + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void signOut(@NotNull MutableState<Boolean> isLogged) {
        FirebaseAuth.getInstance().signOut();
        isLogged.setValue(false);
    }


    public void deleteUser(
            String email,
            String password
    ) {
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


    public void reinstalisationDeMotDePass(String email, Strings localStrings ) {

        Context context = this.activity.getBaseContext();
        this.mAuth.sendPasswordResetEmail(email)

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, localStrings.getSentInstructionOfPasswordReset(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, localStrings.getFailedToResetPassword(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @NotNull
    public String userName(@NotNull Strings current) {
        if (this.getFireBaseUser() == null) {
            return current.getSignIn();
        } else {
            return this.getFireBaseUser().getName();
        }
    }

    @NotNull
    public String userEmail(@NotNull Strings current) {
        if (this.getFireBaseUser() == null) {
            return current.getCreatAccount();
        } else {
            return this.getFireBaseUser().getEmail();
        }
    }
}
