package com.ggallici.tenpo.controllers;

import com.ggallici.tenpo.dtos.record.RecordResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecordController {
//    private final RecordService recordService;
//    private final RecordMapper recordMapper;
//
//    @GetMapping("/records")
//    public ResponseEntity<RecordResponseDto> getAll() {
//        var result = recordService.getAll();
//        var recordResponseDto = recordMapper.toRecordResponseDto(result);
//        return ResponseEntity.ok(addResponseDto);
//    }
}
