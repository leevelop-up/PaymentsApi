package com.example.paymentsapi.controller;

import com.example.paymentsapi.repository.Card.CardInfo;
import com.example.paymentsapi.service.ProduceKeyService;
import com.example.paymentsapi.web.dto.CommonDto;
import com.example.paymentsapi.web.dto.ResultDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/manage")
//키발급 컨트롤러 (관리자만 사용)
//발급시 회사명 기준으로 발급되며 발급시 중복체크 필수 (회사당 하나의 키만 발급)
public class ProduceKeyController {
    private final ProduceKeyService produceKeyService;


    @ApiOperation("회사 등록")
    @PostMapping(value = "/company")
    public ResponseEntity<ResultDto<Void>> ProduceCompany(String Company){

        CommonDto InsertCompany = produceKeyService.insertCompany(Company);
        //TODO: 담당자 아이디 추가

        ResultDto<Void> result  = ResultDto.in(InsertCompany.getStatus(), InsertCompany.getMessage());
        return ResponseEntity.status(InsertCompany.getHttpStatus()).body(result);
    }

    @ApiOperation("키발급 기능")
    @PostMapping(value = "/key")
    public ResponseEntity<ResultDto<Void>> ProduceKey(Integer CompanyCode){

        //회사 코드값을 받아서 함께 입력
        CommonDto InsertKey = produceKeyService.insertKey(CompanyCode);


        ResultDto<Void> result  = ResultDto.in(InsertKey.getStatus(), InsertKey.getMessage());
        return ResponseEntity.status(InsertKey.getHttpStatus()).body(result);
    }

    @ApiOperation("카드 등록")
    @PostMapping(value = "/inserCard")
    public ResponseEntity<ResultDto<Void>> RegistryCard(@RequestBody CardInfo cardRequest){

        CommonDto InsertCard = produceKeyService.insertCard(cardRequest.getCardNameKR());
        //TODO: 담당자 아이디 추가

        ResultDto<Void> result  = ResultDto.in(InsertCard.getStatus(), InsertCard.getMessage());
        return ResponseEntity.status(InsertCard.getHttpStatus()).body(result);
    }


}
