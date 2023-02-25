package com.example.myhandlocksstore.models;

import java.io.Serializable;

public class UpdateUserModel implements Serializable {
    int userNumber;
    String userAddress;

    public UpdateUserModel(int userNumber, String userAddress) {
        this.userNumber = userNumber;
        this.userAddress = userAddress;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}
