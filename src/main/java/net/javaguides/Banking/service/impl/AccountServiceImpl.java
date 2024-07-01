package net.javaguides.Banking.service.impl;

import net.javaguides.Banking.dto.AccountDto;
import net.javaguides.Banking.entity.Account;
import net.javaguides.Banking.mapper.AccountMapper;
import net.javaguides.Banking.repository.AccountRepository;
import net.javaguides.Banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;


    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long Id) {
        Account account = accountRepository.findById(Id)
                                           .orElseThrow(()-> new RuntimeException("Account not found"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long Id, double amount) {
        Account account = accountRepository.findById(Id)
                                           .orElseThrow(()-> new RuntimeException("Account does not exist"));

        account.setBalance(amount+account.getBalance());
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long Id, double amount) {
       Account account =  accountRepository.findById(Id)
                         .orElseThrow(()-> new RuntimeException("Account does not exist"));

       if(account.getBalance() < amount){
           throw new RuntimeException("Insufficient Balance");
       }
       account.setBalance(account.getBalance()-amount);
       Account savedAccount = accountRepository.save(account);
       return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
         List<AccountDto> accountDto = accountRepository.findAll()
                 .stream()
                 .map((accounts)->AccountMapper.mapToAccountDto(accounts))
                 .collect(Collectors.toList());

        return accountDto;
    }

    @Override
    public void deleteAccount(Long Id) {
        Account account = accountRepository.findById(Id)
                .orElseThrow( ()-> new RuntimeException("Account does not exist") );

        accountRepository.delete(account);
    }
}
