package com.example.merchant.service;

import com.example.merchant.model.Merchant;
import com.example.merchant.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantService {

    private final MerchantRepository merchantRepository;

    @Autowired
    public MerchantService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }


    public Optional<Merchant> findById(String id) {
        return merchantRepository.findById(id);
    }

    public Merchant save(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    public Merchant update(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    public void delete(Merchant merchant) {
        merchantRepository.delete(merchant);
    }

    public void updateMerchantAmount(String email, int amount) {
        Optional<Merchant> merchant = merchantRepository.findByEmail(email);
        merchant.ifPresent((m) -> {
            merchant.get().setTotalTransactionSum(m.getTotalTransactionSum() + amount);
            merchantRepository.save(merchant.get());
        });
    }

}
