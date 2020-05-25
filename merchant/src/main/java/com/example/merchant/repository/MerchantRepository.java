package com.example.merchant.repository;

import com.example.merchant.model.Merchant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantRepository extends CrudRepository<Merchant, String> {

    Optional<Merchant> findByEmail(String email);

    Optional<Merchant> findByNameOrEmail(String name, String email);

}
