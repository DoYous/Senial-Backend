package com.senials.partyboardimage.controller;

import com.senials.common.ResponseMessage;
import com.senials.partyboardimage.dto.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
public class PartyBoardImageController {

    private final ResourceLoader resourceLoader;

    @Autowired
    public PartyBoardImageController(
            ResourceLoader resourceLoader
    ) {
        this.resourceLoader = resourceLoader;
    }

    // 모임 섬네일(대표) 이미지 저장
    @PostMapping("/partyboardimages")
    public ResponseEntity<ResponseMessage> uploadPartyBoardThumbnails(
            @RequestParam List<MultipartFile> multiFiles
    ) throws IOException {

        /* 파일 임시 저장 경로 지정 */
        String randomId = UUID.randomUUID().toString().replace("-", "");
        Resource resource = resourceLoader.getResource("classpath:static/img/party_board/" + randomId + "/thumbnail");
        String filePath = null;

        if (!resource.exists()) {
            String root = "src/main/resources/static/img/party_board/" + randomId + "/thumbnail";
            File file = new File(root);

            file.mkdirs();

            filePath = file.getAbsolutePath();
        } else {
            filePath = resource.getFile().getAbsolutePath();
        }

        Map<String, Object> responseMap = new HashMap<>();

        List<FileDTO> files = new ArrayList<>();
        List<String> savedFiles = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        try {
            for(MultipartFile file  : multiFiles) {
                /* 파일명 랜덤 UUID 변경 처리 */
                String originFileName = file.getOriginalFilename();
                String ext = originFileName.substring(originFileName.lastIndexOf("."));
                String savedFileName = UUID.randomUUID().toString().replace("-", "")
                        + ext;


                /* 파일 정보 등록 */
                files.add(new FileDTO(originFileName, savedFileName, filePath));

                /* 파일 저장 */
                file.transferTo(new File(filePath + "/" + savedFileName));

                savedFiles.add(savedFileName);
            }

            responseMap.put("tempNumber", randomId);
            responseMap.put("savedFiles", savedFiles);

            return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "업로드 성공", responseMap));

        } catch (Exception e) {

            for (FileDTO file : files) {
                new File(filePath + "/" + file.getSavedFileName()).delete();
            }

            return ResponseEntity.internalServerError().headers(headers).body(new ResponseMessage(500, "업로드 실패", null));
        }
    }

    // 모임 섬네일(대표) 이미지 추가
    @PutMapping("/partyboardimages/{partyBoardNumber}")
    public ResponseEntity<ResponseMessage> addPartyBoardThumbnails(
            @PathVariable int partyBoardNumber,
            @RequestParam List<MultipartFile> addedFiles
    ) throws IOException {

        String root = System.getProperty("user.dir") + "/src/main/resources/static" + "/img/party_board/" + partyBoardNumber + "/thumbnail";
        String filePath = null;
        File fileDir = new File(root);

        if (!fileDir.exists()) {

            fileDir.mkdirs();
            filePath = fileDir.getAbsolutePath();

        } else {

            filePath = fileDir.getAbsolutePath();

        }

        Map<String, Object> responseMap = new HashMap<>();

        List<FileDTO> files = new ArrayList<>();
        List<String> savedFiles = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        try {
            for(MultipartFile addedFile  : addedFiles) {

                /* 파일명 랜덤 UUID 변경 처리 */
                String originFileName = addedFile.getOriginalFilename();
                String ext = originFileName.substring(originFileName.lastIndexOf("."));
                String savedFileName = UUID.randomUUID().toString().replace("-", "")
                        + ext;

                /* 파일 정보 등록 */
                files.add(new FileDTO(originFileName, savedFileName, filePath));

                /* 파일 저장 */
                addedFile.transferTo(new File(filePath + "/" + savedFileName));

                savedFiles.add(savedFileName);
            }

            responseMap.put("savedFiles", savedFiles);

            /* created로 바꾸고 사진 출력하는 API 주소 전달하기 */
            return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "업로드 성공", responseMap));

        } catch (Exception e) {

            for (FileDTO file : files) {
                new File(filePath + "/" + file.getSavedFileName()).delete();
            }

            return ResponseEntity.internalServerError().headers(headers).body(new ResponseMessage(500, "업로드 실패", null));
        }
    }

}
