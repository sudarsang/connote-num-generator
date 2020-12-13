package com.suda.connotenumgenerator.processor;

import com.suda.connotenumgenerator.domain.CarrierAccount;

public class ConnoteGenerator {
    static final int EVEN_SERIES_MULTI_FACTOR = 3;
    static final int ODD_SERIES_MULTI_FACTOR = 7;
    static final int CHECKSUM_MULTI_FACTOR = 10;

    static final String NULL_CONS_INDEX = "Provided Consignment Index is Null";
    static final String NEGATIVE_START_POS = "Provided Start position cannot be negative";

    public String generate(CarrierAccount carrierAccount) {
        if(carrierAccount.getLastUsedIndex() < 0 || carrierAccount.getRangeStart() < 0 || carrierAccount.getRangeEnd() <= 0) {
            System.out.println("calculateConsignmentIndex: Index and Digits cannot be <= 0");
            return null;
        }
        if(carrierAccount.getPrefix() == null || carrierAccount.getAccountNumber() == null) {
            System.out.println("Carrier Prefix or account Number cannot be null");
            return null;
        }
        final var newIndex = carrierAccount.getLastUsedIndex() + 1;

        if(newIndex > carrierAccount.getRangeEnd()) {
            System.out.println("New Index is larger than range end, Connote number is not generated.");
            return null;
        }
        if(newIndex < carrierAccount.getRangeStart()) {
            System.out.println("New Index is less than range start, Connote number is not generated.");
            return null;
        }

        final String consignmentIndex = calculateConsignmentIndex(newIndex, carrierAccount.getDigits());
        int totalSum = (calculateSum(consignmentIndex,0)) * EVEN_SERIES_MULTI_FACTOR
                + (calculateSum(consignmentIndex, 1)) * ODD_SERIES_MULTI_FACTOR;

        final int checksum = CHECKSUM_MULTI_FACTOR - (totalSum % CHECKSUM_MULTI_FACTOR);        //TODO how this works

        return carrierAccount.getPrefix() +
                carrierAccount.getAccountNumber() +
                consignmentIndex +
                checksum;
    }

    String calculateConsignmentIndex(final int index, final int digits) {
        if(digits <= 0 || index <= 0) {
            System.out.println("calculateConsignmentIndex: Index and Digits cannot be <= 0");
            return null;
        }
        return String.format("%0"+ digits + "d", index);
    }

    int calculateSum(String consignmentIndex, int startingPosition) {
        assert consignmentIndex != null : NULL_CONS_INDEX;
        assert startingPosition >= 0 : NEGATIVE_START_POS;

        final char[] values = consignmentIndex.toCharArray();
        final var valueLength = values.length;

        if(valueLength <= 2) {
            System.out.println("ConsignmentIndex length needs to be more than 2");
            return 0;
        }

        int sum = 0;
        for (int i= valueLength -1 - startingPosition; i >= 0; i = i-2) {
            sum += Integer.parseInt(String.valueOf(values[i]));
        }

        return sum;
    }
}
