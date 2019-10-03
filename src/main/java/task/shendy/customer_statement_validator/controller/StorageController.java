package task.shendy.customer_statement_validator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import task.shendy.customer_statement_validator.entity.BriefMetadata;
import task.shendy.customer_statement_validator.entity.HTTPResponse;
import task.shendy.customer_statement_validator.entity.Metadata;
import task.shendy.customer_statement_validator.factory.MetadataFactory;
import task.shendy.customer_statement_validator.factory.ResponseEntityFactory;
import task.shendy.customer_statement_validator.service.StorageService;
import task.shendy.customer_statement_validator.stream.SSEPublisher;
import task.shendy.customer_statement_validator.validation.ValidationBatchService;

import java.io.File;

@RestController
@RequestMapping("/api/storage")
public class StorageController {

    //MARK: Properties

    private final StorageService storageService;
    private final SSEPublisher ssePublisher;
    private final ValidationBatchService validationBatchService;

    //MARK: Static Properties

    public final static String storagePath = "/tmp/storage/";

    //MARK: Constructors

    @Autowired
    public StorageController(StorageService storageService, SSEPublisher ssePublisher,
                             ValidationBatchService validationBatchService) {
        this.storageService = storageService;
        this.ssePublisher = ssePublisher;
        this.validationBatchService = validationBatchService;
    }

    //MARK: HTTP Handler Methods

    @CrossOrigin
    @GetMapping
    public Flux<BriefMetadata> findAll() {
        return this.storageService.getAllBriefMetadata();
    }

    @CrossOrigin
    @PostMapping
    public Mono<Void> uploadFile(@RequestPart FilePart file) {
        Metadata metadata = MetadataFactory.createFromFilePart(file);
        BriefMetadata briefMetadata = MetadataFactory.createBrief(metadata);

        File customerStatementFile = new File(storagePath + metadata.getStorageFileName());
        File metadataFile = new File(storagePath + metadata.getId() + ".json");

        return file.transferTo(customerStatementFile)
                .doOnSuccess(aVoid -> this.storageService.writeMetadataToFile(metadata, metadataFile))
                .doOnSuccess(aVoid -> this.ssePublisher.emitUploadedEvent(briefMetadata))
                .doOnSuccess(aVoid -> this.validationBatchService.run(metadata.getStorageFileName()));
    }

    @CrossOrigin
    @DeleteMapping
    public Mono<ResponseEntity<HTTPResponse>> deleteFile(@RequestParam String id) {
        long deletedFiles = this.storageService.deleteById(id);

        if (deletedFiles > 0) {
            this.ssePublisher.emitDeletedEvent(id);
            return Mono.just(ResponseEntityFactory.fileDeleted());
        } else {
            return Mono.just(ResponseEntityFactory.fileNotFound());
        }
    }
}
