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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class FirebaseController {

    private static FirebaseController instance = null;

    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;


    private FirebaseController() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public static FirebaseController getInstance() {
        if (instance == null) {
            instance = new FirebaseController();
        }

        return instance;
    }

    public void loginUser(final String userEmail, String userPassword, final RequestListener<User> listener) {
        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            listener.onError("Email Or Password Is Blank");
            return;
        }
        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            db.collection("Users").whereEqualTo("email", userEmail)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        List<User> users = task.getResult().toObjects(User.class);

                                        listener.onComplete(users.get(0));
                                    }
                                }
                            });

                        } else {
                            listener.onError(task.getException().getMessage());
                        }
                    }
                });

    }


    public void signUpUser(final User user, final String password, final RequestListener requestListener) {
        if (user.getName().isEmpty()) {
            requestListener.onError("User Name Can't Be Empty");
            return;
        }

        if (user.getEmail().isEmpty()) {
            requestListener.onError("Email Can't Be Empty");
            return;
        }

        if (password.isEmpty()) {
            requestListener.onError("Password Can't Be Empty");
            return;
        }

        db.collection("Users")
                .whereEqualTo("name", user.getName())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<User> users = task.getResult().toObjects(User.class);
                if (users.isEmpty()) {
                    mAuth.createUserWithEmailAndPassword(user.getEmail(), password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        db.collection("Users")
                                                .add(user)
                                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                        if (task.isSuccessful()) {
                                                            requestListener.onComplete(null);
                                                        } else {
                                                            requestListener.onError(task.getException().getMessage());
                                                        }

                                                    }
                                                });

                                    } else {
                                        requestListener.onError(task.getException().getMessage());
                                    }
                                }
                            });

                } else {
                    requestListener.onError("Username already exists");
                }

            }
        });

    }

    void debtToMe(User currentUser, final RequestListener<List<Debt>> requestListener) {

        db.collection("Debts")
                .whereEqualTo("to", currentUser.getName())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots
                            , @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            requestListener.onError(e.getMessage());
                        } else {
                            List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                            List<Debt> tempList = new ArrayList<>();
                            for (DocumentSnapshot doc : documents) {
                                tempList.add(doc.toObject(Debt.class));
                            }
                            requestListener.onComplete(tempList);
                        }
                    }
                });
    }

    void debtToOthers(User currentUser, final RequestListener<List<Debt>> requestListener) {


        db.collection("Debts").whereEqualTo("from", currentUser.getName())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots
                            , @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            requestListener.onError(e.getMessage());
                        } else {
                            List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                            List<Debt> tempList = new ArrayList<>();
                            for (DocumentSnapshot doc : documents) {
                                tempList.add(doc.toObject(Debt.class));
                            }
                            requestListener.onComplete(tempList);
                        }
                    }
                });

    }

    void updateDebt(final Debt debt, final RequestListener requestListener) {
        db.collection("Debts")
                .whereEqualTo("to", debt.getTo())
                .whereEqualTo("from", debt.getFrom())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);

                db.collection("Debts").document(documentSnapshot.getId())
                        .update("amount", debt.getAmount())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    requestListener.onComplete(null);
                                } else {
                                    requestListener.onError(task.getException().getMessage());
                                }
                            }
                        });
            }
        });
    }

    void deleteDebt(final Debt debt, final RequestListener requestListener) {
        db.collection("Debts")
                .whereEqualTo("to", debt.getTo())
                .whereEqualTo("from", debt.getFrom())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);

                db.collection("Debts").document(documentSnapshot.getId()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    requestListener.onComplete(null);
                                } else {
                                    requestListener.onError(task.getException().getMessage());
                                }
                            }
                        });
            }
        });
    }

    void addDebt(Debt debt, final RequestListener requestListener) {
        db.collection("Debts")
                .add(debt).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    requestListener.onComplete(null);
                } else {
                    requestListener.onError(task.getException().getMessage());
                }
            }
        });


    }

    void getAllUsers(final RequestListener<List<User>> requestListener) {
        db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    requestListener.onError(e.getMessage());
                } else {
                    List<User> users = queryDocumentSnapshots.toObjects(User.class);
                    requestListener.onComplete(users);
                }

            }
        });

    }

    void getAllDebts(final RequestListener<List<Debt>> requestListener) {
        db.collection("Debts").addSnapshotListener(
                new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            requestListener.onError(e.getMessage());
                        } else {
                            List<Debt> allDebts = queryDocumentSnapshots.toObjects(Debt.class);
                            requestListener.onComplete(allDebts);

                        }
                    }
                }
        );
    }


    void updateUser(final User user, final RequestListener requestListener) {
        db.collection("Users").whereEqualTo("email", user.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String userId = task.getResult().getDocuments().get(0).getId();
                    db.collection("Users").document(userId)
                            .update("ismanager", user.isIsmanager())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        requestListener.onComplete(null);
                                    } else {
                                        requestListener.onError(task.getException().getMessage());
                                    }

                                }
                            });

                } else {
                    requestListener.onError(task.getException().getMessage());
                }

            }
        });

    }
}

