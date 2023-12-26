package poiAPI.controller;

import org.springframework.core.io.FileSystemResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poiAPI.service.OfficeFileService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/files")
public class FilesController {
    private final OfficeFileService officeFileService;

    @GetMapping(value = "/{fileName}")
    @ResponseBody
    public ResponseEntity<FileSystemResource> getFile(@PathVariable String fileName) {
        log.info("Получен запрос на формирование файла: {}", fileName);
        FileSystemResource file = officeFileService.createOoxmlFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}
