package hiccup.hiccupstore.commonutil.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileStore {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final AmazonS3Client amazonS3Client;

    public String getFullPath(String s3Category, String filename) {
//        return Paths.get("").toAbsolutePath().toString() +File.separator+ fileDir + filename;
        return amazonS3Client.getUrl(bucket, s3Category+'/'+filename).toString();
    }

    public List<UploadFile> storeFiles(String s3Category, List<MultipartFile> multipartFiles)
            throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(s3Category,multipartFile));
            }
        }

        return storeFileResult;

    }
    public UploadFile storeFile(String s3Category, MultipartFile multipartFile) throws IOException
    {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try(InputStream inputStream = multipartFile.getInputStream()){
            amazonS3Client.putObject(new PutObjectRequest(bucket,s3Category+"/"+storeFileName,inputStream,objectMetadata).
                    withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (IOException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,exception.getMessage());
        }
        return new UploadFile(originalFilename, storeFileName);
    }
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public void deleteFile(String s3Category,String fileName){
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, s3Category+"/"+fileName));
    }

}
