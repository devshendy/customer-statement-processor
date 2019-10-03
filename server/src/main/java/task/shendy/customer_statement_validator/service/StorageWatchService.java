//package task.shendy.customer_statement_validator.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import task.shendy.customer_statement_validator.enums.FileExtension;
//import task.shendy.customer_statement_validator.stream.ServiceEventPublisher;
//import task.shendy.customer_statement_validator.validation.ValidationBatchService;
//
//import java.io.IOException;
//import java.nio.file.*;
//
//import static task.shendy.customer_statement_validator.controller.StorageController.storagePath;
//
//@Service
//public class StorageWatchService {
//
//    //MARK: Properties
//
//    private final ServiceEventPublisher serviceEventPublisher;
//    private final ValidationBatchService validationBatchService;
//
//    //MARK: Constructors
//
//    @Autowired
//    public StorageWatchService(ServiceEventPublisher serviceEventPublisher, ValidationBatchService validationBatchService) {
//        this.serviceEventPublisher = serviceEventPublisher;
//        this.validationBatchService = validationBatchService;
//    }
//
//    //MARK: Public Methods
//
//    @Async("simpleAsyncTaskExecutor")
//    public void start() {
//        try {
//            WatchService watchService;
//            WatchKey watchKey;
//
//            watchService = FileSystems.getDefault().newWatchService();
//            Paths.get(storagePath).register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
//
//            while((watchKey = watchService.take()) != null) {
//                for(WatchEvent<?> event : watchKey.pollEvents()) {
//                    String fileName = event.context().toString();
//
//                    if (FileExtension.isFileSupported(fileName)) {
//                        switch (event.kind().name()) {
//                            case "ENTRY_CREATE":
//                                this.validationBatchService.run(fileName);
//                                break;
//                        }
//                    }
//                }
//
//                watchKey.reset();
//            }
//
//        } catch (InterruptedException | IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //MARK: Private Methods
//
////    private void emitUploadedServiceEvent(String fileName) {
////        serviceEventPublisher.emitToAll(new ServiceEvent(fileName, ServiceEventType.UPLOADED));
////    }
////
////    private void emitDeletedServiceEvent(String fileName) {
////        serviceEventPublisher.emitToAll(new ServiceEvent(fileName, ServiceEventType.DELETED));
////    }
//}
