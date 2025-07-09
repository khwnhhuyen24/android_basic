package com.example.myapplication.model;

import java.util.ArrayList;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class LoginResponse{
    public int customerId;
    public KolWalletModel kolWallet;
    public String customerLoginId;
    public String accountType;
    public int totalFollow;
    public int totalFollowing;
    public int totalReview;
    public boolean isFirstLogin;
    public String countryFlag;
    public String ranking;
    public String voucherCode;
    public String displayName;
    public String email;
    public String avatar;
    public String sex;
    public String birthday;
    public String phone;
    public String introduce;
    public boolean blockSelf;
    public String bankName;
    public String bankNumber;
    public String bankUser;
    public boolean isKolGongu365;
    public boolean allowPostReview;
    public boolean requireApproveReview;
    public ArrayList<KolSlideModel> kolSlides;
    public String lastLoginTime;
    public int numberCancelOrder;


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public KolWalletModel getKolWallet() {
        return kolWallet;
    }

    public void setKolWallet(KolWalletModel kolWallet) {
        this.kolWallet = kolWallet;
    }

    public String getCustomerLoginId() {
        return customerLoginId;
    }

    public void setCustomerLoginId(String customerLoginId) {
        this.customerLoginId = customerLoginId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getTotalFollow() {
        return totalFollow;
    }

    public void setTotalFollow(int totalFollow) {
        this.totalFollow = totalFollow;
    }

    public int getTotalFollowing() {
        return totalFollowing;
    }

    public void setTotalFollowing(int totalFollowing) {
        this.totalFollowing = totalFollowing;
    }

    public int getTotalReview() {
        return totalReview;
    }

    public void setTotalReview(int totalReview) {
        this.totalReview = totalReview;
    }

    public boolean isFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        isFirstLogin = firstLogin;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public boolean isBlockSelf() {
        return blockSelf;
    }

    public void setBlockSelf(boolean blockSelf) {
        this.blockSelf = blockSelf;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankUser() {
        return bankUser;
    }

    public void setBankUser(String bankUser) {
        this.bankUser = bankUser;
    }

    public boolean isKolGongu365() {
        return isKolGongu365;
    }

    public void setKolGongu365(boolean kolGongu365) {
        isKolGongu365 = kolGongu365;
    }

    public boolean isAllowPostReview() {
        return allowPostReview;
    }

    public void setAllowPostReview(boolean allowPostReview) {
        this.allowPostReview = allowPostReview;
    }

    public boolean isRequireApproveReview() {
        return requireApproveReview;
    }

    public void setRequireApproveReview(boolean requireApproveReview) {
        this.requireApproveReview = requireApproveReview;
    }

    public ArrayList<KolSlideModel> getKolSlides() {
        return kolSlides;
    }

    public void setKolSlides(ArrayList<KolSlideModel> kolSlides) {
        this.kolSlides = kolSlides;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getNumberCancelOrder() {
        return numberCancelOrder;
    }

    public void setNumberCancelOrder(int numberCancelOrder) {
        this.numberCancelOrder = numberCancelOrder;
    }
}



