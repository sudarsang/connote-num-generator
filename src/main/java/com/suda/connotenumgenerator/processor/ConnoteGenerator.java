package com.suda.connotenumgenerator.processor;

import com.suda.connotenumgenerator.domain.CarrierAccount;

/**
 * This class is used to generate Consignment Note Number for Freights using the CarrierAccount POJO
 */
public class ConnoteGenerator {
    static final int EVEN_SERIES_MULTI_FACTOR = 3;
    static final int ODD_SERIES_MULTI_FACTOR = 7;
    static final int CHECKSUM_MULTI_FACTOR = 10;

    static final String NULL_CONS_INDEX = "Provided Consignment Index is Null";
    static final String NEGATIVE_START_POS = "Provided Start position cannot be negative";

    /**
     * This method generates the next Connote Number by merging the courier Prefix, Account Number, generated consignment Index & checksum.
     * This method validates if the new index is within the provided range.
     * Since this method is dependant only on this POJO, it's possible to extend this to be used as a REST micro service/ any other required implementation.
     * Checksum is calculated in the optimum way to find the gap with next multiplication of 10.
     * @param carrierAccount The POJO which provides the prefix, Account Number, index digits, last index, range start & end values.
     * @return The next Consignment Note Number (Connote Number) generated.
     */
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

        final int checksum = (totalSum % CHECKSUM_MULTI_FACTOR ==0) ? 0: (CHECKSUM_MULTI_FACTOR - (totalSum % CHECKSUM_MULTI_FACTOR));   //This will give the gap between the sum and next multiple of 10

        return carrierAccount.getPrefix() +
                carrierAccount.getAccountNumber() +
                consignmentIndex +
                checksum;
    }

    /**
     * This is a helper method to generate the consignment Index using the provided index.
     * This method adds required 0s to the prefix to generate index with length of the digits always.
     * Eg: An index with 19456 and digits 10 will produce 0000019456, where there are five 0s added to front to make it 10 digits.
     * Note: This method is marked for package access to make sure it can be unit tested.
     * @param index The index which needs to be padded with 0s.
     * @param digits The length of the consignment index which is expected to be produced.
     * @return The consignment Index with the size of provided digit value.
     */
    String calculateConsignmentIndex(final int index, final int digits) {
        if(digits <= 0 || index <= 0) {
            System.out.println("calculateConsignmentIndex: Index and Digits cannot be <= 0");
            return null;
        }
        return String.format("%0"+ digits + "d", index);
    }

    /**
     * This is a helper method to calculate the sum of every second number in the provided consignment Index.
     * The starting position is configurable which helps to reuse this for both even & odd position sum calculation.
     * Note: This method is marked for package access to make sure it can be unit tested.
     * @param consignmentIndex The index which needs to be calculated with sum to help the checksum generation.
     * @param startingPosition The starting position from right. Pass this as 0 to find the even position elements' total.
     *                         Pass this as 1 to find the odd position elements' total.
     * @return The sum of the every second digit in the Index.
     */
    int calculateSum(final String consignmentIndex, final int startingPosition) {
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
