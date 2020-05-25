package com.example.merchant.controller;

import com.example.merchant.controller.utils.ControllerUtils;
import com.example.merchant.model.Transaction;
import com.example.merchant.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/transaction")
@RestController
public class TransactionController {

    private final ControllerUtils controllerUtils;
    private final TransactionService transactionService;
//    private final StateMachineFactory<TransactionStates, TransactionEvents> stateMachineFactory;
//    private final StateMachinePersister<TransactionStates, TransactionEvents, Transaction> stateMachinePersister;

    @Autowired
    public TransactionController(ControllerUtils controllerUtils, TransactionService transactionService) {
        this.controllerUtils = controllerUtils;
        this.transactionService = transactionService;
    }

    @PostMapping("authorize")
    public ResponseEntity<Transaction> initiateTransaction(@RequestHeader(value = "Authorization") String header,
                                                           @Valid @RequestBody Transaction transaction) {
        String email = controllerUtils.extractEmailFromToken(header);
        return new ResponseEntity<>(transactionService.authorizeTransaction(transaction, email), HttpStatus.OK);
    }

    @PostMapping("charge")
    public ResponseEntity<Transaction> chargeTransaction(@Valid @RequestBody Transaction transaction) {
        return new ResponseEntity<>(transactionService.chargeTransaction(transaction), HttpStatus.OK);
    }

    @PostMapping("refund")
    public ResponseEntity<Transaction> refundTransaction(@Valid @RequestBody Transaction transaction) {
        return new ResponseEntity<>(transactionService.refundTransaction(transaction), HttpStatus.OK);
    }

    //TODO - Try using state machines
//    @PostMapping("/{id}/create/{event}")
//    public ResponseEntity<String> createEvent(@RequestHeader(value = "Authorization") String header,
//                                              @PathVariable("id") Transaction transaction,
//                                              @PathVariable("event") TransactionEvents event) throws Exception {
//        StateMachine<TransactionStates, TransactionEvents> stateMachine =
//                stateMachinePersister.restore(stateMachineFactory.getStateMachine(), transaction);
//        if (stateMachine.sendEvent(event)) {
//            stateMachinePersister.persist(stateMachine, transaction);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Wrong event", HttpStatus.BAD_REQUEST);
//        }
//    }

}
