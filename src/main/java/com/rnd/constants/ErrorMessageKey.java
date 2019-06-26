package com.rnd.constants;

public class ErrorMessageKey {
    private ErrorMessageKey() {}

    public static final String DONOR_MUST_BE_PRESENT = "account.donor_must_be_present";
    public static final String RECIPIENT_MUST_BE_PRESENT = "account.recipient_must_be_present";
    public static final String STEP_MUST_POSITIVE = "step.must_be_positive";
    public static final String ACCOUNT_NOT_FOUND = "account.not_found";
    public static final String OPTIMISTIC_LOCK_EXCEPTION = "operation.optimistic_lock";
}
