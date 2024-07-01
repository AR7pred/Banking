package net.javaguides.Banking.controller;

import net.javaguides.Banking.dto.AccountDto;
import net.javaguides.Banking.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //build create account REST API
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        AccountDto newAccountDto = accountService.createAccount(accountDto);
        return new ResponseEntity<>(newAccountDto, HttpStatus.CREATED);
    }

    //build GET account by ID REST API
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
        AccountDto accountDto = accountService.getAccountById(id);
        return new ResponseEntity<>(accountDto, HttpStatus.FOUND);
    }

    //build deposit balance of account by ID   REST API
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id,@RequestBody Map<String,Double> request){

        Double amount = request.get("amount");

        AccountDto accountDto = accountService.deposit(id,amount);
        return new ResponseEntity<>(accountDto,HttpStatus.ACCEPTED);
    }

    //build withdraw amount by ID REST API
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdrawal(@PathVariable Long id ,@RequestBody Map<String,Double> request){

        Double amount = request.get("amount");
        AccountDto accountDto = accountService.withdraw(id,amount);
        return new ResponseEntity<>(accountDto,HttpStatus.ACCEPTED);
    }

    //build GET All Account REST API
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accountDto = accountService.getAllAccounts();
        return new ResponseEntity<>(accountDto,HttpStatus.FOUND);
    }

    //build Delete account by id REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted successfully");
    }
}
