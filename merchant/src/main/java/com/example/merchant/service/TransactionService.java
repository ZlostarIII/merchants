package com.example.merchant.service;

import com.example.merchant.exception.AppException;
import com.example.merchant.model.MerchantStatus;
import com.example.merchant.model.Transaction;
import com.example.merchant.model.TransactionStates;
import com.example.merchant.repository.MerchantRepository;
import com.example.merchant.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final MerchantRepository merchantRepository;
    private final MerchantService merchantService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, MerchantRepository merchantRepository, MerchantService merchantService) {
        this.transactionRepository = transactionRepository;
        this.merchantRepository = merchantRepository;
        this.merchantService = merchantService;
    }

    public Optional<Transaction> findById(String id) {
        return transactionRepository.findById(id);
    }

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction update(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void delete(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    public Transaction authorizeTransaction(Transaction transaction, String email) {
        if (isMerchantActive(email)) {
            throw new AppException("Merchant is not active");
        }
        if (transaction.getAmount() <= 0) {
            transaction.setStatus(TransactionStates.REVERSED);
        }
        transaction.setMerchant(merchantRepository.findByEmail(email).get());
        return transactionRepository.save(transaction);
    }

    public Transaction chargeTransaction(Transaction transaction) {
        if (TransactionStates.ERROR.equals(transaction.getStatus())
                || TransactionStates.REVERSED.equals(transaction.getStatus())
                || TransactionStates.REFUNDED.equals(transaction.getStatus())) {
            throw new AppException("Cannot create a charge transaction");
        }
        Transaction chargeTransaction = Transaction.builder()
                .amount(transaction.getAmount())
                .customerEmail(transaction.getCustomerEmail())
                .customerPhone(transaction.getCustomerPhone())
                .status(TransactionStates.APPROVED)
                .merchant(transaction.getMerchant())
                .referenceId(transaction.getId())
                .build();
        merchantService.updateMerchantAmount(
                transaction.getMerchant().getEmail(), transaction.getAmount());
        return transactionRepository.save(chargeTransaction);
    }

    public Transaction refundTransaction(Transaction transaction) {
        if (!TransactionStates.APPROVED.equals(transaction.getStatus())) {
            throw new AppException("Cannot create a refund transaction");
        }
        transaction.setStatus(TransactionStates.REFUNDED);
        transactionRepository.save(transaction);

        Transaction refundTransaction = Transaction.builder()
                .amount(transaction.getAmount())
                .customerEmail(transaction.getCustomerEmail())
                .customerPhone(transaction.getCustomerPhone())
                .status(TransactionStates.REFUNDED)
                .merchant(transaction.getMerchant())
                .referenceId(transaction.getId())
                .build();
        merchantService.updateMerchantAmount(
                transaction.getMerchant().getEmail(), Math.negateExact(transaction.getAmount()));
        return transactionRepository.save(refundTransaction);
    }

    private boolean isMerchantActive(String email) {
        return merchantRepository.findByEmail(email).isPresent()
                && MerchantStatus.ACTIVE.equals(merchantRepository.findByEmail(email).get().getStatus());
    }
}
