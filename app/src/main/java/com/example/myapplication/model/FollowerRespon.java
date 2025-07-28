package com.example.myapplication.model;

import java.util.ArrayList;
import java.util.Date;

public class FollowerRespon {
    public int customerId;
    public String displayName;
    public String email;
    public String avatar;
    public String phone;
    public String ranking;
    public String customerLoginId;
    public String accountType;
    public boolean isFirstLogin;
    public String voucherCode;
    public boolean isActive;
    public String countryFlag;
    public String followStatus;
    public int totalFollow;
    public int totalReview;
    public boolean isKolGongu365;
    public boolean allowPostReview;
    public boolean requireApproveReview;

    public String getInsertDateTime() {
        return insertDateTime;
    }

    public void setInsertDateTime(String insertDateTime) {
        this.insertDateTime = insertDateTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
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

    public boolean isFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        isFirstLogin = firstLogin;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }

    public int getTotalFollow() {
        return totalFollow;
    }

    public void setTotalFollow(int totalFollow) {
        this.totalFollow = totalFollow;
    }

    public int getTotalReview() {
        return totalReview;
    }

    public void setTotalReview(int totalReview) {
        this.totalReview = totalReview;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDefaultDeliveryAddress() {
        return defaultDeliveryAddress;
    }

    public void setDefaultDeliveryAddress(String defaultDeliveryAddress) {
        this.defaultDeliveryAddress = defaultDeliveryAddress;
    }

    public boolean isBlockSelf() {
        return blockSelf;
    }

    public void setBlockSelf(boolean blockSelf) {
        this.blockSelf = blockSelf;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String insertDateTime;
    public String type;
    public String provider;
    public String defaultDeliveryAddress;
    public boolean blockSelf;
    public String sex;
    public Date birthday;
}

