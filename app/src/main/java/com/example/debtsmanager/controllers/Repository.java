package com.example.debtsmanager.controllers;

import com.example.debtsmanager.interfaces.DataChangeObserver;
import com.example.debtsmanager.interfaces.RequestListener;
import com.example.debtsmanager.models.Debt;
import com.example.debtsmanager.models.User;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static Repository instance = null;

    private List<Debt> debtsToMe;
    private List<Debt> debtsToOther;
    private List<User> allTheUsers;
    private List<Debt> allDebts;


    private DataChangeObserver observer;
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    private final FirebaseController firebaseController;

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    private Repository() {
        firebaseController = FirebaseController.getInstance();
    }

    private void updateData() {
        debtsToMe = new ArrayList<>();
        debtsToOther = new ArrayList<>();

        updateDebtsToMe();
        updateDebtToOther();
        updateAllUsers();
        updateAllDebts();
    }

    private void updateAllDebts() {
        firebaseController.getAllDebts(new RequestListener<List<Debt>>() {
            @Override
            public void onComplete(List<Debt> debts) {
                allDebts = debts;
                if (observer != null) {
                    observer.dataChanged();
                }
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    private void updateDebtsToMe() {
        firebaseController.debtToMe(currentUser, new RequestListener() {
            @Override
            public void onComplete(Object o) {

                debtsToMe = (List<Debt>) o;
                if (observer != null) {
                    observer.dataChanged();
                }
            }

            @Override
            public void onError(String msg) {

            }
        });
    }


    private void updateDebtToOther() {
        firebaseController.debtToOthers(currentUser, new RequestListener() {
            @Override
            public void onComplete(Object o) {
                debtsToOther = (List<Debt>) o;
                if (observer != null) {
                    observer.dataChanged();
                }
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    public void deleteDebt(Debt debt, final RequestListener listener) {
        firebaseController.deleteDebt(debt, new RequestListener() {
            @Override
            public void onComplete(Object o) {
                listener.onComplete(null);
            }

            @Override
            public void onError(String msg) {
                listener.onError("Was Error:" + msg);
            }
        });
    }


    public void addDebt(Debt debt, final RequestListener listener) {
        if (debt.getFrom().equals(debt.getTo())) {
            listener.onError("Can't To Debt To Same User");
            return;
        }
        if (debt.getAmount() == 0) {
            listener.onError("Can't Amount Be Zero");
            return;
        }

        final Debt debtToAdd = new Debt();
        Debt tempDebt = null;

        for (Debt d : allDebts) {
            if (debt.getFrom().equals(d.getTo()) && debt.getTo().equals(d.getFrom())) {
                tempDebt = d;
                break;
            }
        }
        if (tempDebt != null) {
            int newAmount = tempDebt.getAmount() - debt.getAmount();

            if (newAmount == 0) {
                firebaseController.deleteDebt(tempDebt, new RequestListener() {
                    @Override
                    public void onComplete(Object o) {
                        listener.onComplete(null);
                    }

                    @Override
                    public void onError(String msg) {
                        listener.onError("Was Error:" + msg);
                    }
                });
                return;
            }

            debtToAdd.setAmount(newAmount);
            debtToAdd.setFrom(tempDebt.getFrom());
            debtToAdd.setTo(tempDebt.getTo());

            if (newAmount < 0) {
                newAmount = debt.getAmount() - tempDebt.getAmount();
                debtToAdd.setAmount(newAmount);
                debtToAdd.setFrom(tempDebt.getTo());
                debtToAdd.setTo(tempDebt.getFrom());

                firebaseController.deleteDebt(tempDebt, new RequestListener() {
                    @Override
                    public void onComplete(Object o) {

                        firebaseController.addDebt(debtToAdd, new RequestListener() {
                            @Override
                            public void onComplete(Object o) {
                                listener.onComplete(null);
                            }

                            @Override
                            public void onError(String msg) {
                                listener.onError("Was Error:" + msg);
                            }
                        });

                    }

                    @Override
                    public void onError(String msg) {

                        listener.onError("Was Error:" + msg);
                    }
                });

            } else {
                firebaseController.updateDebt(debtToAdd, new RequestListener() {
                    @Override
                    public void onComplete(Object o) {
                        listener.onComplete(null);
                    }

                    @Override
                    public void onError(String msg) {

                        listener.onError("Was Error:" + msg);
                    }
                });

            }


        } else {
            for (Debt revD : allDebts) {
                if (debt.equals(revD)) {
                    tempDebt = revD;
                    break;
                }
            }

            if (tempDebt != null) {
                debt.setAmount(debt.getAmount() + tempDebt.getAmount());
                firebaseController.updateDebt(debt, new RequestListener() {
                    @Override
                    public void onComplete(Object o) {
                        listener.onComplete(null);
                    }

                    @Override
                    public void onError(String msg) {

                        listener.onError("Was Error:" + msg);
                    }
                });

            } else {
                firebaseController.addDebt(debt, new RequestListener() {
                    @Override
                    public void onComplete(Object o) {
                        listener.onComplete(null);
                    }

                    @Override
                    public void onError(String msg) {
                        listener.onError("Was Error:" + msg);
                    }
                });
            }
        }

    }


    public void login(String email, String password, final RequestListener listener) {
        if (email.isEmpty() || password.isEmpty()) {
            listener.onError("Email Or Password Is Blank");
        } else {
            firebaseController.loginUser(email, password, new RequestListener() {
                @Override
                public void onComplete(Object o) {
                    currentUser = (User) o;

                    updateData();

                    listener.onComplete(null);
                }

                @Override
                public void onError(String msg) {

                    listener.onError("Was Error:" + msg);
                }
            });
        }
    }


    private void updateAllUsers() {
        firebaseController.getAllUsers(new RequestListener<List<User>>() {
            @Override
            public void onComplete(List<User> users) {

                allTheUsers = users;
            }

            @Override
            public void onError(String msg) {

            }
        });

    }

    public void changeUserType(User user, RequestListener listener) {
        user.setIsmanager(!user.isIsmanager());
        firebaseController.updateUser(user, listener);
    }

    public List<Debt> getDebtsToMe() {
        return debtsToMe;
    }

    public List<Debt> getDebtsToOther() {
        return debtsToOther;
    }

    public void setObserver(DataChangeObserver observer) {
        this.observer = observer;
    }

    public List<User> getAllTheUsers() {
        return allTheUsers;
    }

    public List<Debt> getAllDebts() {
        return allDebts;
    }
}
