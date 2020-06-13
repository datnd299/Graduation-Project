package com.example.signboard2.model.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountData   {

   @SerializedName("acc")
   @Expose
   private Account acc;

   public Account getAcc() {
       return acc;
   }

   public void setAcc(Account acc) {
       this.acc = acc;
   }

}
