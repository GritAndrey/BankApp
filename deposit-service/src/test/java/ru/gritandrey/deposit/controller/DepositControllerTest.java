package ru.gritandrey.deposit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.gritandrey.deposit.DepositApplication;
import ru.gritandrey.deposit.config.SpringH2DatabaseConfig;
import ru.gritandrey.deposit.dto.AccountResponseDto;
import ru.gritandrey.deposit.dto.BillResponseDto;
import ru.gritandrey.deposit.dto.DepositResponseDto;
import ru.gritandrey.deposit.entity.Deposit;
import ru.gritandrey.deposit.repository.DepositRepository;
import ru.gritandrey.deposit.rest.AccountServiceClient;
import ru.gritandrey.deposit.rest.BillServiceClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DepositApplication.class, SpringH2DatabaseConfig.class})
public class DepositControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DepositRepository depositRepository;

    @MockBean
    private BillServiceClient billServiceClient;

    @MockBean
    private AccountServiceClient accountServiceClient;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private static final String REQUEST = "{\n" +
            "    \"billId\": 1,\n" +
            "    \"amount\": 3000\n" +
            "}";
    @Test
    public void deposit() throws Exception {
        BillResponseDto billResponseDTO = createBillResponseDto();
        Mockito.when(billServiceClient.getBillById(ArgumentMatchers.anyLong())).thenReturn(billResponseDTO);
        Mockito.when(accountServiceClient.getAccountById(ArgumentMatchers.anyLong())).thenReturn(createAccountResponseDto());
        MvcResult mvcResult = mockMvc.perform(post("/deposits")
                        .content(REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk())
                .andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        List<Deposit> deposits = depositRepository.findDepositByEmail("test@test.com");
        ObjectMapper objectMapper = new ObjectMapper();
        DepositResponseDto depositResponseDto = objectMapper.readValue(body, DepositResponseDto.class);

        Assertions.assertThat(depositResponseDto.getEmail()).isEqualTo(deposits.get(0).getEmail());
        Assertions.assertThat(depositResponseDto.getAmount()).isEqualTo(BigDecimal.valueOf(3000));
    }

    private AccountResponseDto createAccountResponseDto() {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setAccountId(1L);
        accountResponseDto.setBills(Arrays.asList(1L, 2L, 3L));
        accountResponseDto.setCreationDate(LocalDate.now());
        accountResponseDto.setEmail("test@test.com");
        accountResponseDto.setName("testName");
        accountResponseDto.setPhone("+16598432");
        return accountResponseDto;
    }
    private BillResponseDto createBillResponseDto() {
        BillResponseDto billResponseDto = new BillResponseDto();
        billResponseDto.setAccountId(1L);
        billResponseDto.setAmount(BigDecimal.valueOf(1000));
        billResponseDto.setId(1L);
        billResponseDto.setCreationDate(LocalDate.now());
        billResponseDto.setIsDefault(true);
        billResponseDto.setOverdraftEnabled(true);
        return billResponseDto;
    }
}