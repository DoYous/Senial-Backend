package com.senials.report.controller;

import com.senials.common.ResponseMessage;
import com.senials.common.TokenParser;
import com.senials.config.HttpHeadersFactory;
import com.senials.report.dto.ReportDTO;
import com.senials.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportController {

    private final TokenParser tokenParser;
    private final HttpHeadersFactory headersFactory;
    private final ReportService reportService;


    @Autowired
    public ReportController(
            TokenParser tokenParser
            , HttpHeadersFactory headersFactory
            , ReportService reportService
    ) {
        this.tokenParser = tokenParser;
        this.headersFactory = headersFactory;
        this.reportService = reportService;
    }


    @PostMapping("/reports")
    public ResponseEntity<ResponseMessage> postReport(
            @RequestHeader(name = "Authorization") String token
            , @RequestBody ReportDTO reportDTO
    ) {

        int userNumber = tokenParser.extractUserNumberFromToken(token);
        reportDTO.setReporterNumber(userNumber);

        reportService.registerReport(reportDTO);

        HttpHeaders headers = headersFactory.createJsonHeaders();
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage());
    }
}
