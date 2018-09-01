package task.shendy.customer_statement_validator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import task.shendy.customer_statement_validator.entity.HTTPResponse;
import task.shendy.customer_statement_validator.factory.ResponseEntityFactory;
import task.shendy.customer_statement_validator.service.StorageService;

import java.util.Optional;

@RestController
@RequestMapping("/api/validation")
public class ValidationController {

    //MARK: Properties

    private final StorageService storageService;

    //MARK: Constructors

    @Autowired
    public ValidationController(StorageService storageService) {
        this.storageService = storageService;
    }

    //MARK: HTTP Handler Methods

    @CrossOrigin
    @GetMapping("{id}")
    public Mono<ResponseEntity<HTTPResponse>> getMetadataById(@PathVariable String id) {
        return Mono.just(this.storageService.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ResponseEntityFactory::metadataFileFound)
                .defaultIfEmpty(ResponseEntityFactory.fileNotFound());
    }
}
