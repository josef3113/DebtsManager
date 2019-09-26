package com.example.debtsmanager.controllers;

import androidx.annotation.NonNull;

import com.example.debtsmanager.interfaces.RequestListener;
import com.example.debtsmanager.models.Debt;
import com.example.debtsmanager.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirebaseController
{
    private static FirebaseController instance = null;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private User currentUser;

    private FirebaseController()
    {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public static FirebaseController getInstance() {
        if(instance ==  null)
        {
            instance = new FirebaseController();
        }

        return instance;
    }

    public void loginUser(final String userEmail, String userPassword, final RequestListener listener)
    {
        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                       if(task.isSuccessful())
                       {
                           db.collection("Users").whereEqualTo("email",userEmail)
                                   .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                               @Override
                               public void onComplete(@NonNull Task<QuerySnapshot> task)
                               {
                                   if(task.isSuccessful())
                                   {
                                       List<User> users = task.getResult().toObjects(User.class);

                                       currentUser = users.get(0);
                                       listener.onComplete(null);
                                   }
                               }
                           });

                       }else
                       {
                           listener.onError(task.getException().getMessage());
                       }
                    }
                });

    }


    public void signUpUser(final User user, final String password, final RequestListener requestListener)
    {

       db.collection("Users")
               .whereEqualTo("name", user.getName())
               .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
       {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task)
           {
               List<User> users = task.getResult().toObjects(User.class);
               if (users.isEmpty())
               {
                   mAuth.createUserWithEmailAndPassword(user.getEmail(),password)
                           .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                           {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task)
                               {
                                 if (task.isSuccessful())
                                 {
                                     db.collection("Users")
                                             .add(user)
                                             .addOnCompleteListener(new OnCompleteListener<DocumentReference>()
                                             {
                                                 @Override
                                                 public void onComplete(@NonNull Task<DocumentReference> task)
                                                 {
                                                     if(task.isSuccessful())
                                                     {
                                                         requestListener.onComplete(null);
                                                     }else
                                                     {
                                                         requestListener.onError(task.getException().getMessage());
                                                     }

                                                 }
                                             });

                                 }else
                                 {
                                     requestListener.onError(task.getException().getMessage());
                                 }
                               }
                           });

               }else
               {
                   requestListener.onError("Username already exists");
               }

           }
       });

    }

    public void debtToMe(final RequestListener requestListener)
    {
        db.collection("Debts")
                .whereEqualTo("to", currentUser.getName())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            List<Debt> debts = task.getResult().toObjects(Debt.class);
            if (!debts.isEmpty()) {
                requestListener.onComplete(debts);
            } else {
                requestListener.onError("No Data");
            }
        }
        });
    }

    public void debtToOthers(final RequestListener requestListener)
    {
        db.collection("Debts")
                .whereEqualTo("from", currentUser.getName())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Debt> debts = task.getResult().toObjects(Debt.class);
                if (!debts.isEmpty()) {
                    requestListener.onComplete(debts);
                } else {
                    requestListener.onError("No Data");
                }
            }
        });
    }

    public void addDebt(String toUser,String stringAmount,final RequestListener requestListener)
    {
        try {

            Debt debtToAdd = new Debt(currentUser.getName(), toUser, Integer.parseInt(stringAmount));

            db.collection("Debts").add(debtToAdd).addOnCompleteListener(
                    new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                requestListener.onComplete(null);
                            } else {
                                requestListener.onError(task.getException().getMessage());
                            }
                        }

                    }

            );
        }
        catch (Exception ex)
        {
            requestListener.onError(ex.getMessage());
        }
    }

}

