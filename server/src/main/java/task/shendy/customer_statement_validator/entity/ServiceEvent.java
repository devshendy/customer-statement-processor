//package task.shendy.customer_statement_validator.entity;
//
//import task.shendy.customer_statement_validator.enums.ServiceEventType;
//
//import java.io.File;
//
//import static task.shendy.customer_statement_validator.controller.StorageController.storagePath;
//
//public class ServiceEvent {
//
//    //MARK: Properties
//
//    private String id;
//    private ServiceEventType event;
//    private File metadataFile;
//
//    //MARK: Constructors
//
//    public ServiceEvent(String id, ServiceEventType event) {
//        this.id = id;
//        this.event = event;
//    }
//
//    public ServiceEvent(Metadata metadata, ServiceEventType event) {
//        this.id = metadata.getId();
//        this.event = event;
//        this.metadataFile = new File(storagePath + metadata.getId() + ".json");
//    }
//
//    //MARK: Getters and Setters
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public ServiceEventType getEvent() {
//        return event;
//    }
//
//    public void setEvent(ServiceEventType event) {
//        this.event = event;
//    }
//
//    public File getMetadataFile() {
//        return metadataFile;
//    }
//
//    public void setMetadataFile(File metadataFile) {
//        this.metadataFile = metadataFile;
//    }
//}
