package ru.gritandrey.bill.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gritandrey.bill.dto.BillRequestDto;
import ru.gritandrey.bill.dto.BillResponseDto;
import ru.gritandrey.bill.service.BillService;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class BillController {
    private final BillService billService;

    @GetMapping("/{billId}")
    public BillResponseDto get(@PathVariable Long billId) {
        return billService.get(billId);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long create(@RequestBody BillRequestDto billRequestDto) {
        return billService.create(billRequestDto);
    }

    @PutMapping(value = "/{billId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BillResponseDto update(@RequestBody BillRequestDto billRequestDto, @PathVariable Long billId) {
        return billService.update(billId, billRequestDto);
    }
    @DeleteMapping("/{accountId}")
    public BillResponseDto delete(@PathVariable Long accountId) {
        return billService.delete(accountId);
    }
}
