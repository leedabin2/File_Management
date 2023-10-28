package com.example.file_management.file.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.example.file_management.file.domain.entity.FileInfo;
import com.example.file_management.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${R2.BUCKET_NAME}")
    private String bucketName;

    private final FileRepository fileRepository;
    private final AmazonS3Client amazonS3Client;


    /***
     * 파일 업로드, DB에 파일 정보 저장 영역
     * @param recivedfile
     * @throws IOException
     */
    public void fileUpload(MultipartFile recivedfile) throws IOException{
        // 업로더가 업로드한 file 이름
        String originalFilename = recivedfile.getOriginalFilename();
//
//            // uuid 식별자 붙힌 file 이름
//            String saveFileName = createSavedFileName(originalFilename);
//
//            // file이 로컬storage에 저장되는 경로(위치) -> cloud flare로 바꿀 예정
//            String savedPath = getFullPath(saveFileName);
//
//            File file = new File(savedPath);
//            // 파라미터로 java.io.File 객체를 받아 해당 위치에 파일을 저장!!!!!
//            recivedfile.transferTo(file);

//            // 로컬storage에 저장 된 file의 정보 db에 저장
//            recodeFileInfoToDB(originalFilename, saveFileName, savedPath);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(recivedfile.getContentType());
        objectMetadata.setContentLength(recivedfile.getInputStream().available());
        try {
            System.out.println("aaa");
            amazonS3Client.putObject(bucketName, originalFilename, recivedfile.getInputStream(), objectMetadata);
            System.out.println("bbb");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /***
     * 로컬storage에 저장 된 file의 정보 db에 저장
     * @param originalFilename
     * @param savedFileName
     * @param savedPath
     */
    private void recodeFileInfoToDB(String originalFilename, String savedFileName, String savedPath){
        FileInfo fileInfo = FileInfo.builder()
                .savedFileName(savedFileName)
                .originalFileName(originalFilename)
                .savedPath(savedPath)
                .build();
        fileRepository.save(fileInfo);
    }

    /***
     * 저장소에 저장된 파일의 경로(위치) -> cloudflare 주소로 변경 예정
     * @param saveFileName
     * @return  파일 저장 경로
     */
    private String getFullPath(String saveFileName) {   // 로컬 storage에 저장된 경로
        return uploadPath + saveFileName;
    }

    /***
     * 업로드한 파일의 확장자(.py, .hwp, .png 등등) 추출 메소드
     * @param originalFilename
     * @return String 확장자
     */
    public String extractExt(String originalFilename){  // 파일 확장자 추출 메소드
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    /***
     * uuid를 이용해 파일 저장 -> 식별자
     * @param originalFilename
     * @return String 저장소에 저장될 파일명
     */
    public String createSavedFileName(String originalFilename) {    // 로컬 storage에 저장되는 savedFileName 생성 메소드
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();     // 식별자
        return uuid + "." + ext;
    }

    /***
     * DB에서 파일 경로 찾기
     */

    @Override
    public FileInfo getFile(String fileName) throws FileNotFoundException {
        return fileRepository.findById(fileName)
                .orElseThrow(() -> new FileNotFoundException("File not found"));
    }



//    /***
//     * 업로드된 파일 가져오기
//     * @param originalFileName
//     */
//    @Override
//    public FileInfo convertFiles(String originalFileName, String targetExtension) throws IOException {
//        // 1. DB에서 원본 파일 정보 조회하기
//        FileInfo originalFileInfo = fileRepository.findById(originalFileName)
//                .orElseThrow(() -> new FileNotFoundException("Original file not found"));
//
//        // 2. S3에서 원본 파일 다운로드 (originalFileInfo.getSavedPath() 사용)
//        S3Object s3Object = amazonS3Client.getObject(new GetObjectRequest(bucketName, originalFileName));
//        InputStream objectData = s3Object.getObjectContent();
//
//        // 2-1. 임시파일 생성 및 다운로드 받은 데이터 저장
//        File tempFile = File.createTempFile("temp", null);
//        FileUtils.copyInputStreamToFile(objectData, tempFile);
//
//
//        // 3. 임시파일을 대상 확장자로 변환 (Apache POI 사용)
//        // 아래의 convertUsingPOI 메소드는 Apache POI 등의 라이브러리를 이용해 파일 변환 로직을 구현해야 합니다.
//        File convertedFile = convertUsingPOI(tempFile, targetExtension);
//
//        // 4. 변환된 파일을 S3에 업로드하고 새 경로 얻기
//        String newFilePath = uploadToS3(convertedFile);
//
//        // 5. 새 FileInfo 객체 생성 및 DB에 저장
//        FileInfo convertedFileInfo = FileInfo.builder()
//                .originalFileName(originalFileInfo.getOriginalFileName())
//                .savedFileName(createSavedFileName(originalFileInfo.getOriginalFileName()))
//                .savedPath(newFilePath)
//                .build();
//
//        fileRepository.save(convertedFileInfo);
//
//        return convertedFileInfo;
//    }
}