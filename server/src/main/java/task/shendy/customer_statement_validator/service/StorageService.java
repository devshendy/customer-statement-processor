package task.shendy.customer_statement_validator.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import task.shendy.customer_statement_validator.entity.BriefMetadata;
import task.shendy.customer_statement_validator.entity.Metadata;
import task.shendy.customer_statement_validator.util.JacksonMapperUtil;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static task.shendy.customer_statement_validator.controller.StorageController.storagePath;

@Service
public class StorageService {

    //MARK: Public Methods

    public Optional<Metadata> findById(String id) {
        File metadataFile = new File(storagePath + id + ".json");
        Metadata metadata = JacksonMapperUtil.mapFileIntoMetadata(metadataFile);

        return metadata == null ? Optional.empty() : Optional.of(metadata);
    }

    public Flux<BriefMetadata> getAllBriefMetadata() {
        Collection<File> fileList = FileUtils.listFiles(new File(storagePath), new String[]{"json"}, false);

        return Flux.fromStream(fileList.stream())
                .map(JacksonMapperUtil::mapFileIntoBriefMetadata)
                .filter(Objects::nonNull);
    }

    public long deleteById(String id) {
        File[] fileList = new File(storagePath).listFiles();

        return fileList == null ? 0 : Arrays.stream(fileList)
                .filter(file -> file.getName().startsWith(id.trim()))
                .map(File::delete).count();
    }

    public void updateMetadataFile(Metadata metadata) {
        File metadataFile = new File(storagePath + metadata.getId() + ".json");
        JacksonMapperUtil.writeMetadataToFile(metadata, metadataFile);
    }

    public BriefMetadata mapFileIntoBriefMetadata(File metadataFile) {
        return JacksonMapperUtil.mapFileIntoBriefMetadata(metadataFile);
    }

    public void writeMetadataToFile(Metadata metadata, File metadataFile) {
        JacksonMapperUtil.writeMetadataToFile(metadata, metadataFile);
    }
}
