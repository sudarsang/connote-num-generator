package com.suda.connotenumgenerator.processor;

import com.suda.connotenumgenerator.domain.CarrierAccount;

public class ConnoteGenerator {

    public String generate(CarrierAccount carrierAccount) {
        //validate input- if the next val is greater than max, return warning
        final String consignmentIndex = calculateConsignmentIndex(carrierAccount.getLastUsedIndex() + 1, carrierAccount.getDigits());
        System.out.println("consignmentIndex = " + consignmentIndex);
        System.out.println("consignmentIndex.length() = " + consignmentIndex.length());

        return null;
    }

    String calculateConsignmentIndex(final int index, final int digits) {
        if(digits <= 0 || index <= 0) {
            System.out.println("calculateConsignmentIndex: Index and Digits cannot be <= 0");
            return null;
        }
        return String.format("%0"+ digits + "d", index);
    }
}
