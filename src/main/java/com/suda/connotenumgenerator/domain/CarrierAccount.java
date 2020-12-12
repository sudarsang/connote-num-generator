package com.suda.connotenumgenerator.domain;

public class CarrierAccount {
    private String prefix;
    private String accountNumber;
    private int digits;
    private int lastUsedIndex;
    private int rangeStart;
    private int rangeEnd;

    public CarrierAccount(String prefix, String accountNumber, int digits, int lastUsedIndex, int rangeStart, int rangeEnd) {
        this.prefix = prefix;
        this.accountNumber = accountNumber;
        this.digits = digits;
        this.lastUsedIndex = lastUsedIndex;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(final String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getDigits() {
        return digits;
    }

    public void setDigits(final int digits) {
        this.digits = digits;
    }

    public int getLastUsedIndex() {
        return lastUsedIndex;
    }

    public void setLastUsedIndex(final int lastUsedIndex) {
        this.lastUsedIndex = lastUsedIndex;
    }

    public int getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(final int rangeStart) {
        this.rangeStart = rangeStart;
    }

    public int getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(final int rangeEnd) {
        this.rangeEnd = rangeEnd;
    }
}
