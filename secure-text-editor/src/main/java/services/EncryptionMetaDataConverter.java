package services;

import DTOs.EncryptionMetadata;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ste.Encryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class EncryptionMetaDataConverter {
    private static final Logger logger = LoggerFactory.getLogger(EncryptionMetaDataConverter.class);
    public EncryptionMetadata lookUpMetaData(String id){
        String json = getMetaDataFromSystem(id);
        if(json == ""){
            return null;
        }
        return deserializeMetadata(json);
    }

    private String getMetaDataFromSystem(String uiid)  {
        String fileName = System.getProperty("user.home");
        fileName+= "\\STE\\encryption\\MetaData\\"+uiid.toString()+".json";
        Path path = Paths.get(fileName);
        try {
            return Files.readString(path);
        } catch (IOException e) {
            System.out.println("File does not exist!");
            e.printStackTrace();
        }
        return "";
    }

    public void storeMetaData(String json, UUID uiid){
        String fileName = System.getProperty("user.home");
        fileName+= "\\STE\\encryption\\MetaData\\"+uiid.toString()+".json";
        File metaData = new File(fileName);
        try {
            Files.write(metaData.toPath(), json.getBytes());
        }catch (IOException e){
            logger.error("file could not be stored!");
            e.printStackTrace();
        }
    }

    public String serializeMetadata(EncryptionMetadata metadata) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(metadata);  // Convert the metadata object to a JSON string
    }
    public EncryptionMetadata deserializeMetadata(String jsonMetadata) {
        Gson gson = new Gson();
        return gson.fromJson(jsonMetadata, EncryptionMetadata.class);
    }
}
